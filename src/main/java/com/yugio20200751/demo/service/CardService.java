package com.yugio20200751.demo.service;

import com.yugio20200751.demo.domain.Card;
import com.yugio20200751.demo.dto.CardWithRatingDto;
import com.yugio20200751.demo.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public List<CardWithRatingDto> getAllCardsWithRatings() {
        return cardRepository.findAllWithAverageRating();
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    public void saveAllCards(List<Card> cards) {
        cardRepository.saveAll(cards);
    }
    // 이름으로 카드를 검색하는 서비스 메소드
    public List<Card> searchCardsByName(String name) {
        return cardRepository.findByNameContaining(name);
    }
}
