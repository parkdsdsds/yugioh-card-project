package com.yugio20200751.demo.controller;

import com.yugio20200751.demo.dto.CommentRequest;
import com.yugio20200751.demo.dto.CommentResponse;
import com.yugio20200751.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/cards/{cardId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping
    public String addComment(@PathVariable Long cardId,
                             @ModelAttribute CommentRequest request,
                             Authentication authentication) {

        String username = authentication.getName();
        commentService.addComment(cardId, username, request);

        // 댓글 작성 후 카드 상세 페이지로 리다이렉트
        return "redirect:/cards/" + cardId;
    }

    // 댓글 조회 (비회원도 가능) ← **이 함수만 남기세요!**
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long cardId, Authentication authentication) {
        String loginUsername = (authentication != null) ? authentication.getName() : null;
        List<CommentResponse> comments = commentService.getComments(cardId, loginUsername);
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

    // 댓글 수정(작성자만)
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long cardId,
                                              @PathVariable Long commentId,
                                              @RequestBody CommentRequest request,
                                              Authentication authentication) {
        String username = authentication.getName();
        commentService.updateComment(commentId, username, request.getContent());
        return ResponseEntity.ok().build();
    }
}
