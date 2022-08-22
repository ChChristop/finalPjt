package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Dish;

@Mapper 
public interface DishDao {

	public List<Dish> get();

	public void add(Dish dish);

	public Dish getOne(int id);

	public void edit(Dish dish);

	public void upHit(int id);

	public void delete(int id);
}
