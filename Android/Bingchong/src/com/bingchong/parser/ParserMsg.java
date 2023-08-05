package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.MsgBean;

public class ParserMsg {
	public static ArrayList<MsgBean> getParsedResult(String jsonResponse){
		ArrayList<MsgBean> arrList = new ArrayList<MsgBean>();
		try {

			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString("Status").trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray("Result");
				for (int i = 0; i < array.length(); i++) {
					MsgBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	private static MsgBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		try {
			MsgBean result = new MsgBean();	
			result.id = jsonObject.getInt("uid");
			return result; 

		} 
		catch (Exception ex) {
		}
		return null;
	}



}
