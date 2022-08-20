package com.example.demo.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminDTO;
import com.example.demo.service.adminService.AdminService;
import com.github.javaparser.utils.Log;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminService;
	
	@GetMapping("/{anum}")
	@ResponseBody
	public String getAdmin() {

	return "TEST";
	}
	
	@GetMapping("/adminID/{adminID}")
	public ResponseEntity<Boolean> searchAdmin(@PathVariable String adminID){
		
		boolean result = adminService.CheckadminID(adminID);
		
		if(result)
			return new ResponseEntity<>(true,HttpStatus.OK);
		else
			return new ResponseEntity<>(false,HttpStatus.OK);
	
	}
	
	
	@PostMapping("/register")
	
	public ResponseEntity<Long> addAdmin(@RequestBody AdminDTO adminDTO){
		
		Log.info("Controller /register 접근");
		
		Long anum = adminService.register(adminDTO);
			
	
		return new ResponseEntity<>(anum, HttpStatus.OK);
	}

	
}
