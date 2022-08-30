package com.example.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.service.memberService.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// 회원 아이디 또는 회원 식별자로 회원 조회
	@GetMapping("/search/{memberID}")
	public ResponseEntity<MemberDTO> searchMember(@PathVariable String memberID) {

		log.info("[MemberController /api/member/search/{memberID}] : 회원 조회 중 : " + memberID);

		MemberDTO result = null;

		try {
			long mnum = Long.parseLong(memberID);
			result = memberService.findMember(mnum);

		} catch (Exception e) {
			result = memberService.findMember(memberID);
		}

		// 회원 아이디 중복 체크 true or false 사용
		if (result == null) {
			log.info("[MemberController /api/member/search/{memberID}] : 회원 조회 실패 : " + memberID);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			log.info("[MemberController /api/member/search/{memberID}] : 회원 조회 성공 : " + memberID);
			
			result.setMemberPW("");
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

//	// 회원 리스트 조회
//	@GetMapping("/member-list")
//	public ResponseEntity<PageResultDTO<MemberVO, MemberDTO>> adminlist(@ModelAttribute PageRequestDTO pageRequestDTO) {
//
//		log.info("회원 리스트 조회 ------------------------- ");
//		
//		PageResultDTO<MemberVO, MemberDTO> result = memberService.getAmindList(pageRequestDTO);
//
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}

	// 회원 리스트 조회
	@GetMapping("/member-list")
	public ResponseEntity<PageResultDTO<Map<String, Object>, MemberDTO>> adminlist2(
			@ModelAttribute PageRequestDTO pageRequestDTO) {

		log.info("[MemberController /api/member/search/{memberID}] : 회원 리스트 조회 ");

		PageResultDTO<Map<String, Object>, MemberDTO> result = memberService.getAdminList2(pageRequestDTO);

		if (result == null)
			new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 회원 삭제 URI
	@DeleteMapping("/delete/{mnum}")
	public ResponseEntity<Long> removeAdmin(@PathVariable Long mnum, HttpServletRequest request) {

		log.info("[MemberController /api/member/delete/{mnum}] : 회원 탈퇴 : " + mnum);

		// 추후 주석 해제 예정

		/*
		 * long getNumber = (long) request.getAttribute("GetNumber");
		 * 
		 * if (mnum != getNumber) {
		 * 
		 * log.warn("/api/member/delete/{mnum} 접근 : " + "jwt 회원번호 :" + getNumber +
		 * "요청 회원번호 :" + mnum);
		 * 
		 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		 */

		// 정상적으로 삭제됐으면 anum 리턴, 아니면 0
		Long result = memberService.remove(mnum);

		if (result > 0) {
			log.info("[MemberController /api/member/delete/{mnum}] : 회원 탈퇴 성공 : " + mnum);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			log.warn("[MemberController /api/member/delete/{mnum}] : 회원 탈퇴 실패: " + mnum);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	// 회원 수정 URI
	@PutMapping("/update")
	public ResponseEntity<Long> updateAdmin(@RequestBody MemberDTO memberDTO, HttpServletRequest request) {

		log.info("[MemberController /api/member/update] : 회원 수정 : " + memberDTO.getMemberID());

		// 추후 주석 해제 예정

		/*
		 * long getNumber = (long) request.getAttribute("GetNumber");
		 * 
		 * if (memberDTO.getMnum() != getNumber) {
		 * 
		 * log.warn("/api/member/delete/{mnum} 접근 : " + "jwt 회원번호 :" + getNumber +
		 * "요청 회원번호 :" + memberDTO.getMnum());
		 * 
		 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		 */

		Long result = memberService.update(memberDTO);

		if (result > 0) {
			log.info("[MemberController /api/member/delete/{mnum}] : 회원 수정 성공 : " + memberDTO.getMemberID());
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			log.warn("[MemberController /api/member/delete/{mnum}] : 회원 수정 실패: " + memberDTO.getMemberID());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}
