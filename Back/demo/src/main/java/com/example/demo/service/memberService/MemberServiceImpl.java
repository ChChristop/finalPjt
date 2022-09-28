package com.example.demo.service.memberService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.MemberDAO;
import com.example.demo.dao.RefrigeratorDAO;
import com.example.demo.dao.pwToken.PwTokenDAO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultDTO;
import com.example.demo.service.function.ObjectMapperToDTO;
import com.example.demo.vo.MemberVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final PasswordEncoder passwordEncoder;

	private final MemberDAO memberDAO;

	private final RefrigeratorDAO refrigeratorDAO;
	
	private final PwTokenDAO pwTokenDAO;

	@Override
	public MemberDTO findMember(long mnum) {

		MemberDTO memberDTO = null;

		try {

			List<Map<String, Object>> get = memberDAO.findMemberbyMnum(mnum);
			
			ObjectMapperToDTO objectMapperToDTO = new ObjectMapperToDTO(get,memberDTO,MemberDTO.class);
			
			memberDTO = (MemberDTO) objectMapperToDTO.changeObjectMapperToDTO();
			
			log.info("[MemberServiceImpl] [findMember 성공] [{}] ", memberDTO.getMemberID());

			return memberDTO;

		} catch (Exception e) {
			log.warn("[MemberServiceImpl] [findMember 실패] [{}] ", memberDTO.getMemberID());

			return memberDTO;
		}

	}

	@Override
	public MemberDTO findMember(String memberID) {

		MemberDTO memberDTO = null;

		try {
			List<Map<String, Object>> get = memberDAO.findMemberbyMemberID(memberID);
			
			ObjectMapperToDTO objectMapperToDTO = new ObjectMapperToDTO(get,memberDTO,MemberDTO.class);
			
			memberDTO = (MemberDTO) objectMapperToDTO.changeObjectMapperToDTO();

			
			log.info("[MemberServiceImpl] [findMember 성공] [{}]", memberDTO.getMemberID());
			
			return memberDTO;

		} catch (Exception e) {
			log.warn("[MemberServiceImpl] [findMember 실패] [{}]",memberDTO.getMemberID());

			return memberDTO;
		}

	}


	@Override
	public Long register(MemberDTO memberDTO) {

		memberDTO.setMemberPW(passwordEncoder.encode(memberDTO.getMemberPW()));

		MemberVO member = dtoTOvo(memberDTO);

		long result = 0L;
		try {

			memberDAO.addMember(member);
			result = member.getMnum();
			log.info("[MemberServiceImpl] [register 성공] [{}]", memberDTO.getMemberID());

			return result;

		} catch (Exception e) {

			log.warn("[MemberServiceImpl] [register 실패] [{}]", memberDTO.getMemberID());

			return result;
		}
	}

	@Override
	public boolean checkMemberID(String id) {

		Optional<String> result = memberDAO.checkByMemberId(id);

		return (result.isPresent()) ? true : false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public long remove(long mnum) {

		Long result = 0L;

		try {

			refrigeratorDAO.deleteIngredientByMnumr(mnum);
			result = memberDAO.removeMemberbyMnum(mnum);

			log.info("[MemberServiceImpl] [remove 성공] [{}]", mnum);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
			return result;

		} catch (Exception e) {
			log.warn("[MemberServiceImpl] [remove 실패] [{}] ", mnum);
			e.printStackTrace();
			return result;
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long update(MemberDTO memberDTO) {

		memberDTO.setMemberPW(passwordEncoder.encode(memberDTO.getMemberPW()));

		MemberVO member = dtoTOvo(memberDTO);

		try {
			Long result = memberDAO.updateMemberByMnum(member);
			memberDAO.updateModDateByMnum(memberDTO.getMnum());
			log.info("[MemberServiceImpl] [update 성공] [{}] ", memberDTO.getMnum());
			return result;

		} catch (Exception e) {
			log.warn("[MemberServiceImpl] [update 실패] [{}]",+ memberDTO.getMnum());
			return 0L;
		}

	}

//	@Override
	public PageResultDTO<MemberVO, MemberDTO> getMemberList(PageRequestDTO pageRequestDTO) {

		// 기준
		if (pageRequestDTO.getBasis() == "pk") {

			pageRequestDTO.setBasis("mnum");
		}

		pageRequestDTO.setterChange();

		List<MemberVO> result = null;

		Function<MemberVO, MemberDTO> fn = null;

		int count = 0;

		try {

			// 조회 메서드
			result = memberDAO.getMemberList(pageRequestDTO);

			// dto->vo 변환 함수 함수
			fn = (map) -> voTOdto(map);
			count = memberDAO.countMemberAllList(pageRequestDTO);

			return new PageResultDTO<>(result, fn, pageRequestDTO, count);
			
		} catch (Exception e)  {return null;}

	}

	// join table 테스트
	@Override
	public PageResultDTO<Map<String, Object>, MemberDTO> getMemberList2(PageRequestDTO pageRequestDTO) {

		// 기w준
		if (pageRequestDTO.getBasis() == "pk") {

			pageRequestDTO.setBasis("mnum");
		}

		pageRequestDTO.setterChange();

		try {
			// 조회 메서드
			List<Map<String, Object>> result = memberDAO.getMemberList2(pageRequestDTO);

			// dto->vo 변환 함수 함수
			Function<Map<String, Object>, MemberDTO> fn = (map) -> mapTOdto(map);

			int count = memberDAO.countMemberAllList(pageRequestDTO);
			
			return new PageResultDTO<>(result, fn, pageRequestDTO, count);
			
		} catch (Exception e) {return null;}
	}

	@Override
	public List<Map<String, Object>> topUser() {
		
			
		try {
			
			List<Map<String, Object>> result = memberDAO.topUser();
			
			return result;
			
		}catch(Exception e) {
						
			e.printStackTrace();
			
			return null;
		}
	
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean changePW(MemberDTO memberDTO) {
		
		MemberVO vo = MemberVO.builder()
				.memberID(memberDTO.getMemberID())
				.memberPW(passwordEncoder.encode(memberDTO.getMemberPW()))
				.mnum(memberDTO.getMnum())
				.build();
		
		long result = memberDAO.changeMemberPwByID(vo);
		
		if(result == 0) {
			log.warn("[MemberServiceImpl] [changePW 실패] [memberDTO.getMeberID()]");
			return false;
		}
		
		pwTokenDAO.deletePwToken(vo.getMnum());
		log.info("[MemberServiceImpl] [changePW 성공] [memberDTO.getMeberID()]");
		return true;
	}
	

}
