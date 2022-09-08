package com.example.demo.config.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.shaded.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomLoginFailHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException accessException) throws IOException, ServletException {
		

		if (accessException instanceof AuthenticationServiceException) {
			
			log.warn("[/api/login] [회원 로그인 실패(서버)] [{}] ", (String) request.getAttribute("id"));
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json; charset=utf-8");
			
			JSONObject json = new JSONObject();
		
			json.put("code", "401");
			json.put("message", "회원 가입 필요");
			
			PrintWriter out = response.getWriter();
			
			out.print(json);

		
		} else if(accessException instanceof BadCredentialsException) {
			request.setAttribute("error", "비밀번호가 틀립니다.");
			
		} else if(accessException instanceof LockedException) {
			request.setAttribute("error", "잠긴 계정입니다..");
			
		} else if(accessException instanceof DisabledException) {
			request.setAttribute("error", "비활성화된 계정입니다..");
			
		} else if(accessException instanceof AccountExpiredException) {
			request.setAttribute("error", "만료된 계정입니다..");
			
		} 
		
		
	}
	
	

}
