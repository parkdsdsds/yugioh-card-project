package com.yugio20200751.demo.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {

    private Long id;
    private String username;      // 작성자 이름
    private String content;
    private LocalDateTime createdAt;
}
