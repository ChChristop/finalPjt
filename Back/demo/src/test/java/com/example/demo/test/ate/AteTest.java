package com.example.demo.test.ate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultVO;
import com.example.demo.service.AteService;
import com.example.demo.vo.Ate;

@SpringBootTest
public class AteTest {

	@Autowired
	private AteService ate;
	
	
	@Test
	public void ateTest1() {
		
		PageRequestDTO dto = new PageRequestDTO();
		
		dto.setterChange();

		
		PageResultVO<Ate> result = ate.getUserAteList(dto, 100);
		
		result.getDtoList().stream().forEach(i-> System.out.println(i.getMnum()));
	}
	
	
	
	
	
}
