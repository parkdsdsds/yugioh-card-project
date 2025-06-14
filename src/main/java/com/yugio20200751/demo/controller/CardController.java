package com.yugio20200751.demo.controller;

import com.yugio20200751.demo.domain.Card;
import com.yugio20200751.demo.service.CardDataLoader;
import com.yugio20200751.demo.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CardDataLoader cardDataLoader;

    // 카드 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }

    // 카드 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        return cardService.getCardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/load")
    public ResponseEntity<String> loadCards() {
        cardDataLoader.loadAndSaveCards();
        return ResponseEntity.ok("카드 데이터 로딩 완료");


    }
    // 이름으로 카드를 검색하는 API 엔드포인트
    @GetMapping("/search")
    public ResponseEntity<List<Card>> searchCardsByName(@RequestParam String name) {
        List<Card> cards = cardService.searchCardsByName(name);
        return ResponseEntity.ok(cards);
    }

}
