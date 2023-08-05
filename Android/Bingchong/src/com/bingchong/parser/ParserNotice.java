package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.NoticeBean;

public class ParserNotice {
	public static ArrayList<NoticeBean> getParsedResult(String jsonResponse){
		ArrayList<NoticeBean> arrList = new ArrayList<NoticeBean>();
		try {

			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString("Status").trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray("Result");
				for (int i = 0; i < array.length(); i++) {
					NoticeBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	public static NoticeBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		try {
			NoticeBean obj = new NoticeBean();
			if(!jsonObject.isNull(NoticeBean.ID))
				obj.id = jsonObject.getInt(NoticeBean.ID);
			if(!jsonObject.isNull(NoticeBean.CONTENTS))
				obj.contents = jsonObject.getString(NoticeBean.CONTENTS);
			if(!jsonObject.isNull(NoticeBean.SERIAL))
				obj.serial = jsonObject.getString(NoticeBean.SERIAL);
			if(!jsonObject.isNull(NoticeBean.TITLE))
				obj.title = jsonObject.getString(NoticeBean.TITLE);
			if(!jsonObject.isNull(NoticeBean.YEAR))
				obj.year = jsonObject.getInt(NoticeBean.YEAR);

			return obj; 

		} 
		catch (Exception ex) {
		}
		return null;
	}



}
