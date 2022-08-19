package com.example.demo.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends BaseVO{ 

	private int anum;
	
	private String adminID;
	
	private String nickName;
	
	private String adminPW;
	
	private String role;
	
	private String phonNumber;
	
	private LocalDateTime LastAccess;
	
}
