package com.example.demo.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.example.demo.common.Constants;
import com.example.demo.service.DishDBService;

@RestController
public class RestTestController {
	
	@Autowired
	DishDBService dishService;

	String MY_KEY = Constants.MY_KEY;
	
//	@GetMapping("/tagtest/{start}/{end}/{anum}")
	 public void main(String[] args, @PathVariable int start,@PathVariable int end, @PathVariable int anum) {

	        try {
	  
	            String openApiUrl = "http://openapi.foodsafetykorea.go.kr/api/"
	            		+ MY_KEY + "/COOKRCP01/xml/"+ start + "/" + end;

	            URL obj = new URL(openApiUrl);
	            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
	            con.setDoOutput(true);
	            con.setRequestMethod("GET");
	            
	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	            Document document = (Document) docBuilder.parse(new InputSource(con.getInputStream()));
	        
	            document.getDocumentElement().normalize();
	        
	            List<Map<String, Object>> list = new ArrayList<>();
	            List<Map<String, Object>> ing = new ArrayList<>();
	            
	            for(int i = 0; i<(end-start+1); i++) {
	            Node firstNode = document.getElementsByTagName("row").item(i);
	            NodeList childNodeList = firstNode.getChildNodes();
	            Map<String, Object> nodeMapData = getNodeList(childNodeList);
	           dishService.dbGO(nodeMapData,anum);
	          
	           String result = RegexCheck(nodeMapData.get("RCP_PARTS_DTLS"));
	           String num = (String) nodeMapData.get("RCP_SEQ");
	           
	           goDish_ing(result,num);
 
	           }
	           
	           
	        } catch (Exception e){
	            e.printStackTrace();
	        }
	    }

		
	    public void goDish_ing(String result, String num) {
	    	
	    	   Map<String, Object> ingMap = new HashMap<>(); 
	           List<String> ingList = new ArrayList<>();
	   			for(int j= 0; j<result.split(",").length; j++) {
	   				String ingItem = result.split(",")[j].trim();
	   				if(!ingItem.isEmpty()) {
	   			
	   						ingMap.put("ing",ingItem);
	   						ingMap.put("RCP_SEQ", num);
	   						dishService.ingAdd(ingMap);
	   			
	   				}
		   		}
		
	}


		public String RegexCheck(Object obj) {
	    	
			 String result;
	    	 String target = String.valueOf(obj);
	    	 String regEx = "[(0-9]+[^gl리개)]*[gl리개)]+"; 
	    	 String regEx1 = "\\n[가-힣]* :";
	    	 String regEx2 = "\\[\\S*\\]";
	    	 Pattern pat = Pattern.compile(regEx);
	    	 Pattern pat1 = Pattern.compile(regEx1);
	    	 Pattern pat2 = Pattern.compile(regEx2);
	    	 
	    	 Matcher m2 = pat2.matcher(target);
	    	 result = m2.replaceAll(",");
	    	 Matcher m = pat.matcher(result);
	    	 result = m.replaceAll("");
	    	 Matcher m1 = pat1.matcher(result);
	    	 result = m1.replaceAll(", ");
	    	 
	    	 //\n거르기
	    	 result = result.replaceAll("\n", ", ");
	    	 //,,두개
	    	 result = result.replaceAll(",,",",");
	    	 
	    	 result = result.replaceAll("육수","");
	    	 result = result.replaceAll("양념","");
	    	 result = result.replaceAll("재료","");
	    	 result = result.replaceAll("다진","");
	    	 result = result.replaceAll("채썬","");
	    	 result = result.replaceAll("마른","");
	    	 result = result.replaceAll("채친","");
	    	 result = result.replaceAll("것","");
	    	 
	    	 System.out.println("result::: " + result);
	    	 
	    	 return result;

	    }
	    	

		public static Map<String,Object> getNodeList(NodeList nodeList){
	        Map<String, Object> dataMap = new HashMap<>();
	        for(int i = 0; i < nodeList.getLength(); i++){
	            String tagName = nodeList.item(i).getNodeName();
	            if(!"#text".equals(tagName)){
	                //System.out.println("tagName = " + tagName);
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
