package com.yugio20200751.demo.repository;

import com.yugio20200751.demo.domain.Card;
import com.yugio20200751.demo.domain.Member;
import com.yugio20200751.demo.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.card.id = :cardId")
    Optional<Double> findAverageScoreByCardId(@Param("cardId") Long cardId);

    // 특정 회원과 특정 카드에 대한 평점을 찾는 메소드 추가
    Optional<Rating> findByMemberAndCard(Member member, Card card);
}