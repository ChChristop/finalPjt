package com.example.demo.service.adminService;

import com.example.demo.dto.AdminDTO;
import com.example.demo.vo.Admin;


public interface AdminService {
	
	AdminDTO findAmindByID(String id);
	
	Long register(AdminDTO adminDTO);
	
	boolean CheckadminID(String id);
	
	String remove(String adminID);
	
	Long update(AdminDTO adminDTO);
	
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
	
	default AdminDTO voTOdto(Admin admin) {

		AdminDTO adminDTO = AdminDTO.builder()
				.anum(admin.getAnum())
				.adminID(admin.getAdminID())
				.nickName(admin.getNickName())
				.phonNumber(admin.getPhonNumber())
				.role(admin.getRoleList())
				.lastAccess(admin.getLastAccess())
				.date(admin.getDate())
				.modDate(admin.getModDate())
				.build();
		
				return adminDTO;
	}

}
