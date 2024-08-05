package com.sociallogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers("/users/login", "/users/signup", "/main").permitAll()
                            .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login ->
                    oauth2Login
                            .loginPage("/users/login")
                            .defaultSuccessUrl("/main", true)
                            .failureUrl("/login?error=true")
            )
            .logout(logout -> logout
                    .logoutUrl("/logout") // 로그아웃 URL 설정
                    .logoutSuccessUrl("/users/login") // 로그아웃 성공 후 리디렉션 URL 설정
                    .invalidateHttpSession(true) // 세션 무효화
                    .deleteCookies("JSESSIONID") // 쿠키 삭제
            );
        return http.build();
    }
}
