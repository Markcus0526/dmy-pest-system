package com.bingchong.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.bingchong.*;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.activity.db.DBPlaceInfoActivity;
import com.bingchong.activity.setting.SettingOwnerActivity;
import com.bingchong.activity.users.UserDetailActivity;
import com.bingchong.bean.PointBean;
import com.bingchong.db.DataMgr;
import com.bingchong.utils.AppConstants;
import com.bingchong.utils.ResolutionSet;
import com.bingchong.utils.Utilities;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MovingInfoUserActivity extends SuperActivity implements OnClickListener {

    private  ArrayList<PointBean> mPointList = new ArrayList<PointBean>();
    private  ArrayList<PointBean> mOverlayPointList = new ArrayList<PointBean>();

    private MapView mapView = null;
    private BaiduMap baiduMap = null;
    private LocationClient bm_loc_client;
    private MyLocationData mLocData;
    private InfoWindow mInfoWindow = null;

    private OverlayOptions option;
    private Marker mainMarker = null;
    private ArrayList<Marker> arrPointMarker = new ArrayList<Marker>();
    private View mWatcherPopupView;
    private View mEmptyPopupView;
    private BDLocation location;
    //private TextView popText;
    private LatLng point;
    private boolean isInit = false;

    public PointBean selectedPoint = null;

    /*
    private OverlayContainer mPointOverlays = null;
    private OverlayContainer mPointMovingOverlays = null;
    */

    public static MovingInfoActivity mInstance = null;
    private ProgressDialog pleaseWaitDialog;

    private ImageButton locationButton;

    public static Context getAppContext()
    {
        return mInstance.getApplicationContext();
    }

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());

		super.onCreate(savedInstanceState, R.layout.moving_info_user);
	}

    private void refreshMap()
    {
        mPointList = DataMgr.getPointsFromDB();
        arrPointMarker.clear();
        for (int i = 0; i < mPointList.size(); i++) {
            PointBean obj = mPointList.get(i);

            LatLng point = new LatLng(obj.latitude, obj.longitude);
            //构建Marker图标
            //ImageView mainLocation = (ImageView)mPopupView.findViewById(R.id.main_location);
            View pointView = LayoutInflater.from(this).inflate(R.layout.place_mark_view, null);
            if(obj.type == 1)
                pointView = LayoutInflater.from(this).inflate(R.layout.place_moving_mark_view, null);
            ResolutionSet.instance.iterateChild(pointView, mScrSize.x, mScrSize.y);
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(pointView);
            //构建MarkerOption，用于在地图上添加Marker
            option = new MarkerOptions()
                    .position(point)
                    .zIndex(1)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            //将marker添加到地图上
            arrPointMarker.add((Marker) (baiduMap.addOverlay(option)));
        }
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id)
		{

		}
	}

    @Override
    protected void onResume() {
        bm_loc_client.start();
        //initSearchManager();
        mapView.onResume();
        super.onResume();	//To change body of overridden methods use File | Settings | File Templates.
    }


    @Override
    protected void onPause()
    {
        //Log.d("erik_debug_passMian_onPause", ""+Global.isLoggedIn(this.getApplicationContext()));
        bm_loc_client.stop();
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        bm_loc_client.stop();
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void initializeActivity() {
        initBaidu();

        pleaseWaitDialog = new ProgressDialog(MovingInfoUserActivity.this);
        pleaseWaitDialog.setIndeterminate(true);
        pleaseWaitDialog.setMessage(this.getString(R.string.strLoading));
        pleaseWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });

        refreshMap();
    }


    private void initBaidu()
    {
        locationButton = (ImageButton)findViewById(R.id.locate_btn);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bm_loc_client.start();
//				mMapController.setZoom(15);
                baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
            }
        });

        mapView = (MapView)findViewById(R.id.mapview);
        mapView.showScaleControl(true);
        mapView.showZoomControls(true);

        baiduMap = mapView.getMap();
        mWatcherPopupView = LayoutInflater.from(this).inflate(R.layout.pop_view, null);
        ResolutionSet.instance.iterateChild(mWatcherPopupView, mScrSize.x, mScrSize.y);
        mEmptyPopupView = LayoutInflater.from(this).inflate(R.layout.empty_pop_view, null);
        ResolutionSet.instance.iterateChild(mEmptyPopupView, mScrSize.x, mScrSize.y);
        //popText = (TextView)mPopupView.findViewById(R.id.location_tips);

        //自动定位
        autoLocate();

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker == mainMarker) {
                    final RelativeLayout rlTips = (RelativeLayout) mWatcherPopupView.findViewById(R.id.rlTips);
                    final TextView popText = (TextView) mWatcherPopupView.findViewById(R.id.location_tips);
                    popText.setText(AppCommon.loadUserInfo().name);
                    rlTips.setVisibility(View.VISIBLE);
                    mWatcherPopupView.invalidate();
                    //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                    mInfoWindow = null;
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromBitmap(getBitmapFromView(mWatcherPopupView)),
                            point, 0, new InfoWindow.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick() {
                            baiduMap.hideInfoWindow();
                            rlTips.setVisibility(View.GONE);
                            pushNewActivityAnimated(SettingOwnerActivity.class, null);
                        }
                    });
                    //显示InfoWindow
                    baiduMap.showInfoWindow(mInfoWindow);

                } else {
                    int index = 0;
                    selectedPoint = null;
                    for (index = 0; index < arrPointMarker.size(); index++) {
                        Marker markerItem = arrPointMarker.get(index);
                        if (marker == markerItem) {
                            selectedPoint = mPointList.get(index);
                            break;
                        }
                    }

                    if (selectedPoint != null) {
                        final RelativeLayout rlTips = (RelativeLayout) mEmptyPopupView.findViewById(R.id.rlTips);
                        final TextView popText = (TextView) mEmptyPopupView.findViewById(R.id.location_tips);
                        LatLng ptMark = new LatLng(selectedPoint.latitude, selectedPoint.longitude);
                        double distance = Global.calcDistanceKM(point.latitude, point.longitude,
                                selectedPoint.latitude, selectedPoint.longitude);
                        if(distance > 1)
                            popText.setText(selectedPoint.name + "\n 距离：" + distance + "公里");
                        else
                            popText.setText(selectedPoint.name + "\n 距离：" + distance * 1000 + "米");
                        rlTips.setVisibility(View.VISIBLE);
                        mEmptyPopupView.invalidate();
                        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                        mInfoWindow = null;
                        mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromBitmap(getBitmapFromView(mEmptyPopupView)),
                                ptMark, 0, new InfoWindow.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick() {
                                baiduMap.hideInfoWindow();
                                rlTips.setVisibility(View.GONE);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(selectedPoint.id));
                                pushNewActivityAnimated(DBPlaceInfoActivity.class, bundle);
                            }
                        });
                        //显示InfoWindow
                        baiduMap.showInfoWindow(mInfoWindow);
                    }
                }
                return true;
            }
        });

        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            public void onMapStatusChangeStart(MapStatus status) {
                baiduMap.clear();

                try {
                    if(mainMarker != null) {
                        final RelativeLayout rlTips = (RelativeLayout) mWatcherPopupView.findViewById(R.id.rlTips);
                        rlTips.setVisibility(View.GONE);
                        mWatcherPopupView.invalidate();
                        mainMarker.remove();
                    }

                    point = new LatLng(MovingInfoUserActivity.this.location.getLatitude(), MovingInfoUserActivity.this.location.getLongitude());
                    //构建Marker图标
                    //ImageView mainLocation = (ImageView)mPopupView.findViewById(R.id.main_location);
                    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(getBitmapFromView(mWatcherPopupView));
                    //构建MarkerOption，用于在地图上添加Marker
                    option = new MarkerOptions()
                            .position(point)
                            .zIndex(5)
                            .icon(bitmap);
                    //在地图上添加Marker，并显示
                    //将marker添加到地图上
                    mainMarker = (Marker) (baiduMap.addOverlay(option));
                    refreshMap();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void onMapStatusChangeFinish(MapStatus status) {

            }

            public void onMapStatusChange(MapStatus status) {

            }
        });

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                baiduMap.hideInfoWindow();
                final RelativeLayout rlTipsWatcher= (RelativeLayout) mWatcherPopupView.findViewById(R.id.rlTips);
                rlTipsWatcher.setVisibility(View.VISIBLE);
                mWatcherPopupView.invalidate();
                final RelativeLayout rlTipsEmpty = (RelativeLayout) mEmptyPopupView.findViewById(R.id.rlTips);
                rlTipsEmpty.setVisibility(View.VISIBLE);
                mEmptyPopupView.invalidate();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
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

            MovingInfoUserActivity.this.location = location;

            mLocData = new MyLocationData.Builder().latitude(location.getLatitude()).longitude(location.getLongitude()).accuracy(location.getRadius()).direction(location.getDirection()).build();
            baiduMap.setMyLocationData(mLocData);
            if (!isInit) {
                MapStatus status = new MapStatus.Builder().zoom(16).target(new LatLng(location.getLatitude(), location.getLongitude())).build();
                baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(status));
                isInit = true;
            }
            //move to location position
            LatLng point = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
            baiduMap.animateMapStatus(update);
            //showPopupOverlay(location);
            bm_loc_client.stop();
        }
    }

    /**
     * 显示弹出窗口图层PopupOverlay
     * @param location
     */
    private void showPopupOverlay(BDLocation location){
        //定义User Maker坐标点
        if(mainMarker != null) {
            final RelativeLayout rlTips = (RelativeLayout) mWatcherPopupView.findViewById(R.id.rlTips);
            rlTips.setVisibility(View.GONE);
            mWatcherPopupView.invalidate();
            mainMarker.remove();
        }

        point = new LatLng(location.getLatitude(),
                location.getLongitude());
        //构建Marker图标
        //ImageView mainLocation = (ImageView)mPopupView.findViewById(R.id.main_location);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(getBitmapFromView(mWatcherPopupView));
        //构建MarkerOption，用于在地图上添加Marker
        option = new MarkerOptions()
                .position(point)
                .zIndex(5)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        //将marker添加到地图上
        mainMarker = (Marker) (baiduMap.addOverlay(option));
        //binder listeners

    }

    /**
     * 将View转换成Bitmap的方法
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    private void autoLocate() {
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
        //实例化定位服务，LocationClient类必须在主线程中声明
        bm_loc_client = new LocationClient(MovingInfoUserActivity.this);
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
    }
}