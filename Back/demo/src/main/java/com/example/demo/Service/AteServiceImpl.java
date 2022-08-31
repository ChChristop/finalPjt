package com.example.demo.service;

import java.util.List;

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
	public void add(Ate ate) {
		
		ateDao.add(ate);
	}

	@Override
	public List<Ate> get() {
		
		List<Ate> list = ateDao.get();
		
		return list;
	}

	@Override
	public Ate getOne(int ate_num) {

		return ateDao.getOne(ate_num);
	}

	@Override
	public void editAte(Ate ate) {
		
		ateDao.editAte(ate);
	}

	@Override
	public void delete(int ate_num) {
		ateDao.delete(ate_num);
		
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
	public void commAdd(DishComm dishComm) {
		
		ateDao.commAdd(dishComm);
	}

	@Override
	public void commDelete(DishComm dishComm) {
		
		ateDao.commDelete(dishComm);
	}

	@Override
	public void commEdit(DishComm dishComm) {
		
		ateDao.commEdit(dishComm);
	}

	@Override
	public List<DishComm> commGet(int ate_num) {
		
		return ateDao.commGet(ate_num);
	}

	
}
