package com.example.demo.service.point;

import com.example.demo.dto.PointDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultVO;

public interface PointService {
	
	PageResultVO<PointDTO> userPoint(PageRequestDTO dto,long mnum);

	PageResultVO<PointDTO> userPointby7d(PageRequestDTO dto,long mnum);
}
