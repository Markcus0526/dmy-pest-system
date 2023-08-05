package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.WatcherBean;

public class ParserWatcher {
	 public static ArrayList<WatcherBean> getParsedResult(String jsonResponse){
		 ArrayList<WatcherBean> arrList = new ArrayList<WatcherBean>();
	        try {

	        	JSONObject mainJsonObject = new JSONObject(jsonResponse);
	            String success =mainJsonObject.getString("Status").trim();
				if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
					JSONArray array = mainJsonObject.getJSONArray("Result");
					 for (int i = 0; i < array.length(); i++) {
						 WatcherBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	  private static WatcherBean parseJsonResponse(JSONObject jsonObject) throws Exception{
	        try {
	        	WatcherBean obj = new WatcherBean();
				if(!jsonObject.isNull(WatcherBean.ID))
					obj.id = jsonObject.getInt(WatcherBean.ID);
				if(!jsonObject.isNull(WatcherBean.USER_ID))
					obj.user_id = jsonObject.getInt(WatcherBean.USER_ID);
				if(!jsonObject.isNull(WatcherBean.POINT_LIST))
					obj.setPointList(jsonObject.getString(WatcherBean.POINT_LIST));

	            return obj; 
	        } 
	        catch (Exception ex) {
	        }
	        return null;
	  }
	  

	   
}
