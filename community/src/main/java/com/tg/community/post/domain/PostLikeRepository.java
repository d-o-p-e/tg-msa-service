package com.tg.community.post.domain;

public interface PostLikeRepository {

    Boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);

    PostLike save(PostLike build);
}
