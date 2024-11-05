package com.mingi.ch6_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mingi.ch6_1.details.CustomUserDetails;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {
	
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	@Lazy // 참조 순환 오류를 막음
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	@Lazy // 참조 순환 오류를 막음
	private SCryptPasswordEncoder sCryptPasswordEncoder;	
    

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		CustomUserDetails user = userDetailsService.loadUserByUsername(username);
		
		switch (user.getUser().getAlgorithm()) {
			case BCRYPT:
				return checkPassword(user, password, bCryptPasswordEncoder);
				
			case SCRYPT:
				return checkPassword(user, password, sCryptPasswordEncoder);
				
		}
		throw new BadCredentialsException("Bad crdentials");
	}

	private Authentication checkPassword(CustomUserDetails user, String password,
			PasswordEncoder encoder) {
		
		if (encoder.matches(password, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(
					user.getUsername(),
					user.getPassword(),
					user.getAuthorities());
		}
		else {
			throw new BadCredentialsException("Bad crdentials");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
	}
	
	
}
