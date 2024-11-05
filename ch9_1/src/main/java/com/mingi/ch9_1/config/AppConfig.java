package com.mingi.ch9_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

import com.mingi.ch9_1.filiters.CsrfTokenLogger;
import com.mingi.ch9_1.filiters.StaticKeyAuthenticationFilter;

@Configuration
public class AppConfig {

//	private StaticKeyAuthenticationFilter skaFilter;
//	
//	public AppConfig(StaticKeyAuthenticationFilter filter) {
//		this.skaFilter = filter;
//		
//	}
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
//		http.httpBasic(Customizer.withDefaults()); // 기본 인증 활성화		
//		// 커스텀 필터 추가
//		http.addFilterAt(skaFilter, BasicAuthenticationFilter.class)
//			.authorizeHttpRequests(c -> c.anyRequest().permitAll());
		
		http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class)
			.authorizeRequests().anyRequest().permitAll();
		
		return http.build();
		
	}
}
