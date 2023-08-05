/**
 * 
 */
package com.bingchong.utils;

import android.util.Log;

/**
 * @author ChungJin.Sim 130427
 * 
 * Add the simple logging function to all classes in my project.
 * 
 * Each class should contain an instance of this class, 
 * and register their own TAG using the setTag() function.
 *
 */
public class MyLog {
	
	private static final String  m_szID = "SIM: ";
    private static final boolean m_bDoLog = true;
    
    public static void d(String tag, String msg) {
		if( m_bDoLog & true )
			Log.d(m_szID+tag, msg);
	}
    
    public static void e(String tag, String msg) {
		if( m_bDoLog & true )
			Log.e(m_szID+tag, msg);
	}
    
    public static void i(String tag, String msg) {
		if( m_bDoLog & true )
			Log.i(m_szID+tag, msg);
	}
    
    public static void v(String tag, String msg) {
    	if ( m_bDoLog & true )
    		Log.v (m_szID+tag, msg);
    }
    
    public static void w(String tag, String msg) {
    	if ( m_bDoLog & true )
    		Log.w (m_szID+tag, msg);
    }
    
    public static void wtf(String tag, String msg) {
    	if ( m_bDoLog & true )
    		Log.wtf (m_szID+tag, msg);
    }
    
}
