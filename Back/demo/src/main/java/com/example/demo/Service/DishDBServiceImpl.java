package com.example.demo.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DishDBDao;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class DishDBServiceImpl implements DishDBService {

	@Autowired
	DishDBDao dishDBDao;


	@Override
	public void dbGO(Map<String, Object> nodeMapData) {
		dishDBDao.dbGO(nodeMapData);
		
	}

	
}
