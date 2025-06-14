package com.yugio20200751.demo.controller;

import com.yugio20200751.demo.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/cards/{cardId}/ratings")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public String addRating(@PathVariable Long cardId,
                            @RequestParam int score,
                            Authentication authentication) {
        String username = authentication.getName();
        ratingService.addOrUpdateRating(cardId, username, score); // 업데이트 로직으로 변경
        return "redirect:/cards/" + cardId;
    }

    @GetMapping("/average")
    @ResponseBody
    public ResponseEntity<?> getAverageRating(@PathVariable Long cardId) {
        double averageScore = ratingService.getAverageRating(cardId);
        return ResponseEntity.ok(Map.of("averageScore", averageScore));
    }
}