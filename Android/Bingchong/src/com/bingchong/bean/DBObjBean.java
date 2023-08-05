package com.bingchong.bean;

import com.google.gson.Gson;

public class DBObjBean {
	
	public final static int TYPE_NONE		= 0;
	public final static int TYPE_USER		= 1;
	public final static int TYPE_BLIGHT 	= 2;
	public final static int TYPE_POINT		= 3;
	public final static int TYPE_BLIGHT_INFO	= 4;
	public final static int TYPE_POINT_INFO		= 5;
	public final static int TYPE_FORM		= 6;
	public final static int TYPE_XIAN		= 7;
	public final static int TYPE_REPORT		= 8;
	
	public int id;
	public long obj_id;
	public int type;
	public String name;
	public String contents = "";
	
	public DBObjBean(){
		type = TYPE_NONE;
	}
	
	public DBObjBean(Object obj){
		type = TYPE_NONE;
		setFrom(obj);
	}
	
	public void setFrom(Object obj)
	{
		this.obj_id = -1;
		this.name = "";
		this.type = TYPE_NONE;
		this.contents = getJsonObject(obj);
		if(obj instanceof UserBean)
		{
			type = TYPE_USER;
			name = ((UserBean)obj).name;
			obj_id = (int)((UserBean)obj).id;
		}
		else if(obj instanceof PointBean)
		{
			type = TYPE_POINT;
			name = ((PointBean)obj).name;
			obj_id = ((PointBean)obj).id;
		}
		else if(obj instanceof BlightBean)
		{
			type = TYPE_BLIGHT;
			name = ((BlightBean)obj).name;
			obj_id = ((BlightBean)obj).id;
		}
		else if(obj instanceof XianBean)
		{
			type = TYPE_XIAN;
			name = ((XianBean)obj).name;
			obj_id = ((XianBean)obj).id;
		}
		else if(obj instanceof FormBean)
		{
			type = TYPE_FORM;
			name = ((FormBean)obj).name;
			obj_id = ((FormBean)obj).id;
		}
		else if(obj instanceof ReportBean)
		{
			type = TYPE_REPORT;
			name = ((ReportBean)obj).blightName;
			obj_id = ((ReportBean)obj).id;
		}			
	}
	
	public void setFrom(UserBean obj)
	{
		this.obj_id = (int)obj.id;
		this.name = obj.name.toString();
		this.type = TYPE_USER;
		this.contents = getJsonObject(obj);
	}
	
	public Object getBeanObj()
	{
		if(contents.length() == 0)
			return null;
		Object obj = null;
		try{
			Gson gson = new Gson();
			switch(type)
			{
			case TYPE_USER:
				obj = gson.fromJson(contents, UserBean.class);
				break;
			case TYPE_BLIGHT:
				obj = gson.fromJson(contents, BlightBean.class);
				break;
			case TYPE_POINT:
				obj = gson.fromJson(contents, PointBean.class);
				break;
			case TYPE_XIAN:
				obj = gson.fromJson(contents, XianBean.class);
				break;				
			case TYPE_FORM:
				obj = gson.fromJson(contents, FormBean.class);
				break;
			case TYPE_REPORT:
				obj = gson.fromJson(contents, ReportBean.class);
				break;
			}
			
		}
		catch(Exception ex)
		{
			obj = null;
		}
		
		return obj;
	}
	
	public String toString()
	{
		return name;
	}
	
	private String getJsonObject(Object obj){
		try {

			Gson gson = new Gson();
			String jsonStr = gson.toJson(obj);

			return jsonStr;
		} 
		catch (Exception ex) {
			return "";
		}		  
	}	  	
}
