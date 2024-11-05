package com.mingi.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;

import com.mingi.jwt.dto.UserDto;
import com.mingi.jwt.entities.User;
import com.mingi.jwt.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    
    private HandlerMethodArgumentResolver xxx;
    
    private RequestParamMethodArgumentResolver uyyy;
    
    private DispatcherServlet dps;

    // POSTMAN으로 테스트할때 Body에서 raw.JSON으로 새로운 nickname과 username, password를 넣어야 한다.
    // authorities가 생략될 경우엔 ROLE_USER가 된다.
    // 생성된 유저 정보를 JSON으로 반환 받는다.
    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    // POSTMAN으로 테스트할때 Authorization에서 Bearer Token으로 accessToken을 넣어야 한다.
    // 반환값으로 해당 유저의 정보를 JSON으로 받는다.
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
    	
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

	// POSTMAN으로 테스트할때 Authorization에서 Bearer Token으로 ADMIN의 accessToken을 넣어야 한다.
    // 반환값으로 해당 유저의 정보를 JSON으로 받는다.
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}