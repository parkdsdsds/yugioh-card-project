package com.yugio20200751.demo.service;

import com.yugio20200751.demo.domain.Card;
import com.yugio20200751.demo.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

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
}
