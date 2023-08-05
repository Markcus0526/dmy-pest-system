package com.bingchong.bean;

import com.bingchong.Constant;
import com.bingchong.net.HttpParams;

public class BlightBean {
	
	public final static String ID = "uid";
	public final static String NAME = "name";
	public final static String KIND = "kind";
	public final static String INFO1= "info1";
	public final static String INFO2= "info2";
	public final static String INFO3= "info3";
	public final static String INFO4= "info4";
	public final static String INFO5= "info5";
	public final static String NOTE = "note";
	public final static String SEARIAL = "serial";
	public final static String STATUS = "status";
	public final static String PHOTO = "photo";		
	public final static String LONGITUDE = "longitude";
	public final static String LATITUDE = "latitude";	
	
	public final static String SHENG_ID = "sheng_id";
	public final static String SHI_ID = "shi_id";
	public final static String XIAN_ID = "xian_id";
    public final static String FORM_IDS = "form_ids";


    public long id;
	public String name;
	public long createDate;
	public int	status;
	public String kind = "B";
	public String info1;
	public String info2;
	public String info3;
	public String info4;
	public String info5;
	public String info;
	public String note;
	public String serial;
	public String imgUri="";
	public double longitude;
	public double latitude;
	
	public int sheng_id;
	public int shi_id;
	public int xian_id;
    public String form_ids;
	
	private final static int max_string_count = 17;
	
	public BlightBean()
	{
		status = Constant.REVIEW_FIX;
	}
	
	public String toString()
	{
		String str;
		if(serial != null)
			str =  serial + " " + name;
		else
			str = name;
		
		if(str.length() > max_string_count){
			str = str.substring(0, max_string_count-1);
			str = str + "...";
		}
		
		return str;
	}
	
	public boolean isFly()
	{
		return "C".equals(kind);
	}
	
	public void setKind(Boolean isFly)
	{
		if(isFly)
			kind = "C";
		else
			kind = "B";
	}	
	
	public HttpParams getPostParameters(){

		HttpParams postParameters = new HttpParams();
		
		postParameters.addParam(NAME, name);		
		postParameters.addParam(KIND, kind);	
		postParameters.addParam(LONGITUDE, longitude);		
		postParameters.addParam(LATITUDE, latitude);			
		postParameters.addParam(INFO1, info1);	
		postParameters.addParam(INFO2, info2);	
		postParameters.addParam(INFO3, info3);	
		postParameters.addParam(INFO4, info4);	
		postParameters.addParam(INFO5, info5);	
		postParameters.addParam(NOTE, note);	
		postParameters.addParam(PHOTO, imgUri);	
		
		postParameters.addParam(SHENG_ID, sheng_id);	
		postParameters.addParam(SHI_ID, shi_id);	
		postParameters.addParam(XIAN_ID, xian_id);
        postParameters.addParam(FORM_IDS, form_ids);
		
		return postParameters;
	}		
}
