package com.example.demo.service.point;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.point.PointDAO;
import com.example.demo.dto.PointDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
	
	private final PointDAO pointDAO;
	
	@Override
	public List<PointDTO> userPoint(long mnum) {
		
		List<PointDTO> result = pointDAO.findUserPointbyMnum(mnum);
		
		if(result.size()<1) return new ArrayList<PointDTO>();
		
		return result;
	}

}
