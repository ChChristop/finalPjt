package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Dish;
import com.example.demo.vo.DishComm;
import com.example.demo.vo.DishDB;

@Mapper 
public interface DishDao {

	public List<Map<String, Object>> get();

	public void add(DishDB dish);

	public Map<String, Object> getOne(int RCP_SEQ);

	public void edit(DishDB dish);

	public void upHit(int RCP_SEQ);

	public int delete(int RCP_SEQ);

	public void pictures(int dnum);

	public void delPicture(int dnum);

	public void editPicture(Map<String, Object> param);

	public int dishLike(int mnum, int RCP_SEQ);

	public void goDishLike(int RCP_SEQ, int mnum);

	public void goDishDislike(int RCP_SEQ, int mnum);

	public int getNum();

	public void addInfo(int dnum, int anum);

	public void editInfo(Dish dish1, int rCP_SEQ, int anum);

	public int commAdd(DishComm dishComm);

	public int commDelete(DishComm dishComm);

	public int commEdit(DishComm dishComm);

	public List<DishComm> commGet(int rCP_SEQ);

	public List<Map<String, Object>> search(String select, String searchI);

	public List<Map<String, Object>> topDish();

	public void deleIng(int rCP_SEQ);

	public void deleteDish(int rCP_SEQ);

}