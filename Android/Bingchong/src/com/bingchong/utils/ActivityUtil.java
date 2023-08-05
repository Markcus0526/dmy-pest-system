package com.bingchong.utils;

import com.bingchong.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtil {

	/**
	 * 启动Activity
	 * 
	 * @param activity
	 * @param intent
	 * @param animation
	 */
	public static void startActivity(Activity activity, Intent intent,
			boolean animation) {
		activity.startActivity(intent);
		if (animation) {
			activity.overridePendingTransition(R.anim.right_to_left,
					R.anim.none);
		}
	}

	/**
	 * 动画模式启动Activity
	 * 
	 * @param activity
	 * @param intent
	 * 
	 */
	public static void startActivity(Activity activity, Intent intent) {
		startActivity(activity, intent, true);
	}
	
	/**
	 * 动画模式启动Activity
	 * 
	 * @param activityContext
	 * @param intent
	 */
	public static void startActivity(Context activityContext, Intent intent){
		startActivity((Activity)activityContext, intent);
	}
	
	/**
	 * 以响应Result的方式启动Activity
	 * 
	 * @param activity
	 * @param intent
	 * @param animation 动画模式
	 */
	public static void startActivityForResult(Activity activity, Intent intent, int requestCode, boolean animation) {
		activity.startActivityForResult(intent, requestCode);
		if (animation) {
			activity.overridePendingTransition(R.anim.right_to_left,
					R.anim.none);
		}
	}

	/**
	 * 以动画模式，以响应Result的方式启动Activity
	 * 
	 * @param activity
	 * @param intent
	 */
	public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
		startActivityForResult(activity, intent, requestCode, true);
	}

}
