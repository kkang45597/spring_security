package com.mingi.jwt.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 이 코드는 JWT 인증을 사용하는 Spring Security 설정에서 권한이 부족한 사용자가 리소스에 접근하려고 할 때 HTTP 403 응답을 반환하도록 설정하는 역할을 한다.
// 이렇게 하면 클라이언트는 자신이 권한이 부족함을 알 수 있다.
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}