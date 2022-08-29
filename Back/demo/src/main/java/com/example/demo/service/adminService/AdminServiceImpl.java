package com.example.demo.service.adminService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.AdminDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.vo.AdminVO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final PasswordEncoder passwordEncoder;

	private final AdminDAO adminDAO;

	// 조회 메서드
	@Override
	public PageResultDTO<AdminVO, AdminDTO> getAmindList(PageRequestDTO pageRequestDTO) {

		log.info("관리자 리스트 찾는 중 : ");

		// 기준
		if (pageRequestDTO.getBasis() == "pk") {

			pageRequestDTO.setBasis("anum"); 
		}
		
		pageRequestDTO.setterChange();
		
		// 조회 메서드
		List<AdminVO> result = adminDAO.getAdminList(pageRequestDTO);

		// dto->vo 변환 함수 함수
		Function<AdminVO, AdminDTO> fn = (admin) -> voTOdto(admin);

		// totalpage 조건 없음
		int count = adminDAO.countAdminAllList(pageRequestDTO);

		return new PageResultDTO<>(result, fn, pageRequestDTO, count);
	}

	// 등록 메서드
	@Override
	public Long register(AdminDTO adminDTO) {

		adminDTO.setAdminPW(passwordEncoder.encode(adminDTO.getAdminPW()));

		AdminVO admin = dtoTOvo(adminDTO);

		log.info("관리자 추가 중 : " + adminDTO);

		adminDAO.addAdmin(admin);

		return (long) admin.getAnum();
	}
	
	@Override
	public AdminDTO findAdmin(String adminID) {
		log.info("관리자 조회 확인 : " + adminID);

		Optional<AdminVO> result = adminDAO.findAdminIdByID(adminID);

		AdminDTO adminDTO;

		if (result.isPresent()) {

			AdminVO admin = result.get();

			adminDTO = voTOdto(admin);

			return adminDTO;

		} else {

			return null;
		}
	}


	@Override
	public AdminDTO findAdmin(long anum) {
		log.info("관리자 조회 확인 : " + anum);

		Optional<AdminVO> result = adminDAO.findAdminIdByAnum(anum);

		AdminDTO adminDTO;

		if (result.isPresent()) {

			AdminVO admin = result.get();

			adminDTO = voTOdto(admin);

			return adminDTO;

		} else {
			return null;
		}
	}

	// 중복확인 메서드
	@Override
	public boolean CheckadminID(String adminId) {

		log.info("관리자 아이디 중복 확인 : " + adminId);

		Optional<String> result = adminDAO.CheckByAdminId(adminId);

		return (result.isPresent()) ? true : false;
	}

	// 관리자 삭제 메서드
	@Override
	public long remove(long anum) {

		log.info("관리자 삭제 중 :" + anum);

		Long result = adminDAO.removeAdminByAnum(anum);

		return (result == 1) ? anum : null;
	}

	// 관리자 수정 메서드
	@Override
	@Transactional
	public Long update(AdminDTO adminDTO) {

		adminDTO.setAdminPW(passwordEncoder.encode(adminDTO.getAdminPW()));

		AdminVO admin = dtoTOvo(adminDTO);

		log.info("관리자 수정 중 : " + adminDTO);

		adminDAO.updateAdminByAnum(admin);
		
		adminDAO.updateModDateByAnum(adminDTO.getAnum());

		return (long) admin.getAnum();
	}

}
