package com.example.demo.controller;

import java.util.ArrayList;
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
import com.example.demo.service.elastic.ElasticSearch;
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
	
	private final ElasticSearch elaService;

	// 회원 등록 URI
	@PostMapping("/register")
	public ResponseEntity<Long> addAdmin(@RequestBody MemberDTO memberDTO) {
		try {
			log.info("[/api/register] [addAdmin] [{}]", memberDTO.getMemberID());

			// 정상적으로 등록됐으면 anum 리턴, 아니면 0;
			Long result = memberService.register(memberDTO);

			if (result > 0) {

				log.info("[/api/register] [addAdmin 성공] [{}]", memberDTO.getMemberID());

				return new ResponseEntity<>(result, HttpStatus.OK);

			} else {

				log.warn("[/api/register] [addAdmin 실패] [{}]", memberDTO.getMemberID());

				return new ResponseEntity<>(0L, HttpStatus.ACCEPTED);

			}
		} catch (Exception e) {

			log.warn("[/api/register] [addAdmin 실패(서버)] [{}]", memberDTO.getMemberID());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 회원 아이디 중복 체크 URI
	@GetMapping("/id-check/{memberID}")
	public ResponseEntity<Boolean> checkAdminID(@PathVariable String memberID) {

		try {

			log.info("[/api/id-check/{memberID}] [checkAdminID] [{}]", memberID);

			// 회원 아이디 중복 체크 true or false 사용
			boolean result = memberService.checkMemberID(memberID);

			return (result) ? new ResponseEntity<>(true, HttpStatus.OK)
					: new ResponseEntity<>(false, HttpStatus.ACCEPTED);

		} catch (Exception e) {

			return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/topUser")
	public ResponseEntity<List<Map<String, Object>>> topUser() {
		
		try {

			log.info("[/topUser] [topUser] []");

			List<Map<String, Object>> result = memberService.topUser();

			if (result.size() == 0) {
				
				log.info("[/topUser] [topUser] []");
				
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.ACCEPTED);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {

			log.info("[/topUser] [topUser 실패(서버)] []");

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/topDish")
	public ResponseEntity<List<Map<String, Object>>> topDish() {
		
		try {
			
			log.info("[/topDish] [topDish] []");

			List<Map<String, Object>> result = dishService.topDish();

			if (result.size() == 0) {
				log.warn("[/topDish] [topDish 실패(서버)] []");
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.ACCEPTED);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			log.warn("[/topDish] [topDish 실패(서버)] []");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 비밀번호 재설정 이메일 발송
	@PostMapping("/account/email/send")
	public ResponseEntity<Boolean> sendEmail(@RequestBody Map<String, String> memberID) {

		try {
			
			String email = memberID.get("memberID");

			log.info("[/api/account/email/send] [sendEmail] [{}]", email);

			boolean result = mailService.sendMail(email);

			if (!result) {

				log.info("[/api/account/email/send] [sendEmail 실패] [{}]", email);

				return new ResponseEntity<>(result, HttpStatus.ACCEPTED);

			}

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {

			log.info("[/api/account/email/send] [sendEmail 실패(서버)] [{}]", memberID);

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 토큰 유효성 확인
	@PostMapping("/account/email/check-token")
	public ResponseEntity<String[]> checkToken(@RequestBody PwTokenVO pwTokenVO) {
		try {
			
		String result[] = mailService.checkToken(pwTokenVO.getPwToken());

			log.info("[/account/email/check-token] [checkToken] [{}]", pwTokenVO.getPwToken());

			if (result.length == 0) {

				log.info("[/account/email/check-token] [checkToken 실패] [{}]", pwTokenVO.getPwToken());

				return new ResponseEntity<>(new String[] {}, HttpStatus.ACCEPTED);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {

			log.info("[/account/email/check-token] [checkToken 실패(서버)] [{}]", pwTokenVO.getPwToken());

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 비밀번호 변경
	@PostMapping("/account/email/changePW")
	public ResponseEntity<Boolean> changePW(@RequestBody MemberDTO memberDTO) {
		try {

			boolean result = memberService.changePW(memberDTO);

			if (!result) {
				log.info("[/account/email/changePW] [changePW 실패] [{}]", memberDTO.getMemberID());
				return new ResponseEntity<Boolean>(result, HttpStatus.ACCEPTED);
			}
			
			log.info("[/account/email/changePW] [changePW 변경] [{}]", memberDTO.getMemberID());

			return new ResponseEntity<Boolean>(result, HttpStatus.OK);

		} catch (Exception e) {
			log.info("[/account/email/changePW] [changePW 실패(서버)] [{}]", memberDTO.getMemberID());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/keyword")
	public ResponseEntity<List<String>> keyword(){
		
		List<String> result  = elaService.search();
		
		return new ResponseEntity<List<String>>(result,HttpStatus.OK);
		
		
	}

}
