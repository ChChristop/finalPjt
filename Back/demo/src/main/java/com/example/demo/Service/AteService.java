package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.Ate;
import com.example.demo.vo.DishComm;

public interface AteService {

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


}
