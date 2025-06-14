package com.yugio20200751.demo.repository;

import com.yugio20200751.demo.domain.Card;
import com.yugio20200751.demo.dto.CardWithRatingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    // 필요하면 여기서 커스텀 쿼리 메서드 추가 가능
    @Query("SELECT new com.yugio20200751.demo.dto.CardWithRatingDto(c, AVG(r.score)) " +
            "FROM Card c LEFT JOIN Rating r ON c.id = r.card.id " +
            "GROUP BY c.id")
    List<CardWithRatingDto> findAllWithAverageRating();

    // 이름으로 검색하는 메소드
    List<Card> findByNameContaining(String name);
}
