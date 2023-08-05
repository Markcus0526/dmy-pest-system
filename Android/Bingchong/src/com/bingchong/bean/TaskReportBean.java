package com.bingchong.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.bingchong.Constant;
import com.bingchong.net.HttpParams;
import com.bingchong.utils.AppDateTimeUtils;


/**
 * This Class is the Getter/Setter for All User Info Fields.  
 */

public class TaskReportBean implements Comparable<TaskReportBean>{

    public final static String ID = "uid";
	public final static String NAME = "name";
	public final static String FORM_ID = "form_id";
	public final static String FORM_NAME = "form_name";
	public final static String USER_NAME = "user_name";
	public final static String POINT_ID = "point_id";
	public final static String DATE = "report_time";
	public final static String BLIGHT_KIND = "blight_kind";
	public final static String BLIGHT_NAME = "blight_name";
	public final static String STATE = "review_status";

    public long id;
	public String  name;
	public int  form_id;
	public String form_name;
	public String user_name;
	public int	point_id;
	public String date;
	public String blight_kind="B";
	public String blight_name="";
	public String imgUri="";
	public int state;
	
	public TaskReportBean()
	{
	}
	
	public boolean isFlay()
	{
		return "C".equals(blight_kind);
	}
	
	public String toString()
	{
		return date + " " + user_name + "\n" + blight_name + " " + form_name;
	}
	public int compareTo(TaskReportBean another) {
		if (another == null) return 1;
		// sort descending, most recent first
		return another.date.compareTo(date);
	}	
}
