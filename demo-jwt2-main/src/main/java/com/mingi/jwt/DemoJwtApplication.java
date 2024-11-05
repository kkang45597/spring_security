package com.mingi.jwt;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mingi.jwt.dto.UserDto;
import com.mingi.jwt.entities.Authority;
import com.mingi.jwt.entities.User;
import com.mingi.jwt.repository.UserRepository;

@SpringBootApplication
public class DemoJwtApplication {
	
	@Bean
	public CommandLineRunner dataLoader(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder
			) {
		
		return new CommandLineRunner() {
		      @Override
		      public void run(String... args) throws Exception {
		    	  
		    	  // data.sql에 있던 admin 패스워드에 passwordEncoder를 적용하기 위해 대신 넣음
		    	  Authority adminAuthority = Authority.builder().authorityName("ROLE_ADMIN").build();
		    	  UserDto adminUserDto = UserDto.builder()
		    			  .username("admin")
		    			  .password("12345")
		    			  .nickname("admin")
		    			  .build();
		    	  User adminUser = User.builder()
		    			  .username(adminUserDto.getUsername())
		    			  .password(passwordEncoder.encode(adminUserDto.getPassword()))
		                  .nickname(adminUserDto.getNickname())
		                  .authorities(Collections.singleton(adminAuthority))
		                  .activated(true)
		                  .build();
		    	  userRepository.save(adminUser);
		    	  //
		    	  
		    	  Authority authority = Authority.builder()
		                  .authorityName("ROLE_USER")
		                  .build();
		    	  
		    	  UserDto userDto = UserDto.builder()
		    			  .username("intheeast0305@gmail.com")
		    			  .password("12345")
		    			  .nickname("sungwon")
		    			  .build();
		    	  
		    	  User user = User.builder()
		                  .username(userDto.getUsername())
		                  .password(passwordEncoder.encode(userDto.getPassword()))
		                  .nickname(userDto.getNickname())
		                  .authorities(Collections.singleton(authority))
		                  .activated(true)
		                  .build();

		          userRepository.save(user);
		      }
		};
		
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoJwtApplication.class, args);
	}

}
