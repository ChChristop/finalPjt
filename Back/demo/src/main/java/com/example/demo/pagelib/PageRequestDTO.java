package com.example.demo.pagelib;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
	
	//현재 페이지
	private int page;

	//사이즈
	private int size;
	
	//기준 칼럼
	private String basis;
	
	//오름차순, 내림차순인지 정함 true 오름차순, false 내림차순
	private boolean align;
	
	//기본 값
	public PageRequestDTO(){
		this.page = 1;
		this.size = 10;
		this.align = false;
		this.basis = "pk";
		
	} 

}
