package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.JwtTokkenDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
public class LoginController {
	
	private final JwtTokkenDAO jwtTokkenDAO;
	
	private final AdminDAO adminDAO;
	
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	@PostMapping("/api/login")
	public ResponseEntity<String> login (HttpServletRequest request) {
		log.info("/api/login RestController 접근");
		
		String refreshToken = (String) request.getAttribute("refreshToken");
		
		String id = (String) request.getAttribute("id");
		
		jwtTokkenDAO.createJWTTokenInDB(id,refreshToken);
		
		adminDAO.updateLastAceesDATEByAdmin(id);
		
		return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
		
	}
	
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	@GetMapping("/api/logout/{id}")
	public ResponseEntity<String> logout(@PathVariable String id){

		jwtTokkenDAO.refreshTokenRemove(id);
		
		adminDAO.updateLastAceesDATEByAdmin(id);
		
		log.info("Logout Success");
		
		return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
	}
	

}
