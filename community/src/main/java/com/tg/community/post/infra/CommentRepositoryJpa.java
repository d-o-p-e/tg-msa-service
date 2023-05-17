package com.tg.community.post.infra;

import com.tg.community.post.domain.Comment;
import com.tg.community.post.domain.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepositoryJpa extends JpaRepository<Comment, Long>, CommentRepository {

    Comment save(Comment build);

    @Query("select c from Comment c join c.user where c.post.id = :postId")
    List<Comment> findAllByPostIdFetch(Long postId);

    int deleteByIdAndUserId(Long commentId, Long userId);
}
