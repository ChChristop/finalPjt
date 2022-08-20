package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

	private Long anum;
	
	private String adminID;
	
	private String nickName;
	
	private String adminPW;
	
	private Set<String> role;
	
	private String phonNumber;
	
	private LocalDateTime lastAccess;
	
	private LocalDateTime date;
	
	private LocalDateTime modDate;
	
	
}
