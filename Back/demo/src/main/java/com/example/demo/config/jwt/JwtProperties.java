package com.example.demo.config.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.auth.PrincipalDetails;
import com.github.javaparser.utils.Log;

public interface JwtProperties {

	public final String TOKEN_PREFIX = "Bearer ";

	public final String SECRETKEY = "final";

	public final int SECRETKEY_EXPIRATION_TIME = 600000;// 10분 //5000 5초;  

	public final String SECRETKEY_HEADER_STRING = "Authorization";

	public final String REFRESHKEY = "rfstokken";

	// 10일 1일 86,400,000 (1/1000초)
	public final int REFRESHKEY_EXPIRATION_TIME = 864000000; // 10일

	public final String REFRESHKEY_HEADER_STRING = "Refresh-Token";

	default String CreateJWTToken(PrincipalDetails principalDetails) {
		String jwtToken = JWT.create().withSubject(principalDetails.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + SECRETKEY_EXPIRATION_TIME))
				.withClaim("anum", principalDetails.getAdminDTO().getAnum())
				.withClaim("adminID", principalDetails.getAdminDTO().getAdminID())
				.withClaim("nickname", principalDetails.getAdminDTO().getNickName())
				.withClaim("role", principalDetails.getAuthorities().toString()).sign(Algorithm.HMAC512(SECRETKEY));

		return jwtToken;
	}

	default String CreateJWTRefreshToken(PrincipalDetails principalDetails) {

		String jwtRefreshToken = JWT.create().withSubject(principalDetails.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + REFRESHKEY_EXPIRATION_TIME))
				.withClaim("adminID", principalDetails.getAdminDTO().getAdminID())
				.sign(Algorithm.HMAC512(REFRESHKEY));

		return jwtRefreshToken;
	}

	default String[] vaildateJwtToken(String jwtToken, boolean check) throws Exception {
		
		String key = (check)?SECRETKEY:REFRESHKEY;
		
		String[] result = new String[2];

		LocalDateTime expireLocalDateTimeByJwtToken;
		
		try{
			Date expireDateByJwtToken = JWT.require(Algorithm.HMAC512(key))
					.build()
					.verify(jwtToken)
					.getExpiresAt();
			
			expireLocalDateTimeByJwtToken =  LocalDateTime.ofInstant(
					expireDateByJwtToken.toInstant(),
					ZoneId.systemDefault());
			
			String expireDateByJwtTokenToString = expireLocalDateTimeByJwtToken.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss"));
			
			String getId = JWT.require(Algorithm.HMAC512(key))
					.build()
					.verify(jwtToken)
					.getClaim("adminID")
					.asString();
			
			
			result[0] = expireDateByJwtTokenToString;
			result[1] = getId;
			
			return result;
			
		}catch(Exception e) {
			
			Log.error(e.getMessage());
			
			return new String[0];
			
		}
		
	}

}
