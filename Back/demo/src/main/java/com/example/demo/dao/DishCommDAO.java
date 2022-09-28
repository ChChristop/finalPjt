package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.DishCommDTO;
import com.example.demo.pagelib.PageRequestDTO;

@Mapper
public interface DishCommDAO {

	@Select("SELECT COUNT(*) FROM DISH_COMM WHERE MNUM = #{mnum}")
	public int dishCommCountbyMnum(long mnum);

	public List<DishCommDTO> dishCommListbyMnum(PageRequestDTO dto, long mnum);
	

	
}
