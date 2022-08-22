package com.example.demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface JwtTokkenDAO {

	@Insert("INSERT INTO JWTTOKEN(ID, JWT) "
			+ "VALUES(#{id},#{jwt})")
	int createJWTTokenInDB(String id, String jwt);

	@Delete("DELETE FROM JWTTOKEN WHERE id = #{id}")
	void refreshTokenRemove(String id);

	@Select("SELECT FROM JWTTOKEN WHERE id = #{id}")
	void findJWTByAdminID(String id);

}
