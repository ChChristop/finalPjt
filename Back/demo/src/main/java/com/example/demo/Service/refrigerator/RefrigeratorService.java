package com.example.demo.service.refrigerator;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.dto.RefrigeratorDTO;
import com.example.demo.vo.RefrigeratorVO;

public interface RefrigeratorService {


	//냉장고 재료 등록
	long register(RefrigeratorDTO refrigeratorDTO);
	
	//냉장고 재료 수정
	long update(RefrigeratorDTO refrigeratorDTO);
	
	//냉장고에서 재료 제거 
	long remove(long refrenum);
	
	//냉장고 재료 조회
	List<RefrigeratorDTO> findRefreigeratorListbyMnum(long mnum);

	
	
	//dto -> vo
	default RefrigeratorDTO voTOdto(RefrigeratorVO vo) {

		RefrigeratorDTO refrigeratorDTO = new RefrigeratorDTO();
		
		refrigeratorDTO.setRefrenum(vo.getRefrenum());
		
		refrigeratorDTO.setMnum(vo.getMnum());
		
		refrigeratorDTO.setIname(vo.getIname());
		
		refrigeratorDTO.setExpirationDate(vo.getExpirationDate());
		
		return refrigeratorDTO;
		
	};
	
	//vo -> dto
	default RefrigeratorVO dtoTOvo(RefrigeratorDTO dto) {


		RefrigeratorVO refrigeratorVO = new RefrigeratorVO();
		
		refrigeratorVO.setRefrenum(dto.getRefrenum());
		
		refrigeratorVO.setMnum(dto.getMnum());
		
		refrigeratorVO.setIname(dto.getIname());
		
		refrigeratorVO.setExpirationDate(dto.getExpirationDate());
		
		return refrigeratorVO;
		
	};
}
