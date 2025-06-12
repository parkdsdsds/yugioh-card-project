package com.yugio20200751.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter // ← isMine 세팅을 위해 필요
@Builder
public class CommentResponse {

    private Long id;
    private String username;      // 작성자 이름
    private String content;
    private LocalDateTime createdAt;

    private boolean isMine;       // 본인 댓글 여부
    // builder().isMine(true/false) ... 로 사용
}
