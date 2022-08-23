package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.JwtVO;

@Mapper
public interface JwtTokkenDAO {

	@Insert("INSERT INTO JWTTOKEN(ID, JWT, IP) "
			+ "VALUES(#{id},#{jwt},#{ip})")
	int createJWTTokenInDB(String id, String jwt,String ip);

	@Delete("DELETE FROM JWTTOKEN WHERE id = #{id}")
	void refreshTokenRemove(String id);

	@Select("SELECT * FROM JWTTOKEN WHERE id = #{id}")
	List<JwtVO> findJWTByAdminID(String id);
	
	@Select("SELECT * FROM JWTTOKEN WHERE id = #{id} AND ip = #{ip}")
	Optional<JwtVO> findJWTByIdandIp(String id,String ip);
	
	@Update("UPDATE JWTTOKEN SET JWT=#{jwt}, DATE=current_timestamp WHERE ID = #{id} and ip = #{ip} ")
	boolean updateJWTByAdminIdAndIp(String jwt, String id, String ip);

}
