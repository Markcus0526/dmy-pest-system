package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.ExtraTaskBean;


public class ParserExtraTask {
	public static ArrayList<ExtraTaskBean> getParsedResult(String jsonResponse){
		ArrayList<ExtraTaskBean> arrList = new ArrayList<ExtraTaskBean>();
		try {

			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString("Status").trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray("Result");
				for (int i = 0; i < array.length(); i++) {
					ExtraTaskBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	public static ExtraTaskBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		try {
			ExtraTaskBean obj = new ExtraTaskBean();	
			if(!jsonObject.isNull(ExtraTaskBean.ID))
				obj.id = jsonObject.getInt(ExtraTaskBean.ID);
			if(!jsonObject.isNull(ExtraTaskBean.NAME))
				obj.name = jsonObject.getString(ExtraTaskBean.NAME);
			if(!jsonObject.isNull(ExtraTaskBean.ADMIN_ID))
				obj.admin_id= jsonObject.getInt(ExtraTaskBean.ADMIN_ID);
			if(!jsonObject.isNull(ExtraTaskBean.WATCHER_ID))
				obj.watcher_id= jsonObject.getInt(ExtraTaskBean.WATCHER_ID);
			if(!jsonObject.isNull(ExtraTaskBean.NOTICE_DATE))
				obj.notice_date= jsonObject.getString(ExtraTaskBean.NOTICE_DATE);
			if(!jsonObject.isNull(ExtraTaskBean.REPORT_DATE))
				obj.report_date= jsonObject.getString(ExtraTaskBean.REPORT_DATE);
			if(!jsonObject.isNull(ExtraTaskBean.NOTE))
				obj.note = jsonObject.getString(ExtraTaskBean.NOTE);
			if(!jsonObject.isNull(ExtraTaskBean.USER_NAME))
				obj.user_name= jsonObject.getString(ExtraTaskBean.USER_NAME);
			if(!jsonObject.isNull(ExtraTaskBean.STATUS))
				obj.status= jsonObject.getInt(ExtraTaskBean.STATUS);

			return obj; 
		} 
		catch (Exception ex) {
		}
		return null;
	}



}
