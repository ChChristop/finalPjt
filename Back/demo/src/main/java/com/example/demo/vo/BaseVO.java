package com.example.demo.vo;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter

abstract class BaseVO {

	private LocalDateTime date;
	
	private LocalDateTime modDate;
	

}
