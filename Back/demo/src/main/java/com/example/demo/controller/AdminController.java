package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.service.adminService.AdminService;
import com.example.demo.service.memberService.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	
	private final MemberService memberService;

	// 관리자 아이디 중복 체크 URI
	@GetMapping("/checkID/{adminID}")
	public ResponseEntity<Boolean> checkAdminID(@PathVariable String adminID) {

		log.info("[/api/admin/checkID/{adminID}] [checkAdminID] [{}]", adminID);

		// 관리자 아이디 중복 체크 true or false 사용
		boolean result = adminService.CheckadminID(adminID);

		return (result) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.OK);
	}

	// 관리자 아이디로 관리자 조회
	@GetMapping("/adminID/{adminID}")
	public ResponseEntity<AdminDTO> searchAdmin(@PathVariable String adminID) {

		log.info("[/api/admin/adminID/{adminId}] [searchAdmin] [{}]", adminID);

		AdminDTO result = null;

		try {
			long anum = Long.parseLong(adminID);
			result = adminService.findAdmin(anum);
		} catch (Exception e) {
			result = adminService.findAdmin(adminID);
		}

		if (result == null) {
			log.warn("[/api/admin/adminID/{adminId}] [searchAdmin 실패] [{}]", adminID);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			log.warn("[/api/admin/adminID/{adminId}] [searchAdmin 성공] [{}]", adminID);
			result.setAdminPW("");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	// 관리자 리스트 조회
	@GetMapping("/admin-list")
	public ResponseEntity<PageResultDTO<Map<String,Object>, AdminDTO>> adminlist(@ModelAttribute PageRequestDTO pageRequestDTO,
			Model mopdel) {

		log.info("[/api/admin/admin-list] [adminlist] []");

		PageResultDTO<Map<String,Object>, AdminDTO> result = adminService.getAdminList(pageRequestDTO);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 관리자 등록 URI
	@PostMapping("/register")

	public ResponseEntity<Long> addAdmin(@RequestBody AdminDTO adminDTO) {

		log.info("[/api/admin/register] [addAdmin] [{}]", adminDTO.getAdminID());

		// 정상적으로 등록됐으면 관리자 식별자 리턴;
		Long result = adminService.register(adminDTO);

		if (result > 0) {
			log.info("[/api/admin/register] [addAdmin 성공] [{}]", adminDTO.getAdminID());
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			log.warn("[/api/admin/register] [addAdmin 실패] [{}]", adminDTO.getAdminID());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}

	// 관리자 삭제 URI, 관리자 삭제 시 연관된 게시글, 댓글 삭제도 이루어져야 할지 회의 필요
	// 탈퇴 삭제
	@DeleteMapping("/delete/{anum}")
	public ResponseEntity<Long> removeAdmin(@PathVariable Long anum) {

		log.info("[/api/delete/{anum}] [removeAdmin] [{}]", anum);

		// 정상적으로 삭제됐으면 anum 리턴, 아니면 0
		Long result = adminService.remove(anum);

		if (result > 0) {
			log.info("[/api/admin/delete/{anum}] [removeAdmin 성공] [{}]",anum);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			log.warn("[/api/admin/delete/{anum}] [removeAdmin 실패] [{}]", anum);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	// 관리자 수정 URI
	@PutMapping("/update")
	public ResponseEntity<Long> updateAdmin(@RequestBody AdminDTO adminDTO) {

		log.info("[/api/admin/update] [updateAdmin] [{}]",adminDTO.getAdminID());

		Long result = adminService.update(adminDTO);

		if (result > 0) {
			log.info("[/api/admin/update] [updateAdmin 성공] [{}]",adminDTO.getAdminID());
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			log.warn("[/api/admin/update] : [updateAdmin 실패] [{}] ",adminDTO.getAdminID());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	
	// 회원 리스트 조회
	@GetMapping("/member-list")
	public ResponseEntity<PageResultDTO<Map<String, Object>, MemberDTO>> adminlist2(
			@ModelAttribute PageRequestDTO pageRequestDTO) {

		log.info("[/api/member/member-list] [adminlist2]");

		PageResultDTO<Map<String, Object>, MemberDTO> result = memberService.getMemberList2(pageRequestDTO);

		if (result == null)
			new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
