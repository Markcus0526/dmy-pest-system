package com.bingchong;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.bingchong.bean.UserBean;
import com.bingchong.utils.AppPreferences;
import com.bingchong.utils.ResolutionSet;

/**
 * Created with IntelliJ IDEA.
 * User: KHM
 * Date: 14-2-4
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
public class AppCommon
{
	public static final boolean ISDEBUG() { return true; }

	public static final String getBaiduKey() { return "KfZXtzv4ZI3aMySr3Kc8Y0pg"; }
	public static final String getPreferenceName() { return "bingchong"; }
	public static final int getPhoneLength() { return 11; }
	public static final int getVerifyKeyLength() { return 4; }
	public static final int getMinPwdLength() { return 6; }
	public static final int getMaxPwdLength() { return 16; }

	public static Toast gToast = null;

    public static AppPreferences appPreferences;

	/**
	 * Show toast in the center of the screen
	 *
	 * @param context The context to show the toast
	 * @param msg The message to show
	 */
	public static void showToast(Context context, String msg)
	{
		showToast(context, msg, Gravity.CENTER);
	}

	/**
	 * Show toast in the center of the screen
	 *
	 * @param context The context to show the toast
	 * @param strid The message resource id
	 */
	public static void showToast(Context context, int strid)
	{
		showToast(context, strid, Gravity.CENTER);
	}

	/**
	 * Show toast with the gravity in the screen
	 *
	 * @param context The context to show the toast
	 * @param strid The message resource id
	 * @param gravity The position to show the toast
	 * @see android.view.Gravity
	 */
	public static void showToast(Context context, int strid, int gravity)
	{
		String msg = context.getResources().getString(strid);
		showToast(context, msg, gravity);
	}


	/**
	 * Show the toast when app is in debug mode
	 *
	 * @param context The context to show the toast
	 * @param szMsg The message to show
	 * @see android.view.Gravity
	 */
	public static void showDebugToast(Context context, String szMsg) {
		if (!AppCommon.ISDEBUG())
			return;

		showToast(context, szMsg, Gravity.CENTER);
	}


	/**
	 * Show toast with the gravity in the screen
	 *
	 * @param context The context to show the toast
	 * @param msg The message to show
	 * @param gravity The position to show the toast
	 * @see android.view.Gravity
	 */
	public static void showToast(Context context, String msg, int gravity)
	{
		if (gToast != null)
			gToast.cancel();

		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.common_custom_toast, null);

		TextView txtView = (TextView)view.findViewById(R.id.txt_message);
		txtView.setText(msg);

		gToast = new Toast(context);
		gToast.setView(view);

		Point ptSize = Global.getScreenSize(context);
		ResolutionSet.instance.iterateChild(view, ptSize.x, ptSize.y);

		gToast.setDuration(Toast.LENGTH_LONG);
		if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM)
			gToast.setGravity(gravity, 0, ptSize.y * 1 / 5);
		else
			gToast.setGravity(gravity, 0, 0);
		gToast.show();
	}

	public static void clearUserInfo(Context appContext)
	{

	}

	public static boolean saveAutoLoginFlag(Context appContext, boolean bFlag)
	{
		boolean bSuccess = true;
		SharedPreferences prefs = null;

		try
		{
			prefs = appContext.getSharedPreferences(getPreferenceName(), Context.MODE_PRIVATE);
			SharedPreferences.Editor edit = prefs.edit();
			edit.putBoolean("autologinflag", bFlag);
			edit.commit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			bSuccess = false;
		}

		return bSuccess;
	}

    public static void saveUserInfo(UserBean obj){

        appPreferences.SetUserId(obj.id);
        appPreferences.SetUserName(obj.name);
        appPreferences.SetUserFullName(obj.name);
		appPreferences.SetValue(UserBean.REAL_NAME, obj.real_name);
        appPreferences.SetValue(UserBean.PHONE, obj.phone);
        appPreferences.SetValue(UserBean.PLACE, obj.place);
        appPreferences.SetValue(UserBean.JOB, obj.job);
        appPreferences.SetValue(UserBean.STATUS, obj.status);
        appPreferences.SetValue(UserBean.SHENG, obj.sheng);
        appPreferences.SetValue(UserBean.SHI, obj.shi);
        appPreferences.SetValue(UserBean.XIAN, obj.xian);
        appPreferences.SetValue(UserBean.SERIAL, obj.serial);
		appPreferences.SetValue(UserBean.IMG_URL, obj.imgurl);
        appPreferences.SetIntValue(UserBean.RIGHTS_ID, (int) obj.rights_id);
        appPreferences.SetIntValue(UserBean.SHENGS_ID, (int)obj.shengs_id);
        appPreferences.SetIntValue(UserBean.SHIS_ID, (int)obj.shis_id);
        appPreferences.SetIntValue(UserBean.XIANS_ID, (int)obj.xians_id);
        appPreferences.SetValue(UserBean.POINT_LIST, obj.point_list);
		appPreferences.SetIntValue(UserBean.LEVEL, obj.level);
		appPreferences.SetValue(UserBean.TOKEN, obj.token);
    }

    public static UserBean loadUserInfo(){
        UserBean obj = new UserBean();

        obj.id = appPreferences.GetUserId();
        obj.user = appPreferences.GetUserName();
        obj.name = appPreferences.GetUserFullName();
		obj.real_name = appPreferences.GetValue(UserBean.REAL_NAME);
        obj.phone = appPreferences.GetValue(UserBean.PHONE);
        obj.place = appPreferences.GetValue(UserBean.PLACE);
        obj.job = appPreferences.GetValue(UserBean.JOB);
        obj.status = appPreferences.GetValue(UserBean.STATUS);
        obj.sheng = appPreferences.GetValue(UserBean.SHENG);
        obj.shi = appPreferences.GetValue(UserBean.SHI);
        obj.xian = appPreferences.GetValue(UserBean.XIAN);
        obj.serial = appPreferences.GetValue(UserBean.SERIAL);
		obj.imgurl = appPreferences.GetValue(UserBean.IMG_URL);
        obj.rights_id = appPreferences.GetIntValue(UserBean.RIGHTS_ID);
        obj.shengs_id = appPreferences.GetIntValue(UserBean.SHENGS_ID);
        obj.shis_id = appPreferences.GetIntValue(UserBean.SHIS_ID);
        obj.xians_id = appPreferences.GetIntValue(UserBean.XIANS_ID);
        obj.point_list = appPreferences.GetValue(UserBean.POINT_LIST);
		obj.level = appPreferences.GetIntValue(UserBean.LEVEL);
		obj.token = appPreferences.GetValue(UserBean.TOKEN);

        return obj;
    }

    public static long loadUserID()
    {
        return appPreferences.GetUserId();
    }

	public static String loadUserToken()
	{
		return appPreferences.GetValue(UserBean.TOKEN);
	}

    public static Boolean isAdmin()
    {
        return (appPreferences.GetIntValue(UserBean.RIGHTS_ID) > 0);
    }
}
