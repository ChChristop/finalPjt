package com.example.demo.service.adminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.AdminDTO;
import com.example.demo.vo.Admin;


public interface AdminService {
	

	Long register(AdminDTO adminDTO);
	
	boolean CheckadminID(String id);
	
	default Admin dtoTOvo(AdminDTO adminDTO) {
		
		Admin admin = Admin.builder()
				.adminID(adminDTO.getAdminID())
				.adminPW(adminDTO.getAdminPW())
				.nickName(adminDTO.getNickName())
				.phonNumber(adminDTO.getPhonNumber())
				.role("MEMBER,ADMIN")
				.build();
		
		return admin;
		
	}

}
