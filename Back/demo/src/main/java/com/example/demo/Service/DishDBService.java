package com.example.demo.service;

import java.util.Map;

public interface DishDBService {

	public void dbGO(Map<String, Object> nodeMapData, int anum);
	/*
	 * 재료만(dish_ing) 테이블 저장
	 */
	public void ingAdd(Map<String, Object> ingMap);
	/*
	 * 재료 중복 체크 
	 */
	public boolean ingCheck(String ingItem, String num);

}
