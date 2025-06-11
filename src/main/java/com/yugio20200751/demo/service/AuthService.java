package com.yugio20200751.demo.service;

import com.yugio20200751.demo.domain.Member;
import com.yugio20200751.demo.dto.SignupRequest;
import com.yugio20200751.demo.repository.MemberRepository;
import com.yugio20200751.demo.security.JwtTokenProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final MemberRepository memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;

    public AuthService(MemberRepository memberRepo,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtProvider) {
        this.memberRepo = memberRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public String login(String username, String rawPassword) {
        Member member = memberRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return jwtProvider.createToken(username, authorities);
    }

    public String signup(SignupRequest request) {
        if (memberRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        Member newMember = new Member();
        newMember.setUsername(request.getUsername());
        newMember.setPassword(passwordEncoder.encode(request.getPassword()));
        newMember.setEmail(request.getEmail());

        memberRepo.save(newMember);

        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return jwtProvider.createToken(request.getUsername(), authorities);
    }
}
