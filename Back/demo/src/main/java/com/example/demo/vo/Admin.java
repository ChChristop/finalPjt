package com.example.demo.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends BaseVO{ 

	private Long anum;
	
	private String adminID;
	
	private String nickName;
	
	private String adminPW;
	
	private String role;
	
	private String phonNumber;
	
	private LocalDateTime LastAccess;
	
	public Set<String> getRoleList(){
		if(!(this.role.length() > 0)) {
			return new HashSet<>();
		}
		
		return Stream.of(this.role.split(",")).map(
				r->"ROLE_"+r
		).collect(Collectors.toSet());
		}

	}
	
