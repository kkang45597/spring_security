package com.mingi.Ch8_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class ProjectConfig {

	// 8.1
//    @Bean
//    public UserDetailsService userDetailsService() {
//        var manager = new InMemoryUserDetailsManager();
//
//        var user1 = User.withUsername("john")
//                        .password("12345")
//                        .roles("ADMIN")
//                        .build();
//
//        var user2 = User.withUsername("jane")
//                        .password("12345")
//                        .roles("MANAGER")
//                        .build();
//
//        manager.createUser(user1);
//        manager.createUser(user2);
//
//        return manager;
//    }
	
	// 8.5
//	@Bean
//    public UserDetailsService userDetailsService() {
//        var manager = new InMemoryUserDetailsManager();
//
//        var user1 = User.withUsername("john")
//                        .password("12345")
//                        .authorities("read")
//                        .build();
//
//        var user2 = User.withUsername("jane")
//                        .password("12345")
//                        .authorities("read", "premium")
//                        .build();
//
//        manager.createUser(user1);
//        manager.createUser(user2);
//
//        return manager;
//    }
	
	// 8 Finale
	@Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("12345")
                .authorities("read")
                .roles("ADMIN")
                .authorities("ROLE_ADMIN","read")
                .build();

        var user2 = User.withUsername("jane")
                .password("12345")
                .authorities("ROLE_MANAGER", "read", "premium")
                .build();

        var user3 = User.withUsername("bill")
                .password("12345")
                .authorities("read", "premium")
                .build();

        var user4 = User.withUsername("kris")
                .password("12345")
                .authorities("ROLE_MANAGER", "read")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);
        manager.createUser(user3);
        manager.createUser(user4);

        return manager;
    }

    // 8.1
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) 
//			throws Exception {
//    	
//		http.httpBasic(Customizer.withDefaults());
//       
//    	http.authorizeHttpRequests(a -> a
//    		.requestMatchers("/hello").hasRole("ADMIN")
//    		.requestMatchers("/ciao").hasRole("MANAGER")
//    		.anyRequest().permitAll() // 모든 or 다른(이외) 나머지
////    		.anyRequest().denyAll() // 모두 거부
////    		.anyRequest().authenticated() // 인증된 사용자에게만
//    		);
//
//        return http.build();
//    }
    
    // 8.2
//    @Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) 
//			throws Exception {
//    	
//		http.httpBasic(Customizer.withDefaults());
//       
//    	http.authorizeHttpRequests(a -> a
//    		.requestMatchers(HttpMethod.GET, "/a").authenticated()
//    		.requestMatchers(HttpMethod.POST, "/a").permitAll()
//    		.anyRequest().denyAll()
//    		);
//    	
//    	// 스프링 시큐리티의 CSRF는 디폴트로 enable 상태이다.
//    	http.csrf(c -> c.disable());
//
//        return http.build();
//	}
    
	// 8.3
//    @Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) 
//			throws Exception {
//    	
//		http.httpBasic(Customizer.withDefaults());
//       
//    	http.authorizeHttpRequests(a -> a
//    		// "/a/b/**" -> 경로 표현식 (path expression!!)
//    		.requestMatchers("/a/b/**").authenticated()
//    		.anyRequest().permitAll()
//    		);
//    	
//    	// 스프링 시큐리티의 CSRF는 디폴트로 enable 상태이다.
//    	http.csrf(c -> c.disable());
//
//        return http.build();
//	}
    
    // 8.4
//    @Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) 
//			throws Exception {
//    	
//		http.httpBasic(Customizer.withDefaults());
//       
//    	http.authorizeHttpRequests(a -> a
//    		// "/a/b/**" -> 경로 표현식 (path expression!!)
//    		.requestMatchers("/product/{code:^[0-9]*$}").permitAll()
//    		.anyRequest().denyAll()
//    		);
//    	
//    	// 스프링 시큐리티의 CSRF는 디폴트로 enable 상태이다.
//    	http.csrf(c -> c.disable());
//
//        return http.build();
//	}
	
	// 8.5
//    @Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) 
//			throws Exception {
//    	
//		http.httpBasic(Customizer.withDefaults());
//       
//    	http.authorizeHttpRequests(a -> a
//    			.requestMatchers(new RegexRequestMatcher(".*/[us|uk|ca]+/[en|fr].*", HttpMethod.GET.name()))
//	    		.authenticated()
//	    		.anyRequest().hasAuthority("premium")
//    		);
//    	
//    	// 스프링 시큐리티의 CSRF는 디폴트로 enable 상태이다.
//    	http.csrf(c -> c.disable());
//
//        return http.build();
//	}
    
    // 8.6
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) 
//			throws Exception {
//  	
//		http.httpBasic(Customizer.withDefaults());
//     
//  	http.authorizeHttpRequests(a -> a
//  			.requestMatchers("/email/{email:.*(.+@.+\\.com)}", HttpMethod.GET.name()).permitAll()
//	    		.anyRequest().denyAll()
//  		);
//  	
//  	// 스프링 시큐리티의 CSRF는 디폴트로 enable 상태이다.
//  	http.csrf(c -> c.disable());
//
//      return http.build();
//	}
	
	// 8 Final
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(
                c -> c.requestMatchers(HttpMethod.GET, "/admin/**").access(                		
                        new WebExpressionAuthorizationManager(
                                "hasRole('ADMIN') and hasAuthority('read')"))                
                        .requestMatchers(HttpMethod.GET,"/manager/home").access(
                                new WebExpressionAuthorizationManager(
                                        "hasRole('MANAGER') and hasAuthority('read')"))
                        .requestMatchers(HttpMethod.GET,"/manager/**").access(
                                new WebExpressionAuthorizationManager(
                                        "hasRole('MANAGER') and hasAuthority('read') and hasAuthority('premium')"))
                        .requestMatchers(HttpMethod.POST,"/manager/**").access(
                                new WebExpressionAuthorizationManager(
                                        "hasRole('MANAGER') and hasAuthority('read') and hasAuthority('premium')"))
                        .requestMatchers(HttpMethod.GET,"/user/**").hasAuthority("premium")
                        .requestMatchers(HttpMethod.GET,"/public/**").permitAll()
                        .anyRequest().authenticated()
        );

        http.csrf(
                c -> c.disable()
        );

        return http.build();
    }
	
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
