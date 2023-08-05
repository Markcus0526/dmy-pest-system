package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;

public class ParserObject {

	 public static Boolean getParsedResult(String jsonResponse){
	        try {
	        	JSONObject mainJsonObject = new JSONObject(jsonResponse);
	            String success =mainJsonObject.getString(Constant.STATUS).trim();
				if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS))
					return true;
	        } 
	        catch (Exception ex) {
	        }
	        
	        return false;
	  }
	 
	 public static ArrayList<String> getParsed(String jsonResponse){
		 ArrayList<String> arrList = new ArrayList<String>();
	        try {

	        	JSONObject mainJsonObject = new JSONObject(jsonResponse);
	            String success =mainJsonObject.getString(Constant.STATUS).trim();
				if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
					JSONArray array = mainJsonObject.getJSONArray(Constant.RESULT);
					 for (int i = 0; i < array.length(); i++) {
						 Object item = array.get(i);
						 String obj = "";
						 if(item instanceof JSONObject)
							 obj = parseJsonResponse((JSONObject)item);
						 else
							 obj = item.toString();
						 if(obj != null)
							 arrList.add(obj);
					 }
					 return arrList;
				}
				else{
					String error =mainJsonObject.getString(Constant.ERROR).trim();
					System.out.println("Error = "+error);
				}
	            
	        } 
	        catch (Exception ex) {
	        }
	        
	        return arrList;
	  }
	 
	  private static String parseJsonResponse(JSONObject jsonObject) throws Exception{
		  String obj = new String();
		  try {
			 obj = jsonObject.toString();
		  } 
		  catch (Exception ex) {
		  }
		  return obj;
	  }	 
	  
}
