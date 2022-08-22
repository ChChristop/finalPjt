package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.AdminDTO;
import com.example.demo.vo.Admin;

@Mapper
public interface AdminDAO {
	
	//관리자 등록 쿼리
	@Insert("INSERT INTO ADMIN(ADMINID, NICKNAME, ADMINPW, ROLE, PHONNUMBER) "
			+ "VALUES(#{adminID},#{nickName},#{adminPW},#{role},#{phonNumber})")
	@Options(useGeneratedKeys = true, keyProperty = "anum")
	Long addAdmin(Admin admin);
	
	//관리자 전체 리스트 조회
	@Select("SELECT ANUM, ADMINID, NICKNAME, PHONNUMBER, DATE, LASTACCESSDATE FROM ADMIN")
	List<Admin> getAllAdminList();
	
	@Select("select adminID from admin where adminID = #{adminID}")
	Optional<String> CheckByAdminId(String adminID);
	
	@Select("select ANUM, ADMINID, ADMINPW, NICKNAME, ROLE, DATE, MODDATE, LASTACCESSDATE from admin where adminID = #{adminID}")
	Optional<Admin> findByAdminId(String adminID);
	
	@Delete("delete from admin where ADMINID=#{adminID}")
	Long removeAdminbyAnum(String adminID);
	
	@Update("UPDATE ADMIN "
			+ "SET  ADMINPW=#{adminPW}, NICKNAME=#{nickname}, PHONNUMBER=#{phonNumber},Role=#{role} "
			+ "WHERE ANUM=#{anum} ")
	Long updateAdmin(Admin admin);
	
	
	@Update("UPDATE ADMIN "
			+ "SET lastAccessDate=current_timestamp " 
			+ "WHERE ADMINID=#{adminID}")
	void updateLastAceesDATEByAdmin(String adminID);
	
	

}
