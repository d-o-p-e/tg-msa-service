package com.tg.community.post.domain;

import com.tg.community.post.domain.dto.CreatePostRequestDto;
import com.tg.community.post.domain.dto.FeedResponseDto;

import java.util.List;

public interface PostService {
    List<FeedResponseDto> getFeed(Long userId, FeedOption feedOption);

    void create(Long userId, CreatePostRequestDto createPostRequestDto);

    void delete(Long userId, Long postId);

    void like(Long userId, Long postId);

    void unlike(Long userId, Long postId);
}
