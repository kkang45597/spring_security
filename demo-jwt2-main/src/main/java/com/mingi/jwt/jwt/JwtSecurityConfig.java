package com.mingi.jwt.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// 이 코드는 Spring Security 설정에 커스텀 JWT 인증 필터인 JwtFilter를 추가하는 역할을 한다.
// 이 필터를 통해 Spring Security는 요청이 들어올 때마다 JWT 토큰을 검사하고, 유효한 경우 인증을 설정한다.

// 이를 통해 클라이언트가 Authorization 헤더에 유효한 JWT 토큰을 담아 요청하면, JwtFilter가 토큰을 검증하고 인증을 수행하므로 
// 별도의 세션이나 쿠키 없이도 요청이 인증된 사용자로서 처리될 수 있다.

@RequiredArgsConstructor
public class JwtSecurityConfig extends 
	SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http) {
    	
    	http.addFilterBefore(
                new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
