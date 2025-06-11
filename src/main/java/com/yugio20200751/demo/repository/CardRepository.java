package com.yugio20200751.demo.repository;

import com.yugio20200751.demo.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    // 필요하면 여기서 커스텀 쿼리 메서드 추가 가능
}
