package com.yugio20200751.demo.service;

import com.yugio20200751.demo.domain.Card;
import com.yugio20200751.demo.domain.Comment;
import com.yugio20200751.demo.domain.Member;
import com.yugio20200751.demo.dto.CommentRequest;
import com.yugio20200751.demo.dto.CommentResponse;
import com.yugio20200751.demo.repository.CardRepository;
import com.yugio20200751.demo.repository.CommentRepository;
import com.yugio20200751.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final CardRepository cardRepository;

    @Transactional
    public void addComment(Long cardId, String username, CommentRequest request) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("카드가 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .member(member)
                .card(card)
                .build();

        commentRepository.save(comment);
    }

    public List<CommentResponse> getComments(Long cardId,String loginUsername) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("카드가 존재하지 않습니다."));

        return commentRepository.findByCardOrderByCreatedAtDesc(card)
                .stream()
                .map(c -> CommentResponse.builder()
                        .id(c.getId())
                        .username(c.getMember().getUsername())
                        .content(c.getContent())
                        .createdAt(c.getCreatedAt())
                        .isMine(loginUsername != null && loginUsername.equals(c.getMember().getUsername())) // 본인 댓글 체크!
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        if (!comment.getMember().getUsername().equals(username)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }


    @Transactional
    public void updateComment(Long commentId, String username, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        if (!comment.getMember().getUsername().equals(username)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        comment.setContent(newContent); // JPA dirty checking
    }


}
