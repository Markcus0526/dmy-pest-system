package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.WatcherBean;
import com.bingchong.bean.XianBean;

public class ParserXian {
	 public static ArrayList<XianBean> getParsedResult(String jsonResponse){
		 ArrayList<XianBean> arrList = new ArrayList<XianBean>();
	        try {

	        	JSONObject mainJsonObject = new JSONObject(jsonResponse);
	            String success =mainJsonObject.getString(Constant.STATUS).trim();
				if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
					JSONArray array = mainJsonObject.getJSONArray(Constant.RESULT);
					 for (int i = 0; i < array.length(); i++) {
						 XianBean obj = parseJsonResponse((JSONObject) array.get(i));
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

	  public static XianBean parseJsonResponse(JSONObject jsonObject) throws Exception{
	        try {

	        	XianBean obj = new XianBean();
	        	if(!jsonObject.isNull(XianBean.ID))
	        		obj.id = jsonObject.getInt(XianBean.ID);
	        	if(!jsonObject.isNull(XianBean.NAME))
	        		obj.name = jsonObject.getString(XianBean.NAME);
	        	if(!jsonObject.isNull(XianBean.SHI_ID))
	        		obj.shi_id = jsonObject.getInt(XianBean.SHI_ID);
	        	return obj; 
	            
	        } 
	        catch (Exception ex) {
	        }
	        return null;
	  }
	  

	   
}
