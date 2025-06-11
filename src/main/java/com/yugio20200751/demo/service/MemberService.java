package com.yugio20200751.demo.service;

import com.yugio20200751.demo.domain.Member;
import com.yugio20200751.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository,
    PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 저장 (회원가입)
    public Member save(Member member) {
        String raw = member.getPassword();
        member.setPassword(passwordEncoder.encode(raw));
        return memberRepository.save(member);
    }

    // 전체 회원 목록 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 사용자 이름으로 조회
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    // ID로 삭제
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
