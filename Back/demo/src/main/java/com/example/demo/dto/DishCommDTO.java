package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishCommDTO {

		private long mnum;
		
		private String RCP_SEQ;
		
		private LocalDateTime date;
		
		private String content;
		
		private LocalDateTime editDate;
	
}
