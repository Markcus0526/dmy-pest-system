package com.bingchong.parser;

import java.util.ArrayList;

import com.bingchong.bean.FieldBean;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bingchong.Constant;
import com.bingchong.bean.FormBean;

public class ParserForm {
	 public static ArrayList<FormBean> getParsedResult(String jsonResponse){
		 ArrayList<FormBean> arrList = new ArrayList<FormBean>();
	        try {

	        	JSONObject mainJsonObject = new JSONObject(jsonResponse);
	            String success =mainJsonObject.getString(Constant.STATUS).trim();
				if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
					JSONArray array = mainJsonObject.getJSONArray(Constant.RESULT);
					 for (int i = 0; i < array.length(); i++) {
						 FormBean obj = parseJsonResponse((JSONObject) array.get(i));
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
	  public static FormBean parseJsonResponse(JSONObject jsonObject) throws Exception{
		  FormBean obj = new FormBean();
      		try {
	        	obj.id = jsonObject.getInt(FormBean.ID);
	        	obj.name = jsonObject.getString(FormBean.NAME);
                JSONArray jsonFields = jsonObject.getJSONArray("fields");
                ArrayList<FieldBean> arrFields = new ArrayList<FieldBean>();
                for(int i = 0; i < jsonFields.length(); i++)
                {
                    FieldBean fieldObj = ParserField.parseJsonResponse(jsonFields.getJSONObject(i));
                    arrFields.add(fieldObj);
                }
                obj.setFields(arrFields);
	        }
	        catch (Exception ex) {
	        	return null;
	        }	        

	        return obj;
	  }
}
