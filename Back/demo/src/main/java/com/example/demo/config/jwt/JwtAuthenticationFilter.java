package com.example.demo.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.dao.JwtTokkenDAO;
import com.example.demo.dto.AdminDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

//로그인 인증관련 필터
@Log4j2
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter implements JwtProperties {

	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
		super(defaultFilterProcessesUrl, authenticationManager);
		this.authenticationManager = authenticationManager;
	}

	//login 필터
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		log.info("JwtAuthneticationFilter : 로그인 시도 중 ");
		
		ObjectMapper om = new ObjectMapper();
		
		AdminDTO adminDTO = null;
		
		try {
			adminDTO = om.readValue(request.getInputStream(), AdminDTO.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				adminDTO.getAdminID(), adminDTO.getAdminPW());
		
		//세션 생성
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		
//		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		
		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		log.info("로그인 되었음 : " + principalDetails.getUsername());
		
		String jwtToken = CreateJWTToken(principalDetails);

		String jwtRefreshToken = CreateJWTRefreshToken(principalDetails);

		request.setAttribute("refreshToken", jwtRefreshToken);
		
		request.setAttribute("id", principalDetails.getUsername());

		response.addHeader(SECRETKEY_HEADER_STRING, TOKEN_PREFIX + jwtToken);

		response.addHeader(REFRESHKEY_HEADER_STRING, TOKEN_PREFIX + jwtRefreshToken);
		
		chain.doFilter(request, response);

		

	}

}
