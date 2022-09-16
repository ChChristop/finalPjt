package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishLikeDTO {
	
	private long lnum;
	
	private long mnum;
	
	@JsonProperty("RCP_SEQ")
	private String RCP_SEQ;
	
	@JsonProperty("RCP_NM")
	private String RCP_NM;
	
	private LocalDateTime date;
	
	@JsonProperty("ATT_FILE_NO_MAIN")
	private String ATT_FILE_NO_MAIN;

}
