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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.config.socialLogin.AccessTokenRequestDTO;
import com.example.demo.controller.LoginController;
import com.example.demo.dao.JwtTokkenDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.memberService.MemberService;
import com.example.demo.vo.MemberVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleLoginServiceImpl implements GoogleLoginService, JwtProperties {
	

	private final MemberDAO memberDAO;
	
	private final MemberService memberService;
	
	private final JwtTokkenDAO jwtTokkenDAO;
	

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String clientID;
	
	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String clientPW;
		
	@Override
	public MemberDTO login(String getHeader) {

//	 		레거시 코드	
//			String getHeader = request.getHeader("abc");
//			String code = getHeader.substring(getHeader.indexOf("code")+5,getHeader.indexOf("&scope"));
			
			String code = getHeader;

			String decodedCode = "";
			
			 try {
				decodedCode = java.net.URLDecoder.decode(code, StandardCharsets.UTF_8.name());
			} catch (UnsupportedEncodingException e) {

			}
			
			HttpHeaders headers = new HttpHeaders();
			
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			
			  MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			    params.add("client_id", clientID);
			    params.add("client_secret", clientPW);
			    params.add("code", decodedCode);
			    params.add("grant_type", "authorization_code");
			    params.add("redirect_uri", "http://localhost:5500/ttest/aa.html");
//			    params.add("redirect_uri", "http://localhost:8080/api/oauth/login");

			   HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);
				
			   RestTemplate rt = new RestTemplate();
			  
			ResponseEntity<String> accessTokenResponse = rt.exchange(
				    	"https://oauth2.googleapis.com/token",
				        HttpMethod.POST,
				        accessTokenRequest,
				        String.class
				    );
				    
				ObjectMapper objectMapper = new ObjectMapper();
				
				AccessTokenRequestDTO token = null;
				
				try {
					token = objectMapper.readValue(accessTokenResponse.getBody(),AccessTokenRequestDTO.class);
				} catch (JsonMappingException e1 ) {
					e1.printStackTrace();
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				
				String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com" + "/tokeninfo").queryParam("id_token", token.getId_token()).toUriString();

		        String resultJson = rt.getForObject(requestUrl, String.class);
		        
		        System.out.println(resultJson.toString());	
		        
		        MemberDTO member = null;
		        
		        GoogleUserInfo googleUserInfo = null; 
		    	
				try {
					googleUserInfo = objectMapper.readValue(resultJson,GoogleUserInfo.class);
					
					member = MemberDTO.builder()
							.memberID(googleUserInfo.getEmail())
							//비밀번호 임의 값 생성 로그인시 직접적으로 치지 않음
							.memberPW("1111")
							.nickname(googleUserInfo.getName())
							//핸드폰 번호 임의 값 생성
							.phoneNumber("01012341234")
							.build();
					
					member = check(member);
			
					
					return member;
	
				} catch (JsonMappingException e1 ) {
					e1.printStackTrace();
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
		        
		        return new MemberDTO();
	}
	

	@Override
	public MemberDTO check(MemberDTO dto) {
		
		//DB에 아이디가 있는지 확인 중
		Optional<MemberVO> member = memberDAO.findMemberbyMemberID(dto.getMemberID());
		
		if(!(member.isPresent())){
			
			memberService.register(dto);
			
			dto.setRole("MEMBER");
			dto.setMemberPW("");
			
			return dto;
		}

		//DB에 회원이 있으면 정보 반환
		MemberDTO memberDTO = memberService.voTOdto(member.get());
		
		memberDTO.setRole("ROLE_"+memberDTO.getRole());
		
		return memberDTO;
	}
	
	public void loginSuccess(MemberDTO memberDTO, HttpServletRequest request) {
		
		PrincipalDetails principalDetails = new PrincipalDetails(memberDTO); 	
		request.setAttribute(SECRETKEY_HEADER_STRING, TOKEN_PREFIX + CreateJWTToken(principalDetails));
		request.setAttribute("refreshToken", TOKEN_PREFIX + CreateJWTRefreshToken(principalDetails));
		request.setAttribute("id", memberDTO.getMemberID());

		
		LoginController loginController = new LoginController(jwtTokkenDAO, null, memberDAO);
		
		loginController.memberLogin(request);
		
		
	}



}
