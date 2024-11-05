package com.mingi.jwt.service;

import java.time.LocalDateTime;
import java.util.Optional;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.io.Decoders;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.mingi.jwt.controller.RefreshTokenRequest;
import com.mingi.jwt.controller.TokenResponse;
import com.mingi.jwt.dto.LoginDto;
import com.mingi.jwt.dto.TokenDto;
import com.mingi.jwt.entities.RefreshToken;
import com.mingi.jwt.jwt.JwtFilter;
import com.mingi.jwt.jwt.TokenProvider;
import com.mingi.jwt.repository.RefreshTokenRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
	
	private final TokenProvider tokenProvider;
    @Autowired
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;	
	
	public Optional<TokenResponse> makeTokens(LoginDto loginDto) {
		log.info("makeTokens");
    	
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("username=" + authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createToken(authentication, true);
        String refreshToken = tokenProvider.createAndPersistRefreshTokenForUser(authentication);
        
        TokenResponse tokenResponse = new TokenResponse(accessToken,
        		refreshToken);
        
        Optional<TokenResponse> optTokenResponse = 
        		Optional.ofNullable(tokenResponse);        
        
//        SecurityContextHolder.clearContext(); // 토큰 발급후 인증 정보를 저장할 필요가 없기에 삭제가 목적이지만 이게 맞나? (아닌가?)
//        log.info("delete SecurityContext"); //
        
        
        return optTokenResponse;        
	}
	
	@Transactional
	public Optional<TokenDto> makeNewAccessToken(RefreshTokenRequest refreshTokenRequest,
    		Authentication authentication) {
		String refreshTokenValue = refreshTokenRequest.getRefreshToken();		
    	
    	log.info("refreshToken from user. token value=" + refreshTokenValue);
    	
        RefreshToken validRefreshToken = 
        		refreshTokenRepository.findById(refreshTokenValue)
                .orElseThrow(() -> new IllegalStateException("Invalid refresh token"));

        TokenDto tokenDto = null;
        Optional<TokenDto> optTokenDto = null;
        if (isTokenExpired(validRefreshToken)) {
            refreshTokenRepository.delete(validRefreshToken);
            optTokenDto = Optional.ofNullable(tokenDto);
            return optTokenDto;
        }
        
        log.info("refreshToken from database. token value=" + validRefreshToken.getToken());
        
        String accessToken = tokenProvider.createToken(authentication, true);
        
        tokenDto = new TokenDto(accessToken);
        optTokenDto = Optional.ofNullable(tokenDto);
        return optTokenDto;
        
	}
	
	public boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isBefore(LocalDateTime.now());
    }
	
}
