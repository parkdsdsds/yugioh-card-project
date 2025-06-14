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
        return "redirect:/cards/" + cardId;
    }

    // 댓글 조회 (이 메소드는 그대로 둡니다)
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long cardId, Authentication authentication) {
        String loginUsername = (authentication != null) ? authentication.getName() : null;
        List<CommentResponse> comments = commentService.getComments(cardId, loginUsername);
        return ResponseEntity.ok(comments);
    }

    // 댓글 삭제 (Form 전송을 위해 POST 방식으로 변경하고 리다이렉트)
    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long cardId,
                                @PathVariable Long commentId,
                                Authentication authentication) {
        String username = authentication.getName();
        commentService.deleteComment(commentId, username);
        return "redirect:/cards/" + cardId;
    }

    // 댓글 수정 (Form 전송을 위해 POST 방식으로 변경하고 리다이렉트)
    @PostMapping("/{commentId}/update")
    public String updateComment(@PathVariable Long cardId,
                                @PathVariable Long commentId,
                                @RequestParam String content, // RequestBody 대신 RequestParam 사용
                                Authentication authentication) {
        String username = authentication.getName();
        commentService.updateComment(commentId, username, content);
        return "redirect:/cards/" + cardId;
    }
}