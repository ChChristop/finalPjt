package com.example.demo.service.elastic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

	public MultiSearchTemplateResponse getById(String[] indexName) throws IOException {

		LocalDateTime now = LocalDateTime.now();

		LocalDateTime nowMinus = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour() - 1,
				0, 0);

		LocalDateTime now1 = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), 0, 0);

		MultiSearchTemplateRequest multiRequest = new MultiSearchTemplateRequest();

		SearchTemplateRequest request = new SearchTemplateRequest();
		request.setRequest(new SearchRequest(indexName));

		request.setScriptType(ScriptType.INLINE);

		String script = "{" + "		  \"size\": 0," + "		  \"query\": {" + "		    \"bool\": {"
				+ "		      \"must\": [{" + "		          \"term\": {" + "		            \"content\": \"검색어\""
				+ "		          }" + "		        }," + "		        {" + "		          \"range\": {"
				+ "		           \"datetime\": {" + "		             \"gte\": \"now-2h\","
				+ "		              \"lte\": \"now\"" + "		            }" + "		          }"
				+ "		        }" + "		      ]" + "		    }" + "		  }," + "		  \"aggs\": {"
				+ "		    \"sum_aggs\": {" + "		      \"terms\": {" + "		        \"field\": \"ref\","
				+ "		        \"size\": 5" + "		      }" + "		    }" + "		  }" + "		}";

		request.setScript(script);

		Map<String, Object> scriptParams = new HashMap<>();

		request.setScriptParams(scriptParams);

		multiRequest.add(request);

		return client.msearchTemplate(multiRequest, RequestOptions.DEFAULT);
	}

	public List<String> search() {
		
		int nowHour = LocalDateTime.now().getHour();
		
		if (9 <= nowHour && nowHour < 11) {
			return search2();
		}
		
		String datePattern = "";
		
		if(nowHour < 9) {
			datePattern = DateTimeFormatter.ofPattern("yyyy.MM.dd").format(LocalDateTime.now().minusDays(1));
		}
		
		datePattern = DateTimeFormatter.ofPattern("yyyy.MM.dd").format(LocalDateTime.now());

		String[] indexName = new String[1]; 
		indexName[0] = "springboot-keyword-";
		indexName[0] += datePattern;

		MultiSearchTemplateResponse multiResponse = null;

		try {
			multiResponse = getById(indexName);
		} catch (Exception e) {
			log.info("[ElasticSearch] [search()] [오류]");
		}

		List<String> result = null;

		for (Item item : multiResponse.getResponses()) {

			if (item.isFailure()) {
				String error = item.getFailureMessage();

			} else {

				SearchTemplateResponse searchTemplateResponse = item.getResponse();
				SearchResponse searchResponse = searchTemplateResponse.getResponse();

				Terms terms = searchResponse.getAggregations().get("sum_aggs");
				result = terms.getBuckets().stream().map(i -> i.getKey().toString()).collect(Collectors.toList());

			}
		}
		return result;
	}

	@SuppressWarnings("null")
	public List<String> search2() {

//		List<String> indexName = new ArrayList<String>();

		String[] indexName = new String[2]; 
//		List<String> result = new ArrayList<String>();
		
//		for (int i = 0; i < 2; i++) {
			
			List<String> result1 = null;
			
//			indexName.add(i, "springboot-keyword-"
//					+ DateTimeFormatter.ofPattern("yyyy.MM.dd").format(LocalDateTime.now().minusDays(i)));
			
			indexName[0] = "springboot-keyword-"
					+ DateTimeFormatter.ofPattern("yyyy.MM.dd").format(LocalDateTime.now().minusDays(1));
			indexName[1] = "springboot-keyword-"
					+ DateTimeFormatter.ofPattern("yyyy.MM.dd").format(LocalDateTime.now().minusDays(0));
			
			MultiSearchTemplateResponse multiResponse = null;

			try {
				multiResponse = getById(indexName);
		
			for (Item item : multiResponse.getResponses()) {

				if (item.isFailure()) {
					String error = item.getFailureMessage();

				} else {

					SearchTemplateResponse searchTemplateResponse = item.getResponse();
					SearchResponse searchResponse = searchTemplateResponse.getResponse();

					Terms terms = searchResponse.getAggregations().get("sum_aggs");
					result1 = terms.getBuckets().stream().map(j -> j.getKey().toString()).collect(Collectors.toList());	
					
					for(String s : result1) {
//						result.add(s);
					}
				}
			}
			
			} catch (Exception e) {
				log.info("[ElasticSearch] [search2()] [오류]");
			}

//		}
		
		return result1;
	}

}
