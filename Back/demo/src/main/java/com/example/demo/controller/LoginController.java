package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.auth.AdminCheck;
import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.JwtTokkenDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class LoginController {

	private final JwtTokkenDAO jwtTokkenDAO;

	private final AdminDAO adminDAO;
	
	private final MemberDAO memberDAO;


	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	@PostMapping("/api/login/admin")
	public ResponseEntity<AdminDTO> adminLogin(HttpServletRequest request) {

		log.info("/api/login/admin RestController 접근");

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
			

		return new ResponseEntity<>(adminDTO, HttpStatus.OK);

	}
	
	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	@PostMapping("/api/login/member")
	public ResponseEntity<MemberDTO> memberLogin(HttpServletRequest request) {

		log.info("/api/login/admin RestController 접근");

		String refreshToken = (String) request.getAttribute("refreshToken");

		String id = (String) request.getAttribute("id");

		String ip = (String) request.getHeader("X-FORWARDED-FOR");
		
		MemberDTO memberDTO = (MemberDTO) request.getAttribute("memberDTO");
		

		if (ip == null)
			ip = request.getRemoteAddr();

		boolean checkToken = jwtTokkenDAO.updateJWTByAdminIdAndIp(refreshToken, id, ip);

		if (!checkToken) {

			jwtTokkenDAO.createJWTTokenInDB(id, refreshToken, ip);
		}

			memberDAO.updateLastAceesDATEByMemberID(id);	

		return new ResponseEntity<>(memberDTO, HttpStatus.ACCEPTED);
	}

	@Transactional(rollbackFor = { RuntimeException.class, Error.class })
	@GetMapping("/api/logout/{id}")
	public ResponseEntity<String> logout(@PathVariable String id) {

		jwtTokkenDAO.refreshTokenRemove(id);
		
		boolean check = AdminCheck.check;

		if (check) {
			adminDAO.updateLastAceesDateByAdminID(id);
		} else {
			memberDAO.updateLastAceesDATEByMemberID(id);
		}
		
		log.info("로그인 성공");

		return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
	}

}
