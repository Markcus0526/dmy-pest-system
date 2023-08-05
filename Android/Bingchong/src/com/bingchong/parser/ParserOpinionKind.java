package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.OpinionKindBean;

public class ParserOpinionKind {
	 public static ArrayList<OpinionKindBean> getParsedResult(String jsonResponse){
		 ArrayList<OpinionKindBean> arrList = new ArrayList<OpinionKindBean>();
	        try {

	        	JSONObject mainJsonObject = new JSONObject(jsonResponse);
	            String success =mainJsonObject.getString("Status").trim();
				if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
					JSONArray array = mainJsonObject.getJSONArray("Result");
					 for (int i = 0; i < array.length(); i++) {
						 OpinionKindBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	  private static OpinionKindBean parseJsonResponse(JSONObject jsonObject) throws Exception{
	        try {
	        	OpinionKindBean obj = new OpinionKindBean();
				if(!jsonObject.isNull(OpinionKindBean.ID))
					obj.id = jsonObject.getInt(OpinionKindBean.ID);
				if(!jsonObject.isNull(OpinionKindBean.NAME))
					obj.name = jsonObject.getString(OpinionKindBean.NAME);

	            return obj; 
	        } 
	        catch (Exception ex) {
	        }
	        return null;
	  }
	  

	   
}
