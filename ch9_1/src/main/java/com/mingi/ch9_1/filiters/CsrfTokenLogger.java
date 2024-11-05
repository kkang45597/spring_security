package com.mingi.ch9_1.filiters;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.security.web.csrf.CsrfToken;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class CsrfTokenLogger implements Filter {
	
	private Logger logger = Logger.getLogger(CsrfTokenLogger.class.getName());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
			throws IOException, ServletException {
		
		Object o = request.getAttribute("_csrf");
		CsrfToken token = (CsrfToken) o;
		
		logger.info("CSRF token " + token.getToken());
		filterchain.doFilter(request, response);
	}

}
