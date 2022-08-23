package com.example.demo.config.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AdminDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.vo.AdminVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
//UsernamePasswordAuthenticationFilter 필터 후에 적용 됨
public class PrincipalDetailsService implements UserDetailsService{
	
	private final AdminDAO adminDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("PrincipalDetailsService : 진입");
		
		//DB 접속
		Optional<AdminVO> result = adminDAO.findAdminIdByID(username);
		
		if(result.isEmpty()) {
			throw new UsernameNotFoundException("Check ID");
		}
		
		AdminVO admin = result.get();
		
		AdminDTO adminDTO = AdminDTO.builder()
				.anum(admin.getAnum())
				.adminID(admin.getAdminID())
				.nickName(admin.getNickName())
				.adminPW(admin.getAdminPW())
				.phoneNumber(admin.getPhoneNumber())
				.lastAccess(admin.getLastAccessDate())
				.date(admin.getDate())
				.modDate(admin.getModDate())
				.role(admin.getRoleList())
				.build();
		
		return new PrincipalDetails(adminDTO, false);
	}

}
