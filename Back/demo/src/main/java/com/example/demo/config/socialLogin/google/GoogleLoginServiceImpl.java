package com.example.demo.config.socialLogin.google;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.config.auth.AdminCheck;
import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.config.socialLogin.AccessTokenRequestDTO;
import com.example.demo.controller.LoginController;
import com.example.demo.dao.JwtTokkenDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.dao.RefrigeratorDAO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.memberService.MemberService;
import com.example.demo.vo.MemberVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleLoginServiceImpl implements GoogleLoginService {

	private final MemberDAO memberDAO;

	private final MemberService memberService;

	private final JwtTokkenDAO jwtTokkenDAO;

	private final RefrigeratorDAO refrigeratorDAO;

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String clientID;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String clientPW;

	@Override
	public MemberDTO login(String getHeader) {

//	 		레거시 코드	
//			String getHeader = request.getHeader("abc");
//			String code = getHeader.substring(getHeader.indexOf("code")+5,getHeader.indexOf("&scope"));
		
		//Header 분석
		String code = getHeader;

		String decodedCode = "";

		try {
			decodedCode = java.net.URLDecoder.decode(code, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {

		}
		
		
		//구글에 인가코드 발송
		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Type", "application/x-www-form-urlencoded");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", clientID);
		params.add("client_secret", clientPW);
		params.add("code", decodedCode);
		params.add("grant_type", "authorization_code");
		params.add("redirect_uri", "http://localhost:5500/ttest/aa.html");

		HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

		RestTemplate rt = new RestTemplate();

		
		ResponseEntity<String> accessTokenResponse = rt.exchange("https://oauth2.googleapis.com/token", HttpMethod.POST,
				accessTokenRequest, String.class);

		ObjectMapper objectMapper = new ObjectMapper();

		AccessTokenRequestDTO token = null;

		try {
			token = objectMapper.readValue(accessTokenResponse.getBody(), AccessTokenRequestDTO.class);
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

		String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com" + "/tokeninfo")
				.queryParam("id_token", token.getId_token()).toUriString();

		String resultJson = rt.getForObject(requestUrl, String.class);

		MemberDTO member = null;

		GoogleUserInfo googleUserInfo = null;

		try {
			googleUserInfo = objectMapper.readValue(resultJson, GoogleUserInfo.class);

			String id = googleUserInfo.getEmail();
			member = MemberDTO.builder().memberID(id)
					.nickname(googleUserInfo.getName()).build();

			member = check(member);
			
			if(member.getMemberID() == null) {
				member.setMemberID(id);
			}

			return member;

		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

		//로그인 실패시 빈 객체 리턴
		return new MemberDTO();
	}

	// 나중에 지워야할 듯
	@Override
	public MemberDTO check(MemberDTO dto) {

		// DB에 아이디가 있는지 확인 중
		log.info("[GoogleLoginServiceImpl] [check] [{}]", dto.getMemberID());
		
		Optional<MemberVO> member = memberDAO.findMemberbyMemberIDForJWT(dto.getMemberID());

		// DB에 아이디가 없으면 자동 가입 -> 나중에 수정 예정
		if (!(member.isPresent())) {

			return new MemberDTO();
		}

		// DB에 회원이 있으면 정보 반환
		MemberDTO memberDTO = memberService.voTOdto(member.get());

		memberDTO.setRole("ROLE_" + memberDTO.getRole());

		return memberDTO;
	}

	//로그인 성공시
	@Override
	public void loginSuccess(MemberDTO memberDTO, HttpServletRequest request) {

		log.info("[GoogleLoginServiceImpl] [구글 로그인 성공] [{}]", memberDTO.getMemberID());
		
		PrincipalDetails principalDetails = new PrincipalDetails(memberDTO);
		
		//JWTToken 생성하여 request에 붙임
		request.setAttribute(JwtProperties.SECRETKEY_HEADER_STRING,
				JwtProperties.TOKEN_PREFIX + JwtProperties.CreateJWTToken(principalDetails));
		
		//RefreshToken 생성하여 request에 붙임
		request.setAttribute("refreshToken",
				JwtProperties.TOKEN_PREFIX + JwtProperties.CreateJWTRefreshToken(principalDetails));
		
		request.setAttribute("id", memberDTO.getMemberID());
		request.setAttribute("memberDTO", memberDTO);

		LoginController loginController = new LoginController(jwtTokkenDAO, null, memberDAO, refrigeratorDAO);

		//응답하기 위해 memberLogin 으로 이동
		loginController.memberLogin(request);

	}

	//모바일 로그인
	@Override
	public MemberDTO mobileGoogleLogin(String id) {

		return mobileCheck(id);

	}

	//모바일 로그인
	@Override
	public MemberDTO mobileCheck(String id) {

		// DB에 아이디가 있는지 확인 중
		Optional<MemberVO> member = memberDAO.findMemberbyMemberIDForJWT(id);
		
		AdminCheck.check(false);
		
		// DB에 일치하는 아이디가 없으면 빈 객체 리턴
		if (!(member.isPresent())) {
			
			log.warn("[GoogleLoginServiceImpl] [모바일 구글 실패] [{}]", id);
			
			return new MemberDTO();
		}
		// DB에 회원이 있으면 정보 반환
		MemberDTO memberDTO = memberService.voTOdto(member.get());

		memberDTO.setRole("ROLE_" + memberDTO.getRole());

		return memberDTO;
	}

}
