package com.example.demo.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefrigeratorVO {
	
	//냉장고 재료 식별자
	private long refrenum;
	
	//회원 식별자
	private long mnum;
	
	//(레거시 코드)재료 식별자
	private long ingrnum;
	
	//재료 이름
	private String iname;
	
	//유통 기한
	private LocalDate expirationDate;
	

}
