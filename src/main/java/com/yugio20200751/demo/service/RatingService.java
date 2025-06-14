package com.yugio20200751.demo.service;

import com.yugio20200751.demo.domain.Card;
import com.yugio20200751.demo.domain.Member;
import com.yugio20200751.demo.domain.Rating;
import com.yugio20200751.demo.repository.CardRepository;
import com.yugio20200751.demo.repository.MemberRepository;
import com.yugio20200751.demo.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional; // Optional 임포트 추가

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MemberRepository memberRepository;
    private final CardRepository cardRepository;

    // 평점 등록 또는 업데이트 (로그인 사용자만)
    @Transactional
    public void addOrUpdateRating(Long cardId, String username, int score) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다."));

        // 사용자가 이 카드에 매긴 기존 평점이 있는지 확인
        Optional<Rating> existingRating = ratingRepository.findByMemberAndCard(member, card);

        if (existingRating.isPresent()) {
            // 이미 평점이 있으면 점수만 업데이트
            Rating rating = existingRating.get();
            rating.setScore(score);
            ratingRepository.save(rating);
        } else {
            // 평점이 없으면 새로 생성
            Rating newRating = Rating.builder()
                    .member(member)
                    .card(card)
                    .score(score)
                    .build();
            ratingRepository.save(newRating);
        }
    }

    // 평점 평균 조회 (모두 허용)
    public double getAverageRating(Long cardId) {
        return ratingRepository.findAverageScoreByCardId(cardId).orElse(0.0);
    }
}