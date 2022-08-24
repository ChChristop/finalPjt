package com.example.demo.Service;

import java.util.List;
import java.util.Map;

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
	public List<Dish> get() {
		
		List<Dish> list =  dishDao.get();
		return list;
	}

	@Override
	public void add(Dish dish) {
		
		dishDao.add(dish);
	}
	
	@Override
	public List<Dish> getOne(int id) {
		
		return dishDao.getOne(id);
	}  

	@Override
	public void edit(Dish dish) {
		
		dishDao.edit(dish);
	}

	@Override
	public void upHit(int id) {
	
		dishDao.upHit(id);
	}

	@Override
	public void delete(int id) {
		
		dishDao.delete(id);
	}

	@Override
	public void addPicture(Map<String, Object> param) {
		dishDao.addPicture(param);
	}

	@Override
	public void delPicture(int dnum) {
		dishDao.delPicture(dnum);
		
	}

	@Override
	public void editPicture(Map<String, Object> param) {
		dishDao.editPicture(param);
		
	}

	
}
