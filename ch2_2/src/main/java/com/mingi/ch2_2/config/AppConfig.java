package com.mingi.ch2_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppConfig {
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		// 스프링 부트 스타터 시큐리티의 Basic 인증에 관한 디폴트 기능을 수정 없이 그대로 사용하겠다는 의미
		http.httpBasic(Customizer.withDefaults());
		
		http.authorizeHttpRequests(
				c -> c.anyRequest().authenticated());
		
		return http.build();
		
	}

	@Bean
	UserDetailsService userDetailsService() {
		
		var user = User.withUsername("John")
				.password("12345")
				.authorities("read")
				.build();

		// 교제 방식
//		var userDetailsService = 
//				new InMemoryUserDetailsManager();
//		userDetailsService.createUser(user);
//		return userDetailsService;
		
		// 강사님 방식
		return new InMemoryUserDetailsManager(user); // 테스트 용도
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); // 테스트 용도
	}
}
