package com.bingchong.backend;

import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.mapapi.map.*;
import com.bingchong.*;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.utils.AppPreferences;
import com.bingchong.utils.MyUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import org.json.JSONObject;

public class ServiceBaiduMap extends Service {

	private static final int  SEND_LOCATION_INTERVAL_TEST = 5000; // 5 seconds
	private static final int  SEND_LOCATION_INTERVAL = 1000*300; // 2 minutes
	private static String  m_curLocationProvider = LocationManager.GPS_PROVIDER;

	private static final String  TAG = "ServiceBaiduMap";

	public static double  mLat = 0.0, mLong = 0.0;

    private static Handler  m_toastHandler = null;

    public static ServiceBaiduMap m_instance = null;

    public static final boolean  TEST_MODE = false;
    private static Timer  m_timerLocSender = null;

    private AppPreferences appPreferences;

    private LocationClient bm_loc_client;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void onCreate() {
        super.onCreate();
        
        m_instance = this;

        appPreferences = new AppPreferences(getApplicationContext());

        //实例化定位服务，LocationClient类必须在主线程中声明
        bm_loc_client = new LocationClient(ServiceBaiduMap.this);
        bm_loc_client.registerLocationListener(new BDLocationListenerImpl());//注册定位监听接口

        /**
         * LocationClientOption 该类用来设置定位SDK的定位方式。
         */
        LocationClientOption option = new LocationClientOption();
        bm_loc_client.setLocOption(option);  //设置定位参数

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(3000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        option.setOpenGps(true);

        bm_loc_client.start();  // 调用此方法开始定位

        startSendLocation();
    }

    public class BDLocationListenerImpl implements BDLocationListener {

        /**
         * 接收异步返回的定位结果，参数是BDLocation类型参数
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || location.getAddrStr() == null) {
                return;
            }
            mLong = location.getLongitude();
            mLat = location.getLatitude();
        }
    }

    public void startSendLocation () {
        if ( m_timerLocSender != null )
            return;

        m_timerLocSender = new Timer();
        if ( TEST_MODE == true ) 
        {
            m_timerLocSender.scheduleAtFixedRate(new SendLocationTask(), 0, SEND_LOCATION_INTERVAL_TEST);
        } else 
        {
            m_timerLocSender.scheduleAtFixedRate(new SendLocationTask(), 0, SEND_LOCATION_INTERVAL);
        }
    }

	//tells activity to run on ui thread
    class SendLocationTask extends TimerTask {

        @Override
        public void run() {
            if ( MyUtils.isGpsProviderEnabled(ServiceBaiduMap.this)==false ) {
                //MyUtils.showGpsEnableDialog(ServiceBaiduMap.this);
                if ( MyUtils.isNetworkProviderEnabled(ServiceBaiduMap.this) == false ) {
                    MyUtils.showMyToast(ServiceBaiduMap.this, m_toastHandler, R.string.no_location_service_found);
                    return;
                }
            } else {
                if ( m_curLocationProvider.equals(LocationManager.GPS_PROVIDER) == false ) {
                    MyUtils.showMyToast(ServiceBaiduMap.this, m_toastHandler, R.string.gps_now_enabled);
                }
            }

            if ( MyUtils.isLocationServiceEnabled(ServiceBaiduMap.this) ) {
                Date now = new Date();
                if ( MyUtils.isNetworkConnected(ServiceBaiduMap.this)
                        && mLat > 1 && mLong > 1
                        && appPreferences.GetUserId() > 0
                        && appPreferences.IsAdmin() == false
                        && now.getHours() >= 0 && now.getHours() <= 24 ) {
                    CommManager.uploadWatcherTrack(appPreferences.GetUserId(), mLong + "", mLat + "", upload_watchertrack_handler);
                }
            }
        }
    };

    AsyncHttpResponseHandler upload_watchertrack_handler = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {

            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    //AppCommon.showToast(ServiceBaiduMap.this, "位置上传成功 (" + mLong + "," + mLat + ")");
                }
                else{
                    //AppCommon.showToast(ServiceBaiduMap.this, "位置上传失败 (" + mLong + "," + mLat + ")");
                }
            }
            catch (Exception ex)
            {
                //AppCommon.showToast(ServiceBaiduMap.this, "位置上传失败:" + ex.toString());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
            //AppCommon.showToast(ServiceBaiduMap.this, "位置上传失败:" + content);
        }
    };
}
