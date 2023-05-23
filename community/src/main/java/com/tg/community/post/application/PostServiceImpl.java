package com.tg.community.post.application;

import com.tg.community.post.domain.FeedOption;
import com.tg.community.post.domain.MediaService;
import com.tg.community.post.domain.Post;
import com.tg.community.post.domain.PostLike;
import com.tg.community.post.domain.PostLikeRepository;
import com.tg.community.post.domain.PostRepository;
import com.tg.community.post.domain.PostService;
import com.tg.community.post.domain.dto.CreatePostEventDto;
import com.tg.community.post.domain.dto.CreatePostRequestDto;
import com.tg.community.post.domain.dto.FeedResponseDto;
import com.tg.community.post.infra.PostCreateKafkaProducerEvent;
import com.tg.community.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final MediaService mediaService;
    private final PostRepository postRepository;
    private final PostCreateKafkaProducerEvent postCreateKafkaProducerEvent;

    @Value("${aws.s3.directory.image}")
    private String s3ImageDirectory;

    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void create(Long userId, CreatePostRequestDto createPostRequestDto) {
        String fileName = mediaService.uploadFile(createPostRequestDto.getImage(), s3ImageDirectory);
        postRepository.save(
                Post.builder()
                    .user(userRepository.getReferenceById(userId))
                    .content(createPostRequestDto.getContent())
                    .category(createPostRequestDto.getCategory())
                    .imageUrl(fileName)
                    .build()
        );
        postCreateKafkaProducerEvent.sendMessage(new CreatePostEventDto(userId));
    }

    @Transactional(readOnly = true)
    public List<FeedResponseDto> getFeed(Long userId, FeedOption feedOption) {
        List<FeedResponseDto> feed = postRepository.getFeed(feedOption);
        if (userId != null) {
            for (FeedResponseDto dto : feed) {
                dto.setIsLiked(postLikeRepository.existsByUserIdAndPostId(userId, dto.getPostId()));
                dto.setIsMyPost(dto.getUserId().equals(userId));
            }
        }
        return feed;
    }

    @Transactional
    public void delete(Long userId, Long targetPostId) {
        Post post = postRepository.findById(targetPostId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!post.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        String imageUrl = post.getImageUrl();
        postRepository.delete(post);
        mediaService.deleteFile(s3ImageDirectory + imageUrl);
    }

    public void like(Long userId, Long postId) {
        try {
            postLikeRepository.save(
                    PostLike.builder()
                            .user(userRepository.getReferenceById(userId))
                            .post(postRepository.getReferenceById(postId))
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 게시글입니다.");
        }
    }

    public void unlike(Long userId, Long postId) {
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
