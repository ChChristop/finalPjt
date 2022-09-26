package com.example.demo.vo;

import lombok.Data;

@Data
public class Dish{

	private String anum;			// 작성자
	private String RCP_SEQ;
	private String url;				// 동영상
	private int dish_like;			// 좋아요 개수
	private int ate;				// 먹음 개수
	private String date;			// 최초 작성 시간
	private String t_time;			// 시간 → 아침, 점심, 저녁, 야식 
	private String m_ingredient;	// 주재료 → 육류, 채소, 해산물, 기타
	private String t_weather;		// 날씨 → 맑음, 비, 눈, 흐림		 
	private int hit;				// 조회수
	private String editdate;		// 수정 시간
	
}
