package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.memberService.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AllPermitURI {
	
	private final MemberService memberService;
	
	// 회원 등록 URI
	@PostMapping("/register")
	public ResponseEntity<Long> addAdmin(@RequestBody MemberDTO memberDTO) {

		log.info("회원 등록 중 --------------------------");

		// 정상적으로 등록됐으면 anum 리턴, 아니면 0;
		Long result = memberService.register(memberDTO);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 회원 아이디 중복 체크 URI
	@GetMapping("/id-check/{memberID}")
	public ResponseEntity<Boolean> checkAdminID(@PathVariable String memberID) {
		
		log.info(" 회원 ID 중복 체크 중 " + memberID);

		// 회원 아이디 중복 체크 true or false 사용
		boolean result = memberService.checkMemberID(memberID);

		return (result) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.OK);
	}

}
