package com.yugio20200751.demo.controller;

import com.yugio20200751.demo.domain.Card;
import com.yugio20200751.demo.dto.CardWithRatingDto;
import com.yugio20200751.demo.dto.CommentResponse;
import com.yugio20200751.demo.service.CardService;
import com.yugio20200751.demo.service.CommentService;
import com.yugio20200751.demo.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CardPageController {

    private final CardService cardService;
    private final CommentService commentService;
    private final RatingService ratingService;

    // 이 메소드를 수정합니다.
    @GetMapping("/cards")
    public String viewCards(Model model, Authentication authentication) {
        // 평점이 포함된 DTO 리스트를 조회합니다.
        List<CardWithRatingDto> cardsWithRatings = cardService.getAllCardsWithRatings();
        model.addAttribute("cards", cardsWithRatings);

        boolean isLoginUser = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isAuthenticated", isLoginUser);

        return "cards";
    }

    @GetMapping("/cards/{id}")
    public String viewCardDetail(@PathVariable Long id,
                                 Model model,
                                 Authentication authentication) {
        Card card = cardService.getCardById(id)
                .orElseThrow(() -> new RuntimeException("카드를 찾을 수 없습니다."));

        String loginUsername = (authentication != null) ? authentication.getName() : null;
        List<CommentResponse> comments = commentService.getComments(id, loginUsername);
        double averageScore = ratingService.getAverageRating(id);

        model.addAttribute("cardId", card.getId());
        model.addAttribute("name", card.getName());

        String attribute = (card.getAttribute() != null && !card.getAttribute().isBlank())
                ? card.getAttribute() : "정보 없음";
        model.addAttribute("attribute", attribute);

        model.addAttribute("type", card.getType());
        String level = (card.getLevel() != null) ? String.valueOf(card.getLevel()) : "정보 없음";
        model.addAttribute("level", level);
        model.addAttribute("desc", card.getDesc());
        model.addAttribute("imageUrl", card.getImageUrl());
        model.addAttribute("comments", comments);
        model.addAttribute("averageScore", String.format("%.1f", averageScore));

        boolean isLoginUser = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isAuthenticated", isLoginUser);

        return "cardDetail";
    }
}