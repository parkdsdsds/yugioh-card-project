package com.yugio20200751.demo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yugio20200751.demo.domain.Card;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardDataLoader {

    private final CardService cardService;

    private static final String API_URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php";

    public void loadAndSaveCards() {
        RestTemplate restTemplate = new RestTemplate();
        CardApiResponse response = restTemplate.getForObject(API_URL, CardApiResponse.class);

        if (response != null && response.data != null) {
            List<Card> cards = new ArrayList<>();

            for (CardDto dto : response.data) {
                if (dto.cardImages != null && !dto.cardImages.isEmpty()) {
                    cards.add(Card.builder()
                            .id(dto.id)
                            .name(dto.name)
                            .type(dto.type)
                            .desc(dto.desc)
                            .atk(dto.atk)
                            .def(dto.def)
                            .level(dto.level)
                            .race(dto.race)
                            .attribute(dto.attribute)
                            .imageUrl(dto.cardImages.get(0).imageUrl)
                            .build());
                }
            }

            cardService.saveAllCards(cards);
            log.info("총 {}장의 카드 저장 완료", cards.size());
        }
    }

    // 내부 DTO 클래스들 (응답 매핑용)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CardApiResponse {
        public List<CardDto> data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CardDto {
        public Long id;
        public String name;
        public String type;
        public String desc;
        public Integer atk;
        public Integer def;
        public Integer level;
        public String race;
        public String attribute;

        @JsonProperty("card_images")
        public List<CardImageDto> cardImages;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CardImageDto {
        @JsonProperty("image_url")
        public String imageUrl;
    }
}
