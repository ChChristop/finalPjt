package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AteDTO;
import com.example.demo.dto.DishCommDTO;
import com.example.demo.dto.DishLikeDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.PointDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultVO;
import com.example.demo.service.AteService;
import com.example.demo.service.DishService;
import com.example.demo.service.memberService.MemberService;
import com.example.demo.service.point.PointService;
import com.example.demo.vo.Ate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	private final PointService pointService;
	
	private final AteService ateService;
	
	private final DishService dishService;

	// 회원 아이디 또는 회원 식별자로 회원 조회
	@GetMapping("/search/{memberID}")
	public ResponseEntity<MemberDTO> searchMember(@PathVariable String memberID) {

		log.info("[/api/member/search/{memberID}] [searchMember] [{}]", memberID);

		MemberDTO result = null;

		try {
			long mnum = Long.parseLong(memberID);
			result = memberService.findMember(mnum);

		} catch (Exception e) {
			result = memberService.findMember(memberID);
		}

		// 회원 아이디 중복 체크 true or false 사용
		if (result == null) {
			log.info("[/api/member/search/{memberID}] [searchMember 성공] [{}]",memberID);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			log.warn("[/api/member/search/{memberID}] [searchMember 실패] [{}]", memberID);
			
			result.setMemberPW("");
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}


	// 회원 삭제 URI
	@DeleteMapping("/delete/{mnum}")
	public ResponseEntity<Long> removeMember(@PathVariable Long mnum, HttpServletRequest request) {

		log.info("[/api/member/delete/{mnum}] [removeMember] [{}]", mnum);

		// 추후 주석 해제 예정

		/*
		 * long getNumber = (long) request.getAttribute("GetNumber");
		 * 
		 * if (mnum != getNumber) {
		 * 
		 * log.warn("[/api/member/delete/{mnum}] [removeAdmin 실패] [{}]", mnum);
		 * 
		 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		 */

		// 정상적으로 삭제됐으면 anum 리턴, 아니면 0
		Long result = memberService.remove(mnum);

		if (result > 0) {
			log.info("[api/member/delete/{mnum}] [removeMember 성공] [{}]", mnum);
			return new ResponseEntity<>(mnum, HttpStatus.OK);
		} else {
			log.warn("[/api/member/delete/{mnum}] [removeMember 실패] [{}]", mnum);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	// 회원 수정 URI
	@PutMapping("/update")
	public ResponseEntity<Long> updateMember(@RequestBody MemberDTO memberDTO, HttpServletRequest request) {

		log.info("[/api/member/update] [updateMember] [{}]", memberDTO.getMemberID());

		// 추후 주석 해제 예정

		/*
		 * long getNumber = (long) request.getAttribute("GetNumber");
		 * 
		 * if (memberDTO.getMnum() != getNumber) {
		 * 
		 * log.warn("[/api/member/update] [updateAdmin 실패] [{}]", memberDTO.getMnum());
		 * 
		 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		 */

		Long result = memberService.update(memberDTO);

		if (result > 0) {
			log.info("[/api/member/delete/{mnum}] [updateMember 성공] [{}]", memberDTO.getMemberID());
			return new ResponseEntity<>(memberDTO.getMnum(), HttpStatus.OK);
		} else {
			log.warn("[/api/member/delete/{mnum}] [updateMember 실패] [{}]", memberDTO.getMemberID());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	// 포인트 조회
	@GetMapping("/point-list/{mnum}")
	public ResponseEntity<PageResultVO<PointDTO>> userPoint(
			@PathVariable long mnum, PageRequestDTO pageRequestDTO){
		
		log.info("[/api/point-list/{mnum}] [userPoint] [{}]",mnum);
		
		// 추후 주석 해제 예정

		/*
		 * long getNumber = (long) request.getAttribute("GetNumber");
		 * 
		 * if (mnum != getNumber) {
		 * 
		 * log.warn("[/api/point-list/{mnum}] [userPoint 실패] [{}]", mnum);
		 * 
		 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		 */
		
		PageResultVO<PointDTO> result =  pointService.userPoint(pageRequestDTO,mnum);
		
		if(result == null) return new ResponseEntity<>(new PageResultVO<PointDTO>(),HttpStatus.ACCEPTED);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//7일 이내 활동 내역
	@GetMapping("/activity-history/{mnum}")
	public ResponseEntity<PageResultVO<PointDTO>> activityHistory(
			@PathVariable long mnum, PageRequestDTO pageRequestDTO){
		
		log.info("[/api/member/activity-history/{mnum}] [activityHistory] [{}]",mnum);
		
		// 추후 주석 해제 예정

		/*
		 * long getNumber = (long) request.getAttribute("GetNumber");
		 * 
		 * if (mnum != getNumber) {
		 * 
		 * log.warn("[/api/member/activity-history/{mnum}] [activityHistory 실패] [{}]",mnum);
		 * 
		 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		 */
		
		PageResultVO<PointDTO> result =  pointService.userPointby7d(pageRequestDTO,mnum);
		
		if(result == null) return new ResponseEntity<>(new PageResultVO<PointDTO>(),HttpStatus.ACCEPTED);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//좋아요 조회
	@GetMapping("/like-list/{mnum}")
	public ResponseEntity<PageResultVO<DishLikeDTO>> likeList(
			@PathVariable long mnum, PageRequestDTO pageRequestDTO){
		
		log.info("[/api/member/like-list/{mnum}] [likeList] [{}]",mnum);	
		
		// 추후 주석 해제 예정

		/*
		 * long getNumber = (long) request.getAttribute("GetNumber");
		 * 
		 * if (mnum != getNumber) {
		 * 
		 * log.warn("[/api/member/like-list/{mnum}] [likeList 실패] [{}]",mnum);	
		 * 
		 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
		 */
		
		PageResultVO<DishLikeDTO> result = dishService.getLikeListbyMnum(pageRequestDTO, mnum);
		
		
		
		if(result == null) return new ResponseEntity<>(new PageResultVO<DishLikeDTO>(),HttpStatus.ACCEPTED);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//댓글 조회
		@GetMapping("/dishcomm-list/{mnum}")
		public ResponseEntity<PageResultVO<DishCommDTO>> commList(
				@PathVariable long mnum, PageRequestDTO pageRequestDTO){
			
			log.info("[/api/member/dishcomm-list/{mnum}] [commList] [{}]",mnum);
			
			// 추후 주석 해제 예정

			/*
			 * long getNumber = (long) request.getAttribute("GetNumber");
			 * 
			 * if (mnum != getNumber) {
			 * 
			 * log.warn("[/api/member/dishcomm-list/{mnum}] [commList 실패] [{}]",mnum);
			 * 
			 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
			 */
			
			PageResultVO<DishCommDTO> result  = dishService.getCommListbyMnum(pageRequestDTO, mnum);
			
			if(result == null) return new ResponseEntity<>(new PageResultVO<DishCommDTO>(),HttpStatus.ACCEPTED);
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		//먹음 리스트 조회(페이지 처리 안한 것)
		@GetMapping("/getList/{mnum}")
		public ResponseEntity<List<Ate>> getUserList(@PathVariable long mnum, HttpServletRequest request) {
			
			log.info("[/api/getList/{mnum}] [getUserList] ] [{}]", mnum);
			
			
			// 추후 주석 해제 예정

			/*
			 * long getNumber = (long) request.getAttribute("GetNumber");
			 * 
			 * if (mnum != getNumber) {
			 * 
			 * log.warn("[/api/getList/{mnum}] [getUserList 실패] ] [{}]", mnum);;
			 * 
			 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
			 */	  
			 
			List<Ate> result = ateService.getAllList(mnum);

			log.info("[/api/getList/{mnum}] [getUserList 성공] [{}]", mnum);
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		//먹음 리스트 조회(페이지 처리 한 것)
		@GetMapping("/ate-list/{mnum}")
		public ResponseEntity<PageResultVO<AteDTO>> getAteUserList2(@PathVariable long mnum, HttpServletRequest request,
				PageRequestDTO pageRequestDTO) {
			
			log.info("[/api/member/ate-list/{mnum}] [getAteUserList2] [{}]", mnum);
			
			// 추후 주석 해제 예정

			/*
			 * long getNumber = (long) request.getAttribute("GetNumber");
			 * 
			 * if (mnum != getNumber) {
			 * 
			 * log.warn("[/api/member/ate-list/{mnum}] [getAteUserList2 실패] ] [{}]", mnum);
			 * 
			 * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
			 */
			
			PageResultVO<AteDTO> result = ateService.getUserAteList(pageRequestDTO,mnum);

			if(result == null) return new ResponseEntity<>(new PageResultVO<AteDTO>(),HttpStatus.ACCEPTED);
			
			log.info("[/api/member/ate-list/{mnum}] [getAteUserList2 성공] [{}]", mnum);
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	

}
