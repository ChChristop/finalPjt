package com.example.demo.config.socialLogin;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenRequestDTO {
	
	
	private String access_token ;
	
	private Date expires_in;
	
	private String refresh_token;
	
	private String scope;
	
	private String token_type;
	
	private String id_token;


}
