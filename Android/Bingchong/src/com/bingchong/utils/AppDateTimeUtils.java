package com.bingchong.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This Class is a Utility for various types of Date Operation,Conversion and
 * etc..
 */

public class AppDateTimeUtils {

	/**
	 * This Function pre append Month and Date with '0' if both are less than
	 * '10' .
	 */

	public static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	/**
	 * This Function return the Current Date Time in String Format (eg.
	 * YYYY-MM-DD HH:mm:ss) .
	 */

	public static String getCurrentDateTimeInHms() {

		Calendar date = Calendar.getInstance();
		date.setTime(new Date(System.currentTimeMillis()));
		int start_date = date.get(Calendar.DATE);
		int month = date.get(Calendar.MONTH) + 1;
		int year = date.get(Calendar.YEAR);
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int min = date.get(Calendar.MINUTE);
		int sec = date.get(Calendar.SECOND);
		String dateTime = String.valueOf(year) + "-"
				+ String.valueOf((pad(month))) + "-"
				+ String.valueOf((pad(start_date))) + " "
				+ String.valueOf((pad(hour))) + ":"
				+ String.valueOf((pad(min))) + ":" + String.valueOf((pad(sec)));
		System.out.println("getCurrentDateTimeInHms = " + dateTime);
		return dateTime;
	}

	public static String getDate(long milliSeconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(milliSeconds));
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String requiredDate = dateFormat.format(cal.getTime());
		return requiredDate;
	}
	
	public static String getDate(long milliSeconds, SimpleDateFormat fm) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(milliSeconds));
		String requiredDate = fm.format(cal.getTime());
		return requiredDate;
	}	

	public static String getTime(long milliSeconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(milliSeconds));
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String requiredTime = dateFormat.format(cal.getTime());
		return requiredTime;
	}

	/**
	 * This Function return the Current Date in String Format (eg. YYYY-MM-DD )
	 * .
	 */

	public static String getCurrentDate() {

		Calendar date = Calendar.getInstance();
		date.setTime(new Date(System.currentTimeMillis()));
		int start_date = date.get(Calendar.DATE);
		int month = date.get(Calendar.MONTH) + 1;
		int year = date.get(Calendar.YEAR);
		String datetoday = String.valueOf(year) + "-"
				+ String.valueOf((pad(month))) + "-"
				+ String.valueOf((pad(start_date)));
		System.out.println("getCurrentDate = " + datetoday);
		return datetoday;
	}

	/**
	 * This Function return the Current Time in String Format (eg. HH:mm:ss ) .
	 */

	public static String getCurrentTime() {

		Calendar date = Calendar.getInstance();
		date.setTime(new Date(System.currentTimeMillis()));
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int min = date.get(Calendar.MINUTE);
		int sec = date.get(Calendar.SECOND);
		String timetoday = String.valueOf((pad(hour))) + ":"
				+ String.valueOf((pad(min))) + ":" + String.valueOf((pad(sec)));
		System.out.println("getCurrentTime = " + timetoday);
		return timetoday;
	}

	/**
	 * This Function return the Current Time in MilliSeconds. See below for
	 * @params.
	 */

	public static long getTimeInMilliSecond(int year, int month, int day,
			int hour, int minute, int second) {

		GregorianCalendar cal = new GregorianCalendar(year, month, day, hour,
				minute, second);
		long time = cal.getTimeInMillis();
		return time;
	}

	/**
	 * This Function convert the Time in milliseconds ( String ) to Simple Date
	 * Time formate ( dd/MM/yyyy HH:mm:ss ) and return the String. See below for
	 * @params.
	 */

	public static String getDateTime(String timeInMillis) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date resultdate = new Date(
				AppStringUtils.convertStringToLong(timeInMillis));
		String strDateTime = sdf.format(resultdate);
		return strDateTime;
	}

	/**
	 * This Function convert the Time in milliseconds ( long ) to Simple Date
	 * Time formate ( dd/MM/yyyy HH:mm:ss ) and return the String. See below for
	 * @params.
	 */

	public static String getDateTime(long timeInMillis) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date resultdate = new Date(timeInMillis);
		String strDateTime = sdf.format(resultdate);
		return strDateTime;
	}
	
	public static String getSimpleDateTime(long timeInMillis) {

		Date resultdate = new Date(timeInMillis);
		Calendar date = Calendar.getInstance();
		date.setTime(resultdate);

		int start_date = date.get(Calendar.DATE);
		int month = date.get(Calendar.MONTH) + 1;
		int year = date.get(Calendar.YEAR);
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int min = date.get(Calendar.MINUTE);
		int sec = date.get(Calendar.SECOND);

		String dateTime = String.valueOf(year) + "-"
				+ String.valueOf((pad(month))) + "-"
				+ String.valueOf((pad(start_date))) + " "
				+ String.valueOf((pad(hour))) + ":"
				+ String.valueOf((pad(min)));
		System.out.println("getCurrentDateTimeInHms = " + dateTime);
		return dateTime;
	}	
}
