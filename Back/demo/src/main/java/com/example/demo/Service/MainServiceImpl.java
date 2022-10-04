package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MainDao;
import com.example.demo.vo.NonMember;

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

	@Override
	public List<Map<String, Object>> recipeAuto(String iname) {
	
		return mainDao.recipeAuto(iname);
	}

	@Override
	public List<Map<String, Object>> ingAllReco(int mnum) {
	
		return mainDao.ingAllReco(mnum);
	}

	@Override
	public List<Map<String, Object>> ingAllReco_9999(NonMember nonMember) {
		
		return mainDao.ingAllReco_9999(nonMember);
	}

	@Override
	public List<Map<String, Object>> getIngs(int mnum) {
	
		return mainDao.getIngs(mnum);
	}

	@Override
	public List<Map<String, Object>> getList(String string) {
		
		return mainDao.getList(string);
	}

	@Override
	public List<Map<String, Object>> mainSearch(Map<String, Object> searchMap) {

		return mainDao.mainSearch(searchMap);
	}

	@Override
	public int searchCNT(Map<String, Object> searchMap) {
		
		return mainDao.searchCNT(searchMap);
	}

	@Override
	public List<Map<String, Object>> ingAllReco_9999_2() {
	
		
		return mainDao.ingAllReco_9999_2();
	}


	
}
