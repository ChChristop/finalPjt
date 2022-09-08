package com.example.demo.test.ranking;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.MemberDAO;

@SpringBootTest
public class ranking {
	
	@Autowired
	private MemberDAO memberdao;
	
	@Test
	public void test() {
		
		List<Map<String,Object>> result =   memberdao.topUser();
		
		
		System.out.println(result.size());
		
		
	}

}
