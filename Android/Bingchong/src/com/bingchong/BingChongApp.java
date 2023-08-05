package com.bingchong;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.baidu.mapapi.*;

import java.util.ArrayList;

import com.bingchong.backend.ServiceBaiduMap;
import com.bingchong.net.WebService;


/**
 * Created with IntelliJ IDEA.
 * User: COSMOS
 * Date: 8/17/13
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class BingChongApp extends Application
{
    public static Context mAppContext;

    private final static String  BUNDLE_KEY_STORE_INFO = "StoreInfo";
    private final static String  BUNDLE_KEY_ACCOUNT_INFO = "AccountInfo";
    private final static String  BUNDLE_KEY_PRODUCT_INFO = "ProductInfo";
    private final static String  BUNDLE_KEY_USERID = "UserId";
    private final static String  BUNDLE_KEY_USERID_CHANGED = "UserIdChanged";

    public static Context attendActivity = null;

    public static int m_nUserID;

    public static int m_nSignStatus = 0;
    public static boolean  m_bSignStatusPending = false;
    public final static int  SIGN_STATUS_NOT_ABLE = 0;
    public final static int  SIGN_STATUS_IN_ABLE = 1;
    public final static int  SIGN_STATUS_OUT_ABLE = 2;

    public static WebService m_WebService = new WebService();
    
    @Override
    public void onCreate() {

        //start service
        if ( ServiceBaiduMap.m_instance == null )
            startService(new Intent(this, ServiceBaiduMap.class));

        mAppContext = getApplicationContext();

        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static void saveSession ( Bundle outState ) {        
        outState.putInt(BUNDLE_KEY_USERID, m_nUserID);
    }

    public static void loadSession ( Bundle inState ) {        
        m_nUserID = inState.getInt(BUNDLE_KEY_USERID);

        if ( m_WebService == null )
        	m_WebService = new WebService();
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
