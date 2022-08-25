package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Dish;

@Mapper 
public interface DishDao {

	public List<Dish> get();

	public void add(Dish dish);

	public List<Dish> getOne(int id);

	public void edit(Dish dish);

	public void upHit(int id);

	public void delete(int id);

	public void addPicture(Map<String, Object> param);

	public void pictures(int dnum);

	public void delPicture(int dnum);

	public void editPicture(Map<String, Object> param);

	public int dishLike(int mnum, int dnum);

	public void goDishLike(int dnum, int mnum);
	
	
}
