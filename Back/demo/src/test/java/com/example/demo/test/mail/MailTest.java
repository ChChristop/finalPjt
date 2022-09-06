package com.example.demo.test.mail;

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
	
//	@Test
//	public void test() {
//		
////		boolean result = mailService.sendMail("kimwpdyd@naver.com");
//		
//		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJraW13cGR5ZEBuYXZlci5jb20iLCJleHAiOjE2NjI1MzY4ODIsIm1lbWJlcklEIjoia2ltd3BkeWRAbmF2ZXIuY29tIn0.EGD0UZLVBpDztDsynrN4jpxSrSQpeTDz6gQwMqPn9gmWfd6drjUT4ELRZo1Vw38ps6NmsKygtbAHaSJIAjHy-w";
//
//		String result = mailService.checkToken(token);
//		
//		System.out.println(result);
//	}
	
	
	@Test
	public void test() {
		
		MemberDTO memberDTO = MemberDTO.builder()
				.memberID("kimwpdyd@naver.com")
				.memberPW("4568")
				.build();
		
		
		boolean result = memberService.changePW(memberDTO);
		
		System.out.println(result);
		
	}
	
}
