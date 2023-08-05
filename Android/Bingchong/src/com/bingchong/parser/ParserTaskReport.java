package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.TaskReportBean;

public class ParserTaskReport {
	public static ArrayList<TaskReportBean> getParsedResult(String jsonResponse){
		ArrayList<TaskReportBean> arrList = new ArrayList<TaskReportBean>();
		try {

			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString("Status").trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray("Result");
				for (int i = 0; i < array.length(); i++) {
					TaskReportBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	public static TaskReportBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		TaskReportBean obj = new TaskReportBean();
		try {
            if(!jsonObject.isNull(TaskReportBean.ID))
                obj.id = jsonObject.getInt(TaskReportBean.ID);
			if(!jsonObject.isNull(TaskReportBean.POINT_ID))
				obj.point_id = jsonObject.getInt(TaskReportBean.POINT_ID);
			if(!jsonObject.isNull(TaskReportBean.FORM_ID))
				obj.form_id = jsonObject.getInt(TaskReportBean.FORM_ID);
			if(!jsonObject.isNull(TaskReportBean.FORM_NAME))
				obj.form_name = jsonObject.getString(TaskReportBean.FORM_NAME);
			if(!jsonObject.isNull(TaskReportBean.USER_NAME))
				obj.user_name = jsonObject.getString(TaskReportBean.USER_NAME);			
			if(!jsonObject.isNull(TaskReportBean.STATE))
				obj.state = jsonObject.getInt(TaskReportBean.STATE);
			if(!jsonObject.isNull(TaskReportBean.BLIGHT_KIND))
				obj.blight_kind = jsonObject.getInt(TaskReportBean.BLIGHT_KIND) == 0 ? "B" : "C";
			if(!jsonObject.isNull(TaskReportBean.BLIGHT_NAME))
				obj.blight_name = jsonObject.getString(TaskReportBean.BLIGHT_NAME);
			if(!jsonObject.isNull(TaskReportBean.NAME))
				obj.name = jsonObject.getString(TaskReportBean.NAME);
			if(!jsonObject.isNull(TaskReportBean.DATE))
				obj.date = jsonObject.getString(TaskReportBean.DATE);
		}
		catch (Exception ex) {
			return null;
		}

		return obj;
	}



}
