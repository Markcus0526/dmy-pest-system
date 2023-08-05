package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.BlightBean;

public class ParserBlight {
	public static ArrayList<BlightBean> getParsedResult(String jsonResponse){
		ArrayList<BlightBean> arrList = new ArrayList<BlightBean>();
		try {

			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString(Constant.STATUS).trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray(Constant.RESULT);
				for (int i = 0; i < array.length(); i++) {
					BlightBean obj = parseJsonResponse((JSONObject) array.get(i));
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
    public static BlightBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		BlightBean obj = new BlightBean();
		try {

			obj.id = jsonObject.getLong(BlightBean.ID);
			obj.name = jsonObject.getString(BlightBean.NAME);
			if(!jsonObject.isNull(BlightBean.KIND))
				obj.kind = jsonObject.getInt(BlightBean.KIND) == 0 ? "B" : "C";
			if(!jsonObject.isNull(BlightBean.INFO1))
				obj.info1= jsonObject.getString(BlightBean.INFO1);
			if(!jsonObject.isNull(BlightBean.INFO2))
				obj.info2= jsonObject.getString(BlightBean.INFO2);
			if(!jsonObject.isNull(BlightBean.INFO3))
				obj.info3= jsonObject.getString(BlightBean.INFO3);
			if(!jsonObject.isNull(BlightBean.INFO4))
				obj.info4= jsonObject.getString(BlightBean.INFO4);
			if(!jsonObject.isNull(BlightBean.INFO5))
				obj.info5= jsonObject.getString(BlightBean.INFO5);
			if(!jsonObject.isNull(BlightBean.STATUS))
				obj.status = jsonObject.getInt(BlightBean.STATUS);
			if(!jsonObject.isNull(BlightBean.SEARIAL))
				obj.serial = jsonObject.getString(BlightBean.SEARIAL);
            if(!jsonObject.isNull(BlightBean.FORM_IDS))
                obj.form_ids = jsonObject.getString(BlightBean.FORM_IDS);

            return obj;
		} 
		catch (Exception ex) {
		}
		return obj;
	}

}
