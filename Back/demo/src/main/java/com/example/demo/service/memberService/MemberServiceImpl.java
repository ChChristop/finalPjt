package com.example.demo.service.memberService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.vo.MemberVO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final PasswordEncoder passwordEncoder;

	private final MemberDAO memberDAO;

	@Override
	public MemberDTO findMember(long mnum) {
		log.info("회원 정보 찾는 중.. 회원 식별자 : " + mnum);

		Optional<MemberVO> result = memberDAO.findMemberbyMnum(mnum);

		if (result.isEmpty()) {
			return null;
		}

		MemberVO member = result.get();

		MemberDTO memberDTO = voTOdto(member);

		return memberDTO;
	}

	@Override
	public MemberDTO findMember(String memberID) {
		log.info("회원 정보 찾는 중.. 회원 아이디 : " + memberID);

		Optional<MemberVO> result = memberDAO.findMemberbyMemberID(memberID);

		if (result.isEmpty()) {
			return null;
		}

		MemberVO member = result.get();

		MemberDTO memberDTO = voTOdto(member);

		return memberDTO;
	}

	@Override
	public Long register(MemberDTO memberDTO) {

		log.info("회원 등록 중 : " + memberDTO);

		memberDTO.setMemberPW(passwordEncoder.encode(memberDTO.getMemberPW()));

		MemberVO member = dtoTOvo(memberDTO);

		memberDAO.addMember(member);

		return (long) member.getMnum();
	}

	@Override
	public boolean checkMemberID(String id) {

		log.info("회원 아이디 중복 확인 : " + id);

		Optional<String> result = memberDAO.checkByMemberId(id);

		return (result.isPresent()) ? true : false;
	}

	@Override
	public long remove(long mnum) {
		log.info("회원 삭제 중 : " + mnum);

		Long result = memberDAO.removeMemberbyMnum(mnum);

		return (result == 1) ? mnum : null;
	}

	@Override
	@Transactional
	public Long update(MemberDTO memberDTO) {

		memberDTO.setMemberPW(passwordEncoder.encode(memberDTO.getMemberPW()));

		MemberVO member = dtoTOvo(memberDTO);

		log.info("회원 수정 중 : " + memberDTO);

		memberDAO.updateAdminByMnum(member);
		
		memberDAO.updateModDateByMnum(memberDTO.getMnum());

		return (long) member.getMnum();
	}

	@Override
	public PageResultDTO<MemberVO, MemberDTO> getAmindList(PageRequestDTO pageRequestDTO) {
		log.info("회원 리스트 찾는 중 : ");

		// 기준
		String basis = "";

		if (pageRequestDTO.getBasis() == "pk") {

			basis = "mnum";
		}

		// 정렬
		String align = pageRequestDTO.isAlign() ? "asc" : "desc";

		int page = (pageRequestDTO.getPage() - 1) * 10;

		// 조회 메서드
		List<MemberVO> result = memberDAO.getMemberList(basis, align, page, pageRequestDTO.getSize());

		// dto->vo 변환 함수 함수
		Function<MemberVO, MemberDTO> fn = (admin) -> voTOdto(admin);

		// totalpage 조건 없음
		int count = memberDAO.countAdminAllList();

		return new PageResultDTO<>(result, fn, pageRequestDTO, count);
	}

}
