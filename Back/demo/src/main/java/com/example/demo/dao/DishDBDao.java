package com.example.demo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper 
public interface DishDBDao {

	public int dbGO(Map<String, Object> nodeMapData);

	
	public void dbGoInfo();


	public void ingAdd(Map<String, Object> ingMap);

	
}
