package com.example.demo.controller;

import java.io.File;
import java.util.List;

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

import com.example.demo.Service.AteService;
import com.example.demo.vo.Ate;

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
	 */
	@PostMapping("/add")
	public String add(@ModelAttribute Ate ate,
			@RequestParam("file") MultipartFile file) throws Exception {
	

		File dest = new File(fdir + "/" + file.getOriginalFilename());
		
		file.transferTo(dest);
		
		ate.setAte_picture("/img/" + dest.getName());

	
		ateService.add(ate);

		return  ate.getAte_num() + "이 등록되었습니다.";
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
	 */
	@GetMapping("/get/{ate_num}")
	public Ate getOne(@PathVariable int ate_num) {
		/*
		 * 조회수 +1 
		 */
		ateService.upHit(ate_num);
		

		return ateService.getOne(ate_num);
	}
	
	/*
	 * 먹음 게시글 수정하기(글 번호도 같이 보내주세요) 
	 */
	
	@PutMapping("/edit/{ate_num}")
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
	 * 먹음 게시글 삭제하기(글 번호도 같이 보내주세요) 
	 */
	@DeleteMapping("/delete/{ate_num}")
	public String delete(@PathVariable int ate_num) {
		//정말 삭제하시겠습니까? 질문 하는거...(팝업)
		
		ateService.delete(ate_num);
		
		
		return "글이 삭제되었습니다.";
	}
	
	
	
}
