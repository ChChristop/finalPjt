package com.example.demo.test.security;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.vo.AdminVO;
import com.example.demo.vo.MemberVO;

@SpringBootTest
public class Test123 {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private MemberDAO memberDAO;


//	@Test
//	public void testMemerDAO() {
//		
//		IntStream.rangeClosed(1, 100).forEach((i)->{
//
//		MemberVO member = MemberVO.builder()
//				.memberID("aa"+i+"@abc.com")
//				.memberPW(passwordEncoder.encode("1111"))
//				.nickname("abc"+i)
//				.role("MEMBER")
//				.phoneNumber("01012341234")
//				.build();
//		
//		memberDAO.addMember(member);
//		});
//		}
	
	
	@Test
	public void testEncode() {
		
	IntStream.rangeClosed(1, 100).forEach((i)->{
		
			AdminVO admin = AdminVO.builder()
					.adminID("final"+i)
					.nickName("final"+i)
					.adminPW(passwordEncoder.encode("1234"))
					.role("ADMIN,MEMBER")
					.phoneNumber("01012345678")
					.build();
			
			adminDAO.addAdmin(admin);
	});
	}
	
}
