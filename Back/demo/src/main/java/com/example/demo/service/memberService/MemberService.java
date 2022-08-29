package com.example.demo.service.memberService;

import java.util.Map;

import com.example.demo.dto.MemberDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.vo.MemberVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public interface MemberService {
	

	MemberDTO findMember(long mnum);
	
	MemberDTO findMember(String memberID);
	
	Long register(MemberDTO memberDTO);
	
	boolean checkMemberID(String id);
	
	long remove(long mnum);
	
	Long update(MemberDTO memberDTO);
	
	PageResultDTO<MemberVO, MemberDTO> getAmindList(PageRequestDTO pageRequestDTO);
	
	PageResultDTO<Map<String,Object>, MemberDTO>  getAmindList2(PageRequestDTO pageRequestDTO);
	
	default MemberVO dtoTOvo(MemberDTO memberDTO) {
		
		MemberVO member = MemberVO.builder()
				.mnum(memberDTO.getMnum())
				.memberID(memberDTO.getMemberID())
				.memberPW(memberDTO.getMemberPW())
				.nickname(memberDTO.getNickname())
				.phoneNumber(memberDTO.getPhoneNumber())
				.role("MEMBER")
				.build();
		
		return member;
		
	}
	

	default MemberDTO voTOdto(MemberVO member) {

		MemberDTO memberDTO = MemberDTO.builder()
				.mnum(member.getMnum())
				.memberID(member.getMemberID())
				.nickname(member.getNickname())
				.phoneNumber(member.getPhoneNumber())
				.role(member.getRole())
				.lastAccessDate(member.getLastAccessDate())
				.date(member.getDate())
				.modDate(member.getModDate())
				.build();
		
				return memberDTO;
	}
	
	default MemberDTO mapTOdto(Map<String,Object> member) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		MemberDTO memberDTO = objectMapper.convertValue(member, MemberDTO.class);
		
		
//		MemberDTO memberDTO = MemberDTO.builder()
//				.mnum(Long.valueOf(String.valueOf(member.get("MNUM"))))
//				.memberID((String)member.get("MEMBERID"))
//				.nickname((String)member.get("NICKNAME"))
//				.phoneNumber((String)member.get("PHONENUMBER"))
//				.role((String) member.get("ROLE"))
//				.lastAccessDate((LocalDateTime)member.get("DATE"))
//				.date((LocalDateTime)member.get("DATE"))
//				.modDate((LocalDateTime)member.get("MODATE"))
//				.ateCount(Integer.valueOf(String.valueOf(member.get("ateCount"))))
//				.build();
		
		return memberDTO;
	}


}
