package com.example.demo.vo.point;

import lombok.Data;

@Data
public class PointVO {
	
	//나중에 확장성을 위해 해당 테이블 만듬
	//포인트 식별자
	private int pointID;
	
	//포인트 내용
	private String content;

}
