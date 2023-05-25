package com.tg.community.post.application;

import com.tg.community.post.domain.Comment;
import com.tg.community.post.domain.CommentRepository;
import com.tg.community.post.domain.CommentService;
import com.tg.community.post.domain.Post;
import com.tg.community.post.domain.PostRepository;
import com.tg.community.post.domain.dto.CommentResponseDto;
import com.tg.community.post.domain.dto.CreateCommentRequestDto;
import com.tg.community.user.domain.User;
import com.tg.community.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void create(Long userId, Long postId, CreateCommentRequestDto createCommentRequestDto) {
        User user = userRepository.getReferenceById(userId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        commentRepository.save(
                Comment.builder()
                        .user(user)
                        .post(post)
                        .content(createCommentRequestDto.getContent())
                        .build()
        );
    }

    @Override
    public List<CommentResponseDto> getCommentsList(Long postId) {
        List<Comment> commentList = commentRepository.findAllByPostIdFetch(postId);
        return commentList.stream().map(comment -> CommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .userId(comment.getUser().getId())
                .nickName(comment.getUser().getNickname())
                .profileImageUrl(comment.getUser().getProfileImageUrl())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long userId, Long commentId) {
        int result = commentRepository.deleteByIdAndUserId(commentId, userId);
        if (result == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
