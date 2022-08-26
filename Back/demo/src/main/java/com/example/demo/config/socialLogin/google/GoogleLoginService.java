package com.example.demo.config.socialLogin.google;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.dto.MemberDTO;

public interface GoogleLoginService {
	
	MemberDTO login(String getHeader);
	
	MemberDTO check(MemberDTO memberDTO);
	
	void loginSuccess(MemberDTO memberDTO, HttpServletRequest request)
;}
