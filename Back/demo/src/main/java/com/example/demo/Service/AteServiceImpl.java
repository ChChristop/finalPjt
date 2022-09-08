package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AteDao;
import com.example.demo.vo.Ate;
import com.example.demo.vo.DishComm;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class AteServiceImpl implements AteService {

	@Autowired
	AteDao ateDao;

	@Override
	public int add(Ate ate) {
		
		
		return ateDao.add(ate);
	}

	@Override
	public List<Ate> get() {
		
		List<Ate> list = ateDao.get();
		
		return list;
	}

	@Override
	public Map<String, Object> getOne(int ate_num) {

		return ateDao.getOne(ate_num);
	}

	@Override
	public int editAte(Ate ate) {
		
		
		return ateDao.editAte(ate);
	}

	@Override
	public int delete(int ate_num) {
		
		return ateDao.delete(ate_num);
		
	}

	@Override
	public void upHit(int ate_num) {
		ateDao.upHit(ate_num);
		
	}

	@Override
	public int ateLike(int mnum, int ate_num) {
	
		return ateDao.ateLike(mnum,ate_num);
	}

	@Override
	public void goAteLike(int ate_num, int mnum) {
		
		ateDao.goAteLike(ate_num,mnum);
	}

	@Override
	public void goAteDislike(int ate_num, int mnum) {
		
		ateDao.goAteDislike(ate_num,mnum);
	}

	@Override
	public int commAdd(DishComm dishComm) {
		
		
		return ateDao.commAdd(dishComm);
	}

	@Override
	public int commDelete(DishComm dishComm) {
		
		
		return ateDao.commDelete(dishComm);
	}

	@Override
	public int commEdit(DishComm dishComm) {
		
	
		return 	ateDao.commEdit(dishComm);
	}

	@Override
	public List<DishComm> commGet(int ate_num) {
		
		return ateDao.commGet(ate_num);
	}

	@Override
	public List<Map<String, Object>> search(String select) {

		return ateDao.search(select);
	}

	
}
