package com.yugio20200751.demo.config;

import com.yugio20200751.demo.security.JwtAuthenticationFilter;
import com.yugio20200751.demo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .httpBasic(b -> b.disable())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/cards")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/cards")
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        // ▼▼▼ 여기에 정적 리소스 허용 규칙 추가 ▼▼▼
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // 회원가입/로그인(POST)은 모두 허용
                        .requestMatchers(HttpMethod.POST, "/api/members", "/api/auth/**").permitAll()
                        // 카드 목록, 카드 상세조회(GET)만 모두 허용
                        .requestMatchers(HttpMethod.GET, "/api/cards", "/api/cards/**", "/cards", "/cards/**").permitAll()
                        // 평점 평균 조회 API(GET) 모두 허용
                        .requestMatchers(HttpMethod.GET, "/api/cards/*/ratings/average").permitAll()
                        // 로그인/에러 페이지는 모두 허용
                        .requestMatchers("/login", "/error","/signup").permitAll()
                        // 그 외 모든 요청은 인증 필요!
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}