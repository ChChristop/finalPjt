package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.vo.Ate;

@Mapper 
public interface AteDao {

	public void add(Ate ate);

	public List<Ate> get();

	public Ate getOne(int ate_num);

	public void editAte(Ate ate);

	public void delete(int ate_num, int mnum);

	public void upHit(int ate_num);

	public int ateLike(int mnum, int ate_num);

	public void goAteLike(int ate_num, int mnum);

	public void goAteDislike(int ate_num, int mnum);
	
	public List<Ate> getAllList(long mnum);
	
	public List<Ate> getAteListbyUser(PageRequestDTO dto, long mnum);
	
	@Select("SELECT COUNT(*) FROM ATE WHERE MNUM=#{mnum}")
	public int ateCount(long mnum);
	
}
