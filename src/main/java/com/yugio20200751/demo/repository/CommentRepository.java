package com.yugio20200751.demo.repository;

import com.yugio20200751.demo.domain.Comment;
import com.yugio20200751.demo.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 카드에 달린 댓글 모두 조회
    List<Comment> findByCardOrderByCreatedAtDesc(Card card);
}
