package com.example.demo.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.dto.AdminDTO;

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
		
		// 관리자 또는 회원으로 나누어져 로그인 진행으로 파라미터를 읽을 필요가 있음 
		BufferedReader br = new BufferedReader(
				new InputStreamReader((ServletInputStream)request.getInputStream(),"utf-8"
				));
		
		StringBuffer sb = new StringBuffer("");
		
		String temp;
		
		while((temp = br.readLine()) != null) {
			sb.append(temp);	
		}
		
		br.close();
		
		//postman Json 데이터와 html 에서 읽은 Json 데이터와 양식이 다름
		String[] params = sb.toString().split("&");
		
		String[] param = new String[3];
		
		//postman으로 받을 시
		if(params.length == 1) {
			
			params = params[0].replaceAll("[{}\" ]+","").split(",");
	
			param[0] = params[0].substring(params[0].lastIndexOf(":")+1);
			param[1] = params[1].substring(params[1].lastIndexOf(":")+1);
			param[2] = params[2].substring(params[2].lastIndexOf(":")+1);
		}else {
			param[0] = params[0].substring(params[0].lastIndexOf("=")+1);
			param[1] = params[1].substring(params[1].lastIndexOf("=")+1);
			param[2] = params[2].substring(params[2].lastIndexOf("=")+1);
		
		}
	
			
		//html로 받을 시	

	
		
		
		System.out.println(Arrays.toString(param));
//		ObjectMapper om = new ObjectMapper();
		
		if(param[2].equals("true")) {
	
			System.out.println("==========================================");
			System.out.println("확인 중");
			AdminDTO adminDTO = AdminDTO.builder()
					.adminID(param[0])
					.adminPW(param[1])
					.build();
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					adminDTO.getAdminID(), adminDTO.getAdminPW());
			
			//세션 생성
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			System.out.println("authentication123 " + authentication.toString());
			
//			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			
			return authentication;
		}else {
			return null;
		}
		

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
