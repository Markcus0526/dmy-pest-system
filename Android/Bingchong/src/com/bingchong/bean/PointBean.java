package com.bingchong.bean;

import com.bingchong.Constant;
import com.bingchong.net.HttpParams;
import com.bingchong.parser.ParserObject;

public class PointBean implements CheckObjInterface{
	public final static String ID = "uid";
	public final static String NAME = "name";
    public final static String TYPE = "type";
	public final static String NICKNAME = "nickname";
	public final static String LONGITUDE = "longitude";
	public final static String LATITUDE = "latitude";
	public final static String SHENG_ID = "sheng_id";
	public final static String SHI_ID = "shi_id";
	public final static String XIAN_ID = "xian_id";
	public final static String INFO1= "info1";
	public final static String INFO2= "info2";
	public final static String INFO3= "info3";
	public final static String INFO4= "info4";
	public final static String INFO5= "info5";
	public final static String INFO6= "info6";
	public final static String NOTE = "note";
	public final static String STATUS = "status";	
	public final static String PHOTO = "photo";
    public final static String TASK_COUNT = "task_count";
	public final static String HTML_INFO  = "htmlInfo";

	public int id = 0;
	public String name;
    public int type = 0;
	public String nickname;
	public double longitude;
	public double latitude;
	public int sheng_id;
	public int shi_id;
	public int xian_id;
	public String info1;
	public String info2;
	public String info3;
	public String info4;
	public String info5;
	public String info6;
	public String note;
	public int status;
	public boolean stat;
	public String imgUri="";
    public int task_count = -1;
	public String html_info;
	
	// for client
	public boolean isCheck = false;
	public String  info;

	public final static int DISP_TYPE_NORMAL = 0;
	public final static int DISP_TYPE_NICKNAME = 1;
	
	public PointBean()
	{
		status = Constant.REVIEW_FIX;
		note = "No contents";
	}
	
	public String toString()
	{
		if(dispType == DISP_TYPE_NICKNAME)
			return nickname + " " + name;
		else
			return name;
	}

	@Override
	public boolean isCheck() {
		return isCheck;
	}

	@Override
	public void setCheck(Boolean check) {
		isCheck = check;
	}

	public int dispType = DISP_TYPE_NORMAL;
	public void setDisplayType(int type){
		dispType = type;
	}

	public HttpParams getPostParameters(){

		HttpParams postParameters = new HttpParams();
		
		postParameters.addParam(NAME, name);
        postParameters.addParam(TYPE, type);
		postParameters.addParam(LONGITUDE, longitude);		
		postParameters.addParam(LATITUDE, latitude);		
		postParameters.addParam(INFO1, info1);	
		postParameters.addParam(INFO2, info2);	
		postParameters.addParam(INFO3, info3);	
		postParameters.addParam(INFO4, info4);	
		postParameters.addParam(INFO5, info5);	
		postParameters.addParam(INFO6, info6);	
		postParameters.addParam(SHENG_ID, sheng_id);	
		postParameters.addParam(SHI_ID, shi_id);	
		postParameters.addParam(XIAN_ID, xian_id);	
		postParameters.addParam(NOTE, note);	
		postParameters.addParam(PHOTO, imgUri);
		postParameters.addParam(TASK_COUNT, task_count);

		return postParameters;
	}	
}
