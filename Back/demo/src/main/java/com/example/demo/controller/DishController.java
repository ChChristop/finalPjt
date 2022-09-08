package com.example.demo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.Constants;
import com.example.demo.service.DishService;
import com.example.demo.vo.Dish;
import com.example.demo.vo.DishComm;
import com.example.demo.vo.DishDB;

import groovy.util.logging.Slf4j;

@RestController
@RequestMapping("/api/dish")
@Slf4j
public class DishController {
	
	@Autowired
	DishService dishService;
	
	@Value("${a.imgdir}")
	String fdir;

	@Value("${DishDBuploadPath}")
	String dbUploadPath;
	 
	String ip = Constants.IP_PORT;
	/*
	 * dishDB에서 가져오기 
	 */
	@GetMapping("/get")
	public List<Map<String, Object>> get() {

		//DB에서 받아온 모양
		List<Map<String, Object>> DBlist =  dishService.get();
		
		//리턴해줄 List모양
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(Map<String, Object> resultMap: DBlist) {
			Map<String, Object> map = new HashMap<>();
			
			map.put("hit", resultMap.get("hit")); //조회수
			map.put("writer", resultMap.get("writer")); //작성자
			map.put("date", resultMap.get("date")); //작성시간
			map.put("dish_num", resultMap.get("rcp_seq")); //고유번호
			map.put("dish_name", resultMap.get("rcp_nm"));  //음식명
			map.put("cookery", resultMap.get("rcp_way2")); //조리방법
			map.put("mainIMG", resultMap.get("att_file_no_main")); //메인이미지
			
			
			
			//조리 재료
			String ingSTR = resultMap.get("rcp_parts_dtls").toString();
			ingSTR = ingSTR.replace("재료","");
			ingSTR = ingSTR.replaceAll("\n",", ");
			
			map.put("ingredient", ingSTR); 
			
			//조리방법 불러오기 
			List<String> recipe = new ArrayList<>();
	
			for(int i = 0; i < 20; i++) {
				String manualId = "manual";
				String manualIdNum = "";
				if(i<9) {
					manualIdNum = "0"+String.valueOf(i+1);
				}else {
					manualIdNum = String.valueOf(i+1);
				}
				manualId += manualIdNum;
				if(!resultMap.get(manualId).toString().isEmpty()) {
					String manualStr = resultMap.get(manualId).toString();
					manualStr = manualStr.replace("\n","");
					recipe.add(i,manualStr);
				}
			}
			map.put("recipe", recipe); //조리방법
			
			List<String> imgList = new ArrayList<>();
		
			for(int i = 0; i < 20; i++) {
				String imgId = "manual_img";
				String imgIdNum = "";
				if(i<9) {
					imgIdNum = "0"+String.valueOf(i+1);
				}else {
					imgIdNum = String.valueOf(i+1);
				}
				
				imgId += imgIdNum;
				
				if(!resultMap.get(imgId).toString().isEmpty()) {
					String imgStr = resultMap.get(imgId).toString();
					imgStr = imgStr.replace("\n","");
					imgList.add(i,imgStr);
				}
			}

			map.put("imgList", imgList); //조리이미지

			result.add(map);
		}
		
		return result;
	}
	
	/*
	 * 댓글 추가 
	 */
	@PostMapping("/comm/add/{mnum}/{RCP_SEQ}")
	public String commAdd(@ModelAttribute DishComm dishComm, @PathVariable int mnum, @PathVariable String RCP_SEQ) {
		
		dishComm.setMnum(mnum);
		dishComm.setRCP_SEQ(RCP_SEQ);
		
		String str = "";
		int i = dishService.commAdd(dishComm);
		
		if(i > 0) {
			str = "댓글이 등록되었습니다.";
		}else {
			str = "댓글 등록에 실패하였습니다.";
		}
		
		return str;
	}
	
	/*
	 * 댓글 삭제(작성한 사람만 삭제 가능)  
	 */
	@DeleteMapping("/comm/delete/{mnum}/{RCP_SEQ}")
	public String commDelete(@ModelAttribute DishComm dishComm, @PathVariable int mnum, @PathVariable String RCP_SEQ) {
		
		dishComm.setMnum(mnum);
		dishComm.setRCP_SEQ(RCP_SEQ);
		
		String str = "";
		int i = dishService.commDelete(dishComm);
		
		if(i > 0) {
			str = "댓글이 삭제되었습니다.";
		}else {
			str = "댓글 삭제에 실패하였습니다.";
		}
		
		return str;
	}
	
	/*
	 * 댓글 수정(작성한 사람만 수정 가능)  
	 */
	@PutMapping("/comm/edit/{mnum}/{RCP_SEQ}")
	public String commEdit(@ModelAttribute DishComm dishComm, @PathVariable int mnum, @PathVariable String RCP_SEQ) {
		
		dishComm.setMnum(mnum);
		dishComm.setRCP_SEQ(RCP_SEQ);
		
		String str = "";
		int i = dishService.commEdit(dishComm);
		
		if(i > 0) {
			str = "댓글이 수정되었습니다.";
		}else {
			str = "댓글 수정에 실패하였습니다.";
		}
		
		return str;

	}
	
	/*
	 * 레시피 검색 기능
	 * 검색 일치 내용이 없을 경우 ?
	 */
	@GetMapping("/search")
	public List<Map<String, Object>> search(@RequestParam String select, @RequestParam String searchI) {
		
		List<Map<String, Object>> dishList = dishService.search(select,searchI);
		
		return dishList;
	}
		
	/*
	 * 음식 추가
	 */
	@PostMapping("/add/{mnum}")
	public String add(@ModelAttribute DishDB dish, @PathVariable int mnum,
			@RequestParam("file01") MultipartFile file01,
			@RequestParam("file02") MultipartFile file02,
			@RequestParam("file03") MultipartFile file03,
			@RequestParam("file04") MultipartFile file04,
			@RequestParam("file05") MultipartFile file05,
			@RequestParam("file06") MultipartFile file06,
			@RequestParam("file07") MultipartFile file07,
			@RequestParam("file08") MultipartFile file08,
			@RequestParam("file09") MultipartFile file09,
			@RequestParam("file10") MultipartFile file10) throws Exception {
		
		
		if(!file01.isEmpty()) {
			String savedName = file01.getOriginalFilename();
			savedName = uploadFile(savedName, file01.getBytes());
			dish.setMANUAL_IMG01(ip+"/ate/" + savedName);
		}
		
		if(!file02.isEmpty()) {
			String savedName = file02.getOriginalFilename();
			savedName = uploadFile(savedName, file02.getBytes());
			dish.setMANUAL_IMG02(ip+"/ate/" + savedName);
			}

		if(!file03.isEmpty()) {
			String savedName = file03.getOriginalFilename();
			savedName = uploadFile(savedName, file03.getBytes());
			dish.setMANUAL_IMG03(ip+"/ate/" + savedName);
			}
		
		if(!file04.isEmpty()) {
			String savedName = file04.getOriginalFilename();
			savedName = uploadFile(savedName, file04.getBytes());
			dish.setMANUAL_IMG04(ip+"/ate/" + savedName);
			}
		
		if(!file05.isEmpty()) {
			String savedName = file05.getOriginalFilename();
			savedName = uploadFile(savedName, file05.getBytes());
			dish.setMANUAL_IMG05(ip+"/ate/" + savedName);
			}
		
		if(!file06.isEmpty()) {
			String savedName = file06.getOriginalFilename();
			savedName = uploadFile(savedName, file06.getBytes());
			dish.setMANUAL_IMG06(ip+"/ate/" + savedName);
			}
		
		if(!file07.isEmpty()) {
			String savedName = file07.getOriginalFilename();
			savedName = uploadFile(savedName, file07.getBytes());
			dish.setMANUAL_IMG07(ip+"/ate/" + savedName);
			}
		
		if(!file08.isEmpty()) {
			String savedName = file08.getOriginalFilename();
			savedName = uploadFile(savedName, file08.getBytes());
			dish.setMANUAL_IMG08(ip+"/ate/" + savedName);
			}
		
		if(!file09.isEmpty()) {
			String savedName = file09.getOriginalFilename();
			savedName = uploadFile(savedName, file09.getBytes());
			dish.setMANUAL_IMG09(ip+"/ate/" + savedName);
			}
		
		if(!file10.isEmpty()) {
			String savedName = file10.getOriginalFilename();
			savedName = uploadFile(savedName, file10.getBytes());
			dish.setMANUAL_IMG10(ip+"/ate/" + savedName);
			}
		
		int num = dishService.getNum();

		dish.setRCP_SEQ(Integer.toString(num));
		
		String str = "";
		
		int i = dishService.add(dish, mnum);
		
		if(i > 0) {
			str = dish.getRCP_SEQ() + "이 등록되었습니다.";
		}else {
			str = "글 등록에 실패하였습니다.";
		}
		
		return str;
		
	}
	/*
	 * 음식 정보 수정하기
	 */
	@PutMapping("/edit/{RCP_SEQ}/{mnum}")
	public String edit(@ModelAttribute DishDB dish, @ModelAttribute Dish dish1, @PathVariable int mnum, @PathVariable int RCP_SEQ,
			@RequestParam("file01") MultipartFile file01,
			@RequestParam("file02") MultipartFile file02,
			@RequestParam("file03") MultipartFile file03,
			@RequestParam("file04") MultipartFile file04,
			@RequestParam("file05") MultipartFile file05,
			@RequestParam("file06") MultipartFile file06,
			@RequestParam("file07") MultipartFile file07,
			@RequestParam("file08") MultipartFile file08,
			@RequestParam("file09") MultipartFile file09,
			@RequestParam("file10") MultipartFile file10) throws Exception {
		
		

//		if(!file01.isEmpty()) {
//		String pname = file01.getOriginalFilename();
//		File dest = new File(fdir + "/" + file01.getOriginalFilename());
//		file01.transferTo(dest);
//		dish.setMANUAL_IMG01("/img/"+pname);
//		}
//		
//		if(!file02.isEmpty()) {
//			String pname = file02.getOriginalFilename();
//			File dest = new File(fdir + "/" + file02.getOriginalFilename());
//			file02.transferTo(dest);
//			dish.setMANUAL_IMG02("/img/"+pname);
//			}
//
//		if(!file03.isEmpty()) {
//			String pname = file03.getOriginalFilename();
//			File dest = new File(fdir + "/" + file03.getOriginalFilename());
//			file03.transferTo(dest);
//			dish.setMANUAL_IMG03("/img/"+pname);
//			}
//		
//		if(!file04.isEmpty()) {
//			String pname = file04.getOriginalFilename();
//			File dest = new File(fdir + "/" + file04.getOriginalFilename());
//			file04.transferTo(dest);
//			dish.setMANUAL_IMG04("/img/"+pname);
//			}
//		
//		if(!file05.isEmpty()) {
//			String pname = file05.getOriginalFilename();
//			File dest = new File(fdir + "/" + file05.getOriginalFilename());
//			file05.transferTo(dest);
//			dish.setMANUAL_IMG05("/img/"+pname);
//			}
//		
//		if(!file06.isEmpty()) {
//			String pname = file06.getOriginalFilename();
//			File dest = new File(fdir + "/" + file06.getOriginalFilename());
//			file06.transferTo(dest);
//			dish.setMANUAL_IMG06("/img/"+pname);
//			}
//		
//		if(!file07.isEmpty()) {
//			String pname = file07.getOriginalFilename();
//			File dest = new File(fdir + "/" + file07.getOriginalFilename());
//			file07.transferTo(dest);
//			dish.setMANUAL_IMG07("/img/"+pname);
//			}
//		
//		if(!file08.isEmpty()) {
//			String pname = file08.getOriginalFilename();
//			File dest = new File(fdir + "/" + file08.getOriginalFilename());
//			file08.transferTo(dest);
//			dish.setMANUAL_IMG08("/img/"+pname);
//			}
//		
//		if(!file09.isEmpty()) {
//			String pname = file09.getOriginalFilename();
//			File dest = new File(fdir + "/" + file09.getOriginalFilename());
//			file09.transferTo(dest);
//			dish.setMANUAL_IMG09("/img/"+pname);
//			}
//		
//		if(!file10.isEmpty()) {
//			String pname = file10.getOriginalFilename();
//			File dest = new File(fdir + "/" + file10.getOriginalFilename());
//			file10.transferTo(dest);
//			dish.setMANUAL_IMG10("/img/"+pname);
//			}
//		
		if(!file01.isEmpty()) {
			String savedName = file01.getOriginalFilename();
			savedName = uploadFile(savedName, file01.getBytes());
			dish.setMANUAL_IMG01(ip+"/ate/" + savedName);
		}
		
		if(!file02.isEmpty()) {
			String savedName = file02.getOriginalFilename();
			savedName = uploadFile(savedName, file02.getBytes());
			dish.setMANUAL_IMG02(ip+"/ate/" + savedName);
			}

		if(!file03.isEmpty()) {
			String savedName = file03.getOriginalFilename();
			savedName = uploadFile(savedName, file03.getBytes());
			dish.setMANUAL_IMG03(ip+"/ate/" + savedName);
			}
		
		if(!file04.isEmpty()) {
			String savedName = file04.getOriginalFilename();
			savedName = uploadFile(savedName, file04.getBytes());
			dish.setMANUAL_IMG04(ip+"/ate/" + savedName);
			}
		
		if(!file05.isEmpty()) {
			String savedName = file05.getOriginalFilename();
			savedName = uploadFile(savedName, file05.getBytes());
			dish.setMANUAL_IMG05(ip+"/ate/" + savedName);
			}
		
		if(!file06.isEmpty()) {
			String savedName = file06.getOriginalFilename();
			savedName = uploadFile(savedName, file06.getBytes());
			dish.setMANUAL_IMG06(ip+"/ate/" + savedName);
			}
		
		if(!file07.isEmpty()) {
			String savedName = file07.getOriginalFilename();
			savedName = uploadFile(savedName, file07.getBytes());
			dish.setMANUAL_IMG07(ip+"/ate/" + savedName);
			}
		
		if(!file08.isEmpty()) {
			String savedName = file08.getOriginalFilename();
			savedName = uploadFile(savedName, file08.getBytes());
			dish.setMANUAL_IMG08(ip+"/ate/" + savedName);
			}
		
		if(!file09.isEmpty()) {
			String savedName = file09.getOriginalFilename();
			savedName = uploadFile(savedName, file09.getBytes());
			dish.setMANUAL_IMG09(ip+"/ate/" + savedName);
			}
		
		if(!file10.isEmpty()) {
			String savedName = file10.getOriginalFilename();
			savedName = uploadFile(savedName, file10.getBytes());
			dish.setMANUAL_IMG10(ip+"/ate/" + savedName);
			}
		
		dish.setRCP_SEQ(Integer.toString(RCP_SEQ));
		
	
		String str = "";
		
		int i = dishService.edit(dish, dish1, RCP_SEQ, mnum);
		
		if(i > 0) {
			str = dish.getRCP_SEQ() + "이 수정되었습니다.";
		}else {
			str = "글 수정에 실패하였습니다.";
		}
		
		return str;
		
	}

	

	//좋아요 확인 : 로그인한 회원 같이 받아야함!!!
	//댓글도 같이 불러오기
	@GetMapping("/get/{RCP_SEQ}/{mnum}")
	public Map<String,Object> getOne(@PathVariable int RCP_SEQ, @PathVariable int mnum) {
		
		Map<String,Object> result = new HashMap<>();
		/*
		 * 조회수 +1 
		 */
		dishService.upHit(RCP_SEQ);
		/*
		 * 프론트에서 회원 아이디 받기
		 * 좋아요 체크했나 확인하는 메소드
		 */

		int check = dishService.dishLike(mnum,RCP_SEQ);
		
		String str = "";
			if(check == 1) { //본 게시물 좋아요 눌렀음
				str = "liked";
			}	
			

		Map<String,Object> resultMap = dishService.getOne(RCP_SEQ);
		System.out.println("얍 :::: ");
	
		Map<String, Object> map = new HashMap<>();
	
		map.put("liked", str); //좋아요 누른 회원
		map.put("hit", resultMap.get("hit")); //조회수
		
		map.put("dish_num", resultMap.get("rcp_seq")); //고유번호
		map.put("dish_name", resultMap.get("rcp_nm"));  //음식명
		map.put("cookery", resultMap.get("rcp_way2")); //조리방법
		map.put("mainIMG", resultMap.get("att_file_no_main")); //메인이미지
		
		
		
		//조리 재료
		//조회를 위한 재료는 다시 재가공 필요! 
		
//		String ingSTR = resultMap.get("rcp_parts_dtls").toString();
//		ingSTR = ingSTR.replace("재료","");
//		ingSTR = ingSTR.replaceAll("\n",", ");
//		List<String> ingList = new ArrayList<>();
//		for(int i = 0; i<ingSTR.split(",").length; i++) {
//			ingList.add(ingSTR.split(",")[i]);
//		}
		String ingSTR = resultMap.get("rcp_parts_dtls").toString();
		ingSTR = ingSTR.replace("재료","");
		ingSTR = ingSTR.replaceAll("\n",", ");
		map.put("ingredient", ingSTR); 
		
		//조리방법 불러오기 
		List<String> recipe = new ArrayList<>();

		for(int i = 0; i < 20; i++) {
			String manualId = "manual";
			String manualIdNum = "";
			if(i<9) {
				manualIdNum = "0"+String.valueOf(i+1);
			}else {
				manualIdNum = String.valueOf(i+1);
			}
			manualId += manualIdNum;

			if(!resultMap.get(manualId).toString().isEmpty()) {
				String manualStr = resultMap.get(manualId).toString();
				manualStr = manualStr.replace("\n","");
				recipe.add(i,manualStr);
			}
			}
		
		map.put("recipe", recipe); //조리방법
		
		List<String> imgList = new ArrayList<>();
	
		for(int i = 0; i < 20; i++) {
			String imgId = "manual_img";
			String imgIdNum = "";
			if(i<9) {
				imgIdNum = "0"+String.valueOf(i+1);
			}else {
				imgIdNum = String.valueOf(i+1);
			}
			
			imgId += imgIdNum;
			
			if(!resultMap.get(imgId).toString().isEmpty()) {
				String imgStr = resultMap.get(imgId).toString();
				imgStr = imgStr.replace("\n","");
				imgList.add(i,imgStr);
			}
		}  

		map.put("imgList", imgList); //조리이미지
		
		//댓글 불러오기
		List<DishComm> commList = dishService.commGet(RCP_SEQ);
		
		map.put("commList", commList);
		
		result.put("result",map);
		
		
		
		
		return result;
	}
	
	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		
		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString()+"_"+originalName;
		File target = new File(dbUploadPath, savedName);
		
		FileCopyUtils.copy(fileData, target);
	
		return savedName;
	}
	
	/*
	 * 음식 정보 삭제하기 
	 */
	@DeleteMapping("/delete/{RCP_SEQ}")
	public String delete(@PathVariable int RCP_SEQ) {
		//정말 삭제하시겠습니까? 질문 하는거...(팝업)
		
		String str = "";
		
		int i = dishService.delete(RCP_SEQ);
		
		if(i>0) {
			str = "글이 삭제되었습니다.";
		}else {
			str = "글 삭제에 실패하였습니다.";
		}
		
		
		return str;
	}
	
	/*
	 * 좋아요 등록 / 취소 이부분 어떻게 받을지 프론트랑 상의하기 
	 * 좋아요 등록 / 취소
	 * 유저 번호도 같이 받아야해! 
	 */
	@PostMapping("/like/{RCP_SEQ}/{mnum}")
	public String goDishLike(@PathVariable int RCP_SEQ, @PathVariable int mnum) {

		String str ="";
			//좋아요 안누른 상태면, 좋아요 등록
		if (dishService.dishLike(mnum, RCP_SEQ) == 0) {
			
			dishService.goDishLike(RCP_SEQ, mnum);
			
			str = "좋아요 등록!";
			}else {
			//좋아요 누른 상태면, 좋아요 해제 
				dishService.goDishDislike(RCP_SEQ, mnum);
			str = "좋아요 해제!";
			}
		
		return str;
	}
	

}
