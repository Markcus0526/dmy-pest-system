package com.bingchong.bean;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class WatcherBean {
	public final static String ID="uid";
	public final static String USER_ID="user_id";
	public final static String POINT_LIST ="point_list";

	
	public int id;
	public int user_id;
	private String point_list;
	public ArrayList<String> values = new ArrayList<String>();	
	public WatcherBean(){}
	
	public String toString()
	{
		return point_list;
	}
	
	public void setPointList(String list){
		point_list = list;
		refresh();
	}
	
	public String getPointList(){
		return point_list;
	}
	
	private void refresh(){
		values = new ArrayList<String>();
		String token = ",";
		
		StringTokenizer tokens = new StringTokenizer(point_list, token);
		while (tokens.hasMoreTokens()) {
			values.add(tokens.nextToken().trim());
		}		
	}
	
	public Boolean isContainID(int id){
		String idStr = String.valueOf(id);
		if(values.indexOf(idStr) >= 0)
			return true;
		return false;
	}
}
