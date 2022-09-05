package com.example.demo.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefrigeratorDTO {
	
	//냉장고 재료 식별자
	private long refrenum;
	
	//회원 식별자
	private long mnum;
	
	//(레거시 코드) 재료 식별자
//	private long ingrnum;
	
	//재료명
	private String iname;
	
	//유통기한
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expirationDate;
	
	//(레거시 코드)키워드1
//	private String keyword1;
	
	//(레거시 코드)키워드2
//	private String keyword2;
	
	//(레거시 코드) 키워드3
//	private String keyword3;
	
	
}
