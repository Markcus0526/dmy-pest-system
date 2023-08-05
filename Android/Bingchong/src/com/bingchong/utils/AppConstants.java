package com.bingchong.utils;

import java.io.File;

import android.os.Environment;

/**
 * This Class have all the Required Constant Variables.
 */
public class AppConstants {

    /****************** Baidu information ******************/
//	public static BMapManager gMapManager = null;
    public static String gBaiduKey = "0840FBEE3CF1DA22265009B31BDDF0BE4E31D01C";
    /*************************************************************************************/
    
	public static final String SERVER_BUSY = "-1";
	public static final String NO_INTERNET = "-2";

	public static File SD_ROOT = Environment.getExternalStorageDirectory();
	public static String IMAGE_DIR = "/Sketch/";
	public static String IMAGE_DIR_FTP = "bingchong";

}
