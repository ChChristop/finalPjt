package com.example.demo.vo;

public class Dish {

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
	private String t_weather;		// 날씨 → 맑음, 비, 눈, 흐림
	private String picture;			// 음식 사진
	
	
	public int getDnum() {
		return dnum;
	}
	public void setDnum(int dnum) {
		this.dnum = dnum;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public String getRecipe() {
		return recipe;
	}
	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getDish_like() {
		return dish_like;
	}
	public void setDish_like(int dish_like) {
		this.dish_like = dish_like;
	}
	public int getAte() {
		return ate;
	}
	public void setAte(int ate) {
		this.ate = ate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEditdate() {
		return editdate;
	}
	public void setEditdate(String editdate) {
		this.editdate = editdate;
	}
	public String getT_time() {
		return t_time;
	}
	public void setT_time(String t_time) {
		this.t_time = t_time;
	}
	public String getM_ingredient() {
		return m_ingredient;
	}
	public void setM_ingredient(String m_ingredient) {
		this.m_ingredient = m_ingredient;
	}
	public String getCookery() {
		return cookery;
	}
	public void setCookery(String cookery) {
		this.cookery = cookery;
	}
	public String getT_weather() {
		return t_weather;
	}
	public void setT_weather(String t_weather) {
		this.t_weather = t_weather;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
