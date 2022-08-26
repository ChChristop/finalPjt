package com.example.demo.config.socialLogin.google;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserInfo {

	private String iss;
	
	private String azp;
	
	private String aud;
	
	private String sub;
	
	private String email;
	
	private String email_verified;
	
	private String at_hash;
	
	private String name;
	
	private String picture;
	
	private String given_name;
	
	private String family_name;
	
	private String locale;
	
	private Date iat;
	
	private Date exp;
	
	private String alg;
	
	private String kid;
	
	private String typ;
}
