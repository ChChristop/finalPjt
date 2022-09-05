package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MainDao;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class MainServiceImpl implements MainService {

	@Autowired
	MainDao mainDao;

	@Override
	public List<Map<String, Object>> auto() {
		
		return mainDao.auto();
	}


	
}
