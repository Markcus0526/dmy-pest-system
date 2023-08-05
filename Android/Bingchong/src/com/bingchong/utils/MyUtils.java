package com.bingchong.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.bingchong.R;

import java.io.*;
import java.lang.reflect.Method;


// Collection of miscellaneous stuff.
public class MyUtils {
	private final static String  TAG = "MyUtils";
	
	// Get human-readable expression from the binary buffer.
	// ex: 03 76 5F ...
	public static String  buffer2String ( byte[] buffer, int startpos, int len )
	{
		String  res = "";
		
		for ( int i=0; i<len; i++ ) {
			res += String.format("%02X ", buffer[i+startpos]);
			if ( i % 16 == 15 )
				res += "\n";
		}
		
		return res;
	}
	
	// Return value: first match position
	public static int  searchPattern ( byte[] target, byte[] pattern, int startpos, int afterendpos )
	{
		int  targetlen = target.length;
		int  patternlen = pattern.length;
		
		// Input validation
		if ( patternlen > targetlen )
			return -1;
		
		if ( startpos < 0 )
			return -1;
		
		if ( afterendpos < 0 )
			return -1;
		
		if ( afterendpos-startpos < patternlen )
			return -1;
		
		if ( afterendpos > targetlen )
			return -1;
		
		// initialization
		if ( afterendpos == 0 )
			afterendpos = targetlen;
		
		// Search start
		for ( int i=startpos; i<=afterendpos-patternlen; i++ ) {
			int  j = 0;
			for ( ; j<patternlen; j++ ) {
				if ( target[i+j] != pattern[j] ) {
					break;
				}
			}
			
			if ( j == patternlen )
				return i;
		}
		
		return -1;
	}
	
	/**
	 * This method converts dp unit to equivalent pixels, depending on device density. 
	 * 
	 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on device density
	 */
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}

	/**
	 * This method converts device specific pixels to density independent pixels.
	 * 
	 * @param px A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;
	}


	public static void buttonEffect(View button){
	    button.setOnTouchListener(new OnTouchListener() {

	        public boolean onTouch(View v, MotionEvent event) {
	            switch (event.getAction()) {
	                case MotionEvent.ACTION_DOWN: {
	                    v.getBackground().setColorFilter(0xe0f47521,PorterDuff.Mode.SRC_ATOP);
	                    v.invalidate();
	                    break;
	                }
	                case MotionEvent.ACTION_UP: {
	                    v.getBackground().clearColorFilter();
	                    v.invalidate();
	                    break;
	                }
	            }
	            return false;
	        }
	    });
	}
    
    private static Toast gToast = null;
    public static void showMyToast(Context context, String str)
    {
        if (gToast == null || gToast.getView().getWindowVisibility() != View.VISIBLE) {
            gToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            gToast.show();
        }

    }

    public static void showMyToast(Context context, int resID)
    {
        MyUtils.showMyToast(context, context.getString(resID));
    }

    public static void showMyToast ( final Context context, Handler handler, final String str) {
        if ( handler == null ) {
            return;
        }

        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(context, context.getString(R.string.app_name)+": "+str, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void showMyToast ( Context context, Handler handler, int resId ) {
        MyUtils.showMyToast(context, handler, context.getString(resId));
    }

    public static int getBitmapByteCount ( Bitmap my_bitmap ) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        my_bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao);
        byte[] ba = bao.toByteArray();
        int size = ba.length;

        return size;
    }

    public static File makeAppTempDir(Context ctx, String dirName)
    {
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(), dirName);
        else
            cacheDir= ctx.getCacheDir();

        if(!cacheDir.exists())
            cacheDir.mkdirs();

        deleteAllTempFiles(cacheDir);

         return cacheDir;
    }

    public static void deleteAllTempFiles(File cacheDir)
    {
        File[] files = cacheDir.listFiles();
        if(files==null)
            return;

        for(File f:files)
            f.delete();
    }

    public static int writeBitmapToFile(Bitmap bmp, String filePath)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // write to the temporary file
        File  newfile = new File(filePath);
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(newfile));
            out.write(byteArray);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            if ( out != null ) {
                try {
                    out.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        }

        return byteArray.length;
    }

    public static boolean phoneNumberLengthCheck ( String phoneNumber ) {
        if ( phoneNumber == null )
            return false;

        int  len = phoneNumber.length();
        if ( len<7 || len>11 )
            return false;

        return true;
    }

    //For open keyboard
    public static void openKeyBoard(Context mContext){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
    //For close keyboard
    public static void closeKeyBoard(Context mContext){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
    }

    /* --> -------------------------------- Hardware status -------------------------------------- */

    public static boolean isPhoneDevice ( Context context ) {
        TelephonyManager  telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        // FIXME this could be incorrect in the case of Sony Table S.
        // for more information, see http://stackoverflow.com/questions/12001863/how-to-find-out-whether-a-particular-device-has-sim-hardware-support
        if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE)
            return false;

        return true;
    }

    public static boolean hasSIMCard ( Context context ) {
        TelephonyManager  telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int  simState = telephonyManager.getSimState();
        if (simState != TelephonyManager.SIM_STATE_READY)
            return false;
        return true;
    }

    public static boolean isNetworkConnected (Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if ( activeNetwork == null ) {
            return false;
        }

        if ( activeNetwork.isRoaming() ) {
            return false;
        }

        boolean isConnected = activeNetwork.isConnected();

        return isConnected;
    }

    public static boolean is3gEnabled ( Context context ) {
        ConnectivityManager  connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State mobile = connMan.getNetworkInfo(0).getState();

        return ( mobile == NetworkInfo.State.CONNECTED );
    }

    public static boolean isWifiEnabled ( Context context ) {
        ConnectivityManager  connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State wifi = connMan.getNetworkInfo(1).getState();

        return ( wifi == NetworkInfo.State.CONNECTED );
    }

    // stackoverflow.com/questions/8333604
    public static Boolean isMobileDataEnabled ( Context context ) {
        ConnectivityManager  connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            Class<?> c = Class.forName(connMan.getClass().getName());
            Method m = c.getDeclaredMethod("getMobileDataEnabled");
            m.setAccessible(true);

            return (Boolean)(m.invoke(connMan));
        } catch (Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isGpsProviderEnabled ( Context context ) {
        LocationManager  lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        return ( lm.isProviderEnabled(LocationManager.GPS_PROVIDER) );
    }

    public static boolean isNetworkProviderEnabled ( Context context ) {
        LocationManager  lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        return ( lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) );
    }

    public static boolean isProviderEnabled ( Context context, String provider ) {
        if ( provider.equals(LocationManager.GPS_PROVIDER) ) {
            return MyUtils.isGpsProviderEnabled(context);
        } else if ( provider.equals(LocationManager.NETWORK_PROVIDER) ) {
            return MyUtils.isNetworkProviderEnabled(context);
        }

        return false;
    }

    public static boolean isLocationServiceEnabled ( Context context ) {
        return ( MyUtils.isGpsProviderEnabled(context) || MyUtils.isNetworkProviderEnabled(context) );
    }

    /* ------------------------------------ Hardware status ---------------------------------- <-- */



    /* --> --------------------------------- System services ----------------------------------------- */

    public static void sendSMSMessage ( String target, String text ) {
        SmsManager  sm = SmsManager.getDefault();
        sm.sendTextMessage ( target, null, text, null, null );
    }

    // http://stackoverflow.com/questions/7820080/call-forwading-in-android-programmatically
    public static void callPhone ( Context context, String phone ) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.fromParts("tel", phone, "#");
        dialIntent.setData(uri);
        context.startActivity(dialIntent);
    }

    public static void showGpsEnableDialog ( final Context context ) {
        AlertDialog.Builder gsDialog = new AlertDialog.Builder(context);
        gsDialog.setTitle("");
        gsDialog.setMessage("");
        gsDialog.setPositiveButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                context.startActivity(intent);
            }
        });
        gsDialog.setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    public static void showGpsDisableDialog ( final Context context ) {
        AlertDialog.Builder gsDialog = new AlertDialog.Builder(context);
        gsDialog.setTitle("");
        gsDialog.setMessage("");
        gsDialog.setPositiveButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                context.startActivity(intent);
            }
        });
        gsDialog.setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    public static String getValidJsonString(String content)
    {
        try {
            int index = content.indexOf("{");
            if(index > 0)
                content = content.substring(content.indexOf("{"));
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return content;
    }
    /* ------------------------------------- System services ------------------------------------- <-- */
}
