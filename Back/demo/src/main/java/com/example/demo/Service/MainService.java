package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.vo.NonMember;

public interface MainService {

	public List<Map<String, Object>> auto();

	public List<Map<String, Object>> recipeAuto(String iname);

	public List<Map<String, Object>> ingAllReco(int mnum);

	public List<Map<String, Object>> ingAllReco_9999(NonMember nonMember);

	public List<Map<String, Object>> getIngs(int mnum);

	public List<Map<String, Object>> getList(String string);

}
