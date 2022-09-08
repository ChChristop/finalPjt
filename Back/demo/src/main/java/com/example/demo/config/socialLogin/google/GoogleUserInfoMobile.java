package com.example.demo.config.socialLogin.google;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleUserInfoMobile {

	
	private String email;
	
	private String familyName;
	
	private String givenName;
	
	private String id;
	
	private String name;
	
	private String photo;
	
	
}
