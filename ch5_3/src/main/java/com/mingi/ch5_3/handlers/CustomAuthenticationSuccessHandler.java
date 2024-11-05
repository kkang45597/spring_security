package com.mingi.ch5_3.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Optional;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        var authorities = authentication.getAuthorities();
        @SuppressWarnings("unchecked")
		Optional<GrantedAuthority> auth =
        		(Optional<GrantedAuthority>) authorities.stream()
                .filter(a -> a.getAuthority().equals("read"))
                .findFirst();
        if (auth.isPresent()) {
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/error");
        }
    }
}