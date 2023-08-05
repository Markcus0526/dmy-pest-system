package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;

import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.PointBean;

public class ParserPlace {
	public static ArrayList<PointBean> getParsedResult(String jsonResponse){
		ArrayList<PointBean> arrList = new ArrayList<PointBean>();
		try {

			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString(Constant.STATUS).trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray(Constant.RESULT);
				for (int i = 0; i < array.length(); i++) {
					PointBean obj = parseJsonResponse((JSONObject) array.get(i));
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

	public static PointBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		PointBean obj = new PointBean();
		try {
			if(!jsonObject.isNull(PointBean.ID))
				obj.id = jsonObject.getInt(PointBean.ID);
			if(!jsonObject.isNull(PointBean.NAME))
				obj.name = jsonObject.getString(PointBean.NAME);
            if(!jsonObject.isNull(PointBean.TYPE))
                obj.type = jsonObject.getInt(PointBean.TYPE);
			if(!jsonObject.isNull(PointBean.INFO1))
				obj.info1= jsonObject.getString(PointBean.INFO1);
			if(!jsonObject.isNull(PointBean.INFO2))
				obj.info2= jsonObject.getString(PointBean.INFO2);
			if(!jsonObject.isNull(PointBean.INFO3))
				obj.info3= jsonObject.getString(PointBean.INFO3);
			if(!jsonObject.isNull(PointBean.INFO4))
				obj.info4= jsonObject.getString(PointBean.INFO4);
			if(!jsonObject.isNull(PointBean.INFO5))
				obj.info5= jsonObject.getString(PointBean.INFO5);
			if(!jsonObject.isNull(PointBean.HTML_INFO))
				obj.html_info = jsonObject.getString(PointBean.HTML_INFO);			
			if(!jsonObject.isNull(PointBean.LATITUDE))
				obj.latitude = jsonObject.getDouble(PointBean.LATITUDE);
			if(!jsonObject.isNull(PointBean.LONGITUDE))
				obj.longitude = jsonObject.getDouble(PointBean.LONGITUDE);
			if(!jsonObject.isNull(PointBean.STATUS))
				obj.status = jsonObject.getInt(PointBean.STATUS);
			if(!jsonObject.isNull(PointBean.NOTE))
				obj.note = jsonObject.getString(PointBean.NOTE);
			if(!jsonObject.isNull(PointBean.NICKNAME))
				obj.nickname = jsonObject.getString(PointBean.NICKNAME);
			if(!jsonObject.isNull(PointBean.XIAN_ID))
				obj.xian_id = jsonObject.getInt(PointBean.XIAN_ID);
			if(!jsonObject.isNull(PointBean.TASK_COUNT))
				obj.task_count = jsonObject.getInt(PointBean.TASK_COUNT);
		} 
		catch (Exception ex) {
		}
		return obj;
	}
}
