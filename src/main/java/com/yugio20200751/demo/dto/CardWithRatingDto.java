package com.yugio20200751.demo.dto;

import com.yugio20200751.demo.domain.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardWithRatingDto {

    private Card card;
    private Double averageRating;

    public CardWithRatingDto(Card card, Double averageRating) {
        this.card = card;
        // 평균 평점이 null일 경우(평점이 하나도 없는 카드) 0.0으로 처리합니다.
        this.averageRating = (averageRating == null) ? 0.0 : averageRating;
    }
}