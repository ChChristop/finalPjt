package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.DishLikeDTO;
import com.example.demo.pagelib.PageRequestDTO;

@Mapper
public interface DishLikeDAO {

	@Select("SELECT COUNT(*) FROM DISH_LIKE WHERE MNUM = #{mnum}")
	public int dishLikeCountbyMnum(long mnum);
	
	public List<DishLikeDTO> dishLikeListbyMnum(PageRequestDTO dto, long mnum);
}
