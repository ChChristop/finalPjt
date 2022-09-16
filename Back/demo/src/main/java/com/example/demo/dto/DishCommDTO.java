package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishCommDTO {

		private long mnum;
		
		@JsonProperty("RCP_SEQ")
		private String RCP_SEQ;
		
		private LocalDateTime date;
		
		private String content;
		
		private LocalDateTime editDate;
		
		@JsonProperty("RCP_NM")
		private String RCP_NM;
		
		@JsonProperty("ATT_FILE_NO_MAIN")
		private String ATT_FILE_NO_MAIN;
	
}
