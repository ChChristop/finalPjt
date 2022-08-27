package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DishDao;
import com.example.demo.vo.Dish;
import com.example.demo.vo.DishDB;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

	@Autowired
	DishDao dishDao;

	@Override
	public List<Map<String, Object>> get() {
		
		List<Map<String, Object>> list =  dishDao.get();
		System.out.println("list::: " + list);
		return list;
	}

	@Override
	public void add(Dish dish) {
		
		dishDao.add(dish);
	}
	
	@Override
	public Map<String,Object> getOne(int RCP_SEQ) {
		
		return dishDao.getOne(RCP_SEQ);
	}  

	@Override
	public void edit(Dish dish) {
		
		dishDao.edit(dish);
	}

	@Override
	public void upHit(int RCP_SEQ) {
	
		dishDao.upHit(RCP_SEQ);
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

	@Override
	public int dishLike(int mnum, int dnum) {
		
		return dishDao.dishLike(mnum,dnum);
		
	}

	@Override
	public void goDishLike(int mnum, int dnum) {
	
		dishDao.goDishLike(dnum,mnum);
	}

	@Override
	public void goDishDislike(int dnum, int mnum) {
		
		dishDao.goDishDislike(dnum,mnum);
	}

	
}
