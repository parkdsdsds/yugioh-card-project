package com.yugio20200751.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 한 명의 유저는 한 카드에 대해 한 번만 평점을 매길 수 있도록 유니크 제약조건 설정
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "card_id"})
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int score; // 1점부터 5점까지의 평점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;
}