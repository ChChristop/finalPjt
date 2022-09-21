package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AteDTO {
	private int ate_num; 		// 고유 번호
	@JsonProperty("RCP_SEQ")
	private String RCP_SEQ;		// 음식 게시물 고유 번호(F)
	private int mnum;		// 멤버 고유 번호(F)
	private LocalDateTime ate_date;	// 먹음 등록날짜
	private String ate_picture; // 먹음 사진
	private String ate_content; // 먹음 내용
//	private int ate_like;    // 먹음 좋아요
	private int ate_hit;    // 먹음 조회수
	private String ate_editdate; //먹음 수정날짜
	@JsonProperty("RCP_NM")
	private String RCP_NM; //재료 타이틀
	private int ate_comm_count;	//먹음 댓글 개수
	private int ate_like_count;    // 먹음 좋아요 개수
	
}
