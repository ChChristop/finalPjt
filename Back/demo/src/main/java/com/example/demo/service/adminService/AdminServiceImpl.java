package com.example.demo.service.adminService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.AdminDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.vo.AdminVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final PasswordEncoder passwordEncoder;

	private final AdminDAO adminDAO;

	// 조회 메서드
	@Override
	public PageResultDTO<Map<String,Object>, AdminDTO> getAdminList(PageRequestDTO pageRequestDTO) {

		// 기준
		if (pageRequestDTO.getBasis() == "pk") {

			pageRequestDTO.setBasis("anum"); 
		}
		
		pageRequestDTO.setterChange();
		
		try {
			
			List<Map<String,Object>> result = adminDAO.getAdminList(pageRequestDTO);
			
			Function<Map<String,Object>, AdminDTO> fn = (map) -> mapToDto(map);
				
			
			
			// totalpage 조건 없음
			int count = adminDAO.countAdminAllList(pageRequestDTO);
			
			log.info("[AdminServiceImpl] : getAdminList : 성공");
			
			return new PageResultDTO<>(result, fn, pageRequestDTO, count);
		
		}catch(Exception e) {

			log.warn("[AdminServiceImpl] : getAdminList : 실패");
			
			e.printStackTrace();
			
			return null;
		}

	
	}

	// 등록 메서드
	@Override
	public Long register(AdminDTO adminDTO) {

		adminDTO.setAdminPW(passwordEncoder.encode(adminDTO.getAdminPW()));

		AdminVO admin = dtoTOvo(adminDTO);
		
		long result;

		try {
			
		adminDAO.addAdmin(admin);
		
		result = admin.getAnum();
		
		log.info("[AdminServiceImpl] : register : 성공");
		
		return result;
		
		}catch(Exception e) {
			
			result = 0;
			
			log.info("[AdminServiceImpl] : register : 실패");
			
			return result;
		}
	}
	
	@Override
	public AdminDTO findAdmin(String adminID) {
		
		Optional<AdminVO> result = adminDAO.findAdminIdByIDForJWT(adminID);

		AdminDTO adminDTO;

		if (result.isPresent()) {

			AdminVO admin = result.get();

			adminDTO = voTOdto(admin);

			log.info("[AdminServiceImpl] : findAdmin : 성공");
			
			return adminDTO;

		} else {
			
			log.info("[AdminServiceImpl] : findAdmin : 실패");
			
			return null;
		}
	}


	@Override
	public AdminDTO findAdmin(long anum) {

		Optional<AdminVO> result = adminDAO.findAdminIdByAnumForJWt(anum);

		AdminDTO adminDTO;

		if (result.isPresent()) {

			AdminVO admin = result.get();

			adminDTO = voTOdto(admin);

			log.info("[AdminServiceImpl] : findAdmin : 성공");
			
			return adminDTO;

		} else {
			
			log.info("[AdminServiceImpl] : findAdmin : 실패");
			
			return null;
		}
	}

	// 중복확인 메서드
	@Override
	public boolean CheckadminID(String adminId) {

		Optional<String> result = adminDAO.CheckByAdminId(adminId);
		
		return (result.isPresent()) ? true : false;
	}

	// 관리자 삭제 메서드
	@Override
	public long remove(long anum) {
		Long result;
		
		try {
			
		log.info("[AdminServiceImpl] : remove : 성공");
			
		result = adminDAO.removeAdminByAnum(anum);
		
		}catch(Exception e) {
			
			log.info("[AdminServiceImpl] : remove : 실패");
			
			result = 0L;
			
		}
		return (result == 1) ? anum : 0;
	}

	// 관리자 수정 메서드
	@Override
	@Transactional
	public Long update(AdminDTO adminDTO) {

		adminDTO.setAdminPW(passwordEncoder.encode(adminDTO.getAdminPW()));

		AdminVO admin = dtoTOvo(adminDTO);
		
		long result;;
		
		try {
			
		adminDAO.updateAdminByAnum(admin);
		adminDAO.updateModDateByAnum(adminDTO.getAnum());
		result = admin.getAnum();
		log.info("[AdminServiceImpl] : update : 성공");
		return result;
		}catch(Exception e) {
			
			log.info("[AdminServiceImpl] : update : 실패");
			
			result = 0L;
			return result;
		}
		
	
	}

}
