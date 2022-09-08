package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.auth.AdminCheck;
import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.JwtTokkenDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.dao.RefrigeratorDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.RefrigeratorDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

	private final JwtTokkenDAO jwtTokkenDAO;

	private final AdminDAO adminDAO;
	
	private final MemberDAO memberDAO;
	
	private final RefrigeratorDAO refrigeratorDAO;


	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	@PostMapping("/api/login/admin")
	public ResponseEntity<AdminDTO> adminLogin(HttpServletRequest request) {
	
		String refreshToken = (String) request.getAttribute("refreshToken");

		String id = (String) request.getAttribute("id");

		String ip = (String) request.getHeader("X-FORWARDED-FOR");
		
		AdminDTO adminDTO = (AdminDTO) request.getAttribute("adminDTO");

		if (ip == null)
			ip = request.getRemoteAddr();

		boolean checkToken = jwtTokkenDAO.updateJWTByAdminIdAndIp(refreshToken, id, ip);

		if (!checkToken) {

			jwtTokkenDAO.createJWTTokenInDB(id, refreshToken, ip);
		}
			adminDAO.updateLastAceesDateByAdminID(id);
			
		
		log.info("[/api/login/admin] [관리자 로그인 성공] [{}] ", id);
		
		return new ResponseEntity<>(adminDTO, HttpStatus.OK);

	}
	
	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	@PostMapping("/api/login/member")
	public ResponseEntity<MemberDTO> memberLogin(HttpServletRequest request) {

		String refreshToken = (String) request.getAttribute("refreshToken");

		String id = (String) request.getAttribute("id");

		String ip = (String) request.getHeader("X-FORWARDED-FOR");
		
		MemberDTO memberDTO = (MemberDTO) request.getAttribute("memberDTO");
		
		log.info("[/api/login/admin] [회원 로그인] [{}] ", id);
		
		List<RefrigeratorDTO> refre = refrigeratorDAO.findRefrigeratorDAObyMnum(memberDTO.getMnum());
		
		memberDTO.setRefrigerator(refre);

		if (ip == null)
			ip = request.getRemoteAddr();
		
		boolean checkToken = jwtTokkenDAO.updateJWTByAdminIdAndIp(refreshToken, id, ip);

		if (!checkToken) {

			jwtTokkenDAO.createJWTTokenInDB(id, refreshToken, ip);
		}

			memberDAO.updateLastAceesDATEByMemberID(id);	
			
		log.info("[/api/login/member] [RefershToken DB 저장] [{}]", memberDTO.getMemberID());
		
		log.info("[/api/login/member] [로그인 성공] [{}] ", memberDTO.getMemberID());
			
		return new ResponseEntity<>(memberDTO, HttpStatus.OK);
		
	}

	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	@GetMapping("/api/logout/{id}")
	public ResponseEntity<String> logout(@PathVariable String id) {

		try {
			
		jwtTokkenDAO.refreshTokenRemove(id);
		
		boolean check = AdminCheck.check;

		if (check) {
			adminDAO.updateLastAceesDateByAdminID(id);
		} else {
			memberDAO.updateLastAceesDATEByMemberID(id);
		}
		
		log.info("[/api/logout/{id}] [로그아웃 성공] [{}]", id);

		return new ResponseEntity<>(id, HttpStatus.OK);
		
		}catch(Exception e) {
			
			log.info("[/api/logout/{id}] [로그아웃 실패] [{}]", id);
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
