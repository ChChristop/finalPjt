package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import com.example.demo.config.jwt.JwtAuthenticationFilter;
import com.example.demo.config.jwt.JwtAuthorizationFilter;
import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.JwtTokkenDAO;
import com.example.demo.dao.MemberDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
	
	private final CorsConfig corsConfig = new CorsConfig();
	
	private final AdminDAO adminDAO;
	
	private final MemberDAO memberDAO;
	
	private final JwtTokkenDAO jwtTokkenDAO;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//csrf 비활성화
		http.csrf().disable()
		//세션 사용 안함, Stateless 무상태
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		//기존 login 양식 안씀 일시적 잠금
		.formLogin().disable()
		//기본 httpBasic 안씀
		.httpBasic().disable()
		.addFilter(corsConfig.corsFilter())
		.apply(new MyCustomDsl())
		.and()
	    .authorizeRequests()
				/*
				 * .antMatchers("/api/logout/**") .hasAnyRole("MEMBER","ADMIN")
				 * .antMatchers("/api/admin/**") .hasRole("ADMIN")
				 * .antMatchers("/api/member/member-list") .hasAnyRole("ADMIN")
				 * .antMatchers("/api/member/**") .hasAnyRole("MEMBER","ADMIN")
				 * .antMatchers("/api/refre/**") .hasAnyRole("MEMBER")
				 */
	     	
		    .anyRequest().permitAll();

		return http.build();
	}
	
	public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {

			//비회원이 게시물 읽을 때는 jwt 확인 하지 않게 하기
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http
					.addFilterBefore(new JwtAuthenticationFilter("/api/login/**",authenticationManager),UsernamePasswordAuthenticationFilter.class)
					;
//					.addFilter(new JwtAuthorizationFilter(authenticationManager,adminDAO,memberDAO,jwtTokkenDAO));
		}
	}

	
}
