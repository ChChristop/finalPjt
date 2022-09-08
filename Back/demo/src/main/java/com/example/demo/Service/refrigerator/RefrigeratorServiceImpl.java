package com.example.demo.service.refrigerator;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.RefrigeratorDAO;
import com.example.demo.dto.RefrigeratorDTO;
import com.example.demo.vo.RefrigeratorVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefrigeratorServiceImpl implements RefrigeratorService{
	
	private final RefrigeratorDAO refrigeratorDAO;  
	
	//냉장고 재료 등록
	@Override
	public long register(RefrigeratorDTO refrigeratorDTO) {
		
		RefrigeratorVO vo = dtoTOvo(refrigeratorDTO);
		
		try {
			
		long result = refrigeratorDAO.addIngredientToRefrigerator(vo);
		
		log.info("[RefrigeratorServiceImpl] [register 성공] [{}]", refrigeratorDTO.getIname());
			
		return (result==1)? vo.getRefrenum() : 0L ;
		
		}catch(Exception e) {
			
			log.warn("[RefrigeratorServiceImpl] [register 실패] [{}]", refrigeratorDTO.getIname());
			
			return 0L;
		}
	}

	//냉장고 재료 업데이트
	@Override
	public long update(RefrigeratorDTO refrigeratorDTO) {
		
		RefrigeratorVO vo = dtoTOvo(refrigeratorDTO);
		
		try {
			
		long result = refrigeratorDAO.updateIngredientByRefrigerator(vo);
		
		log.info("[RefrigeratorServiceImpl] [update 성공] [{}]", refrigeratorDTO.getRefrenum());

		return result;
		
		}catch(Exception e) {
			
			log.warn("[RefrigeratorServiceImpl] [update 실패] [{}]", refrigeratorDTO.getRefrenum());
			
			return 0L;
		}
	}

	//냉장고 재료 삭제
	@Override
	public long remove(long refrenum) {
		
		try {
			
		long result = refrigeratorDAO.deleteIngredientByRefrigerator(refrenum);
		
		log.info("[RefrigeratorServiceImpl] [remove 성공] [{}]", refrenum);
		
		return result;
		
		
		}catch(Exception e) {
			
			log.warn("[RefrigeratorServiceImpl]  [remove 성공] [{}]", refrenum);
			
			return 0L;
		}
	}

	//냉장고 리스트 조회
	@Override
	public List<RefrigeratorDTO> findRefreigeratorListbyMnum(long mnum) {
		
		try {
			
		List<RefrigeratorDTO> result = refrigeratorDAO.findRefrigeratorDAObyMnum(mnum);
		
		log.info("[RefrigeratorServiceImpl] [findRefreigeratorListbyMnum 성공] [{}]", mnum);
		
		return result;
		}catch(Exception e) {
			log.warn("[RefrigeratorServiceImpl] [findRefreigeratorListbyMnum 실패] [{}]", mnum);
			return null;
		}
	}

}
