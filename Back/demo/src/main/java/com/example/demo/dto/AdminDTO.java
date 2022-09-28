package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	private int dishCount;
	
	public Set<String> setRole(String roles){
		
		Set<String> roleSet = Stream.of(roles.split(",")).collect(Collectors.toSet());;

		this.role = roleSet;
		
		return roleSet;
	}
	
	
}
