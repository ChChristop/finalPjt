package com.example.demo.Service;

import java.util.List;

import com.example.demo.vo.Ate;

public interface AteService {

	public void add(Ate ate);

	public List<Ate> get();

	public Ate getOne(int ate_num);

	public void editAte(Ate ate);

	public void delete(int ate_num);

	public void upHit(int ate_num);


}
