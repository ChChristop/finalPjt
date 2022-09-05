package com.example.demo.test.point;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.point.PointDAO;
import com.example.demo.service.AteServiceImpl;
import com.example.demo.service.DishServiceImpl;
import com.example.demo.vo.Ate;
import com.example.demo.vo.DishComm;

@SpringBootTest
public class PointTest {
	
	@Autowired
	private AteServiceImpl ateServiceImpl;
	
	@Autowired
	private DishServiceImpl dishImpl;
	
	@Autowired
	private PointDAO pointDAO;
	
	public Ate ate;
	
	public DishComm dishComm;
//	
//	@Test
//	public void test4() {
//		
//		List<PointDTO> result = pointDAO.findUserPointbyMnum(97);
//		
//		result.stream().forEach(i->{
//			System.out.println (i.getMnum());
//			System.out.println (i.getPointID());
//			System.out.println (i.getPoint());
//			System.out.println (i.getContent());
//			
//		});
//	}
	
//	@Test
//	public void test3() {
//		
//		DishComm dishComm = new DishComm();
//		
//		dishComm.setMnum(97);
//		dishComm.setRCP_SEQ("271");
//		
//		dishImpl.commDelete(dishComm);
//	}
	
	
//	@Test
//	public void test3() {
//		
//		DishComm dishComm = new DishComm();
//		
//		dishComm.setMnum(100);
//		dishComm.setRCP_SEQ("271");
//		dishComm.setContent("댓글 구현 중");
//		
//		dishImpl.commAdd(dishComm);
//	}
//	
//	@Test
//	public void test2() {
//		
//		ateServiceImpl.delete(17, 97);
//	}
	
	@Test
	public void test() {
		
		ate = new Ate();
		
		ate.setMnum(100);
		ate.setRCP_SEQ("271");
		ate.setAte_content("맛있게 먹음");
		ate.setAte_picture("사진경로");
		ate.setAte_like(0);
		ate.setAte_hit(0);
		
		
		ateServiceImpl.add(ate);
		
	}
	
	
	

}
