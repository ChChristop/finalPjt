package com.example.demo.test.security;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.IngredientsDAO;
import com.example.demo.vo.IngredientsVO;

@SpringBootTest
public class IngredientsTest {
	

	@Autowired
	private IngredientsDAO ingredientsDAO;
	
	@Test
	public void test() {
		
		
//		IngredientsVO ingredientsVO = new IngredientsVO();
//		
//		ingredientsVO.setIngrnum(650);
//		ingredientsVO.setIname("아이스크림");
//		ingredientsVO.setKeyword1("구구콘");
//		ingredientsVO.setKeyword2("스크류바");

//		ingredientsDAO.addIngredients(ingredientsVO);
		
		List<IngredientsVO> result =  ingredientsDAO.ingredientsAllList();
		
		result.stream().forEach(in->System.out.println(in.getIname()));

	}
	

}
