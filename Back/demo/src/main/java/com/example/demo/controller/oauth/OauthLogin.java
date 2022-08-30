package com.example.demo.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.config.socialLogin.google.GoogleLoginService;
import com.example.demo.config.socialLogin.google.GoogleUserInfoMobile;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.memberService.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OauthLogin {

	private final GoogleLoginService googleLoginService;

	private final MemberService memberService;

//	@GetMapping("/login")
//	@ResponseBody
//	public ResponseEntity<Object> login(@RequestParam(value = "code") String getHeader, HttpServletResponse response,
//			HttpServletRequest request, RedirectAttributes rttr) {

	@GetMapping("/login")
	@ResponseBody
	public ResponseEntity<MemberDTO> login(HttpServletResponse response, HttpServletRequest request) {

		String fullHeader = (String) request.getHeader("code");

		String getHeader = fullHeader.substring(fullHeader.indexOf("code") + 5, fullHeader.indexOf("&scope"));

		MemberDTO memberDTO = googleLoginService.login(getHeader);

//		if (memberDTO == null) {
//			
//		}
//		
		log.info("[OauthLogin api/login] : 구글 로그인 중 : " + memberDTO.getMemberID());

		googleLoginService.loginSuccess(memberDTO, request);

		String jwtToken = (String) request.getAttribute(JwtProperties.SECRETKEY_HEADER_STRING);
		String jwtrefreshToekn = (String) request.getAttribute("refreshToken");

		response.addHeader(JwtProperties.SECRETKEY_HEADER_STRING, jwtToken);
		response.addHeader(JwtProperties.REFRESHKEY_HEADER_STRING, jwtrefreshToekn);

		log.info("[OauthLogin api/login] : 구글 로그인 성공 : " + memberDTO.getMemberID());

		return new ResponseEntity<>(memberDTO, HttpStatus.ACCEPTED);

	}

	@PostMapping("/login/mobile")
	public ResponseEntity<MemberDTO> mobilelogin(@RequestBody GoogleUserInfoMobile googleUserInfo,
			HttpServletResponse response, HttpServletRequest request) {

		log.info("[OauthLogin api/login/mobile] : 모바일 구글 로그인 중 : " + googleUserInfo.toString());

		MemberDTO memberDTO = googleLoginService.mobileGoogleLogin(googleUserInfo.getEmail());

		if (memberDTO.getMemberID() != null) {

			googleLoginService.loginSuccess(memberDTO, request);

			String jwtToken = (String) request.getAttribute(JwtProperties.SECRETKEY_HEADER_STRING);
			String jwtrefreshToekn = (String) request.getAttribute("refreshToken");

			response.addHeader(JwtProperties.SECRETKEY_HEADER_STRING, jwtToken);
			response.addHeader(JwtProperties.REFRESHKEY_HEADER_STRING, jwtrefreshToekn);

			log.info("[OauthLogin api/login/mobile] : 모바일 구글 로그인 성공 : " + googleUserInfo.toString());

			return new ResponseEntity<>(memberDTO, HttpStatus.OK);
		} else {

			log.warn("[OauthLogin api/login/mobile] : 모바일 구글 로그인 실패 : " + googleUserInfo.toString());

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}
