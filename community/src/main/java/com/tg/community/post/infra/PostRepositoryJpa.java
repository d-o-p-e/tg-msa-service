package com.tg.community.post.infra;

import com.tg.community.post.domain.FeedOption;
import com.tg.community.post.domain.Post;
import com.tg.community.post.domain.PostRepository;
import com.tg.community.post.domain.dto.CreatePostRequestDto;
import com.tg.community.post.domain.dto.FeedResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryJpa extends JpaRepository<Post, Long>, PostRepository, PostRepositoryCustom {

    Post save(Post user);

    Optional<Post> findById(Long postId);

    void delete(Post post);

    Post getReferenceById(Long postId);

    List<FeedResponseDto> getFeed(FeedOption feedOption);
}
