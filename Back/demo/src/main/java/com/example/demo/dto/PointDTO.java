package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PointDTO {

	//회원 식별자
	private long mnum;
	
	//포인트 식별자
	private int pointID;

	//획득포인트(차감도 포함)
	private int point;
	
	//참조번호
	@JsonProperty("RCP_SEQ")
	private int RCP_SEQ;
	
	//등록 시간(등록시 테이블에 자동생성)
	private LocalDateTime date;
	
	//포인트 내용
	private String content;
	
	@JsonProperty("RCP_NM")
	private String RCP_NM;
	
	@JsonProperty("ATE_NUM")
	private int ATE_NUM;
	
	@JsonProperty("ATT_FILE_NO_MAIN")
	private String ATT_FILE_NO_MAIN;
	
	@JsonProperty("ATE_PICTURE")
	private String ATE_PICTURE;
	

}
