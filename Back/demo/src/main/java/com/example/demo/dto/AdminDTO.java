package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

	private int anum;
	
	private String adminID;
	
	private String nickName;
	
	private String adminPW;
	
	private String role;
	
	private String phonNumber;
	
	private LocalDateTime LastAccess;
	
	private LocalDateTime date;
	
	private LocalDateTime modDate;
	
	
}
