package com.tg.community.post.controller;

import com.tg.community.auth.Auth;
import com.tg.community.auth.UserContext;
import com.tg.community.auth.domain.SessionUserVo;
import com.tg.community.post.domain.CommentService;
import com.tg.community.post.domain.dto.CommentResponseDto;
import com.tg.community.post.domain.dto.CreateCommentRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "COMMENT", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/{postId}/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComment(@PathVariable Long postId) {
        return ResponseEntity.ok().body(commentService.getCommentsList(postId));
    }

    @Operation(summary = "댓글 작성", description = "댓글을 작성합니다.")
    @Auth
    @PostMapping
    public ResponseEntity<Void> createComment(@PathVariable Long postId, CreateCommentRequestDto createCommentRequestDto) {
        SessionUserVo sessionUserVo = UserContext.getContext();
        commentService.create(sessionUserVo.getId(), postId, createCommentRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @Auth
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @PathVariable String postId) {
        SessionUserVo sessionUserVo = UserContext.getContext();
        commentService.delete(sessionUserVo.getId(), commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
