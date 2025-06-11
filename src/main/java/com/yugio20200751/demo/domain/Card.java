package com.yugio20200751.demo.domain;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "card")
public class Card {
    @PostConstruct
    public void testLog() {
        System.out.println(" Card 엔티티 로딩됨");
    }

    @Id
    private Long id;
    private String name;
    private String type;
    @Column(name = "card_desc", columnDefinition = "TEXT")
    private String desc;
    private Integer atk;
    private Integer def;
    private Integer level;
    private String race;
    private String attribute;
    private String imageUrl;
}
