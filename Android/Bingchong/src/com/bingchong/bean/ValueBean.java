package com.bingchong.bean;

import java.util.*;

public class ValueBean {
	public final static String NAME = "name";
	public final static String VALUE = "value";
	
    public String name;
    public float  val;
    
    public ValueBean(){
    	name = "";
    	val = 0;
    }
    public ValueBean(String name, float val){
    	this.name = name;
    	this.val = val;
    }
}
