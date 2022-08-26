package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.service.memberService.MemberService;
import com.example.demo.vo.AdminVO;
import com.example.demo.vo.MemberVO;
import com.github.javaparser.utils.Log;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// 회원 아이디 또는 회원 식별자로 회원 조회
	@GetMapping("/search/{memberID}")
	public ResponseEntity<MemberDTO> searchMember(@PathVariable String memberID) {
		
		log.info(" 회원 조회 중 " + memberID);
		
		MemberDTO result = null;

		try {
			long mnum = Long.parseLong(memberID);
			result = memberService.findMember(mnum);
		} catch (Exception e) {
			result = memberService.findMember(memberID);
		}

		// 회원 아이디 중복 체크 true or false 사용
		if (result == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			result.setMemberPW("");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	// 회원 리스트 조회
	@GetMapping("/member-list")
	public ResponseEntity<PageResultDTO<MemberVO, MemberDTO>> adminlist(@ModelAttribute PageRequestDTO pageRequestDTO) {

		log.info("회원 리스트 조회 ------------------------- ");
		
		PageResultDTO<MemberVO, MemberDTO> result = memberService.getAmindList(pageRequestDTO);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//회원 삭제 URI
	@GetMapping("/delete/{mnum}")
	public ResponseEntity<Long> removeAdmin(@PathVariable Long mnum) {

		log.info("Delete member by mnum : " + mnum);

		// 정상적으로 삭제됐으면 anum 리턴, 아니면 0
		Long result = memberService.remove(mnum);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 회원 수정 URI
		@PostMapping("/update")

		public ResponseEntity<Long> updateAdmin(@RequestBody MemberDTO memberDTO) {

			log.info("회원 정보 업데이트 : " + memberDTO.getMemberID());

			Long result = memberService.update(memberDTO);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}


	

}
