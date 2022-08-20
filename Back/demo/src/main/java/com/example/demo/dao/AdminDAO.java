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
	
	@Insert("INSERT INTO ADMIN(ADMINID, NICKNAME, ADMINPW, ROLE, phonNumber) "
			+ "VALUES(#{adminID},#{nickName},#{adminPW},#{role},#{phonNumber})")
	@Options(useGeneratedKeys = true, keyProperty = "anum")
	Long addAdmin(Admin admin);
	
	@Select("")
	List<Admin> getAllAdminList();
	
	@Select("select adminID from admin where adminID = #{adminID}")
	Optional<String> CheckByAdminId(String adminID);
	
	@Select("select * from admin where adminID = #{adminID}")
	Optional<Admin> findByAdminId(String adminID);
	
	@Update("")
	Long updateAdmin(AdminDTO admin);

}
