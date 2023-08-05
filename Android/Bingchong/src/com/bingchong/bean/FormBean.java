package com.bingchong.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class FormBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6471376552119021438L;
	
	public int id;
	public String name;
	public int blight_id;
	
	public static final String ID = "uid";
	public static final String NAME = "name";	
	public static final String BLIGHT_ID = "blight_id";	
	
	
	private ArrayList<FieldBean> listFields;
	
	public String toString()
	{
		return name;
	}
	
	public FormBean clone()
	{
		FormBean newObj = new FormBean();
		newObj.id = id;
		newObj.name = name.toString();
		newObj.blight_id = blight_id;
		
		newObj.listFields = new ArrayList<FieldBean>();
		for(int i = 0; i < listFields.size(); i++)
			newObj.listFields.add(listFields.get(i).clone());

		return newObj;
	}
	
	public void setFields(ArrayList<FieldBean> listFields){
		this.listFields = listFields;
		//reMarkIndex();
	}
	
	public ArrayList<FieldBean> getFields(){
		return listFields;
	}	
	
	public void reMarkIndex(){
		int [] lid = new int[4];
		String[] idStr = new String[4];
		int k = 0;
		lid[0] = lid[1] = lid[2] = lid[3] = 0;
		idStr[0] = idStr[1] = idStr[2] = idStr[3] = "";
		int level;
		String id;
		if(listFields == null)
			return;
		for(int i = 0; i < listFields.size(); i++){
			FieldBean obj = listFields.get(i);
			idStr[obj.level - 1] = obj.fieldName;
			level = obj.level;
			lid[level -1]++;
			id = "";
			String str = "";
			for(k = 0; k < level; k++){
				if(k != 0){
					id = id + ".";
					str = str + "-";
				}
				id = id + lid[k];
				str = str + idStr[k];
			}
			
			obj.fieldIndex = id;
			obj.fullName = str;
			
			if(obj.fieldType == FieldBean.FIELD_NONE)
			{
				for(k = level; k < 4; k++){
					lid[k] = 0;
				}
			}
		}
	}
}

