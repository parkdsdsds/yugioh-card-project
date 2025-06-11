package com.yugio20200751.demo.controller;

import com.yugio20200751.demo.domain.Member;
import com.yugio20200751.demo.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 저장 (POST)
    @PostMapping
    public Member save(@RequestBody Member member) {
        return memberService.save(member);
    }

    // 전체 회원 조회 (GET)
    @GetMapping
    public List<Member> findAll() {
        return memberService.findAll();
    }

    // 사용자 이름으로 조회 (GET)
    @GetMapping("/{username}")
    public Member findByUsername(@PathVariable String username) {
        return memberService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
    }

    // ID로 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
    }
}
