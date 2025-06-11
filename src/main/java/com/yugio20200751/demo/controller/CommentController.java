package com.yugio20200751.demo.controller;

import com.yugio20200751.demo.dto.CommentRequest;
import com.yugio20200751.demo.dto.CommentResponse;
import com.yugio20200751.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards/{cardId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<Void> addComment(@PathVariable Long cardId,
                                           @RequestBody CommentRequest request,
                                           Authentication authentication) {
        String username = authentication.getName();
        commentService.addComment(cardId, username, request);
        return ResponseEntity.ok().build();
    }

    // 댓글 조회 (비회원도 가능)
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long cardId) {
        List<CommentResponse> comments = commentService.getComments(cardId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 삭제 (작성자 본인만)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long cardId,
                                              @PathVariable Long commentId,
                                              Authentication authentication) {
        String username = authentication.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.ok().build();
    }
}
