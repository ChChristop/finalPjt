package com.example.demo.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.config.socialLogin.google.GoogleLoginService;
import com.example.demo.dto.MemberDTO;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OauthLogin implements JwtProperties {

	private final GoogleLoginService googleLoginService;

//	@GetMapping("/login")
//	@ResponseBody
//	public ResponseEntity<Object> login(@RequestParam(value = "code") String getHeader, HttpServletResponse response,
//			HttpServletRequest request, RedirectAttributes rttr) {
	
		@GetMapping("/login")
		@ResponseBody
		public ResponseEntity<MemberDTO> login( HttpServletResponse response,
				HttpServletRequest request) {
		
		String ggg= (String) request.getHeader("code");
		
		String getHeader = ggg.substring(ggg.indexOf("code")+5,ggg.indexOf("&scope"));
		
		
		System.out.println("getHeader : "+ getHeader);
		
		MemberDTO memberDTO = googleLoginService.login(getHeader);


		if (memberDTO == null) {
			
		}

		googleLoginService.loginSuccess(memberDTO, request);
		
		String jwtToken = (String)request.getAttribute(SECRETKEY_HEADER_STRING);
		String jwtrefreshToekn = (String)request.getAttribute("refreshToken");
		
		response.addHeader(SECRETKEY_HEADER_STRING, jwtToken);
		response.addHeader(REFRESHKEY_HEADER_STRING, jwtrefreshToekn);
		
		return new ResponseEntity<>(memberDTO, HttpStatus.ACCEPTED);
			
	


	}
}
