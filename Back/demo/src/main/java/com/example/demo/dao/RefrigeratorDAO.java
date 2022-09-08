package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.RefrigeratorDTO;
import com.example.demo.vo.RefrigeratorVO;

@Mapper
public interface RefrigeratorDAO {

	// 냉장고 재료 추가
	@Insert("INSERT INTO REFRIGERATOR(MNUM,INAME,EXPIRATIONDATE) VALUES(#{mnum}, #{iname}, #{expirationDate})")
	@Options(useGeneratedKeys = true, keyProperty = "refrenum")
	public long addIngredientToRefrigerator(RefrigeratorVO refrigeratorVO);

	// 냉장고 재료 삭제
	@Delete("DELETE FROM REFRIGERATOR WHERE REFRENUM = #{refrenum}")
	public long deleteIngredientByRefrigerator(long refrenum);

	// 냉장고 재료 삭제(회원탈퇴시
	@Delete("DELETE FROM REFRIGERATOR WHERE MNUM = #{mnum}")
	public long deleteIngredientByMnumr(long mnum);

	// 냉장고 재료 업데이트
	@Update("UPDATE REFRIGERATOR " + "SET INAME=#{iname}, EXPIRATIONDATE=#{expirationDate} "
			+ "WHERE REFRENUM = #{refrenum}")
	public long updateIngredientByRefrigerator(RefrigeratorVO refrigeratorVO);

	// 냉장고 회원 번호로 검색
	@Select("SELECT * " + "FROM REFRIGERATOR R " + "WHERE R.MNUM = #{mnum}")
	public List<RefrigeratorDTO> findRefrigeratorDAObyMnum(long mnum);

}
