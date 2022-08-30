package com.example.demo.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

import com.example.demo.config.auth.AdminCheck;
import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.MemberDTO;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

//로그인 인증관련 필터
@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter{

	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
		super(defaultFilterProcessesUrl, authenticationManager);
		this.authenticationManager = authenticationManager;
	}

	// login 필터
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		// 관리자 또는 회원으로 나누어져 로그인 진행으로 파라미터를 읽을 필요가 있음
		BufferedReader br = new BufferedReader(
				new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));

		StringBuffer sb = new StringBuffer("");

		String temp;

		while ((temp = br.readLine()) != null) {
			String decodeTemp = java.net.URLDecoder.decode(temp, StandardCharsets.UTF_8.name());
			sb.append(decodeTemp);
		}

		br.close();


		// postman Json 데이터와 html 에서 읽은 Json 데이터와 양식이 다름
		String[] params = sb.toString().split("&");

		String[] param = new String[3];

		// postman으로 받을 시
		if (params.length == 1) {

			params = params[0].replaceAll("[{}\" ]+", "").replace("=", ":").split(",");

		}
		
		param = params;
		
		if(param[0].contains("=")) {
			for(int i = 0; i<param.length;i++ ) {
				param[i] =  params[i].substring(params[i].lastIndexOf("=") + 1);
			}
			
		}else {
			for(int i = 0; i<param.length;i++ ) {
				param[i] =  params[i].substring(params[i].lastIndexOf(":") + 1);
			}
		}

		
		log.info("[JwtAuthneticationFilter] : 로그인 시도 중 " + param[0]);
		
		// html로 받을 시
		UsernamePasswordAuthenticationToken authenticationToken = null;;

//		ObjectMapper om = new ObjectMapper();

		if (param[2].equals("true")) {
			
			AdminCheck.check = true;
			
			AdminDTO adminDTO = AdminDTO.builder().adminID(param[0]).adminPW(param[1]).build();

			authenticationToken = new UsernamePasswordAuthenticationToken(adminDTO.getAdminID(), adminDTO.getAdminPW());

		} else {

			AdminCheck.check = false;
			
			MemberDTO memberDTO = MemberDTO.builder().memberID(param[0]).memberPW(param[1]).build();

			authenticationToken = new UsernamePasswordAuthenticationToken(memberDTO.getMemberID(), memberDTO.getMemberPW());

		}

		// 세션 생성
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		
		log.info("[JwtAuthneticationFilter] : authentication 객체 생성 : " + authentication.getName());

		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
		
		
		log.info("[JwtAuthneticationFilter] : 로그인 성공 : " + principalDetails.getUsername());

		String jwtToken = JwtProperties.CreateJWTToken(principalDetails);

		String jwtRefreshToken = JwtProperties.CreateJWTRefreshToken(principalDetails);

		request.setAttribute("refreshToken", jwtRefreshToken);

		request.setAttribute("id", principalDetails.getUsername());
		
		if(AdminCheck.check) {
			principalDetails.getAdminDTO().setAdminPW("");
			request.setAttribute("adminDTO",principalDetails.getAdminDTO());
			
		}else {
			principalDetails.getMemberDTO().setMemberPW("");
			request.setAttribute("memberDTO",principalDetails.getMemberDTO());
		}

		response.addHeader(JwtProperties.SECRETKEY_HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

		response.addHeader(JwtProperties.REFRESHKEY_HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtRefreshToken);
		

		chain.doFilter(request, response);

	}

}
