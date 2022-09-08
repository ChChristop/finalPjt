package com.example.demo.service.point;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.point.PointDAO;
import com.example.demo.dto.PointDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
	
	private final PointDAO pointDAO;
	
	@Override
	public PageResultVO<PointDTO> userPoint(PageRequestDTO dto,long mnum) {
		
		try {
			
		dto.setterChange();
		
		List<PointDTO> result = pointDAO.findUserPointbyMnum(dto,mnum);
	
		if(result.size()<1) return null;
		
		int count = pointDAO.pointCountbyMnum(mnum);

		log.info("[PointServiceImpl] [userPoint 성공] [{}]",mnum);
		
		return new PageResultVO<>(result,dto,count);
		
		}catch(Exception e) {
			
			log.warn("[PointServiceImpl] [userPoint 실패] [{}]",mnum);
			
			return null;
		}
		
	}

	@Override
	public PageResultVO<PointDTO> userPointby7d(PageRequestDTO dto, long mnum) {
		try {
			
			dto.setterChange();
			
			List<PointDTO> result = pointDAO.findUserPoint7dbyMnum(dto,mnum);
		
			if(result.size()<1) return null;
			
			int count = pointDAO.pointCountbyMnum(mnum);

			log.info("[PointServiceImpl] [userPointby7d 성공] [{}]",mnum);
			
			return new PageResultVO<>(result,dto,count);
			
			}catch(Exception e) {
				
				log.warn("[PointServiceImpl] [userPointby7d 실패] [{}]",mnum);
				
				return null;
			}
			
	}

}
