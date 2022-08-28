package com.example.demo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.example.demo.Service.DishDBService;
import com.example.demo.vo.DishDB;

@RestController
public class RestTestController {
	
	@Autowired
	DishDBService dishService;

	
	
	@GetMapping("/api")
    public String callApiWithJson() {
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        
        try {
        	
	        String apiUrl ="http://openapi.foodsafetykorea.go.kr/api/e6966c1b694a4befb4c1/COOKRCP01/json/1/2";
			URL url = new URL(apiUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();	
			urlConnection.connect();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
			String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            JSONObject jsonObject = XML.toJSONObject(result.toString());
            System.out.println(jsonObject);
            jsonPrintString = jsonObject.toString();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(jsonPrintString);
        
        return jsonPrintString;
	}
	   @GetMapping("/apitest1")
	    public String callApiWithXml1() {
	        StringBuffer result = new StringBuffer();
	        try {
	            String apiUrl = "http://openapi.foodsafetykorea.go.kr/api/e6966c1b694a4befb4c1/COOKRCP01/xml/1/1";
	            URL url = new URL(apiUrl);
	            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	            urlConnection.setRequestMethod("GET");

	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

	            String returnLine;
	            result.append("<xmp>");
	            while((returnLine = bufferedReader.readLine()) != null) {
	                result.append(returnLine + "\n");
	            }
	            urlConnection.disconnect();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        System.out.println("result"+result);
	        String dbGO = result + "</xmp>";
	        System.out.println("dbGO1 :::" + dbGO);
	        
	       return dbGO;
	    }
	
	   @GetMapping("/apitest")
	    public void callApiWithXml() {
	        StringBuffer result = new StringBuffer();
	        try {
	            String apiUrl = "http://openapi.foodsafetykorea.go.kr/api/e6966c1b694a4befb4c1/COOKRCP01/xml/1/1";
	            URL url = new URL(apiUrl);
	            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	            urlConnection.setRequestMethod("GET");

	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

	            String returnLine;
	            result.append("<xmp>");
	            while((returnLine = bufferedReader.readLine()) != null) {
	                result.append(returnLine + "\n");
	            }
	            urlConnection.disconnect();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        Map<String, Object> map = new HashMap<>();
	        
	        String dbGO = result + "</xmp>";
	        System.out.println("dbGO1 :::" + dbGO);
	        
	        dbGO(dbGO);
	    }

	private String dbGO(String dbGO) {
	
		System.out.println("dbGO2 :::" + dbGO);
		// 일단 저장은 나중에
		//dishService.dbGO(dbGO);
		System.out.println(dbGO.length());
		List<Map<String, Object>> list = new ArrayList<>();
		//for(int i = 0 ; i<)
		
		
		
		
		return "db저장 성공";
		
	}
	
	@GetMapping("/tagtest/{start}/{end}")
	 public void main(String[] args, @PathVariable int start,@PathVariable int end) {

	        try {
	  
	            String openApiUrl = "http://openapi.foodsafetykorea.go.kr/api"
	            		+ "/e6966c1b694a4befb4c1/COOKRCP01/xml/"
	            		+ start +
	            		"/" + end;
	            //OpenAPI URL 정보 읽기
	            URL obj = new URL(openApiUrl);
	            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
	            con.setDoOutput(true);
	            con.setRequestMethod("GET");
	            //받아온 XML문서 파싱 => document인스턴스에 저장
	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	            Document document = (Document) docBuilder.parse(new InputSource(con.getInputStream()));
	            //Dom Tree를 XML문서의 구조대로 완성
	            document.getDocumentElement().normalize();
	            //HIT Tag 정보들을  검색
	            //item(번호)에 따라 정보가 바뀜
	            List<Map<String, Object>> list = new ArrayList<>();
	            
	            for(int i = 0; i<(end-start+1); i++) {
	            Node firstNode = document.getElementsByTagName("row").item(i);
	            NodeList childNodeList = firstNode.getChildNodes();
	            Map<String, Object> nodeMapData = getNodeList(childNodeList);
	           System.out.println(nodeMapData.toString());
	           //list.add(nodeMapData);
	           dishService.dbGO(nodeMapData);
	            }
	            System.out.println("list 0::: " + list.get(0));
	            System.out.println("list 1::: " + list.get(1));
	           
	           
	           //DB저장
				/* 돈타치
				 * String openApiUrl =
				 * "http://openapi.foodsafetykorea.go.kr/api/e6966c1b694a4befb4c1/COOKRCP01/xml/1/3";
				 * //OpenAPI URL 정보 읽기 URL obj = new URL(openApiUrl); HttpURLConnection con =
				 * (HttpURLConnection)obj.openConnection(); con.setDoOutput(true);
				 * con.setRequestMethod("GET"); //받아온 XML문서 파싱 => document인스턴스에 저장
				 * DocumentBuilderFactory docBuilderFactory =
				 * DocumentBuilderFactory.newInstance(); DocumentBuilder docBuilder =
				 * docBuilderFactory.newDocumentBuilder(); Document document = (Document)
				 * docBuilder.parse(new InputSource(con.getInputStream())); //Dom Tree를 XML문서의
				 * 구조대로 완성 document.getDocumentElement().normalize(); //HIT Tag 정보들을 검색 Node
				 * firstNode = document.getElementsByTagName("row").item(0); NodeList
				 * childNodeList = firstNode.getChildNodes(); Map<String, Object> nodeMapData =
				 * getNodeList(childNodeList); System.out.println(nodeMapData.toString());
				 * List<String> list = new ArrayList<>(); System.out.println( nodeMapData);
				 */
	           
	          // dishService.dbGO(nodeMapData);
	           
	           
	        } catch (Exception e){
	            e.printStackTrace();
	        }
	    }

		
	    public static Map<String,Object> getNodeList(NodeList nodeList){
	        Map<String, Object> dataMap = new HashMap<>();
	        for(int i = 0; i < nodeList.getLength(); i++){
	            String tagName = nodeList.item(i).getNodeName();
	            if(!"#text".equals(tagName)){
	                System.out.println("tagName = " + tagName);
	                if(nodeList.item(i).getChildNodes().getLength()>1){
	                    dataMap.put(tagName,getNodeList(nodeList.item(i).getChildNodes()));
	                }else{
	                   dataMap.put(tagName, nodeList.item(i).getTextContent());
	                }
	            }
	        }
	        return dataMap;
	    }
	
	
	
	
	
}
