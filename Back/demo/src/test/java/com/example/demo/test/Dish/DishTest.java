package com.example.demo.test.Dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.AteDao;
import com.example.demo.dao.DishDao;
import com.example.demo.dao.point.PointDAO;
import com.example.demo.service.DishService;
import com.example.demo.vo.DishComm;
import com.example.demo.vo.point.PointDescription;
import com.example.demo.vo.point.UserPointVO;

@SpringBootTest
public class DishTest implements PointDescription {

	@Autowired
	private DishService dishService;

	@Autowired
	private DishDao dishDao;

	@Autowired
	private PointDAO pointDAO;
	
	@Autowired
	private AteDao ateDAO;

	int[] abc = { 18, 19, 215, 216, 217, 218, 219, 220, 221, 222, 223, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240,
			241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261,
			262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 28, 280, 281, 282,
			283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 311, 312,
			313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 336, 337, 338, 339, 340, 341, 342, 343, 344,
			345, 346, 347, 348, 349, 350, 351, 352, 353, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366,
			367, 368, 369, 385, 386, 387, 388, 389, 39, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 423, 424, 425,
			426, 427, 441, 442, 443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 470, 471, 472, 473, 474,
			475, 476, 477, 478, 479, 480, 481, 482, 483, 484, 530, 531, 532, 533, 582, 583, 584, 585, 586, 587, 604,
			605, 606, 607, 608, 609, 610, 611, 612, 613, 614, 615, 616, 617, 618, 636, 637, 638, 639, 640, 641, 642,
			643, 644, 645, 669, 670, 671, 672, 673, 674, 677, 85

	};

	String[] bcd = { "GOOD", "Not Bad", "Bad" };

	@Test
	@Transactional(rollbackFor = Exception.class)
	@Commit
	public void tes2() {
		
		try {
			
			DishComm dishComm = new DishComm();
			dishComm.setMnum(100);
			dishComm.setAte_num(20);;
			dishComm.setRCP_SEQ(Integer.toString(19));
			dishComm.setContent("맛있어요!");

			ateDAO.commAdd(dishComm);
			
			UserPointVO vo = new UserPointVO();
			vo.setMnum(dishComm.getMnum());
			vo.setPointID(ATE_COMMENT_PLUS);
			vo.setPoint(ATE_COMMENT_POINT);
			vo.setRCP_SEQ(Integer.parseInt(dishComm.getRCP_SEQ()));

			pointDAO.registerPoint(vo);
			

			System.out.println("[DishServiceImpl] [commAdd] " + dishComm.getMnum());
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

//	@Test
//	public void test1() {
//		for (int i = 1; i < 301; i++) {
//
//			for (int j = 0; j < 2; j++) {
//				
//				Random r = new Random();
//				
//				int r1 = r.nextInt(0, abc.length);
//				
//				Random r2 = new Random();
//				
//				int r3= r2.nextInt(0, 3);
//				
//				
//				DishComm dishComm = new DishComm();
//				
//				dishComm.setMnum(i);
//				dishComm.setRCP_SEQ(Integer.toString(abc[r1]));
//				dishComm.setContent(bcd[r3]);
//
//				String str = "";
//				int k = dishService.commAdd(dishComm);
//				
//				if(k > 0) {
//					str = "댓글이 등록되었습니다.";
//				}else {
//					str = "댓글 등록에 실패하였습니다.";
//				}
//			
//			}
//
//		}
//
//	}

//	@Test
//	public void test() {
//
//		for (int i = 1; i < 301; i++) {
//
//			for (int j = 0; j < 3; j++) {
//				
//				Random r = new Random();
//				int r1 = r.nextInt(0, abc.length);
//				String str = "";
//				// 좋아요 안누른 상태면, 좋아요 등록
//				if (dishService.dishLike(i, abc[r1]) == 0) {
//
//					dishService.goDishLike(abc[r1], i);
//
//					str = "좋아요 등록!";
//				} else {
//					// 좋아요 누른 상태면, 좋아요 해제
//					dishService.goDishDislike(abc[r1], i);
//					str = "좋아요 해제!";
//				}
//			}
//
//		}
//
}
