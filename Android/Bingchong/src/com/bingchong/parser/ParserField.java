package com.bingchong.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.FieldBean;

public class ParserField {
	 public static ArrayList<FieldBean> getParsedResult(String jsonResponse){
		 ArrayList<FieldBean> arrList = new ArrayList<FieldBean>();
	        try {

	        	JSONObject mainJsonObject = new JSONObject(jsonResponse);
	            String success =mainJsonObject.getString(Constant.STATUS).trim();
				if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
					JSONArray array = mainJsonObject.getJSONArray(Constant.RESULT);
					 for (int i = 0; i < array.length(); i++) {
						 FieldBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	  public static FieldBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		  FieldBean obj = new FieldBean();
      		try {
	        	obj.id = jsonObject.getInt(FieldBean.ID);
	        	obj.fieldName = jsonObject.getString(FieldBean.NAME);
	        	String type = jsonObject.getString(FieldBean.TYPE);
	        	obj.setFieldType(type.charAt(0));
	        	obj.setSelectNote(jsonObject.getString(FieldBean.NOTE));
				obj.parentFildId = jsonObject.getLong(FieldBean.PARENT_FILEDID);
	        }
	        catch (Exception ex) {
	        }	        

	        return obj;
	  }
}
