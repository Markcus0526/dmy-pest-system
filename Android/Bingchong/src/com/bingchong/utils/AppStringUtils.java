package com.bingchong.utils;

/**
 * This Class is a String Utility for various types of Operation,Conversion and etc..  
 */

public class AppStringUtils {
	
	
	/**
	 * This Function change the First Letter in UpperCase of any String . 
	 * See below for @params.  
	 */
	
	public static String FirstLetterInUpperCase(String word){
		
		String result = "";		
		if(word.length()>0){			
		  char c = word.charAt(0);
		  String splitedString = word.substring(1, word.length());
		  result = Character.toUpperCase(c)+splitedString;
		}
		return result;
	}
	
	/**
	 * This Function convert the String value (input must be int/long format) into Long Value . 
	 * See below for @params.  
	 */
	
	public static long convertStringToLong(String strValue){
		Long longValue = Long.valueOf(strValue);
		return longValue;
	}
}

