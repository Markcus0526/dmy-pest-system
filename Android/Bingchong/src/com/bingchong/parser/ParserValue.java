package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.ValueBean;

public class ParserValue {
	 public static ArrayList<ValueBean> getParsedResult(String jsonResponse){
		 ArrayList<ValueBean> arrList = new ArrayList<ValueBean>();
	        try {

	        	JSONObject mainJsonObject = new JSONObject(jsonResponse);
	            String success =mainJsonObject.getString("Status").trim();
				if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
					JSONArray array = mainJsonObject.getJSONArray("Result");
					 for (int i = 0; i < array.length(); i++) {
						 ValueBean obj = parseJsonResponse((JSONObject) array.get(i));
						 if(obj != null)
							 arrList.add(obj);
					 }
					 return arrList;
				}
				else{
					String error =mainJsonObject.getString("Error").trim();
					System.out.println("Error = "+error);
				}
	            
	        } 
	        catch (Exception ex) {
	        }

	        return arrList;
	  }
	
	  public static ValueBean parseJsonResponse(JSONObject jsonObject) throws Exception{
	        try {
	        	ValueBean obj = new ValueBean();
				if(!jsonObject.isNull(ValueBean.NAME))
					obj.name = jsonObject.getString(ValueBean.NAME);
				if(!jsonObject.isNull(ValueBean.VALUE))
					obj.val = (float)jsonObject.getDouble(ValueBean.VALUE);

	            return obj; 
	        } 
	        catch (Exception ex) {
	        }
	        return null;
	  }
	  

	   
}
