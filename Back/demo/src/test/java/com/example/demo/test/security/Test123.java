package com.example.demo.test.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import com.example.demo.config.jwt.JwtProperties;
import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.vo.AdminVO;
import com.example.demo.vo.MemberVO;

@SpringBootTest
public class Test123 {

//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
	@Autowired
	private AdminDAO adminDAO;

//	public JwtProperties jwt;
	
//	@Test
//	public void testJwtKey() {
//		System.out.println("jwt properties 테스트 : " + jwt.REFRESHKEY + " " + jwt.SECRETKEY);
//	}
//	
//	
//
//	@Test
//	public void testMemerDAO() {
//		
//		MemberVO member = MemberVO.builder()
//				.memberID("abc@abc.com")
//				.pw(passwordEncoder.encode("1111"))
//				.nickname("abc")
//				.role("MEMBER")
//				.phoneNumber("01012341234")
//				.build();
//		
//		memberDAO.addMember(member);
//	}
	@Test
	public void testEncode1() {
		
		adminDAO.CheckByAdminId("kimwpdyd2");
		
		
	}
	
//	@Test
//	public void testEncode() {
//
//			AdminVO admin = AdminVO.builder()
//					.adminID("final")
//					.nickName("final")
//					.adminPW(passwordEncoder.encode("1234"))
//					.role("ADMIN,MEMBER")
//					.phoneNumber("01012345678")
//					.build();
//			
//			adminDAO.addAdmin(admin);
//
//	}
	
}
