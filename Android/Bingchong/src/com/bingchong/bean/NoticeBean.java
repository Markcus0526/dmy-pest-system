package com.bingchong.bean;

public class NoticeBean {
	public final static String ID="uid";
	public final static String TITLE="title";
	public final static String CONTENTS="contents";
	public final static String YEAR="year";
	public final static String SERIAL="serial";

	
	public int id;
	public String title;
	public String contents;
	public int year;
	public String serial;
	
	public NoticeBean(){}
	
	public String toString()
	{
		return title;
	}
}
