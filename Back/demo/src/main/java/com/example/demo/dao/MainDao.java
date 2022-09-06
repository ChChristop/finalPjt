package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper 
public interface MainDao {

	public List<Map<String, Object>> auto();

	public List<Map<String, Object>> recipeAuto(String iname);

	public List<Map<String, Object>> ingAllReco(int mnum);
	
}