package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Dish;
import com.example.demo.vo.DishDB;

@Mapper 
public interface DishDao {

	public List<Map<String, Object>> get();

	public void add(Dish dish);

	public Map<String, Object> getOne(int RCP_SEQ);

	public void edit(Dish dish);

	public void upHit(int RCP_SEQ);

	public void delete(int id);

	public void addPicture(Map<String, Object> param);

	public void pictures(int dnum);

	public void delPicture(int dnum);

	public void editPicture(Map<String, Object> param);

	public int dishLike(int mnum, int dnum);

	public void goDishLike(int dnum, int mnum);

	public void goDishDislike(int dnum, int mnum);
	
	
}
