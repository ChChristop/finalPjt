package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import com.example.demo.vo.Dish;

public interface DishService {

	/*
	 * 음식 추가 
	 */
	public void add(Dish dish);
	/*
	 * 음식 전체 리스트 불러오기 
	 */
	public List<Dish> get();
	/*
	 * 음식 정보 한개 불러오기
	 */
	public Dish getOne(int id);
	/*
	 * 음식 정보 수정하기
	 */
	public void edit(Dish dish);
	/*
	 *  조회수 +1
	 */
	public void upHit(int id);
	/*
	 * 음식 정보 삭제하기 
	 */
	public void delete(int id);
	
	
	public void addPicture(Map<String, Object> param);
	
	
	
}
