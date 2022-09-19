package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DishDBDao;
import com.example.demo.vo.DishDB;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class DishDBServiceImpl implements DishDBService {

	@Autowired
	DishDBDao dishDBDao;


	@Override
	public void dbGO(Map<String, Object> nodeMapData, int mnum) {
		//중복 체크 먼저
		int count = dishDBDao.checkNum(nodeMapData);
		
		if(count == 0) {
		
			dishDBDao.dbGO(nodeMapData);
			dishDBDao.dbGoInfo(nodeMapData,mnum);
	
		}
		
	}

	@Override
	public void ingAdd(Map<String, Object> ingMap) {
	
		dishDBDao.ingAdd(ingMap);
	}


	@Override
	public boolean ingCheck(String ingItem, String num) {

		return dishDBDao.ingCheck(ingItem,num);
	}

	
}
