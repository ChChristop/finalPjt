package com.example.demo.service;

import java.util.List;
import java.util.Map;

public interface MainService {

	public List<Map<String, Object>> auto();

	public List<Map<String, Object>> recipeAuto(String iname);

	public List<Map<String, Object>> ingAllReco(int mnum);


}
