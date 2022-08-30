package com.example.demo.service.adminService;

import com.example.demo.dto.AdminDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.vo.AdminVO;


public interface AdminService {
	
	AdminDTO findAdmin(long anum);
	
	AdminDTO findAdmin(String adminId);
	
	Long register(AdminDTO adminDTO);
	
	boolean CheckadminID(String id);
	
	long remove(long anum);
	
	Long update(AdminDTO adminDTO);
	
	PageResultDTO<AdminVO, AdminDTO> getAdminList(PageRequestDTO pageRequestDTO);
	
	default AdminVO dtoTOvo(AdminDTO adminDTO) {
		
		AdminVO admin = AdminVO.builder()
				.anum(adminDTO.getAnum())
				.adminID(adminDTO.getAdminID())
				.adminPW(adminDTO.getAdminPW())
				.nickName(adminDTO.getNickName())
				.phoneNumber(adminDTO.getPhoneNumber())
				.role("MEMBER,ADMIN")
				.build();
		
		return admin;
		
	}
	
	default AdminDTO voTOdto(AdminVO admin) {

		AdminDTO adminDTO = AdminDTO.builder()
				.anum(admin.getAnum())
				.adminID(admin.getAdminID())
				.nickName(admin.getNickName())
				.phoneNumber(admin.getPhoneNumber())
				.role(admin.getRoleList())
				.lastAccess(admin.getLastAccessDate())
				.date(admin.getDate())
				.modDate(admin.getModDate())
				.build();
		
				return adminDTO;
	}

}
