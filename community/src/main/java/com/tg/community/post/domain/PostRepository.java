package com.tg.community.post.domain;

import com.tg.community.post.domain.dto.FeedResponseDto;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post user);

    Optional<Post> findById(Long postId);

    void delete(Post post);

    Post getReferenceById(Long postId);

    List<FeedResponseDto> getFeed(FeedOption feedOption);
}
