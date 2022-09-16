package com.example.demo.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
//나중에 jwt 토큰 조회할 경우
public class JwtVO extends BaseVO {

		private int jwtnum;
		
		private String id;
		
		private String jwt;
		
		private String ip;
		
		private LocalDateTime date;
		
}
