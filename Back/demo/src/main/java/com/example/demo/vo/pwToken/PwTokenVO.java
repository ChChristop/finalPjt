package com.example.demo.vo.pwToken;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class PwTokenVO {
	
	private long mnum;
	
	private String authcode;

}
