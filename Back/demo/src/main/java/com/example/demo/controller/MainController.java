package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MainService;
import com.example.demo.vo.NonMember;

import groovy.util.logging.Slf4j;

@RestController
@RequestMapping("/api/main")
@Slf4j
public class MainController {
	
	@Autowired
	MainService mainService;
	
	/*
	 * 모바일 버전 - > 비회원 접속시 메뉴 추천!
	 * 추후 접속 제한은 얘기 해봐야함!!!!
	 */
	@GetMapping("/recipe/reco/9999")
	public List<Map<String, Object>> ingAllReco_9999(@ModelAttribute NonMember nonMember){
		
		List<Map<String, Object>> result = mainService.ingAllReco_9999(nonMember); 
		
		
		return result;
	}
	
	/*
	 * 전체 재료 다 갖고오기
	 */
	@GetMapping("/ing")
	public List<Map<String, Object>> auto() {
		
		List<Map<String, Object>> result = mainService.auto(); 
		
		return result;
	}
	/*
	 * 재료 누르면 관련 메뉴 추천(재료 1개에 관해) 
	 * 이거 안쓸수도 있음
	 */
	@GetMapping("/recipe/{iname}")
	public List<Map<String, Object>> recipeAuto(@PathVariable String iname){
		
		List<Map<String, Object>> result = mainService.recipeAuto(iname); 
		
		return result;
	}
	/*
	 * 유통기한 임박 식품 관련 메뉴 추천 (전체 메뉴)
	 */
	@GetMapping("/recipe/reco/{mnum}")
	public List<Map<String, Object>> ingAllReco(@PathVariable int mnum){
		
		List<Map<String, Object>> result = mainService.ingAllReco(mnum); 
		
		
		return result;
	}
	

}
