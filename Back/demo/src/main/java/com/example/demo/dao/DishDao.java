package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Dish;

@Mapper 
public interface DishDao {

	public void add(Dish dish);
}