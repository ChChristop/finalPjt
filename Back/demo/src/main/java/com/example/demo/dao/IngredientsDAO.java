package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.IngredientsVO;

@Mapper
public interface IngredientsDAO {
	
	@Insert("INSERT INTO ingredients(iname, keyword1, keyword2, keyword3) "
			+ "VALUES(#{iname}, #{keyword1}, #{keyword2}, #{keyword3})")
	@Options(useGeneratedKeys = true, keyProperty = "ingrnum")
	Long addIngredients(IngredientsVO ingredients);
	
	
	@Delete("DELETE FROM ingredients WHERE ingrnum=#{ingrnum}")
	Long deleteIngredients(long ingrnum);
	
	@Update("UPDATE ingredients SET iname=#{iname}, keyword1=#{keyword1}, keyword2=#{keyword2}, keyword3=#{keyword3} "
			+ "WHERE INGRNUM=#{ingrnum}")
	Long upgradeIngredients(IngredientsVO ingredients);
	
	@Select("SELECT INGRNUM, INAME FROM INGREDIENTS")
	List<IngredientsVO>  ingredientsAllList();
}
