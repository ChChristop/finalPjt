package com.example.demo.test.security;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.IngredientsDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.vo.IngredientsVO;

@SpringBootTest
public class Test123 {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private IngredientsDAO ingredientsDAO;


	
//	@Test
//	public void testInputIngr() {
//		
//		String path = "D:\\abc.txt";
//		
//		
//		try {
//			String str = "";
//			BufferedReader bfr = new BufferedReader(new FileReader(path));
//			
//			while((str=bfr.readLine()) != null) {
//				
////				System.out.println(str);
//				
//				String[] result = new String[4];
//				
//				String[] s=  str.split("	");
//				
//				for(int i = 0; i < s.length; i++) {
//					result[i] = s[i];
//					
//				}
//					
////				System.out.println(Arrays.toString(result));
//			
//				
//			
////				
////				ingredientsDAO.addIngredients(in);
//				
//			}
//			
//			bfr.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//	}
//
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
//	
	
//	@Test
//	public void testEncode() {
//		
//	IntStream.rangeClosed(1, 100).forEach((i)->{
//		
//			AdminVO admin = AdminVO.builder()
//					.adminID("final"+i)
//					.nickName("final"+i)
//					.adminPW(passwordEncoder.encode("1234"))
//					.role("ADMIN,MEMBER")
//					.phoneNumber("01012345678")
//					.build();
//			
//			adminDAO.addAdmin(admin);
//	});
//	}
	
}
