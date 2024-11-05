package com.mingi.ch6_1.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mingi.ch6_1.details.CustomUserDetails;
import com.mingi.ch6_1.entity.User;
import com.mingi.ch6_1.repository.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Supplier<UsernameNotFoundException> s =
				() -> new UsernameNotFoundException("Problem during authentication!");
				
				User u = userRepository.findUserByUsername(username).orElseThrow(s);
		
		return new CustomUserDetails(u);
	}

}
