package com.example.demo.vo;

import lombok.Data;

@Data
public class Ate{

	private int ate_num; 		// 고유 번호
	private String dnum;		// 음식 게시물 고유 번호(F)
	private String mnum;		// 멤버 고유 번호(F)
	private String ate_date;	// 먹음 등록날짜
	private String ate_picture; // 먹음 사진
	private String ate_content; // 먹음 내용
	private int ate_like;    // 먹음 좋아요
	private int ate_hit;    // 먹음 조회수
	private String ate_editdate; //먹음 수정날짜
}
