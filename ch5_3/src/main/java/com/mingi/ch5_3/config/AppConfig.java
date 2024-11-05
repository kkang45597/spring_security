package com.mingi.ch5_3.config;

import com.mingi.ch5_3.interceptor.CustomInterceptor;
import com.mingi.ch5_3.handlers.CustomAuthenticationSuccessHandler;
import com.mingi.ch5_3.security.CustomAuthenticationFailureHandler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAsync
public class AppConfig implements WebMvcConfigurer{

	private final CustomInterceptor customInterceptor;
	
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    public AppConfig(CustomInterceptor customInterceptor,
    		CustomAuthenticationSuccessHandler authenticationSuccessHandler,
                     CustomAuthenticationFailureHandler authenticationFailureHandler) {
        this.customInterceptor = customInterceptor;
    	this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder
                .setStrategyName(
                        SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
                );
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor)
                .addPathPatterns("/hello"); // 모든 경로에 대해 인터셉터 적용
    }

    @Bean
    public UserDetailsService uds() {
    	
    	var user = User.withUsername("john")  // id...
                .password("12345")
                .authorities("read")
                .build();
    	
        var uds = new InMemoryUserDetailsManager(user);

//        uds.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("john")
//                        .password("12345")
//                        .authorities("read")
//                        .build()
//        );
//
//        uds.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("bill")
//                        .password("12345")
//                        .authorities("write")
//                        .build()
//        );

        return uds;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.formLogin(c ->
                c.successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
        );

//        http.httpBasic(Customizer.withDefaults());
//        http.exceptionHandling()
//        		.authenticationEntryPoint(new CustomEntryPoint());

        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}