package com.example.demo.service.memberService;

import com.example.demo.dto.MemberDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.vo.MemberVO;

public interface MemberService {
	

	MemberDTO findMember(long mnum);
	
	MemberDTO findMember(String memberID);
	
	Long register(MemberDTO memberDTO);
	
	boolean checkMemberID(String id);
	
	long remove(long mnum);
	
	Long update(MemberDTO memberDTO);
	
	PageResultDTO<MemberVO, MemberDTO> getAmindList(PageRequestDTO pageRequestDTO);
	
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
	
	//테이블 조인 되면 더 늘어나야함
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


}
