package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.AteDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.vo.Ate;
import com.example.demo.vo.DishComm;

@Mapper 
public interface AteDao {

	public int add(Ate ate);

	public List<Ate> get();

	public Map<String, Object> getOne(int ate_num);

	public int editAte(Ate ate);

	public int delete(int ate_num, int mnum);

	public void upHit(int ate_num);

	public int ateLike(int mnum, int ate_num);

	public void goAteLike(int ate_num, int mnum);

	public void goAteDislike(int ate_num, int mnum);

	public int commAdd(DishComm dishComm);

	public int commDelete(DishComm dishComm);

	public int commEdit(DishComm dishComm);

	public List<DishComm> commGet(int ate_num);

	public List<Map<String, Object>> search(String select);
	
	public List<Ate> getAllList(long mnum);
	
	public List<AteDTO> getAteListbyUser(PageRequestDTO dto, long mnum);
	
	@Select("SELECT COUNT(*) FROM ATE WHERE MNUM=#{mnum}")
	public int ateCount(long mnum);
	
}
