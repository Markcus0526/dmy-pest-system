package com.bingchong.utils;

import com.bingchong.asynctask.BaseTask;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This Class will used to store the data in SharedPreferences. This data will
 * store internal Application Database.
 */

public class AppPreferences {

	private static final String CLASS_NAME = "AppPreferences";
	private static String APP_SHARED_PREFS;

	private final String USER_ID = "USER_ID";
	private final String USER_NAME = "USER_NAME";
	private final String USER_IMAGE = "USER_IMAGE";
	private final String USER_PASSWORD = "USER_PASSWORD";
	private final String USER_FULLNAME = "USER_FULLNAME";
	private final String USER_ID_ADMIN = "USER_ID_ADMIN";
	private final String PHONE_NUMBER = "PHONE_NUMBER";
	private final String LOGIN_TIME = "LOGIN_TIME";	
	private final String LAST_TRANSMITION_TIME = "LAST_TRANSMITION_TIME";	
	private final String KEY_VALUE = "KEY_";	

	private final String SERVER_URL = "SERVER_URL";
	private final String IS_SAVE_PASSWORD = "IS_SAVE_PASSWORD";
	
	private SharedPreferences mPrefs;
	private Editor mPrefsEditor;
	
	private final String NETWORK_ENABLE = "NETWORK_ENABLE";

	public static BaseTask BASETASK;

	public AppPreferences(Context context) {

		APP_SHARED_PREFS = context.getApplicationContext().getPackageName();
		this.mPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
				Activity.MODE_PRIVATE);
		this.mPrefsEditor = mPrefs.edit();
	}

	public void SetUserName(String username) {
		mPrefsEditor.putString(USER_NAME, username);
		mPrefsEditor.commit();
		Utilities.printStatement(CLASS_NAME, "SetUserName=" + username);
	}

	public String GetUserName() {
		String username = mPrefs.getString(USER_NAME, "");
		Utilities.printStatement(CLASS_NAME, "getUserName=" + username);
		return username;
	}
	public void SetUserImgPath(String path) {
		mPrefsEditor.putString(USER_IMAGE, path);
		mPrefsEditor.commit();
	}

	public String GetUserImgPath() {
		String path= mPrefs.getString(USER_IMAGE, "");
		return path;
	}	
	
	public void SetPhoneNumber(String val) {
		mPrefsEditor.putString(PHONE_NUMBER, val);
		mPrefsEditor.commit();
	}

	public String GetPhoneNumber() {
		String val = mPrefs.getString(PHONE_NUMBER, "");
		return val;
	}	
	public void SetServerURL(String url) {
		mPrefsEditor.putString(SERVER_URL, url);
		mPrefsEditor.commit();
		Utilities.printStatement(CLASS_NAME, "SetServerURL=" + url);
	}

	public String GetServerURL() {
		String url = mPrefs.getString(SERVER_URL, "");
		Utilities.printStatement(CLASS_NAME, "getServerURL=" + url);
		return url;
	}

	public void SetPassword(String password) {
		mPrefsEditor.putString(USER_PASSWORD, password);
		mPrefsEditor.commit();
		Utilities.printStatement(CLASS_NAME, "SetPassword=" + password);
	}

	public String GetPassword() {
		String password = mPrefs.getString(USER_PASSWORD, "");
		Utilities.printStatement(CLASS_NAME, "getPassword=" + password);
		return password;
	}
	
	public void SetSavePassWord(boolean isSavePassword)
	{
		mPrefsEditor.putBoolean(IS_SAVE_PASSWORD, isSavePassword);
		mPrefsEditor.commit();
	}
	public boolean IsSavePassWord()
	{
		boolean isSavePassword = mPrefs.getBoolean(IS_SAVE_PASSWORD, true);
		return isSavePassword;
	}

	public void SetUserId(long id) {
		mPrefsEditor.putLong(USER_ID, id);
		mPrefsEditor.commit();
		Utilities.printStatement(CLASS_NAME, "SetUserID=" + id);
	}

	public long GetUserId() {
		long id = mPrefs.getLong(USER_ID, 0);
		Utilities.printStatement(CLASS_NAME, "GetUserId=" + id);
		return id;
	}

	public void SetUserFullName(String fname) {
		mPrefsEditor.putString(USER_FULLNAME, fname);
		mPrefsEditor.commit();
		Utilities.printStatement(CLASS_NAME, "SetUserFName=" + fname);
	}

	public String GetUserFullName() {
		String fname = mPrefs.getString(USER_FULLNAME, "");
		Utilities.printStatement(CLASS_NAME, "GetUserFName=" + fname);
		return fname;
	}

	public void SetIsAdmin(Boolean role) {
		mPrefsEditor.putBoolean(USER_ID_ADMIN, role);
		mPrefsEditor.commit();
		Utilities.printStatement(CLASS_NAME, "SetIsAdmin=" + role);
	}

	public Boolean IsAdmin() {
		Boolean role = mPrefs.getBoolean(USER_ID_ADMIN, false);
		Utilities.printStatement(CLASS_NAME, "IsAdmin=" + role);
		return role;
	}
	
	public void SetNetworkEnable(Boolean role) {
		mPrefsEditor.putBoolean(NETWORK_ENABLE, role);
		mPrefsEditor.commit();
	}

	public Boolean IsNetworkEnable() {
		Boolean role = mPrefs.getBoolean(NETWORK_ENABLE, false);
		return role;
	}
	
	public void SetLoginTime(long startTime) {
		mPrefsEditor.putLong(LOGIN_TIME, startTime);
		mPrefsEditor.commit();
		Utilities.printStatement(CLASS_NAME, "LOGIN_TIME=" + startTime);
	}	
	public long getLoginTime(){
		return mPrefs.getLong(LOGIN_TIME, 0);
	}
	public void SetLastTransmition(long timeInMilliseconds) {
		mPrefsEditor.putLong(LAST_TRANSMITION_TIME, timeInMilliseconds);
		mPrefsEditor.commit();
		Utilities.printStatement(CLASS_NAME, "SetLastTransmition="
				+ timeInMilliseconds);
	}

	public long GetLastTransmition() {
		long timeInMilliseconds = mPrefs.getLong(LAST_TRANSMITION_TIME, 0);
		Utilities.printStatement(CLASS_NAME, "GetLastTransmition="
				+ timeInMilliseconds);
		return timeInMilliseconds;
	}
	public void SetValue(String key, String val) {
		String sKey = KEY_VALUE + key;
		mPrefsEditor.putString(sKey, val);
		mPrefsEditor.commit();
	}

	public String GetValue(String key) {
		String sKey = KEY_VALUE + key;		
		String val = mPrefs.getString(sKey, "");
		return val;
	}
	public void SetIntValue(String key, int val) {
		String sKey = KEY_VALUE + key;
		mPrefsEditor.putInt(sKey, val);
		mPrefsEditor.commit();
	}

	public int GetIntValue(String key) {
		String sKey = KEY_VALUE + key;		
		int val = mPrefs.getInt(sKey, 0);
		return val;
	}	
}
