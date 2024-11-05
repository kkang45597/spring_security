package com.mingi.ch10_2.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.mingi.ch10_2.csrf.CustomCsrfTokenRepository;


@Configuration
public class AppConfig {

//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf(c -> {
//            c.csrfTokenRepository(customTokenRepository());
//            c.ignoringRequestMatchers("/ciao");
//
////            HandlerMappingIntrospector i = new HandlerMappingIntrospector();
////            MvcRequestMatcher r = new MvcRequestMatcher(i, "/ciao");
////            c.ignoringRequestMatchers(r);
//
////            String pattern = ".*[0-9].*";
////            String httpMethod = HttpMethod.POST.name();
////            RegexRequestMatcher r = new RegexRequestMatcher(pattern, httpMethod);
////            c.ignoringRequestMatchers(r);
//        });
//
//        http.authorizeRequests()
//             .anyRequest().permitAll();
//    }
	
	@Bean
    public CsrfTokenRepository customTokenRepository() {
        return new CustomCsrfTokenRepository();
    }
	
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http.formLogin(c -> c.defaultSuccessUrl("/main", true));
		
		// 책에 나온데로 했을 경우
//		http.authorizeHttpRequests()
//			.anyRequest().authenticated();
//		http.formLogin()
//			.defaultSuccessUrl("/main", true);
		
		
//		http.csrf(
//				c -> {
//					c.csrfTokenRepository(customTokenRepository);
//					c.csrfTokenRepository(new CsrfTokenRequestAttributeHandler());
//				}
//		);
//		
		return http.build();
		
	}
	
	
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(
                c -> {     // statement....
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
                        configuration.setAllowedMethods(List.of("*"));
                        configuration.setAllowedHeaders(List.of("*"));
                        return  configuration;
                    };
                    c.configurationSource(source);
                });

        http.csrf(
                c -> c.disable() // expression
        );

        http.authorizeHttpRequests(
                c->c.anyRequest().permitAll()
        );

        return http.build();
    }
}
