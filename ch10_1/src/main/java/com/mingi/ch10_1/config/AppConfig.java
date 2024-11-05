package com.mingi.ch10_1.config;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

import com.mingi.ch10_1.filter.CsrfTokenLogger;

@Configuration
public class AppConfig {

	@Bean
	UserDetailsService uds() {
		var uds = new InMemoryUserDetailsManager();
		var u1 = User.withUsername("mary")
				.password("12345")
				.authorities("READ")
				.build();
		
		uds.createUser(u1);
		
		return uds;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http.formLogin(c -> c.defaultSuccessUrl("/main", true));
		
		// 책에 나온데로 했을 경우
//		http.authorizeHttpRequests()
//			.anyRequest().authenticated();
//		http.formLogin()
//			.defaultSuccessUrl("/main", true);
		
		
		http.csrf(c -> c.ignoringRequestMatchers("/ciao"));
		http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class)
			.authorizeHttpRequests(c -> c.anyRequest().authenticated());
		
		
		return http.build();
		
	}
	
	@Bean
    public ServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addContextCustomizers((context) -> {
            Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
            cookieProcessor.setSameSiteCookies("Strict"); // SameSite 속성을 Strict로 설정
            context.setCookieProcessor(cookieProcessor);
        });
        return factory;
    }

	
//	@Bean
//    public FilterRegistrationBean<LoggingFilter> customFilterRegistration() {
//        FilterRegistrationBean<LoggingFilter> registrationBean = 
//        		new FilterRegistrationBean<>(new LoggingFilter());
//        
//        registrationBean.setOrder(1); // 필터의 우선순위 설정
//        registrationBean.addUrlPatterns("/*"); // 필터를 적용할 URL 패턴 설정
//        
//        return registrationBean;
//    }
}
