package com.bingchong.parser;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.ReportBean;

public class ParserReport {
	public static ArrayList<ReportBean> getParsedResult(String jsonResponse){
		ArrayList<ReportBean> arrList = new ArrayList<ReportBean>();
		try {

			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString("Status").trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray("Result");
				for (int i = 0; i < array.length(); i++) {
					ReportBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	private static ReportBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		ReportBean obj = new ReportBean();
		Date day = null;
		try {
			if(!jsonObject.isNull(ReportBean.ID))
				obj.id = jsonObject.getInt(ReportBean.ID);
			if(!jsonObject.isNull(ReportBean.BLIGHT_KIND))
				obj.blight_type = jsonObject.getString(ReportBean.BLIGHT_KIND);
			if(!jsonObject.isNull(ReportBean.BLIGHT_NAME))
				obj.blightName = jsonObject.getString(ReportBean.BLIGHT_NAME);
			if(!jsonObject.isNull(ReportBean.WATCH_TIME)){
				String date = jsonObject.getString(ReportBean.WATCH_TIME);
				day = Constant.dateFormat.parse(date);
				obj.testDate = day.getTime(); 
			}
			if(!jsonObject.isNull(ReportBean.REPORT_TIME)){
				String date = jsonObject.getString(ReportBean.REPORT_TIME);
				day = Constant.dateFormat.parse(date);
				obj.reportDate = day.getTime(); 
			}
		}
		catch (Exception ex) {
			return null;
		}

		return obj;
	}



}
