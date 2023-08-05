package com.bingchong;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.bingchong.bean.UserBean;
import com.bingchong.utils.ResolutionSet;

import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created with IntelliJ IDEA.
 * User: KHM
 * Date: 14-2-7
 * Time: 上午9:42
 * To change this template use File | Settings | File Templates.
 */
public class Global
{
	public static Toast gToast = null;
	public static Context gContext = null;
    public static String getPreferenceName() { return "SoccerLottery_info"; }
    public static int ANIM_NONE() { return -1; }
    public static int ANIM_COVER_FROM_LEFT() { return 0; }
    public static int ANIM_COVER_FROM_RIGHT() { return 1; }
    public static String ANIM_DIRECTION() { return "anim_direction"; }

	public static boolean isExternalStorageRemovable()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
			return Environment.isExternalStorageRemovable();

		return true;
	}


	public static File getExternalCacheDir(Context context) {
		File retFile = null;
		if (hasExternalCacheDir()) {
			retFile = context.getExternalCacheDir();
		}

		if (retFile == null)
		{
			// Before "Froyo" we need to construct the external cache dir ourselves
			String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
			retFile = new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
		}

		return retFile;
	}


	public static boolean hasExternalCacheDir()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}


	public static void logDebugMsg(String szMsg)
	{
		Log.d("debug_msg", szMsg);
	}

	public static String eatSpaces(String szText)
	{
		return szText.replaceAll(" ", "");
	}

	public static String eatFullStops(String szText)
	{
		return szText.replaceAll(".", "");
	}

	public static String eatChinesePunctuations(String szText)
	{
		String szResult = szText;
		String szPuncs = "。？！，、；：“”‘’（）-·《》〈〉";
		String szPuncs2 = "——";
		String szPuncs3 = "……";

		szResult = szResult.replaceAll(szPuncs2, "");
		szResult = szResult.replaceAll(szPuncs3, "");
		for (int i = 0; i < szPuncs.length(); i++)
		{
			char chrItem = szPuncs.charAt(i);
			String szItem = "" + chrItem;
			szResult = szResult.replaceAll(szItem, "");
		}

		return szResult;
	}



	public static Point getScreenSize(Context appContext)
	{
		Point ptSize = new Point(0, 0);

		WindowManager wm = (WindowManager)appContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		if (Global.getSystemVersion(appContext) >= Build.VERSION_CODES.HONEYCOMB_MR2)
			display.getSize(ptSize);
		else
			ptSize = new Point(display.getWidth(), display.getHeight());

		return ptSize;
	}


	public static void selectAllText(EditText editText)
	{
		if (editText == null)
			return;

		editText.requestFocus();
		if (editText.getText().toString().length() > 0)
			editText.setSelection(editText.getText().toString().length());
	}


	public static void showTextToast(Context context, String toastStr)
	{
		if (gToast == null || gToast.getView().getWindowVisibility() != View.VISIBLE)
		{
			gToast = Toast.makeText(context, toastStr, Toast.LENGTH_LONG);
			gToast.show();
		}
	}


	public static void showToastWithView(Context context, View view, int gravity)
	{
		if (gToast != null)
			gToast.cancel();

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




	public static String encodeWithBase64(Bitmap bitmap)
	{
		if (bitmap == null)
			return "";

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return Base64.encodeToString(byteArray, Base64.NO_WRAP);
	}


	/**
	 * Method to show keyboard for the edit control
	 *
	 * @param editText		Edit text to show keyboard
	 * @param context		Context
	 */
	public static void showKeyboardFromEditControl(EditText editText, Context context)
	{
		InputMethodManager mgr = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}


	/**
	 * Method to hide keyboard for the edit control
	 *
	 * @param editText		Edit text to hide keyboard
	 * @param context		Context
	 */
	public static void hideKeyboardFromEditControl(EditText editText, Context context)
	{
		InputMethodManager mgr = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}


	/**
	 * Method to hide keyboard for the every edit controls in the group indicated with parameter
	 *
	 * @param view		View to scan the edit text
	 * @param context	Context
	 */
	public static void hideKeyboardsInView(View view, Context context)
	{
		if (view == null)
			return;

		if (view instanceof EditText) {
			Global.hideKeyboardFromEditControl((EditText)view, context);
		}

		if (view instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup)view;

			int nCount = viewGroup.getChildCount();
			for (int i = 0; i < nCount; i++) {
				hideKeyboardsInView(viewGroup.getChildAt(i), context);
			}
		}
	}


	public static double calcDistanceKM(double lat1, double lng1, double lat2, double lng2)
	{
		double EARTH_RADIUS_KM = 6378.137;

		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double radLng1 = Math.toRadians(lng1);
		double radLng2 = Math.toRadians(lng2);
		double deltaLat = radLat1 - radLat2;
		double deltaLng = radLng1 - radLng2;

		double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(deltaLng / 2), 2)));

		distance = distance * EARTH_RADIUS_KM;

		return (int)(distance * 10) / 10;		// unit : km
	}


	public static String convertToLetter(int iCol)
	{
		String szResult = "";
		int iAlpha;
		int iRemainder = 0;
		iAlpha = iCol / 27;
		iRemainder = iCol - (iAlpha * 26);
		if (iAlpha > 0)
			szResult = Character.toString((char)(iAlpha + 64));

		if (iRemainder > 0)
			szResult += Character.toString((char)(iRemainder + 64));

		return szResult;
	}


	public static int getRelativeLeft(View myView) {
		if (myView.getParent() == null)
			return 0;

		if (myView.getParent() == myView.getRootView())
			return myView.getLeft();
		else
			return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	}


	public static int getRelativeTop(View myView) {
		if (myView.getParent() == null)
			return 0;

		if (myView.getParent() == myView.getRootView())
			return myView.getTop();
		else
			return myView.getTop() + getRelativeTop((View) myView.getParent());
	}


	public static Rect getGlobalRectOfView(View v)
	{
		int nLeft = getRelativeLeft(v);
		int nTop = getRelativeTop(v);
		return new Rect(nLeft, nTop, nLeft + v.getWidth(), nTop + v.getHeight());
	}


	public static int statusBarHeight(Activity activity) {
		int statusBarHeight = 0;

		Rect rectgle= new Rect();
		Window window= activity.getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);

		statusBarHeight = rectgle.top;

		return statusBarHeight;
	}


	public static boolean validateEmail(String email)
	{
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches())
			isValid = true;

		return isValid;
	}



	public static boolean isNumeric(String phone)
	{
		boolean isValid = false;

		String expression = "\\d+";
		CharSequence inputStr = phone;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches())
			isValid = true;

		return isValid;
	}



	public static String getDeviceIMEI(Context context)
	{
		TelephonyManager tm = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}


	public static void skipInputStream(InputStream stream, long nBytes)
	{
		long skippedTotal = 0;

		while (skippedTotal != nBytes) {
			try {
				long skipped = stream.skip(nBytes - skippedTotal);
				assert(skipped >= 0);
				skippedTotal += skipped;
				if (skipped == 0)
					break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String encodeToUTF8(String str)
	{
		String tmp;

		try {
			tmp = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			tmp = str;
		}

		return tmp;
	}


	// Date to String conversion
	public static String date2String(Date dtvalue, String format)
	{
		String szResult = "";
		if (dtvalue == null)
			return "";

		try {
			DateFormat df = null;
			df = new SimpleDateFormat(format);
			szResult = df.format(dtvalue);
		} catch (Exception ex) {
			ex.printStackTrace();
			Global.logDebugMsg(ex.getMessage());
			szResult = null;
		}

		return szResult;
	}


	// String to date conversion
	public static Date string2Date(String szTime, String format)
	{
		if (szTime == null || szTime.equals("") || szTime.contains("0000-00-00"))
			return null;

		DateFormat df = new SimpleDateFormat(format);
		Date dtValue = null;

		try
		{
			dtValue = df.parse(szTime);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			Global.logDebugMsg(ex.getMessage());
			dtValue = null;
		}

		return dtValue;
	}

    public static String getDate(long milliSeconds, String dateFormat)
    {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

	/**************************************** Image operation ****************************************/
	// Rotate image clockwise
	public static Bitmap rotateImage(String pathToImage, int nAngle) {
		// 1. rotate matrix by post concatination
		Matrix matrix = new Matrix();
		matrix.postRotate(nAngle);

		// 2. create Bitmap from rotated matrix
		Bitmap sourceBitmap = BitmapFactory.decodeFile(pathToImage);
		return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
	}



	public static Bitmap getCroppedRoundBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);

		canvas.drawOval(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}



	public static int getImageOrientation(String imagePath){
		int nAngle = 0;

		try {
			File imageFile = new File(imagePath);
			ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());

			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_270:
					nAngle = 270;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					nAngle = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					nAngle = 90;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return nAngle;
	}


	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int cornerRadiusPx)
	{
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = cornerRadiusPx;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}



	/**************************************** Methods for open other app ****************************************/
	public static boolean openDialPad(String phoneNum, Context activityContext)
	{
		boolean bResult = true;

		try {
			Intent intent = new Intent();
			intent.setAction("android.intent.action.DIAL");
			intent.setData(Uri.parse("tel:" + phoneNum));
			activityContext.startActivity(intent);
		} catch (Exception ex) {
			ex.printStackTrace();
			Global.logDebugMsg(ex.getMessage());
			bResult = false;
		}

		return bResult;
	}


	public static boolean openSystemBrowser(Context ctx, String url)
	{
		boolean bResult = true;

		try {
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(url);
			intent.setData(content_url);
			ctx.startActivity(intent);
		} catch (Exception ex) {
			ex.printStackTrace();
			Global.logDebugMsg(ex.getMessage());
			bResult = false;
		}

		return bResult;
	}


	public static boolean callPhone(String phoneNum, Context appContext) {
		boolean bResult = true;

		try {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + phoneNum));
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			appContext.startActivity(intent);
		} catch (Exception ex) {
			ex.printStackTrace();
			Global.logDebugMsg(ex.getMessage());
			bResult = false;
		}

		return bResult;
	}


	public static boolean openSMSApp(String msg, Context appContext)
	{
		boolean bResult = true;

		try {
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setData(Uri.parse("sms:"));
			smsIntent.putExtra("sms_body", msg == null ? "" : msg);
			appContext.startActivity(smsIntent);
		} catch (Exception ex) {
			ex.printStackTrace();
			Global.logDebugMsg(ex.getMessage());
			bResult = false;
		}

		return bResult;
	}



	/************************** App information methods **************************/
	public static int getSystemVersion(Context appContext)
	{
		int nVersion = 0;

		try
		{
			nVersion = Build.VERSION.SDK_INT;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			nVersion = -1;
		}

		return nVersion;
	}


	public static int getCurAppVersionCode(Context appContext)
	{
		int nVersion = 0;

		try
		{
			PackageInfo pInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
			nVersion = pInfo.versionCode;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			nVersion = -1;
		}

		return nVersion;
	}

	public static String getCurAppVersionName(Context appContext)
	{
		String szPack = "";

		try
		{
			PackageInfo pInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
			szPack = pInfo.versionName;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			szPack = "";
		}

		return szPack;
	}


	public static boolean IsQQInstalled(Context appContext)
	{
		return IsAppInstalled(appContext, "com.tencent.mobileqq");
	}


	public static void openApp(Context context, String pack_name)
	{
		PackageManager pm = context.getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage(pack_name);
		context.startActivity(intent);
	}


	public static boolean IsAppInstalled(Context appContext, String packname)
	{
		return getAppVersionName(appContext, packname) != null;
	}


	public static String getAppVersionName(Context appContext, String packname)
	{
		PackageManager pm = appContext.getPackageManager();
		String versionName = null;

		try
		{
			PackageInfo packageInfo = pm.getPackageInfo(packname, PackageManager.GET_ACTIVITIES);
			versionName = packageInfo.versionName;
		}
		catch (PackageManager.NameNotFoundException ex)
		{
			versionName = null;
		}


		return versionName;
	}

	public static long getFolderSize(File directory)
	{
		long length = 0;

		for (File file : directory.listFiles())
		{
			if (file.isFile())
				length += file.length();
			else
				length += getFolderSize(file);
		}

		return length;
	}

	public static Calendar date2Calendar(Date dtvalue) {
		if (dtvalue == null)
			return null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(dtvalue);

		return cal;
	}

    public static boolean isAppBackgroundMode(Context appContext)
    {
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty())
        {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(appContext.getPackageName()))
                return true;
        }

        return false;
    }

	public static int getLevelIndex(String strLevelName)
	{
		if(strLevelName.equals("全部"))
			return -1;
		else if(strLevelName.equals("国家级"))
			return 0;
		else if(strLevelName.equals("省级"))
			return 1;
		else if(strLevelName.equals("市级"))
			return 2;
		else if(strLevelName.equals("县级"))
			return 3;
		return 0;
	}

	public static int getUserLevelIndex(String strLevelName)
	{
		if(strLevelName.equals("全部"))
			return -1;
		else if(strLevelName.equals("县级"))
			return 1;
		else if(strLevelName.equals("市级"))
			return 2;
		else if(strLevelName.equals("省级"))
			return 3;
		else if(strLevelName.equals("国家级"))
			return 4;
		return -1;
	}

	public static boolean checkIsSameLevel(UserBean userItem)
	{
		UserBean selfInfo = AppCommon.loadUserInfo();
		if(selfInfo.rights_id == 0) {
			if(userItem.rights_id == 0)
				return false;
			if(selfInfo.level != userItem.level)
				return false;
			switch (selfInfo.level) {
				case 1:
					if(selfInfo.xians_id == userItem.xians_id)
						return true;
					break;
				case 2:
					if(selfInfo.shis_id == userItem.shis_id)
						return true;
					break;
				case 3:
					if(selfInfo.shengs_id == userItem.shengs_id)
						return true;
					break;
				case 4:
					return true;
			}
		}
		else {
			if(userItem.rights_id != 0)
				return false;
			if(selfInfo.level == 0)
				return true;
			if(userItem.level > selfInfo.level)
				return false;
			switch (selfInfo.level) {
				case 1:
					if(selfInfo.xians_id == userItem.xians_id)
						return true;
					break;
				case 2:
					if(selfInfo.shis_id == userItem.shis_id)
						return true;
					break;
				case 3:
					if(selfInfo.shengs_id == userItem.shengs_id)
						return true;
					break;
			}
		}

		return false;
	}
}
