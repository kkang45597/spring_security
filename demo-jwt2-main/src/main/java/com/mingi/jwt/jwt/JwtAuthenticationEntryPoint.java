package com.mingi.jwt.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 이 코드는 인증이 되지 않은 사용자가 보호된 API에 접근할 때 HTTP 401 응답을 반환하도록 설정하는 역할을 한다.
// 클라이언트가 유효하지 않은 JWT 토큰을 보내거나, 토큰이 없는 상태에서 보호된 엔드포인트에 접근할 때 이 클래스가 실행되어 401 Unauthorized 응답을 반환한다.
// 클라이언트는 인증이 필요함을 인지하고, 다시 로그인하여 유효한 토큰을 발급받아야 한다는 것을 알 수 있다.
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}