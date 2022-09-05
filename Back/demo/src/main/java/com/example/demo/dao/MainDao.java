package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper 
public interface MainDao {

	public List<Map<String, Object>> auto();
	
}
