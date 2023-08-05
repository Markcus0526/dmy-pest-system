package com.bingchong.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.widget.MultiAutoCompleteTextView.Tokenizer;


/**
 * This Class is the Getter/Setter for All User Info Fields.  
 */

public class FieldBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1232001793173112079L;
	public static final int		FIELD_NONE		=	0;
	public static final int		FIELD_DATE		=	1;
	public static final int		FIELD_REAL		=	2;
	public static final int		FIELD_NUMBER	=	3;
	public static final int		FIELD_SELECT	=	4;
	public static final int		FIELD_STRING	=	5;
	public static final int		FIELD_SUM		=	6;
	public static final int		FIELD_PARENT	=	7;
	
	public static final String ID 		= "uid";
	public static final String NAME 	= "name";
	public static final String LEVEL 	= "field_level";		
	public static final String TYPE		= "type";
	public static final String STATE 	= "field_statistic";		
	public static final String NOTE 	= "note";
	public static final String PARENT_FILEDID 	= "parent_fieldid";

	public int	id;
	public String fieldName = "";
	public String fullName = "";
	public String val = "";
	public String unit = "";
	public int	fieldType;
	public long parentFildId = -1;
	public ArrayList<String> values;
	public int	level;
	public int 	state = 0;
	public String fieldIndex;
	
	public FieldBean()
	{
		fieldType = FIELD_NONE;
	}
	
	public FieldBean(int level, String fieldName, int	fieldType, int state, ArrayList<String> values, long parentFildId)
	{
		this.level = level;
		this.fieldName = fieldName;
		this.fieldType	= fieldType;
		this.values = values;
		this.state = state;
		this.parentFildId = parentFildId;
	}
	
	public FieldBean clone(){

		FieldBean newObj = new FieldBean();
		newObj.fieldName = fieldName.toString();
		newObj.val = val.toString();
		newObj.unit = unit.toString();
		newObj.fieldType = fieldType;
		newObj.values = (ArrayList<String>) values.clone();;
		newObj.level = level;
		newObj.fieldIndex = fieldIndex;
		newObj.state = state;
		newObj.parentFildId = parentFildId;
		
		return newObj;
	}
	
	public void SetValue(Object val){
		this.val	= val.toString();		
	}
	
	public String toShortString()
	{
		return fieldName;
	}
	
	public String toString()
	{
		return fieldName;
	}	
	
	public void setFieldType(char ctype){
		fieldType = FIELD_NONE;
		switch(ctype)
		{
			case 'd':
				fieldType = FIELD_DATE;
				break;
			case 'r':
				fieldType = FIELD_REAL;
				break;
			case 'i':
				fieldType = FIELD_NUMBER;
				break;
			case 'c':
				fieldType = FIELD_SELECT;
				break;
			case 't':
				fieldType = FIELD_STRING;
				break;
			case 's':
				fieldType = FIELD_SUM;
				break;
			case 'p':
				fieldType = FIELD_PARENT;
				break;
		}
	}
	
	public void setSelectNote(String note){
		if(note == null)
			return;
		if(fieldType == FIELD_SELECT)
		{
			values = new ArrayList<String>();
			String token = ",";
//			if(note.indexOf(token) < 0)
//				token = ",";
			
			StringTokenizer tokens = new StringTokenizer(note, token);
			while (tokens.hasMoreTokens()) {
				values.add(tokens.nextToken().trim());
			}
		}
		else
			unit = note;
	}
}
