package com.example.demo.vo.point;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserPointVO {
	
	//회원 식별자
	private long mnum;
	
	//포인트 식별자
	private int pointID;

	//획득포인트(차감도 포함)
	private int point;
	
	//참조번호
	private int RCP_SEQ;
	
	//등록 시간(등록시 테이블에 자동생성)
	private LocalDateTime date;
	
	private int ate_num;


}
