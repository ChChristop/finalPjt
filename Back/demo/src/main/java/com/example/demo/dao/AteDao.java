package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Ate;
import com.example.demo.vo.DishComm;

@Mapper 
public interface AteDao {

	public void add(Ate ate);

	public List<Ate> get();

	public Ate getOne(int ate_num);

	public void editAte(Ate ate);

	public void delete(int ate_num);

	public void upHit(int ate_num);

	public int ateLike(int mnum, int ate_num);

	public void goAteLike(int ate_num, int mnum);

	public void goAteDislike(int ate_num, int mnum);

	public void commAdd(DishComm dishComm);

	public void commDelete(DishComm dishComm);

	public void commEdit(DishComm dishComm);

	public List<DishComm> commGet(int ate_num);

	public List<Map<String, Object>> search(String select);
	
}
