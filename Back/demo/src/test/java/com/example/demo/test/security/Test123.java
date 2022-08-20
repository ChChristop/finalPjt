package com.example.demo.test.security;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dao.AdminDAO;
import com.example.demo.vo.Admin;

import lombok.AllArgsConstructor;

@SpringBootTest
@AllArgsConstructor
public class Test123 {

	private final PasswordEncoder passwordEncoder;
	
	private final AdminDAO adminDAO;
	
	@Test
	public void testEncode() {
		
		
	
		Admin admin = Admin.builder()
				.adminID("kimwpdyd3")
				.nickName("김제용")
				.adminPW(passwordEncoder.encode("1234"))
				.role("USER,MEMBER")
				.phonNumber("01028415580")
				.build();
		
		
		adminDAO.addAdmin(admin);
		
	}
	
}
