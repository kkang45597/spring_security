package com.mingi.ch3_1.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.mingi.ch3_1.security.CustomAuthenticationProvider;

@Configuration
public class AppConfig { // 교제 61P (강사님 변형)

//	private final CustomAuthenticationProvider authenticationProvider;
//	public AppConfig(CustomAuthenticationProvider authenticationProvider) {
//		this.authenticationProvider = authenticationProvider;
//	}
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		// 스프링 부트 스타터 시큐리티의 Basic 인증에 관한 디폴트 기능을 수정 없이 그대로 사용하겠다는 의미
		http.httpBasic(Customizer.withDefaults());
		
		// 순환 참조를 유발함
//		http.authenticationProvider(authenticationProvider);
		
		http.authorizeHttpRequests(
				c -> c.anyRequest().authenticated());
		
		return http.build();
		
	}
	
	@Bean
	UserDetailsService userDetailsService(DataSource dataSource) {
		String usersByUsernameQuery = "Select username, password, enabled from users where username = ?";
		String authsByuserQuery = "Select username, authority from sbdt_db.authorities where username = ?";
		
		var userDetailsManager = new JdbcUserDetailsManager(dataSource);
		userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
		userDetailsManager.setAuthoritiesByUsernameQuery(authsByuserQuery);
		return userDetailsManager;
		
	}
}
