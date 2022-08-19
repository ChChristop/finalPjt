package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.AdminDTO;
import com.example.demo.vo.Admin;

@Mapper
public interface AdminDAO {
	
	@Insert("")
	Long addAdmin(AdminDTO adminDTO);
	
	@Select("")
	List<Admin> getAllAdminList();
	
	@Select("")
	Optional<Admin> getAdminList(int anum);
	
	@Delete("")
	Long deleteAdmin(int anu);
	
	@Update("")
	Long updateAdmin(AdminDTO adminDTO);

}
