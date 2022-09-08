package com.example.demo.service.function;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.MemberDTO;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperToDTO<DTO> {
	
	List<Map<String, Object>> objMapper;
	
	Map<String,Object> objMap;
	
	DTO dto;
	
	Class<DTO> className;
	
	public ObjectMapperToDTO(List<Map<String, Object>> objMapper, DTO dto, Class<DTO> className){
		this.objMapper = objMapper;
		this.dto = dto;
		this.className = className;
	}
	
	public ObjectMapperToDTO(Map<String, Object> objMap, DTO dto, Class<DTO> className){
		this.objMap = objMap;
		this.dto = dto;
		this.className = className;
	}

	
	public <DTO> DTO changeObjectMapperToDTO(){
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		dto = objectMapper.convertValue(objMapper.get(0), className);

		return (DTO) dto;
	}
	
	public <DTO> DTO changeObjectMapToDTO(){
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		dto = objectMapper.convertValue(objMap, className);

		return (DTO) dto;
	}
	
	

}
