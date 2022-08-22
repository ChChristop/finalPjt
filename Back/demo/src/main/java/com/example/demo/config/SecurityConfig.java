package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {
	
	private final CorsConfig corsConfig = new CorsConfig();
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//csrf 비활성화
		http.csrf().disable();
		//세션 사용 안함, Stateless 무상태
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		//cors 허가해주는 필터
		//기존 login 양식 안씀
		.formLogin().disable()
		//기본 httpBasic 안씀
		.httpBasic().disable()
		.apply(new MyCustomDsl())
		.and()
	    .authorizeRequests()
		    .anyRequest().permitAll();

		return http.build();
	}
	
	public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			
			http
					//cors 실패를 방지해주는 필터
					.addFilter(corsConfig.corsFilter());
//					.addFilterBefore(new JwtAuthenticationFilter("/api/login",authenticationManager),UsernamePasswordAuthenticationFilter.class)
//					.addFilter(new JwtAuthorizationFilter(authenticationManager,userMapper));

		}
	}
	
}
