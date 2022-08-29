package com.example.demo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper 
public interface DishDBDao {

	public void dbGO(Map<String, Object> nodeMapData);

	
}
