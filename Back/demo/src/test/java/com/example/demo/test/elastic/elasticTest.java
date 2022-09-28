package com.example.demo.test.elastic;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.service.elastic.ElasticSearch;

@SpringBootTest
public class elasticTest {

	@Autowired
	public ElasticSearch ela;

	@Test
	public void test() {

	List<String> result = ela.search();
	
	for(String s : result) {
		System.out.println(s);
	}
	
	
	}
}
