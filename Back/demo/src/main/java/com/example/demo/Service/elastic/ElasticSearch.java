package com.example.demo.service.elastic;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse.Item;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Slf4j
@Repository
@RequiredArgsConstructor
public class ElasticSearch {
	
	private final ElasticsearchOperations elasticsearchOperations;
	private final RestHighLevelClient client;
	
    public MultiSearchTemplateResponse getById(String indexName) throws IOException {
        
    	LocalDateTime now = LocalDateTime.now();
    	
    	LocalDateTime nowMinus =  LocalDateTime.of(
    			now.getYear()
    			,now.getMonth()
    			,now.getDayOfMonth()
    			,now.getHour()-1
    			,0
    			,0);
    	
    	LocalDateTime now1 =  LocalDateTime.of(
    			now.getYear()
    			,now.getMonth()
    			,now.getDayOfMonth()
    			,now.getHour()
    			,0
    			,0);
    	
    	MultiSearchTemplateRequest multiRequest = new MultiSearchTemplateRequest();
    	
    	 SearchTemplateRequest request = new SearchTemplateRequest();  
    	    request.setRequest(new SearchRequest(indexName));

    	    request.setScriptType(ScriptType.INLINE);
    	    
    	    String script = 
    	    	"{"
    	    	+"\"size\": 0,"
    	    	+"\"query\": {"
    	    	+"\"range\": {"
    	    	+"\"datetime\": {"
    	    	+"\"gte\": \"{{value1}}\","
    	    	+"\"lte\": \"{{value2}}\""
    	    	+"}"
    	    	+"}"
    	    	+"},"
    	    	+"\"aggs\": {"
    	    	+" \"sum_aggs\": {"
    	    	+"\"terms\": {"
    	    	+"\"field\": \"ref.keyword\","
    	    	+"\"size\": 5"
    	    	+"}"
    	    	+"}"
    	    	+"}"  
    	    	+"}"
    	    		 ;

    	    request.setScript(script);
    	    
    	    Map<String, Object> scriptParams = new HashMap<>();
    	    scriptParams.put("value1",nowMinus);
    	    scriptParams.put("value2",now);
    	    
    	    System.out.println(nowMinus);
    	    System.out.println(now1);
    	    
    	    request.setScriptParams(scriptParams);

    	    multiRequest.add(request);
    	 
        
        return client.msearchTemplate(multiRequest, RequestOptions.DEFAULT);
    }
    
    
 
	public List<String> search() {
		
		String datePattern = DateTimeFormatter.ofPattern("yyyy.MM.dd").format(LocalDateTime.now());
		
		String indexName = "springboot-keyword-";
			indexName += datePattern;
		
		MultiSearchTemplateResponse multiResponse = null;

		try {
			multiResponse = getById(indexName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> result = null;
		
		for (Item item : multiResponse.getResponses()) {

			if (item.isFailure()) {
				String error = item.getFailureMessage();
			
			} else {

				SearchTemplateResponse searchTemplateResponse = item.getResponse();
				SearchResponse searchResponse = searchTemplateResponse.getResponse();

				Terms terms = searchResponse.getAggregations().get("sum_aggs");
				result = terms.getBuckets().stream().map(i->i.getKey().toString()).collect(Collectors.toList());
			
			}
		}	
		return result;
	}

}
		

