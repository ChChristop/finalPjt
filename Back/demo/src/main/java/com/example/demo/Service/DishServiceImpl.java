package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DishDao;
import com.example.demo.vo.Dish;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

	@Autowired
	DishDao dishDao;

	@Override
	public void add(Dish dish) {
		
		dishDao.add(dish);	
	}
	
	
	
	
}
