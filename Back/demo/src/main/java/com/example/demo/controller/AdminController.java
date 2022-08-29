package com.example.demo.controller;

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
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.service.adminService.AdminService;
import com.example.demo.vo.AdminVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	// 관리자 아이디 중복 체크 URI
	@GetMapping("/checkID/{adminID}")
	public ResponseEntity<Boolean> checkAdminID(@PathVariable String adminID) {

		log.info("/checkID/{adminID} 진입");
		// 관리자 아이디 중복 체크 true or false 사용
		boolean result = adminService.CheckadminID(adminID);

		return (result) ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.OK);
	}

	// 관리자 아이디로 관리자 조회
	@GetMapping("/adminID/{adminId}")
	public ResponseEntity<AdminDTO> searchAdmin(@PathVariable String adminId) {

		AdminDTO result = null;

		try {
			long anum = Long.parseLong(adminId);
			result = adminService.findAdmin(anum);
		} catch (Exception e) {
			result = adminService.findAdmin(adminId);
		}

		// 관리자 아이디 중복 체크 true or false 사용
		if (result == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		else {
			result.setAdminPW("");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	// 관리자 리스트 조회
	@GetMapping("/admin-list")
	public ResponseEntity<PageResultDTO<AdminVO, AdminDTO>> adminlist(@ModelAttribute PageRequestDTO pageRequestDTO,
			Model mopdel) {

		PageResultDTO<AdminVO, AdminDTO> result = adminService.getAmindList(pageRequestDTO);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 관리자 등록 URI
	@PostMapping("/register")

	public ResponseEntity<Long> addAdmin(@RequestBody AdminDTO adminDTO) {

		log.info("Controller /register 접근");

		// 정상적으로 등록됐으면 anum 리턴, 아니면 0;
		Long result = adminService.register(adminDTO);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 관리자 삭제 URI, 관리자 삭제 시 연관된 게시글, 댓글 삭제도 이루어져야 할지 회의 필요
	// 탈퇴 삭제
	@DeleteMapping("/delete/{anum}")
	public ResponseEntity<Long> removeAdmin(@PathVariable Long anum) {

		log.info("Delete admin by anum : " + anum);

		// 정상적으로 삭제됐으면 anum 리턴, 아니면 0
		Long result = adminService.remove(anum);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 관리자 수정 URI
	@PutMapping("/update")

	public ResponseEntity<Long> updateAdmin(@RequestBody AdminDTO adminDTO) {

		log.info("Update admin : " + adminDTO);

		Long result = adminService.update(adminDTO);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
