package com.example.demo.config.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.auth.AdminCheck;
import com.example.demo.config.auth.PrincipalDetails;

@Component
public class JwtProperties {

	public static final String TOKEN_PREFIX = "Bearer ";

	private static String SECRETKEY;

	public static final int SECRETKEY_EXPIRATION_TIME = 600000; // 5초; //600000;// 10분

	public static final String SECRETKEY_HEADER_STRING = "Authorization";

	private static String REFRESHKEY;

	// 10일 static 1일 86,400,000 (1/1000초)
	public static final int REFRESHKEY_EXPIRATION_TIME = 864000000; // 10일

	public static final String REFRESHKEY_HEADER_STRING = "RefreshToken";

	public static String CreateJWTToken(PrincipalDetails principalDetails) {
		
		boolean check = AdminCheck.check;
				
		String jwtToken = "";
		
		if (check) {

			jwtToken = JWT.create().withSubject(principalDetails.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + SECRETKEY_EXPIRATION_TIME))
					.withClaim("anum", principalDetails.getAdminDTO().getAnum())
					.withClaim("adminID", principalDetails.getAdminDTO().getAdminID())
					.withClaim("adminCheck", "true")
					.withClaim("role", principalDetails.getAuthorities().toString())
			
					.sign(Algorithm.HMAC512(SECRETKEY));
		} else {
			
			jwtToken = JWT.create().withSubject(principalDetails.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + SECRETKEY_EXPIRATION_TIME))
					.withClaim("mum", principalDetails.getMemberDTO().getMnum())
					.withClaim("memberID", principalDetails.getMemberDTO().getMemberID())
					.withClaim("adminCheck", "false")
					.withClaim("role", principalDetails.getAuthorities().toString())
					
					.sign(Algorithm.HMAC512(SECRETKEY));
		}

		return jwtToken;
	}

	
	public static String CreateJWTRefreshToken(PrincipalDetails principalDetails) {
		
		String jwtRefreshToken = "";
		
		boolean check = AdminCheck.check;
		
		if (check) {
			
			jwtRefreshToken = JWT.create().withSubject(principalDetails.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + REFRESHKEY_EXPIRATION_TIME))
					.withClaim("adminID", principalDetails.getAdminDTO().getAdminID())
					.withClaim("adminCheck", "true")
					.sign(Algorithm.HMAC512(REFRESHKEY));
		}else {
			jwtRefreshToken = JWT.create().withSubject(principalDetails.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + REFRESHKEY_EXPIRATION_TIME))
					.withClaim("memberID", principalDetails.getMemberDTO().getMemberID())
					.withClaim("adminCheck", "false")
					.sign(Algorithm.HMAC512(REFRESHKEY));
		}

		return jwtRefreshToken;
	}

	
	public static String[] vaildateJwtToken(String jwtToken, boolean check) throws Exception {
		
		if (jwtToken == "") {
			return new String[0];
		}

		String key = (check) ? SECRETKEY : REFRESHKEY;

		String[] result = new String[3];

		LocalDateTime expireLocalDateTimeByJwtToken;

		try {
			
			Date expireDateByJwtToken = JWT.require(Algorithm.HMAC512(key)).build().verify(jwtToken).getExpiresAt();

			expireLocalDateTimeByJwtToken = LocalDateTime.ofInstant(expireDateByJwtToken.toInstant(),
					ZoneId.systemDefault());

			String expireDateByJwtTokenToString = expireLocalDateTimeByJwtToken
					.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss"));
			
			String getID = "";
			
			String abc = JWT.require(Algorithm.HMAC512(key)).build().verify(jwtToken).getClaim("adminCheck").asString();

			if(abc.equals("true")) {
				
				AdminCheck.check = true;
				
				getID = JWT.require(Algorithm.HMAC512(key)).build().verify(jwtToken).getClaim("adminID").asString();
				
			}else {
				AdminCheck.check = false;
				
				getID = JWT.require(Algorithm.HMAC512(key)).build().verify(jwtToken).getClaim("memberID").asString();
			}

			result[0] = expireDateByJwtTokenToString;
			
			result[1] = getID;
			
			result[2] = jwtToken;

			return result;

		} catch (Exception e) {
			
			return new String[0];
		}

	}
	
	//키 숨기기 위해 setter 사용(static에서는 어쩔 수 없음)
	@Value("${JwtProperties.secretKey}")
	public void setSECRETKEY(String value) {
		SECRETKEY = value;
	}
	
	//키 숨기기 위해 setter 사용(static에서는 어쩔 수 없음
	@Value("${JwtProperties.refreshKey}")
	public void setREFRESHKEY(String value) {
		REFRESHKEY = value;
	}

}
