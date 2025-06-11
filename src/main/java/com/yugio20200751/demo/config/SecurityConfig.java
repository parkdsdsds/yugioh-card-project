package com.yugio20200751.demo.config;

import com.yugio20200751.demo.security.JwtAuthenticationFilter;
import com.yugio20200751.demo.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
public class SecurityConfig {

    // ① JwtTokenProvider 빈 주입
    private final JwtTokenProvider jwtTokenProvider;

    // ② 생성자 주입
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    // 1) 비밀번호 해시용 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) HTTP 보안 설정 (시큐리티 필터 체인)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // REST API니까 CSRF·세션·폼로그인·HTTP Basic 모두 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(b -> b.disable())
                .formLogin(f -> f.disable())

                // 회원가입과 로그인만 모두 허용, 나머진 인증 필요
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/members",    // 회원가입
                                "/api/auth/**",// 로그인 등 Auth
                                "/api/cards/load"// 카드 로딩 허용
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/cards",
                                "/api/cards/**", // 상세 조회도 미리 허용
                                "/cards",           // 머스터치
                                "/cards/**"        // 머스터치
                        ).permitAll()
                        .requestMatchers("/error").permitAll()     // ← 여기를 추가
                        // 그 외는 인증 필요
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
