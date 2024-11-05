package com.mingi.Ch7_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
public class ProjectConfig {
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		var manager = new InMemoryUserDetailsManager();
//
//		var user1 = User.withUsername("john")
//			.password("12345")
//			.authorities("read")
//			.build();
//
//		var user2 = User.withUsername("jane")
//			.password("12345")
//			.authorities("read", "write", "delete")
//			.build();
//
//		manager.createUser(user1);
//		manager.createUser(user2);
//
//		return manager;
//	}

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                        .password("12345")
//                        .authorities("READ")
                        .authorities("ROLE_ADMIN")
                        .build();

        var user2 = User.withUsername("jane")
                        .password("12345")
//                        .authorities("WRITE")
                        .authorities("ROLE_MANAGER")
                        .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    /*
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) 
			throws Exception {
    	
		http.httpBasic(Customizer.withDefaults());

//		http.authorizeHttpRequests()
//        		.anyRequest()  // 모든 request 또는 다른 나머지 request
//        			.hasAuthority("WRITE");

//		http.authorizeHttpRequests()
//    		.anyRequest().
//    		hasAnyAuthority("WRITE", "READ");
       
    	http.authorizeHttpRequests(a -> a
    		.anyRequest().hasAuthority("WRITE"));

        return http.build();
    }
    */
	
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//      http.httpBasic(Customizer.withDefaults());
//
//      String expression = "hasAuthority('read') and !hasAuthority('delete')";
//      http.authorizeHttpRequests(
//          c -> c.anyRequest().access(new WebExpressionAuthorizationManager(expression))
//      );
//
//      return http.build();
//  }
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.httpBasic(Customizer.withDefaults());
//
//        http.authorizeHttpRequests(
//            c -> c.anyRequest()
//                	.access(
//                		new WebExpressionAuthorizationManager(
//               // 현재 시간이 오후(12시 이후)인 경우에만 요청을 허용하는 조건
//            "T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).of(12, 0))"))
//        );
//
//        return http.build();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
