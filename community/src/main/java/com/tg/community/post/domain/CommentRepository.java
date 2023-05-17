package com.tg.community.post.domain;

import java.util.List;

public interface CommentRepository {

    Comment save(Comment build);

    int deleteByIdAndUserId(Long commentId, Long userId);

    List<Comment> findAllByPostIdFetch(Long postId);
}
