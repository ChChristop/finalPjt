package com.example.demo.test.security;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.RefrigeratorDAO;
import com.example.demo.vo.RefrigeratorVO;

@SpringBootTest
public class RefrigeratorTest {

	@Autowired
	private RefrigeratorDAO refrigeratorDAO;

//	냉장고 재료 조회
//	@Test
//	public void Test() {
//		
//		List<RefrigeratorDTO> result = refrigeratorDAO.findRefrigeratorDAObyMnum(100);
//		
//		result.stream().forEach(
//				i->{
//					System.out.print(i.getIname());
//					System.out.print(i.getIngrnum());
//					System.out.print(i.getExpirationDate());
//					System.out.println();
//				}
//				
//				);
//		
//	}
//	

//	냉장고 재료 삭제 테스트
//	@Test
//	public void test() {
//		
//		
//		refrigeratorDAO.deleteIngredientByRefrigerator(100, 20);
//	}
//	

//	냉장고 넣기
	@Test
	public void test() {

		for (int j = 4; j < 104; j++) {
			for (int i = 0; i < 4; i++) {
				Random random = new Random();
				
				String[] rr = {"소고기","돼지고기","달걀","고추장"};

				long result = random.nextInt(600) + 1;

				RefrigeratorVO refrigeratorVO = new RefrigeratorVO();

				refrigeratorVO.setMnum(j);
				
				refrigeratorVO.setIname(rr[i]);

				refrigeratorVO.setExpirationDate(LocalDate.now().plusDays(7));

				refrigeratorDAO.addIngredientToRefrigerator(refrigeratorVO);

			}
		}
	}

}
