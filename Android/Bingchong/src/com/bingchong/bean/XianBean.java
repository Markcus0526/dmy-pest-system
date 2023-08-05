package com.bingchong.bean;

public class XianBean {
	public int id;
	public String name;
	public int shi_id;
	public int sheng_id;
	
	public final static String ID = "uid";
	public final static String NAME = "name";
	public final static String SHI_ID = "shi_id";
	public final static String SHENG_ID = "sheng_id";
	
	public XianBean(){}
	public XianBean(int id, String name, int parent_id)
	{
		this.id = id;
		this.name = name;
		this.shi_id = parent_id;
	}
	
	public String toString()
	{
		return name;
	}
}
