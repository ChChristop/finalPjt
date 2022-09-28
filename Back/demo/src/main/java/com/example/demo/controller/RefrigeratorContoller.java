package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RefrigeratorDTO;
import com.example.demo.service.refrigerator.RefrigeratorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/refre/")
@RequiredArgsConstructor
public class RefrigeratorContoller {

	// Member 권한이 있을 경우 접근 가능

	private final RefrigeratorService refrigeratorService;

	// 냉장고 조회
	@GetMapping("/list/{mnum}")
	public ResponseEntity<List<RefrigeratorDTO>> refreList(@PathVariable long mnum, HttpServletRequest request) {

		log.info("[api/refre/list/{mnum}] [refreList] [{}]", mnum);
		try {

			long getNumber = (long) request.getAttribute("GetNumber");

			// // jwt 인증 정보와 요청한 회원의 정보가 다를 경우 //
			if (mnum != getNumber) { // //
				log.warn("[api/refre/list/{mnum}] [refreList 실패] [{}]", mnum);
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			List<RefrigeratorDTO> result = refrigeratorService.findRefreigeratorListbyMnum(mnum);

			if (result.size() == 0) {

				log.info("[api/refre/list/{mnum}] [refreList 실패 회원번호 오류] [{}]", mnum);

				return new ResponseEntity<>(new ArrayList<RefrigeratorDTO>(), HttpStatus.ACCEPTED);

			}

			log.info("[api/refre/list/{mnum}] [refreList 성공] [{}]", mnum);

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {

			log.warn("[api/refre/list/{mnum}] [refreList 실패] [{}]", mnum);

			return new ResponseEntity<List<RefrigeratorDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 냉장고 재료 추가
	@PostMapping("/add")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Long> refreAdd(@RequestBody RefrigeratorDTO refrigeratorDTO, HttpServletRequest request) {
		log.info("[/api/refre/add] [refreAdd] [{}/{}]", refrigeratorDTO.getMnum(), refrigeratorDTO.getIname());
		try {

			long getNumber = (long) request.getAttribute("GetNumber");

			// jwt 인증 정보와 요청한 회원의 정보가 다를 경우
			if (refrigeratorDTO.getMnum() != getNumber) {

				log.warn("[/api/refre/add] [refreAdd 실패] [{}/{}]", refrigeratorDTO.getMnum(),
						refrigeratorDTO.getIname());

				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			long result = refrigeratorService.register(refrigeratorDTO);

			if (result > 0) {
				log.info("[/api/refre/add] [refreAdd 성공] [{}/{}]", refrigeratorDTO.getMnum(),
						refrigeratorDTO.getIname());
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				log.warn("[/api/refre/add] [refreAdd 실패] [{}/{}]", refrigeratorDTO.getMnum(),
						refrigeratorDTO.getIname());
				return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			// 예상치 못한 예외
			log.warn("[/api/refre/add] [refreAdd 실패] [{}/{}]", refrigeratorDTO.getMnum(), refrigeratorDTO.getIname());
			return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// 냉장고 재료 삭제
	@DeleteMapping("/removal/{mnum}/{refrenum}")
	public ResponseEntity<Boolean> refreRemove(@PathVariable long refrenum, @PathVariable long mnum,
			HttpServletRequest request) {

		log.info("[/api/refre/removal/{mnum}/{refrenum}] [refreRemove] [{}/{}]", mnum, refrenum);
		try {
			// 잠시 주석
			long getNumber = (long) request.getAttribute("GetNumber");
//
			// jwt 인증 정보와 요청한 회원의 정보가 다를 경우
			if (mnum != getNumber) {
				System.out.println(getNumber);
				log.warn("[/api/refre/removal/{mnum}/{refrenum}] [refreRemove 실패] [{}/{}]", mnum, refrenum);

				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			long result = refrigeratorService.remove(refrenum);

			if (result > 0) {
				log.info("[/api/refre/removal/{mnum}/{refrenum}] [refreRemove 성공] [{}/{}]", mnum, refrenum);
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				log.warn("[/api/refre/removal/{mnum}/{refrenum}] [refreRemove 실패1] [{}/{}]", mnum, refrenum);
				return new ResponseEntity<>(false, HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			log.warn("[/api/refre/removal/{mnum}/{refrenum}] [refreRemove 실패2] [{}/{}]", mnum, refrenum);
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// 냉장고 재료 업데이트
	@PutMapping("/update")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Boolean> refreUpdate(@RequestBody RefrigeratorDTO refrigeratorDTO,
			HttpServletRequest request) {

		log.info("[/api/refre/update] [refreUpdate] [{}/{}]", refrigeratorDTO.getMnum(), refrigeratorDTO.getIname());

		try {
			// 잠시 주석
			long getNumber = (long) request.getAttribute("GetNumber");

			// jwt 인증 정보와 요청한 회원의 정보가 다를 경우
			if (refrigeratorDTO.getMnum() != getNumber) {

				log.warn("[/api/refre/update] [refreUpdate 실패] [{}/{}]", refrigeratorDTO.getMnum(),
						refrigeratorDTO.getIname());

				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			long result = refrigeratorService.update(refrigeratorDTO);

			if (result > 0) {
				log.info("[/api/refre/update] [refreUpdate 성공] [{}/{}]", refrigeratorDTO.getMnum(),
						refrigeratorDTO.getIname());
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				log.warn("[/api/refre/update] [refreUpdate 실패] [{}/{}]", refrigeratorDTO.getMnum(),
						refrigeratorDTO.getIname());
				return new ResponseEntity<>(false, HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			log.warn("[/api/refre/update] [refreUpdate 실패] [{}/{}]", refrigeratorDTO.getMnum(),
					refrigeratorDTO.getIname());
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
