package com.bingchong.bean;

import com.bingchong.net.HttpParams;
import com.bingchong.parser.ParserObject;

public class ExtraTaskBean {
	
	public final static int	STAT_NOACCEPT = 0;
	public final static int	STAT_ACCEPT = 1;
	public final static int	STAT_COMPLETE = 2;
	
	public final static String ID = "uid";
	public final static String NAME = "name";
	public final static String ADMIN_ID = "admin_id";
	public final static String WATCHER_ID = "watcher_id";
	public final static String NOTICE_DATE = "notice_date";
	public final static String REPORT_DATE = "report_date";
	public final static String USER_NAME = "user_name";
	public final static String NOTE = "note";
	public final static String STATUS = "status";		
	
	
	
	public int id;
	public String name;
	public int admin_id;
	public int watcher_id;
	public String notice_date;
	public String report_date;
	public String note;
	public String user_name;
	public int	status;
	
	public String toString()
	{
		return name + " " + user_name;
	}
	public HttpParams getPostParameters(){

		HttpParams postParameters = new HttpParams();
		
		postParameters.addParam(NAME, name);		
		postParameters.addParam(ADMIN_ID, admin_id);		
		postParameters.addParam(WATCHER_ID, watcher_id);		
		postParameters.addParam(NOTICE_DATE, notice_date);		
		postParameters.addParam(REPORT_DATE, report_date);		
		postParameters.addParam(NOTE, note);	
		postParameters.addParam(STATUS, status);	
		
		return postParameters;
	}	
}
