package com.mingi.jwt.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mingi.jwt.dto.LoginDto;
import com.mingi.jwt.dto.TokenDto;
import com.mingi.jwt.entities.RefreshToken;
import com.mingi.jwt.jwt.JwtFilter;
import com.mingi.jwt.jwt.TokenProvider;
import com.mingi.jwt.repository.RefreshTokenRepository;
import com.mingi.jwt.service.AuthenticationService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
   
    private final AuthenticationService authenticationService;    
    
    // POSTMAN으로 테스트할때 Body에서 raw.JSON으로 username과 password를 넣어야 한다.
    // 반환값으로 accessToken과 refreshToken을 받는다.
    @PostMapping("/login") // login
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginDto loginDto) {

    	Optional<TokenResponse> optTokenResponse = 
    			authenticationService.makeTokens(loginDto);
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + 
        		optTokenResponse.get().getAccessToken());
        
        ResponseEntity<TokenResponse> ret = new ResponseEntity<>(
        		optTokenResponse.get(), 
        		httpHeaders, 
        		HttpStatus.OK);
        
        return ret;
    }     
    
    // POSTMAN으로 테스트할때 Body에서 raw.JSON으로 refreshToken을 넣고, Authorization에서 Bearer Token으로 accessToken을 넣어야 한다.
    // 반환값으로 token을 받는다.
    // jwt.SecurityConfig.filterChain에서 엔드포인트 접근 설정을 permitAll에서 authenticated로 변경하였다.
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody 
    		RefreshTokenRequest refreshTokenRequest,
    		Authentication authentication) {    	
        
        try {
        	Optional<TokenDto> tokenDto = 
        			authenticationService.makeNewAccessToken(
        					refreshTokenRequest, authentication);
        	
        	if (!tokenDto.isEmpty()) {
        		return ResponseEntity.ok(tokenDto.get());
        	} else {
        		return ResponseEntity.badRequest().body("Refresh token expired. Please login again.");
        	}            
            
        } catch (Exception e) {
            // 에러 처리 (예외 메시지 반환 등)
            return ResponseEntity.badRequest().body(e.getMessage());
        }        
    }
    
}