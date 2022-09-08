package com.example.demo.controller.chagePW;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/account/")
@RequiredArgsConstructor
public class ChangePW {
	
//	private final EmailService emailService;
	
	@PostMapping("/reset-pw")
	public void sendMail(){
		
	}

}
