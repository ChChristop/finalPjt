package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.DishService;
import com.example.demo.vo.Dish;

@RestController
@RequestMapping("/api/dish")
public class DishController {
	
	@Autowired
	DishService dishService;

	
	@GetMapping()
	public String test() {
	
		
		return "TEST";
	}
	
	@PostMapping("/add")
	public String add(@RequestBody Dish dish) {

		dishService.add(dish);

		return dish.getTitle() + "이 등록되었습니다.";
	}
	
	
	

}
