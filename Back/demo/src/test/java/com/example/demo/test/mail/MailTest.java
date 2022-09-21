package com.example.demo.test.mail;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.mail.MailService;
import com.example.demo.service.memberService.MemberService;

@SpringBootTest
public class MailTest {

	@Autowired
	private MailService mailService;
	
	@Autowired
	private MemberService memberService;
	
	@Test
	public void test() {
		
		boolean result = mailService.sendMail("kimwpdyd@gmail.com");
//		
//		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJraW13cGR5ZEBuYXZlci5jb20iLCJtbnVtIjoiMTc2IiwiZXhwIjoxNjYyNjE2MjQwLCJtZW1iZXJJRCI6ImtpbXdwZHlkQG5hdmVyLmNvbSJ9.8Ml2MVwrsyUu9G9aHNVF94x1MMNxpdzU2U_ypSRLAaEz80NP2ti-YMs8LhCOe_HNXxAsWzaoeqXEiyuGQbgYPg";
//
//		String result[] = mailService.checkToken(token);
//		
//		System.out.println(Arrays.toString(result));
	}
	
	
//	@Test
//	public void test() {
//		
//		MemberDTO memberDTO = MemberDTO.builder()
//				.memberID("kimwpdyd@naver.com")
//				.memberPW("4568")
//				.build();
//		
//		
//		boolean result = memberService.changePW(memberDTO);
//		
//		System.out.println(result);
//		
//	}
	
}
