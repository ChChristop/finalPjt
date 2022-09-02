package com.example.demo.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.example.demo.service.AteService;
import com.example.demo.vo.Ate;
import com.example.demo.vo.DishComm;

import groovy.util.logging.Slf4j;

@RestController
@RequestMapping("/api/ate")
@Slf4j
public class AteController {
	
	@Autowired
	AteService ateService;
	
	@Value("${a.imgdir}")
	String fdir;
	
	/*
	 * 먹음 등록 
	 * mnum : 회원정보 필요
	 */
	@PostMapping("/add/{RCP_SEQ}/{mnum}")
	public String add(@ModelAttribute Ate ate, @PathVariable String RCP_SEQ, 
			@PathVariable int mnum, 
			@RequestParam("file") MultipartFile file) throws Exception {
	
	
			ate.setRCP_SEQ(RCP_SEQ);
			ate.setMnum(mnum);
		

			File dest = new File(fdir + "/" + file.getOriginalFilename());
			
			file.transferTo(dest);
			
			ate.setAte_picture("/img/" + dest.getName());
			
			ateService.add(ate);
			
	

		return ate.getAte_num() + "이 등록되었습니다.";
	}
	
	/*
	 * 먹음 게시글 전체 불러오기
	 */
	@GetMapping("/get")
	public List<Ate> get() {

		return ateService.get();
	}
	/*
	 * 먹음 게시글 한개 불러오기
	 * 댓글도 같이 불러옴 
	 */
	@GetMapping("/get/{ate_num}/{mnum}")
	public Map<String,Object> getOne(@PathVariable int ate_num, @PathVariable int mnum) {
		Map<String,Object> resultMap = new HashMap<>();
		
		String str="";
		
		/*
		 * 조회수 +1 
		 */
		ateService.upHit(ate_num);
		
		//글가져올때 좋아요 눌렀는지 체크해줘야해 : 수정 필요!
		if(ateService.ateLike(mnum, ate_num) == 1) {
			//좋아요 누른 사람
			str = "liked";	
		}

		Ate result = ateService.getOne(ate_num);
		List<DishComm> commList = ateService.commGet(ate_num);
		
		resultMap.put("commList", commList);
		resultMap.put("result", result);
		resultMap.put("liked", str);

		return resultMap;
	}
	
	/*
	 * 먹음 게시글 수정하기(글 번호, 로그인한 아이디도 같이 보내주세요) 
	 * 수정은 작성자만 가능하게 -> 프론트와 상의
	 */
	
	@PutMapping("/edit/{ate_num}/{mnum}")
	public String edit(@ModelAttribute Ate ate,
			@RequestParam("file") MultipartFile file) throws Exception{
		
		if (file.getSize() > 0) {
			File dest = new File(fdir + "/" + file.getOriginalFilename());
			file.transferTo(dest);

			ate.setAte_picture("/img/" + dest.getName());

		ateService.editAte(ate);
		
	}
	
		return "글 수정되었습니다.";
	}
	/*
	 * 먹음 게시물 조회
	 * 검색 일치 내용이 없을 경우 ?
	 * 수정 : @RequestParam명은 나중에 수정
	 */
	@GetMapping("/search")
	public List<Map<String, Object>> search(@RequestParam String select) {
		
		List<Map<String, Object>> ateList = ateService.search(select);
		
		return ateList;
	}
	

	/*
	 * 먹음 게시글 삭제하기(글 번호도 같이 보내주세요) 
	 */
	@DeleteMapping("/delete/{ate_num}/{munm}")
	public String delete(@PathVariable int ate_num) {
		//정말 삭제하시겠습니까? 질문 하는거...(팝업)
		
		ateService.delete(ate_num);
		
		
		return "글이 삭제되었습니다.";
	}
	/*
	 * 먹음 좋아요 등록 / 취소 
	 * 유저 번호도 같이 받아야해!
	 */
	@PostMapping("/like/{ate_num}/{mnum}")
	public String goDishLike(@PathVariable int ate_num, @PathVariable int mnum) {

		String str ="";
			//좋아요 안누른 상태면, 좋아요 등록
		if (ateService.ateLike(mnum, ate_num) == 0) {
			
			ateService.goAteLike(ate_num, mnum);
			
			str = "먹음 좋아요 등록!";
			//좋아요 누른 상태면, 좋아요 해제 
		}else if(ateService.ateLike(mnum, ate_num) == 1){
			
			ateService.goAteDislike(ate_num, mnum);
			
			str = "먹음 좋아요 해제!";

		}
		
		return str;
	}
	
	/*
	 * 댓글 추가 
	 */
	@PostMapping("/comm/add/{mnum}/{ate_num}")
	public String commAdd(@ModelAttribute DishComm dishComm, @PathVariable int mnum, @PathVariable int ate_num) {
		
		dishComm.setMnum(mnum);
		dishComm.setAte_num(ate_num);
		
		ateService.commAdd(dishComm);
		
		return "댓글이 등록되었습니다.";
	}
	
	/*
	 * 댓글 삭제(작성한 사람만 삭제 가능)  
	 */
	@DeleteMapping("/comm/delete/{mnum}/{ate_num}")
	public String commDelete(@ModelAttribute DishComm dishComm, @PathVariable int mnum, @PathVariable int ate_num) {
		
		dishComm.setMnum(mnum);
		dishComm.setAte_num(ate_num);
		
		ateService.commDelete(dishComm);
		
		return "댓글이 삭제되었습니다.";
	}
	
	/*
	 * 댓글 수정(작성한 사람만 수정 가능)  
	 */
	@PutMapping("/comm/edit/{mnum}/{ate_num}")
	public String commEdit(@ModelAttribute DishComm dishComm, @PathVariable int mnum, @PathVariable int ate_num) {
		
		dishComm.setMnum(mnum);
		dishComm.setAte_num(ate_num);
		
		ateService.commEdit(dishComm);
		
		return "댓글이 수정되었습니다.";
	}
	
	
	
	
	
}
