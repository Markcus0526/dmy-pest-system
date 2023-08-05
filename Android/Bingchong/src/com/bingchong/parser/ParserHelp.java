package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.HelpBean;

public class ParserHelp {
	 public static ArrayList<HelpBean> getParsedResult(String jsonResponse){
		 ArrayList<HelpBean> arrList = new ArrayList<HelpBean>();
	        try {

	        	JSONObject mainJsonObject = new JSONObject(jsonResponse);
	            String success =mainJsonObject.getString("Status").trim();
				if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
					JSONArray array = mainJsonObject.getJSONArray("Result");
					 for (int i = 0; i < array.length(); i++) {
						 HelpBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	  private static HelpBean parseJsonResponse(JSONObject jsonObject) throws Exception{
	        try {
	        	HelpBean obj = new HelpBean();
				if(!jsonObject.isNull(HelpBean.ID))
					obj.id = jsonObject.getInt(HelpBean.ID);
				if(!jsonObject.isNull(HelpBean.TITLE))
					obj.title = jsonObject.getString(HelpBean.TITLE);
				if(!jsonObject.isNull(HelpBean.CONTENTS))
					obj.contents = jsonObject.getString(HelpBean.CONTENTS);

	            return obj; 
	        } 
	        catch (Exception ex) {
	        }
	        return null;
	  }
	  

	   
}
