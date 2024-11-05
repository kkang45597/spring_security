package com.mingi.ch9_1.filiters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticKeyAuthenticationFilter implements Filter {

	@Value("${authorization.key}")
	private String authorizationKey;
	
	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterChain)
			throws IOException, ServletException {
		var httpRequest = (HttpServletRequest) servletrequest;
		var httpResponse = (HttpServletResponse) servletresponse;
		
		String authorization = httpRequest.getHeader("Authorization");
		
		if (authorizationKey.equals(authorization)) {
			filterChain.doFilter(servletrequest, servletresponse);
		}
		else {
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}
