package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.controller.RestTestController;
import com.example.demo.dao.DishDao;
import com.example.demo.vo.Dish;
import com.example.demo.vo.DishComm;
import com.example.demo.vo.DishDB;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

	@Autowired
	DishDao dishDao;
	@Autowired
	RestTestController restTestController;

	@Override
	public List<Map<String, Object>> get() {
		
		List<Map<String, Object>> list =  dishDao.get();
		System.out.println("list::: " + list);
		return list;
	}

	@Override
	public void add(DishDB dish, int mnum) {
		
		dishDao.add(dish);
		
		int dnum= Integer.parseInt(dish.getRCP_SEQ());
		dishDao.addInfo(dnum, mnum);
		
		Object obj = dish.getRCP_PARTS_DTLS();
		String result = restTestController.RegexCheck(obj);
		String num = Integer.toString(dnum);
		restTestController.goDish_ing(result,num);
		
	}
	
	@Override
	public Map<String,Object> getOne(int RCP_SEQ) {
		
		return dishDao.getOne(RCP_SEQ);
	}  

	@Override
	public void edit(DishDB dish, Dish dish1,int rCP_SEQ, int mnum) {
		
		dishDao.edit(dish);
		dishDao.editInfo(dish1, rCP_SEQ, mnum);
	}

	@Override
	public void upHit(int RCP_SEQ) {
	
		dishDao.upHit(RCP_SEQ);
	}

	@Override
	public void delete(int RCP_SEQ) {
		
		dishDao.delete(RCP_SEQ);
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
	public int dishLike(int mnum, int RCP_SEQ) {
		
		return dishDao.dishLike(mnum,RCP_SEQ);
		
	}

	@Override
	public void goDishLike(int RCP_SEQ, int mnum) {
	
		dishDao.goDishLike(RCP_SEQ,mnum);
	}

	@Override
	public void goDishDislike(int RCP_SEQ, int mnum) {
		
		dishDao.goDishDislike(RCP_SEQ,mnum);
	}

	@Override
	public int getNum() {
		
		return dishDao.getNum();
	}

	@Override
	public void commAdd(DishComm dishComm) {
		
		dishDao.commAdd(dishComm);
	}

	@Override
	public void commDelete(DishComm dishComm) {
		
		dishDao.commDelete(dishComm);
	}

	@Override
	public void commEdit(DishComm dishComm) {
		
		dishDao.commEdit(dishComm);
	}

	@Override
	public List<DishComm> commGet(int rCP_SEQ) {

		return dishDao.commGet(rCP_SEQ);
	}

	@Override
	public List<Map<String, Object>> search(String select, String searchI) {
		
		
		return dishDao.search(select,searchI);
	}

	
}
