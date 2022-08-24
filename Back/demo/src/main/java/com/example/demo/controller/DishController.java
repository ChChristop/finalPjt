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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.DishService;
import com.example.demo.vo.Dish;

import groovy.util.logging.Slf4j;

@RestController
@RequestMapping("/api/dish")
@Slf4j
public class DishController {
	
	@Autowired
	DishService dishService;
	
	@Value("${news.imgdir}")
	String fdir;

	
	/*
	 * 음식 추가
	 */
	@PostMapping("/add")
	public String add(@ModelAttribute Dish dish,
			@RequestParam("files") MultipartFile[] files) throws Exception {
	
		for(MultipartFile file : files) {
			Map<String, Object> param = new HashMap<>();
			
			String pname = file.getOriginalFilename();
			File dest = new File(fdir + "/" + file.getOriginalFilename());
			file.transferTo(dest);
			param.put("pname", "/img/"+pname);
			dishService.addPicture(param);
		}
		//pictureTest(file);
		dishService.add(dish);

		return dish.getTitle() + "이 등록되었습니다.";
	}
	/*
	 * 음식 정보 수정하기
	 * 	오수진(0818) : 추후 프론트와 이야기! 
	 *  							-> id 정보 받을 방법 정해서 수정하기
	 *  								->지금은 id가 없어서 내가 적어줘야하지만, 
	 *  									나중엔 dish Map안에 들어있을 예정
	 *  							-> 기존 정보 어떻게 보여줄지 정하기   
	 */
	@PutMapping("/edit/{dnum}")
	public String edit(@ModelAttribute Dish dish,
			@RequestParam("files") MultipartFile[] files, @PathVariable int dnum) throws Exception {
		
		//오수진(0818): 프론트와 상의하기 
			//1. 기존 정보 불러오기 (이건 어떻게 작동해서 보여줄지 상의하자) 
			//Dish BeforeData = dishService.getOne(id);
		
		if (files.length > 0) {
			dishService.delPicture(dnum);
			for(MultipartFile file : files) {
				
				Map<String, Object> param = new HashMap<>();
				
				String pname = file.getOriginalFilename();
				File dest = new File(fdir + "/" + file.getOriginalFilename());
				file.transferTo(dest);
				param.put("pname", "/img/"+pname);
				param.put("dnum", dnum);
				dishService.editPicture(param);
			}
		}
		
		
		dish.setDnum(dnum);
		
		dishService.edit(dish);
		
		return dish.getTitle() +"이 수정되었습니다.";
	}

	
	@GetMapping("/get")
	public List<Dish> get() {

		return dishService.get();
	}
	
	@GetMapping("/get/{dnum}")
	public List<Dish> getOne(@PathVariable int dnum) {
		/*
		 * 조회수 +1 
		 */
		dishService.upHit(dnum);
		
		/*
		 * 아직 테이블 완성 안해서 못함 
		 * 			: 좋아요 테이블 조회해서 로그인한 사람 -> 본 글에 대한 좋아요 눌렀는지 안눌렀는지 체크 
		 */
		
		return dishService.getOne(dnum);
	}
	

	/*
	 * 음식 정보 삭제하기 
	 */
	@DeleteMapping("/delete/{dnum}")
	public String delete(@PathVariable int dnum) {
		//정말 삭제하시겠습니까? 질문 하는거...(팝업)
		
		dishService.delete(dnum);
		
		
		return "글이 삭제되었습니다.";
	}
	
	
	

}
