package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.UserTaskBean;

public class ParserUserTasks {
	public static ArrayList<UserTaskBean> getParsedResult(String jsonResponse) throws Exception{
		ArrayList<UserTaskBean> arrList = new ArrayList<UserTaskBean>();
		try {

			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString("Status").trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray("Result");
				for (int i = 0; i < array.length(); i++) {
					UserTaskBean obj = parseJsonResponse((JSONObject) array.get(i));
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
		// for test
		{
			arrList = new ArrayList<UserTaskBean>();
			UserTaskBean obj = new UserTaskBean();
			obj.id = 1;
			obj.sDate = 10000;
			obj.eDate = 10000;
			obj.task = "test task";
			arrList.add(obj);
		}

		return arrList;
	}
	private static UserTaskBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		try {
			UserTaskBean result = new UserTaskBean();	
			result.id = jsonObject.getInt("UserId");
			return result; 

		} 
		catch (Exception ex) {
		}
		return null;
	}



}
