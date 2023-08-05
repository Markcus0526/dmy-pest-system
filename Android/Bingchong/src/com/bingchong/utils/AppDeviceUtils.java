package com.bingchong.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


/**
 * This Class is a Utility for getting Configurations of Android Device and
 * etc..
 * 
 */

public class AppDeviceUtils {

	private static final String CLASS_NAME = "AppDeviceUtils";

	/**
	 * This Function return the Android DeviceID in String format. Always to add
	 * following Permission in Manifest.
	 * 
	 * Requires Permission: READ_PHONE_STATE
	 * 
	 * See below for @params.
	 */

	public static String getDeviceID(Context ctx) {

		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String iemi = telephonyManager.getDeviceId();
		return iemi;
	}

	/**
	 * This Function check the GPS enable/disable Status. Remember to add
	 * required Permission in Manifest.
	 * 
	 * See below for @params.
	 */

	public static boolean isGPSEnable(Context ctx) {
		LocationManager locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static boolean isNetworkEnable(Context ctx) {

		LocationManager locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	/**
	 * This Function convert the DP to Pixel.
	 * 
	 * See below for @params.
	 */

	public static int convertDPtoPX(Context ctx, int dp) {

		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) ctx).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		float logicalDensity = metrics.density;
		int px = (int) (dp * logicalDensity + 0.5);
		System.out.println("DP to Pixel-->" + dp + " = " + px);
		return px;
	}

	/**
	 * This Function return the Android Device Display Width.
	 * 
	 * See below for @params.
	 */

	public static int getMyDispalyWidth(Context ctx) {

		int Width = ((Activity) ctx).getWindowManager().getDefaultDisplay()
				.getWidth();
		return Width;
	}

	/**
	 * This Function return the Android Device Display Height.
	 * 
	 * See below for @params.
	 */

	public static int getMyDispalyHight(Context ctx) {

		int Hight = ((Activity) ctx).getWindowManager().getDefaultDisplay()
				.getHeight();
		return Hight;
	}

	/**
	 * This Function check SD Card is mounted to Android Device (return
	 * true/false).
	 */

	public static boolean isSdPresent() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * This Function check Internet is Available or Not in the Android Device (
	 * return true/false).
	 * 
	 * * Requires Permission: android.permission.ACCESS_NETWORK_STATE See below
	 * for @params.
	 */

	public static boolean isOnline(Context context) {
		if(context == null)
			return true;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static Boolean SaveToSDCard(Context cxt, Bitmap bitmap,
			String FileName) {

		File pictureFile = new File(AppConstants.SD_ROOT,
				AppConstants.IMAGE_DIR + FileName);
		byte[] pictureBytes;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			bitmap.compress(CompressFormat.JPEG, 100, bos);
			pictureBytes = bos.toByteArray();
			FileOutputStream fs = new FileOutputStream(pictureFile);
			fs.write(pictureBytes);
			fs.flush();
			fs.close();
			System.out.println("Image Saved : " + FileName);
			return true;
		} catch (Exception er) {
			System.out.println(" Error while Saving  : (" + FileName + ") "
					+ er.getMessage());
			return false;
		}
	}

	/**
	 * This Function provide the Last Known Location of the Android Device.
	 * 
	 * Requires Permission: android.permission.ACCESS_COARSE_LOCATION Requires
	 * Permission: android.permission.ACCESS_FINE_LOCATION See below for
	 * 
	 * @params.
	 */

	public static Location getUserLastKnownLocation(Context context) {

		Location mLocation = null;
		try {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			String locationProvider = locationManager.getBestProvider(criteria,
					true);
			mLocation = locationManager.getLastKnownLocation(locationProvider);
			if (mLocation != null) {
				Utilities.printStatement(CLASS_NAME,
						"Last Location Provider = " + mLocation.getProvider());
			}
		} catch (Exception e) {
			mLocation = null;
		}

		return mLocation;
	}

}
