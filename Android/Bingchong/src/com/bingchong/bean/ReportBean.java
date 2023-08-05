package com.bingchong.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.bingchong.Constant;
import com.bingchong.net.HttpParams;
import com.bingchong.utils.AppDateTimeUtils;


/**
 * This Class is the Getter/Setter for All User Info Fields.  
 */

public class ReportBean
{
	
	public final static String ID = "uid";
	public final static String FORM_ID = "form_id";
	public final static String FORM_NAME = "form_name";
	public final static String USER_ID = "user_id";
	public final static String POINT_ID = "point_id";
	public final static String BLIGHT_KIND = "blight_kind";
	public final static String BLIGHT_NAME = "blight_name";
	public final static String PHOTO = "photo";
	public final static String WATCH_TIME = "watch_time";
	public final static String REPORT_TIME = "report_time";
	public final static String LONGITUDE = "longitude";
	public final static String LATITUDE = "latitude";
	
	public int id;
	public int  user_id;
	public int	point_id;
	public String blightName="";
	public String	 blight_type="B";
	public long	blight_id;
	public int  form_id;
	public String  form_name="";
	public String imgUri="";
	public long	testDate = 0;
	public long reportDate = 0;
	public double longitude;
	public double latitude;
    public boolean checked = false;
	
	public FormBean reportForm = null;
	
	public ReportBean()
	{
	}
	
	public boolean isFlay()
	{
		return "C".equals(blight_type);
	}
	
	public String toString()
	{
		return AppDateTimeUtils.getSimpleDateTime(reportDate) + ":" + blightName;
	}
	
	public HttpParams getPostParameters(){
		
		if(reportForm == null || reportForm.getFields() == null)
			return null;

		HttpParams postParameters = new HttpParams();
		
		postParameters.addParam(FORM_ID, ""+form_id);		
		postParameters.addParam(USER_ID, ""+user_id);		
		postParameters.addParam(POINT_ID, ""+point_id);		
		postParameters.addParam(BLIGHT_KIND, ""+blight_type);		
		postParameters.addParam(BLIGHT_NAME, ""+blightName);		
		postParameters.addParam(PHOTO, ""+imgUri);	
		postParameters.addParam(FORM_NAME, ""+form_name);	
		postParameters.addParam(WATCH_TIME, ""+ (testDate/1000));
		postParameters.addParam(LONGITUDE, longitude);
		postParameters.addParam(LATITUDE, latitude);
		
		ArrayList<FieldBean> fields = reportForm.getFields();
		
		for(int i = 0; i < fields.size(); i++)
		{
			FieldBean fi = fields.get(i);
			postParameters.addParam(""+fi.id, ""+fi.val);
		}
		
		return postParameters;
	}
}
