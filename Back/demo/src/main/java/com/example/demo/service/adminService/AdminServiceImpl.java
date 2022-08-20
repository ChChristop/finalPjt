package com.example.demo.service.adminService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AdminDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.vo.Admin;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	
	private final PasswordEncoder passwordEncoder;
	
	
	private final AdminDAO adminDAO;
	
	@Override
	public Long register(AdminDTO adminDTO) {
		
		adminDTO.setAdminPW(passwordEncoder.encode(adminDTO.getAdminPW()));
		
		Admin admin = dtoTOvo(adminDTO);
		
		log.info("관리자 추가 중");
		
		adminDAO.addAdmin(admin);
		
		return (long) admin.getAnum();
	}

	@Override
	public boolean CheckadminID(String adminId) {
		
		log.info("회원 아이디 중복 확인");
		
		Optional<String> result =  adminDAO.CheckByAdminId(adminId);
		
		if(result.isPresent()) {
			return true;
		}else {
			return false;
		}
		
	}
	

}
