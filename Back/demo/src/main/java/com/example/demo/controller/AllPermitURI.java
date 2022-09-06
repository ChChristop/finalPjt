package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.DishService;
import com.example.demo.service.mail.MailService;
import com.example.demo.service.memberService.MemberService;
import com.example.demo.vo.pwToken.PwTokenVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AllPermitURI {
	
	private final MemberService memberService;
	
	private final DishService dishService;
	
	private final MailService mailService;
	
	// 회원 등록 URI
	@PostMapping("/register")
	public ResponseEntity<Long> addAdmin(@RequestBody MemberDTO memberDTO) {

		log.info("[/api/register] [회원 등록] [{}]", memberDTO.getMemberID());

		// 정상적으로 등록됐으면 anum 리턴, 아니면 0;
		Long result = memberService.register(memberDTO);
		
		if(result > 0) log.info("[/api/register] [회원 가입 성공] [{}]", memberDTO.getMemberID());
		else log.warn("[/api/register] [회원 가입 실패] [{}]", memberDTO.getMemberID());
		

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 회원 아이디 중복 체크 URI
	@GetMapping("/id-check/{memberID}")
	public ResponseEntity<Boolean> checkAdminID(@PathVariable String memberID) {
		
		log.info("[/api/id-check/{memberID}] [ID 중복 체크] [{}]", memberID);

		// 회원 아이디 중복 체크 true or false 사용
		boolean result = memberService.checkMemberID(memberID);

		return (result) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.OK);
	}
	

	
	@GetMapping("/topUser")
	public ResponseEntity<List<Map<String,Object>>> topUser(){
		
		log.info("[/topUser] [탑 유저 조회]");
		
		List<Map<String,Object>> result = memberService.topUser();
		
		return new ResponseEntity<>(result,  HttpStatus.OK);
	}
	
	@GetMapping("/topDish")
	public ResponseEntity<List<Map<String,Object>>> topDish(){
		
		log.info("[/topDish] [탑 레시피 조회]");
		
		List<Map<String,Object>> result = dishService.topDish();
		
		return new ResponseEntity<>(result,  HttpStatus.OK);
	}
	
	//비밀번호 재설정 이메일 발송
	@PostMapping("/account/email/send")
	public boolean sendEmail(@RequestBody Map<String,String> memberID) {
		
		String email = memberID.get("memberID");
		
		log.info("[/api/account/email/send] [비밀번호 재설정 이메일 발송] [{}]",email);
		
		boolean result = mailService.sendMail(email);
		
		return result;
	}
	
	//토큰 유효성 확인
	@PostMapping("/account/email/check-token")
	public ResponseEntity<String> checkToken(@RequestBody PwTokenVO pwTokenVO) {
		
		String result = mailService.checkToken(pwTokenVO.getPwToken());
		
		log.info("[/account/email/check-token] [비밀번호 토큰 확인] [{}]",pwTokenVO.getPwToken());
		
		if(result.equals("No Token") || result.equals("No validity"))
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	
	//비밀번호 변경
	@PostMapping("/account/email/changePW")
	public ResponseEntity<Boolean> changePW(@RequestBody MemberDTO memberDTO) {
		
		boolean result = memberService.changePW(memberDTO);
		
		log.info("[/account/email/changePW] [비밀번호 변경] [{}]", memberDTO.getMemberID());
		
		return new ResponseEntity<Boolean>(result,HttpStatus.OK);
	}

}


