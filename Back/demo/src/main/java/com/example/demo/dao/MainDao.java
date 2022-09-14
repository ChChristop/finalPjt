package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.NonMember;

@Mapper 
public interface MainDao {

	public List<Map<String, Object>> auto();

	public List<Map<String, Object>> recipeAuto(String iname);

	public List<Map<String, Object>> ingAllReco(int mnum);

	public List<Map<String, Object>> ingAllReco_9999(NonMember nonMember);
	
}
