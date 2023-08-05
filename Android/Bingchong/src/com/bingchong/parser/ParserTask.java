package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.TaskBean;
import com.bingchong.bean.TaskReportBean;

public class ParserTask {
	public static ArrayList<TaskBean> getParsedResult(String jsonResponse){
		ArrayList<TaskBean> arrList = new ArrayList<TaskBean>();
		try {

			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString(Constant.STATUS).trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray(Constant.RESULT);
				for (int i = 0; i < array.length(); i++) {
					TaskBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	public static TaskBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		TaskBean obj = new TaskBean();	
		try {

			if(!jsonObject.isNull(TaskBean.ID))
				obj.detail_id = jsonObject.getInt(TaskBean.ID);	
			if(!jsonObject.isNull(TaskBean.POINT_ID))
				obj.point_id = jsonObject.getInt(TaskBean.POINT_ID);
			if(!jsonObject.isNull(TaskBean.FORM_ID))
				obj.form_id = jsonObject.getInt(TaskBean.FORM_ID);
			if(!jsonObject.isNull(TaskBean.REPORT_ID))
				obj.report_id = jsonObject.getInt(TaskBean.REPORT_ID);
			if(!jsonObject.isNull(TaskBean.BLIGHT_ID))
				obj.blight_id = jsonObject.getInt(TaskBean.BLIGHT_ID);
			if(!jsonObject.isNull(TaskBean.BLIGHT_NAME))
				obj.blight_name = jsonObject.getString(TaskBean.BLIGHT_NAME);
			if(!jsonObject.isNull(TaskBean.POINT_NAME))
				obj.point_name = jsonObject.getString(TaskBean.POINT_NAME);
			if(!jsonObject.isNull(TaskBean.DATE))
				obj.date = jsonObject.getString(TaskBean.DATE);
			if(!jsonObject.isNull(TaskBean.NAME))
				obj.name = jsonObject.getString(TaskBean.NAME);
		} 
		catch (Exception ex) {
		}
		return obj;
	}



}
