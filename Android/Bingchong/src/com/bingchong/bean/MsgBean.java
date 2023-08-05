package com.bingchong.bean;

public class MsgBean {
	public int id;
	public String title;
	public String contents;
	public long	  date;
	
	public MsgBean(){}
	public MsgBean(int id, String title, String contents)
	{
		this.id = id;
		this.title = title;
		this.contents = contents;
	}
	
	public String toString()
	{
		return title;
	}
}
