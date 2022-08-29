package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.HashAlgorithm;

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
	
	private String phoneNumber;
	
	private LocalDateTime lastAccess;
	
	private LocalDateTime date;
	
	private LocalDateTime modDate;

	
	//게시물 리스트
//	private List<게시물> 게시물;
	
	
}
