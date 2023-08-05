package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.UserBean;



public class ParserUser {

	public static UserBean getParsedOneResult(String jsonResponse){

		try {
			UserBean userBean = new UserBean();
			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString(Constant.STATUS).trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){

				JSONArray array = mainJsonObject.getJSONArray(Constant.RESULT);
				for (int i = 0; i < array.length(); i++) {
					userBean = parseJsonResponse((JSONObject) array.get(0));
				}
				return userBean;
			}
			else{
				String error =mainJsonObject.getString(Constant.ERROR).trim();
				System.out.println("Error = "+error);
				return null;
			}

		} 
		catch (Exception ex) {
		}
		return null;
	}

	public static ArrayList<UserBean> getParsedResult(String jsonResponse){

		ArrayList<UserBean> newList = new ArrayList<UserBean>();
		try {
			JSONObject mainJsonObject = new JSONObject(jsonResponse);
			String success =mainJsonObject.getString(Constant.STATUS).trim();
			if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
				JSONArray array = mainJsonObject.getJSONArray(Constant.RESULT);
				for (int i = 0; i < array.length(); i++) {
					UserBean userBean = parseJsonResponse((JSONObject) array.get(i));
					if(userBean != null)
						newList.add(userBean);
				}
			}
			else{
				String error =mainJsonObject.getString(Constant.ERROR).trim();
				System.out.println("Error = "+error);
			}

		} 

		catch (Exception ex) {

		}

		return newList;
	}	   

	public static UserBean parseJsonResponse(JSONObject jsonObject){
		UserBean obj = new UserBean();
		try {
			if(!jsonObject.isNull(UserBean.ID))
				obj.id = jsonObject.getInt(UserBean.ID);
			if(!jsonObject.isNull(UserBean.USER))
				obj.user = jsonObject.getString(UserBean.USER);
			if(!jsonObject.isNull(UserBean.NAME))
				obj.name = jsonObject.getString(UserBean.NAME);
			if(!jsonObject.isNull(UserBean.REAL_NAME))
				obj.real_name = jsonObject.getString(UserBean.REAL_NAME);
			if(!jsonObject.isNull(UserBean.PLACE))
				obj.place = jsonObject.getString(UserBean.PLACE);
			if(!jsonObject.isNull(UserBean.JOB))
				obj.job= jsonObject.getString(UserBean.JOB);
			if(!jsonObject.isNull(UserBean.PHONE))
				obj.phone= jsonObject.getString(UserBean.PHONE);
			if(!jsonObject.isNull(UserBean.RIGHTS_ID))
				obj.rights_id = jsonObject.getInt(UserBean.RIGHTS_ID);
			if(!jsonObject.isNull(UserBean.IMG_URL))
				obj.imgurl = jsonObject.getString(UserBean.IMG_URL);
			if(!jsonObject.isNull(UserBean.SHENGS_ID))
				obj.shengs_id = jsonObject.getInt(UserBean.SHENGS_ID);
			if(!jsonObject.isNull(UserBean.SHIS_ID))
				obj.shis_id = jsonObject.getInt(UserBean.SHIS_ID);
			if(!jsonObject.isNull(UserBean.XIANS_ID))
				obj.xians_id = jsonObject.getInt(UserBean.XIANS_ID);
			if(!jsonObject.isNull(UserBean.PHONE))
				obj.phone = jsonObject.getString(UserBean.PHONE);
			if(!jsonObject.isNull(UserBean.STATUS))
				obj.status = jsonObject.getString(UserBean.STATUS);
			if(!jsonObject.isNull(UserBean.SHENG))
				obj.sheng = jsonObject.getString(UserBean.SHENG);
			if(!jsonObject.isNull(UserBean.SHI))
				obj.shi = jsonObject.getString(UserBean.SHI);
			if(!jsonObject.isNull(UserBean.XIAN))
				obj.xian = jsonObject.getString(UserBean.XIAN);
            if(!jsonObject.isNull(UserBean.LEVEL))
                obj.level = jsonObject.getInt(UserBean.LEVEL);
            if(!jsonObject.isNull(UserBean.POINT_LIST))
                obj.point_list = jsonObject.getString(UserBean.POINT_LIST);
			if(!jsonObject.isNull(UserBean.TOKEN))
				obj.token = jsonObject.getString(UserBean.TOKEN);
		} 
		catch (Exception ex) {
		}	        
		return obj;
	}
}
