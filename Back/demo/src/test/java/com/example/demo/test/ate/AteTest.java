package com.example.demo.test.ate;

import java.util.Random;

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
	public void ateTest1() {

		for (int i = 1; i < 301; i++) {

			Random r = new Random();
			Random rr = new Random();
			int rr1 = rr.nextInt(0, 3);
			int r1 = r.nextInt(0, abc.length);
			
			for (int k = 0; k < rr1; k++) {
				
				Ate ate1 = new Ate();
				
				Random rrr = new Random();
				int r3 = rrr.nextInt(0, abc.length);
				Random rrrr = new Random();
				int r4 = rrrr.nextInt(0, 3);
				
				ate1.setMnum(i);
				ate1.setRCP_SEQ(Integer.toString(abc[r3]));
				ate1.setAte_like(0);
				ate1.setAte_hit(0);
				ate1.setAte_content(bcd[r4]);

				ate.add(ate1);
			}
		}

	}

}
