package com.example.demo.test.security;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dao.AdminDAO;
import com.example.demo.vo.Admin;

import lombok.AllArgsConstructor;

@SpringBootTest
public class Test123 {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminDAO adminDAO;
	
//	@Test
//	public void testEncode1() {
//		
//		List<Admin> result = adminDAO.getAllAdminList();
//		
//		System.out.println("==========================================");
//		result.stream().forEach(admin->{
//			System.out.println(admin.getAdminID());
//			System.out.println(admin.getNickName());
//			System.out.println(admin.getPhonNumber());
//		});
//		System.out.println("==========================================");
//
//	}
	
	@Test
	public void testEncode() {
		
		
	
		Admin admin = Admin.builder()
				.adminID("kimwpdyd3")
				.nickName("김제용")
				.adminPW(passwordEncoder.encode("1234"))
				.role("ADMIN,MEMBER")
				.phonNumber("01028415580")
				.build();
		
		
		adminDAO.addAdmin(admin);
		
	}
	
}
