package com.cndinfo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cndinfo.security.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	// 왜 프로그램을 종료하여도 authenticated 에 정보가 출력될까
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.httpBasic().disable();
		
		http.authorizeHttpRequests((request) -> request
				// 해당 request에 대해서는 모두 허용한다.
				.requestMatchers("/", "/join", "/loginForm", "/about").permitAll()
				.requestMatchers("/css/**", "/js/**", "/resources/**", "/images/**").permitAll()
				.requestMatchers("/user/save").permitAll()
				// 해당 request에서는 "USER" 역할을 가진 유저에게만 허용한다.
				.requestMatchers("/certi").hasAuthority("USER")
				.requestMatchers("/certi/**").hasAuthority("USER")
				.requestMatchers("/certification").hasAuthority("USER")
			).formLogin((form) -> form
					.loginPage("/loginForm")
					.loginProcessingUrl("/login")
					// 이건 왜 안 될까 ?
					//.defaultSuccessUrl("/")
					.defaultSuccessUrl("/", true)
					// loginForm.html의 name의 값과 동일한 명으로 지정해야 함. ( default : username , password )
					.usernameParameter("email")
					.passwordParameter("pw")
					)
			.logout((logout) -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					)
			;

		return http.build();
	}

	
	
	@Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider();
	}
	
}
