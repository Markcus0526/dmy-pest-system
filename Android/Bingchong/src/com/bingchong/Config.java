package com.bingchong;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.bingchong.bean.Experts;
import com.bingchong.bean.Visitor;

public class Config {
	private static final String TAG = "Config";
	
	public static List<Visitor> visitorList = new ArrayList<Visitor>();
	public static List<Experts> expertsList = new ArrayList<Experts>();
	
	/**
	 * 操作成功
	 */
	public static final String CODE_SUCCESS = "00000";
	public static final boolean DEBUG_MODE = false;
	/**
	 * 是否设置字体
	 */
	public static final boolean IS_SET_FONTS = true;
	/**
	 * 微软雅黑
	 */
	public static final String MSYH = "font/MSYH.ttf";
	/**
	 * 字体
	 */
	public static final String FONTS = MSYH;
	
	public static final String UPDATE_SERVER = "http://192.168.1.130:8080/Zjspyy/";
	public static final String UPDATE_APKNAME = "zjspyy.apk";
	public static final String UPDATE_VERJSON = "login.action";
	public static final String UPDATE_SAVENAME = "updateapkzjspyy.apk";
	
	/**
	 * 本地存储根目录名
	 */
	public static final String CACHE_ROOT_NAME = "zjspyy";
	
	/**
	 * 本地存储图片根目录名
	 */
	public static final String CACHE_PIC_ROOT_NAME = "pics";

	/**
	 * 头像缓存文件夹名称
	 */
	public static final String AVATAR_PIC_FOLDER_NAME = "avatar";
	/**
	 * 缩略图缓存文件夹名称
	 */
	public static final String THUMB_PIC_FOLDER_NAME = "thumb_pic";
	/**
	 * 原图缓存文件夹名称
	 */
	public static final String ORIGINAL_PIC_FOLDER_NAME = "original_pic";
	/**
	 * 保存图片的文件夹名称(就是查看图片点击保存)
	 */
	public static final String SAVED_PIC_FOLDER_NAME = "saved_pic";

	/**
	 * 本地存储音频根目录名
	 */
	public static final String CACHE_AUDIO_ROOT_NAME = "audios";

	/**
	 * @Description 查看原图保存的图片路径
	 * @return
	 */

	public static int OFFICIAL_ORG_ID = 1;
	
	
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					"com.lyxx.zjspyy", 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verCode;
	}
	
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					"com.lyxx.zjspyy", 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verName;	

	}
	
	public static String getAppName(Context context) {
		String verName = context.getResources()
		.getText(R.string.app_name).toString();
		return verName;
	}
}
