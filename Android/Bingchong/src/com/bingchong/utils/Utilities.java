package com.bingchong.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.bingchong.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;


public class Utilities{
	
	/**
	 * This will print the any string in Console/Logcat .
	 * See below for @params.  
	 */
	private static String tempDir = "";
	public static String	getTempDirectory()
	{
		return tempDir;
	}
	public static void	setTempPath(String path)
	{
		tempDir = path;
		File dir= new File(path);
		if(!dir.exists())
			dir.mkdirs();		
	}
	
	public static String saveHtmlFileData(String contents){
		String path = getTempDirectory() + "/" + "info.html";
		try{
			FileOutputStream fos = new FileOutputStream(path);
			byte buf[] = contents.getBytes();
			fos.write(buf);
			fos.close();
		}
		catch(Exception ex)
		{
			
		}
		
		return path;
	}
	
	private static DecimalFormat timerFormat = new DecimalFormat("00"); 
	
	private static boolean alertRet = false;
	
	public static void printStatement(String classname,String message) {
	     System.out.println(classname+"----"+message);
	}
	
	/**
	 * This will show Toast/Alert ( Duration- Long ) for the User with any Specific Message .
	 * See below for @params.  
	 */
	public static void showAppToast(Context ctx, String message){
		Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * This will show Toast/Alert ( Duration- Long ) for the User with any Specific Message .
	 * See below for @params.  
	 */
	public static void showAppToast(Context ctx, int messageId){
		Toast.makeText(ctx, messageId, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * This will show Dialog/Pop Alert for the User with any Specific Message .
	 * See below for @params.  
	 */
	public static void showAppAlert(Context ctx, String title, int messageId){
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		if(title.length()>0){
			alertDialogBuilder.setTitle(title);
		}	
		// set dialog message
		alertDialogBuilder.setMessage(messageId)
				.setCancelable(false)
				.setNegativeButton(ctx.getText(R.string.btn_ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	/**
	 * This will show Dialog/Pop Alert to the User When GPS is Disable with any Specific Message .
	 * See below for @params.  
	 */
	public static boolean showGPSAlert(final Context ctx, CharSequence charSequence, int messageId){
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		if(charSequence.length()>0){
			alertDialogBuilder.setTitle(charSequence);
		}
		
		alertRet = false;
		
		// set dialog message
		alertDialogBuilder.setMessage(messageId)
				.setCancelable(false)
				.setPositiveButton(ctx.getText(R.string.btn_ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						ctx.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
						alertRet = true;
					}
				})
				.setNegativeButton(ctx.getText(R.string.btn_exit), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return alertRet;
	}
	
	
	public static long getUTCtimetick(long time) {
		Calendar c = Calendar.getInstance();
		TimeZone z = c.getTimeZone();
		int offset = z.getRawOffset();
		if(z.inDaylightTime(new Date())){
			offset = offset + z.getDSTSavings();
		}
		return time + offset;
	}
	
	public static String getStringFromTick(long tick)
	{
		String str = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tick);
 		int mY = calendar.get(Calendar.YEAR);
		int mM = calendar.get(Calendar.MONTH) + 1;
		int mD = calendar.get(Calendar.DATE);
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);
        int mS = calendar.get(Calendar.SECOND);
        str += mY + "-" + mM + "-" + mD + " " +  
        		mHour + ":" + timerFormat.format(mMinute) 
        		+ ":"+ timerFormat.format(mS);
        return str;
	}


}
