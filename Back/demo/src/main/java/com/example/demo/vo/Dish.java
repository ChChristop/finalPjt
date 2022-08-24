package com.example.demo.vo;

import java.util.List;

import lombok.Data;

@Data
public class Dish{

	private int dnum; 				// 고유 번호
	private String writer;			// 작성자
	private String title;			// 음식명
	private String ingredient;		// 음식 재료
	private String recipe;			// 만드는 방법
	private String url;				// 동영상
	private int dish_like;			// 좋아요 개수
	private int ate;				// 먹음 개수
	private int hit;				// 조회수
	private String date;			// 최초 작성 시간
	private String editdate;		// 수정 시간
	private String t_time;			// 시간 → 아침, 점심, 저녁, 야식 
	private String m_ingredient;	// 주재료 → 육류, 채소, 해산물, 기타
	private String cookery;			// 조리방법 → 끓이기,굽기,데치기(삶기), 튀김, 무침, 회
	private String t_weather;		// 날씨 → 맑음, 비, 눈, 흐림		// 음식 사진
	private DPicture dpicture;
	
	
}
