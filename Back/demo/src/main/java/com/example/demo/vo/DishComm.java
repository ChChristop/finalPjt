package com.example.demo.vo;

import lombok.Data;

@Data
public class DishComm{

	private int mnum;
	private String RCP_SEQ; //요리 게시물 식별자
	private int ate_num; // 먹음 식별자
	private String date;			
	private String content;
	private String editdate;
	
}
