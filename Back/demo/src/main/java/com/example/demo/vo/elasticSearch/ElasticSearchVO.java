package com.example.demo.vo.elasticSearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(indexName = "springboot-elk")
public class ElasticSearchVO {
	
	@Id
	private String id;
	
	@Field(type = FieldType.Keyword)
	private String type;
	
	@Field(type = FieldType.Keyword)
	private String searchKeyword;
	
	@Field(type = FieldType.Long)
	private int count;
	
	

}
