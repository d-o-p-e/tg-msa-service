package com.tg.community.post.domain;

import com.tg.community.post.domain.dto.CommentResponseDto;
import com.tg.community.post.domain.dto.CreateCommentRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    void create(Long userId, Long postId, CreateCommentRequestDto createCommentRequestDto);

    List<CommentResponseDto> getCommentsList(Long postId);

    @Transactional
    void delete(Long userId, Long commentId);
}
