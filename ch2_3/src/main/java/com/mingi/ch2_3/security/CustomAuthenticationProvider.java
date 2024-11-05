package com.mingi.ch2_3.security;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
		String password = String.valueOf(authentication.getCredentials());
		
		log.info("What is principal :" + authentication.getPrincipal());
		
		if("John".equals(username) && "1234".equals(password)) {
			return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());
		}
		else {
			throw new AuthenticationCredentialsNotFoundException("Not Match Error");
		}
		
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
