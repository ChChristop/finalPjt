package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@PostMapping("/recipe/reco/9999")
	public List<Map<String, Object>> ingAllReco_9999(@ModelAttribute NonMember nonMember){
		
		System.out.println("nonMember :::" + nonMember);
		List<Map<String, Object>> result = mainService.ingAllReco_9999(nonMember); 
		System.out.println("result ::: " + result);
		
		return result;
	}
	
	
	/*
	 * 모바일 - 검색 (정규식)
	 */
	@PostMapping("/search")
	@ResponseBody
	public Map<String, Object> mainSearch(@RequestBody Map<String,Object> searchMap){
		
		Map<String, Object> resultMap = new HashMap<>();
			//System.out.println("searchMap ::: " + searchMap);
		
		//한페이지당 결과는 10개
		//전체 페이지 수 totalPage
		int totalCNT = mainService.searchCNT(searchMap);
		System.out.println("totalCNT ::: " + totalCNT); //지워
		//한장당 원하는 개수
		int onePage = 15; //수정 가능
		int totalPage = (int) Math.ceil((double)totalCNT/onePage);
		System.out.println("totalPage ::: " + totalPage); //지워
		
		//요청 들어온 페이지 (기본은 1)  
		int page = 1;
		if(!searchMap.get("page").toString().isEmpty()) {
			page = Integer.parseInt((String) searchMap.get("page")); 
		}
		
		int start = onePage*(page-1)+1;
		int end = page*onePage;
		searchMap.put("start", start);
		searchMap.put("end", end);
			//System.out.println(searchMap);
		
		
		List<Map<String, Object>> result = new ArrayList<>();
		
		result = mainService.mainSearch(searchMap);
		resultMap.put("result",result);
		resultMap.put("totalPage",totalPage);
		
		return resultMap;
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
	@GetMapping("/recipe/iname")
	public List<Map<String, Object>> recipeAuto(@PathVariable String iname){
		
		List<Map<String, Object>> result = mainService.recipeAuto(iname); 
		
		return result;
	}
	/*
	 * 유통기한 임박 식품 관련 메뉴 추천 (전체 메뉴)
	 */
	@GetMapping("/recipe/reco/{mnum}")
	public List<Map<String, Object>> ingAllReco(@PathVariable int mnum){

		
		List<Map<String, Object>> result = new ArrayList<>();
		
		String ing = "";
		
		
		//1. 냉장고 재료 불러오기 
		List<Map<String, Object>> ings = mainService.getIngs(mnum);
		
		for(int i=0; i< ings.size(); i++) {
			
			Map<String, Object> map = new HashMap<>();
			List<Map<String, Object>> list = new ArrayList<>();
			ing = ings.get(i).get("iname").toString(); //재료명
			list = mainService.getList(ing); //재료 관련 레시피 (리스트)
			
			// 포문으로 꺼내장 
			for(Map la :list) {
				result.add(la);
			}
			
		}
		
		return result;
	}
	


}
