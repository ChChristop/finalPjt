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
	
	private String alignByString;
	
	//오름차순, 내림차순인지 정함 true 오름차순, false 내림차순
	private boolean align;
	
	//검색어
	private String searchThings;
	
	//타입(i - id 검색, n - nickname 검색, in = id,nickname 검색)
	private String type;
	
	private int startRow;
	
	
	//기본 값
	public PageRequestDTO(){
		this.page = 0;
		this.size = 10;
		this.align = false;
		this.basis = "pk";
		this.searchThings = "";
		this.type = "";
		
		
	} 
	
	public void setterChange() {
		
		if(this.align) 
			this.alignByString = "asc"; //오름차순
		else
			this.alignByString = "desc"; //내림차순
		
		if(this.page > 0)
			this.page -= 1;
		
		this.startRow = (this.page * this.size);
		
	}
	
	


}
