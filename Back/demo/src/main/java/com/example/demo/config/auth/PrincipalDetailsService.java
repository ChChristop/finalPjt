package com.example.demo.config.auth;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.function.ObjectMapperToDTO;
import com.example.demo.vo.AdminVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

//UsernamePasswordAuthenticationFilter 필터 후에 적용 됨
public class PrincipalDetailsService implements UserDetailsService{
	
	private final AdminDAO adminDAO;
	
	private final MemberDAO memberDAO;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("[PrincipalDetailsService] : 진입 : " + username);
		
		boolean check = AdminCheck.check;
		
		if(check) {
		
		//DB 접속
		Optional<AdminVO> result = adminDAO.findAdminIdByIDForJWT(username);
		
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
		
		
		log.info("[PrincipalDetails(adminDTO)] : 진입 : " + adminDTO.getAdminID());
		
		return new PrincipalDetails(adminDTO);
		
		}else {

			MemberDTO memberDTO = null;
	
			try {
				
				List<Map<String,Object>> result = memberDAO.findMemberbyMemberID(username);
				
				ObjectMapperToDTO objectMapperToDTO = new ObjectMapperToDTO(result,memberDTO,MemberDTO.class);
				
				memberDTO = (MemberDTO) objectMapperToDTO.changeObjectMapperToDTO();
				
				memberDTO.setAteCount(result.size());
		
				
				if(memberDTO.getMemberID() == null) {
					throw new UsernameNotFoundException("Check ID");
				}
				
				log.info("[PrincipalDetails(memberDTO)] : 진입 : " + memberDTO.getMemberID());
				
				return new PrincipalDetails(memberDTO);
				
			}catch(Exception e) {
				e.printStackTrace();
				
				return null;
			}
	
		}
		
	}
}
