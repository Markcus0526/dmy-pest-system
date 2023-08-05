package com.bingchong.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.widget.*;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.bingchong.*;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.activity.db.DBPlaceInfoActivity;
import com.bingchong.activity.users.UserDetailActivity;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.bean.*;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserPlace;
import com.bingchong.parser.ParserXian;
import com.bingchong.utils.ResolutionSet;
import com.bingchong.widget.DateTimePicker;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.bingchong.widget.DateTimePicker.OnDateChangeListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class MovingInfoActivity extends SuperActivity implements
        OnClickListener, OnClickItemAdapterListener, OnItemSelectedListener, OnDateChangeListener {

    private Spinner 			mSpSheng;
    private Spinner 			mSpShi;
    private Spinner 			mSpXian;
    private Spinner 			mSpPointLevel;
    private Spinner 			mSpPointType;
    private DateTimePicker      mDtPicker;
    private ArrayList<XianBean> arrSheng = new ArrayList<XianBean>();
    private ArrayList<XianBean> arrShi = new ArrayList<XianBean>();
    private ArrayList<XianBean> arrXian = new ArrayList<XianBean>();
    private ArrayList<String> arrPointLevel = new ArrayList<String>();
    private ArrayList<String> arrPointType = new ArrayList<String>();
    private ArrayAdapter<XianBean> AXian = null;
    private  ArrayList<PointBean> mPointList = new ArrayList<PointBean>();

    private ArrayList<Marker> arrPointMarker = new ArrayList<Marker>();
    private ArrayList<Marker> arrWatcherMarker = new ArrayList<Marker>();
    private ArrayList<UserTrackBean> mUserTrks = new ArrayList<UserTrackBean>();
    private ArrayList<LatLng> latLngPolygon = new ArrayList<LatLng>();

    public static MovingInfoActivity mInstance = null;
    private ProgressDialog pleaseWaitDialog;
    private int mNowLevel = -1;  // 0 - guojia, 1 - sheng, 2 - shi, 3 - xian
    private int mNowType = -1;   // 0 - guding, 1 - feiguding
    private int mNowShengId = 0;
    private int mNowShiId = 0;
    private int mNowXianId = 0;
    private String mNowSelectedDate = "";

    private MapView mapView = null;
    private BaiduMap baiduMap = null;
    private OverlayOptions option;
    private View mEmptyPopupView;
    private InfoWindow mInfoWindow = null;

    public PointBean selectedPoint = null;
    public UserTrackBean selectedUsrTrk = null;

    public static Context getAppContext()
    {
        return mInstance.getApplicationContext();
    }

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
		super.onCreate(savedInstanceState, R.layout.moving_info);
	}


    private void initSpinerPosition(int level)
    {
        if(level == 0) {
            ArrayAdapter<XianBean> adapterSheng = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
            adapterSheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpSheng.setAdapter(adapterSheng);
            mSpSheng.setSelection(0);
        }
        else if(level == 1) {
            ArrayAdapter<XianBean> adapterShi = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrShi);
            adapterShi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpShi.setAdapter(adapterShi);
            mSpShi.setSelection(0);
        }
        else if(level == 2) {
            ArrayAdapter<XianBean> adapterXian = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrXian);
            adapterXian.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpXian.setAdapter(adapterXian);
            mSpXian.setSelection(0);
        }
    }

    private void refreshMap()
    {
        baiduMap.clear();
        mPointList.clear();
        arrPointMarker.clear();
        mUserTrks.clear();
        arrWatcherMarker.clear();

        startProgress();
        CommManager.getPoints(AppCommon.loadUserID(), mNowShengId, mNowShiId, mNowXianId, "", mNowType, mNowLevel, mNowSelectedDate, get_points_handler);
        CommManager.getWatcherTracks(mNowShengId, mNowShiId, mNowXianId, mNowSelectedDate, get_user_track_handler);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)parent;

        if(spinner.getId() == R.id.spinner_type){
            mNowType = position - 1;
            refreshMap();
        }
        else if(spinner.getId() == R.id.spinner_level){
            mNowLevel = Global.getLevelIndex(arrPointLevel.get(position));
            refreshMap();
        }
        else if(spinner.getId() == R.id.spinner_sheng) {
            mNowShengId = arrSheng.get(position).id;
            if(AppCommon.loadUserInfo().level == 0 ||
                    AppCommon.loadUserInfo().level == 4 ||
                    AppCommon.loadUserInfo().level == 3) {
                startProgress();
                CommManager.getShis(mNowShengId, get_shis_handler);
            }
        }
        else if(spinner.getId() == R.id.spinner_shi) {
            if(AppCommon.loadUserInfo().level == 0 ||
                    AppCommon.loadUserInfo().level == 4 ||
                    AppCommon.loadUserInfo().level == 3 ||
                    AppCommon.loadUserInfo().level == 2) {
                mNowShiId = arrShi.get(position).id;
                startProgress();
                CommManager.getXians(mNowShiId, get_xians_handler);
            }
        }
        else if (spinner.getId() == R.id.spinner_xian){
            mNowXianId = arrXian.get(position).id;
            refreshMap();
        }

        ResolutionSet.instance.iterateChild(view, mScrSize.x, mScrSize.y);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void OnClickListener(int index) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id)
		{

		}

	}

    @Override
    protected void onDestroy()
    {
        //mMapView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //mMapView.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void initializeActivity() {
        initBaidu();

        // init spinner
        mSpSheng = (Spinner) findViewById(R.id.spinner_sheng);
        mSpSheng.setOnItemSelectedListener(this);
        mSpShi = (Spinner) findViewById(R.id.spinner_shi);
        mSpShi.setOnItemSelectedListener(this);
        mSpXian = (Spinner) findViewById(R.id.spinner_xian);
        mSpXian.setOnItemSelectedListener(this);
        mSpPointLevel = (Spinner) findViewById(R.id.spinner_level);
        mSpPointLevel.setOnItemSelectedListener(this);
        mSpPointType = (Spinner) findViewById(R.id.spinner_type);
        mSpPointType.setOnItemSelectedListener(this);

        arrPointType.add("全部");
        arrPointType.add("固定");
        arrPointType.add("非固定");
        ArrayAdapter<String> adapterPointType = new ArrayAdapter<String>(this, R.layout.item_spinner_white, arrPointType);
        adapterPointType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpPointType.setAdapter(adapterPointType);
        mSpPointType.setSelection(0);

        mDtPicker = (DateTimePicker) findViewById(R.id.edt_date);
        mDtPicker.setOnChangeListener(this);
        mNowSelectedDate = mDtPicker.getDateString();

        pleaseWaitDialog = new ProgressDialog(MovingInfoActivity.this);
        pleaseWaitDialog.setIndeterminate(true);
        pleaseWaitDialog.setMessage(this.getString(R.string.strLoading));
        pleaseWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });

        rightInit();
    }

    private void rightInit()
    {
        UserBean userInfo = AppCommon.loadUserInfo();
        XianBean shengItem = new XianBean();
        XianBean shiItem = new XianBean();
        XianBean xianItem = new XianBean();

        ArrayAdapter<XianBean> adapterSheng = null;
        ArrayAdapter<XianBean> adapterShi = null;
        ArrayAdapter<XianBean> adapterXian = null;

        arrPointLevel.add("全部");
        arrPointLevel.add("国家级");

        switch (userInfo.level)
        {
            case 0: //超管
                arrPointLevel.add("省级");
                arrPointLevel.add("市级");
                arrPointLevel.add("县级");
                startProgress();
                CommManager.getShengs(get_shengs_handler);
                break;
            case 1: //县级
                arrPointLevel.add("县级");
                mNowShengId = (int)userInfo.shengs_id;
                mNowShiId = (int)userInfo.shis_id;
                mNowXianId = (int)userInfo.xians_id;

                shengItem.name = userInfo.sheng;
                shengItem.id = (int)userInfo.shengs_id;
                arrSheng.add(shengItem);
                shiItem.name = userInfo.shi;
                shiItem.id = (int)userInfo.shis_id;
                arrShi.add(shiItem);
                xianItem.name = userInfo.xian;
                xianItem.id = (int)userInfo.xians_id;
                arrXian.add(xianItem);

                mSpSheng.setEnabled(false);
                mSpShi.setEnabled(false);
                mSpXian.setEnabled(false);

                adapterSheng = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
                adapterSheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpSheng.setAdapter(adapterSheng);
                mSpSheng.setSelection(0);

                adapterShi = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrShi);
                adapterShi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpShi.setAdapter(adapterShi);
                mSpShi.setSelection(0);

                adapterXian = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrXian);
                adapterXian.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpXian.setAdapter(adapterXian);
                mSpXian.setSelection(0);
                break;
            case 2: //市级
                arrPointLevel.add("市级");
                mNowShengId = (int)userInfo.shengs_id;
                mNowShiId = (int)userInfo.shis_id;

                shengItem.name = userInfo.sheng;
                shengItem.id = (int)userInfo.shengs_id;
                arrSheng.add(shengItem);
                shiItem.name = userInfo.shi;
                shiItem.id = (int)userInfo.shis_id;
                arrShi.add(shiItem);

                mSpSheng.setEnabled(false);
                mSpShi.setEnabled(false);

                adapterSheng = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
                adapterSheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpSheng.setAdapter(adapterSheng);
                mSpSheng.setSelection(0);

                adapterShi = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrShi);
                adapterShi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpShi.setAdapter(adapterShi);
                mSpShi.setSelection(0);

                startProgress();
                CommManager.getXians(mNowShiId, get_xians_handler);
                break;
            case 3: //省级
                arrPointLevel.add("省级");
                mNowShengId = (int)userInfo.shengs_id;

                shengItem.name = userInfo.sheng;
                shengItem.id = (int)userInfo.shengs_id;
                arrSheng.add(shengItem);

                mSpSheng.setEnabled(false);

                adapterSheng = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
                adapterSheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpSheng.setAdapter(adapterSheng);
                mSpSheng.setSelection(0);

                startProgress();
                CommManager.getXians(mNowShiId, get_xians_handler);
                break;
            case 4: //国家级
                arrPointLevel.add("省级");
                arrPointLevel.add("市级");
                arrPointLevel.add("县级");
                startProgress();
                CommManager.getShengs(get_shengs_handler);
                break;
        }

        ArrayAdapter<String> adapterPointLevel = new ArrayAdapter<String>(this, R.layout.item_spinner_white, arrPointLevel);
        adapterPointLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpPointLevel.setAdapter(adapterPointLevel);
        mSpPointLevel.setSelection(0);
    }

    private void initBaidu()
    {
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.showScaleControl(true);
        mapView.showZoomControls(true);

        baiduMap = mapView.getMap();
        mEmptyPopupView = LayoutInflater.from(this).inflate(R.layout.empty_pop_view, null);
        ResolutionSet.instance.iterateChild(mEmptyPopupView, mScrSize.x, mScrSize.y);

        LatLng point = new LatLng(43.676283, 122.235642);
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
        baiduMap.animateMapStatus(update);

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                int index = 0;
                selectedPoint = null;
                selectedUsrTrk = null;
                for (index = 0; index < arrPointMarker.size(); index++) {
                    Marker markerItem = arrPointMarker.get(index);
                    if (marker == markerItem) {
                        selectedPoint = mPointList.get(index);
                        break;
                    }
                }

                for(index = 0; index < arrWatcherMarker.size(); index++) {
                    Marker markerItem = arrWatcherMarker.get(index);
                    if(marker == markerItem) {
                        selectedUsrTrk = mUserTrks.get(index);
                        break;
                    }
                }

                if (selectedPoint != null) {
                    final RelativeLayout rlTips = (RelativeLayout) mEmptyPopupView.findViewById(R.id.rlTips);
                    final TextView popText = (TextView) mEmptyPopupView.findViewById(R.id.location_tips);
                    LatLng ptMark = new LatLng(selectedPoint.latitude, selectedPoint.longitude);
                    popText.setText(selectedPoint.name);
                    rlTips.setVisibility(View.VISIBLE);
                    mEmptyPopupView.invalidate();
                    //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
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
                else if (selectedUsrTrk != null) {
                    final RelativeLayout rlTips = (RelativeLayout) mEmptyPopupView.findViewById(R.id.rlTips);
                    final TextView popText = (TextView) mEmptyPopupView.findViewById(R.id.location_tips);
                    LatLng ptMark = new LatLng(selectedUsrTrk.latitude, selectedUsrTrk.longitude);
                    popText.setText(selectedUsrTrk.name);
                    rlTips.setVisibility(View.VISIBLE);
                    mEmptyPopupView.invalidate();
                    //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromBitmap(getBitmapFromView(mEmptyPopupView)),
                            ptMark, 0, new InfoWindow.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick() {
                            baiduMap.hideInfoWindow();
                            rlTips.setVisibility(View.GONE);
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(selectedUsrTrk.userid));
                            pushNewActivityAnimated(UserDetailActivity.class, bundle);
                        }
                    });
                    //显示InfoWindow
                    baiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                baiduMap.hideInfoWindow();
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

    public static Bitmap getBitmapFromView(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    public void onDateChanged() {
        mNowSelectedDate = mDtPicker.getDateString();

        refreshMap();
    }

    private AsyncHttpResponseHandler get_shengs_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    arrSheng.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        XianBean obj = ParserXian.parseJsonResponse((JSONObject) retdata.get(i));
                        if(DataMgr.m_curUser.rights_id == UserBean.RIGHT_XIAN_MGR &&
                                DataMgr.m_curUser.xians_id != 0 &&
                                obj.id != DataMgr.m_curUser.xians_id)
                            continue;
                        arrSheng.add(obj);
                    }

                    initSpinerPosition(0);
                }
                else {
                    AppCommon.showToast(MovingInfoActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(MovingInfoActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(MovingInfoActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler get_shis_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    arrShi.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        XianBean obj = ParserXian.parseJsonResponse((JSONObject) retdata.get(i));
                        if(DataMgr.m_curUser.rights_id == UserBean.RIGHT_XIAN_MGR &&
                                DataMgr.m_curUser.xians_id != 0 &&
                                obj.id != DataMgr.m_curUser.xians_id)
                            continue;
                        arrShi.add(obj);
                    }

                    initSpinerPosition(1);
                }
                else {
                    AppCommon.showToast(MovingInfoActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(MovingInfoActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(MovingInfoActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    AsyncHttpResponseHandler get_xians_handler = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {

            stopProgress();
            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.

            try
            {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    arrXian.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        XianBean obj = ParserXian.parseJsonResponse((JSONObject) retdata.get(i));
                        if(DataMgr.m_curUser.rights_id == UserBean.RIGHT_XIAN_MGR &&
                                DataMgr.m_curUser.xians_id != 0 &&
                                obj.id != DataMgr.m_curUser.xians_id)
                            continue;
                        arrXian.add(obj);
                    }

                    initSpinerPosition(2);
                }
                else {
                    AppCommon.showToast(MovingInfoActivity.this, stRetMsg);
                }
            }
            catch (Exception ex)
            { ex.printStackTrace(); }

        }

        @Override
        public void onFailure(Throwable error, String content) {
            stopProgress();
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
        }
    };

    AsyncHttpResponseHandler get_points_handler = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {
            stopProgress();
            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.

            try
            {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);

                    for (int i = 0; i < retdata.length(); i++) {
                        PointBean obj = ParserPlace.parseJsonResponse((JSONObject) retdata.get(i));
                        if(obj != null) {
                            mPointList.add(obj);

                            LatLng point = new LatLng(obj.latitude, obj.longitude);
                            //构建Marker图标
                            View pointView = null;
                            if(obj.type == 0) {
                                if(obj.task_count < 0)
                                    pointView = LayoutInflater.from(MovingInfoActivity.this).inflate(R.layout.place_mark_view, null);
                                else if(obj.task_count == 0)
                                    pointView = LayoutInflater.from(MovingInfoActivity.this).inflate(R.layout.place_completed_mark_view, null);
                                else {
                                    pointView = LayoutInflater.from(MovingInfoActivity.this).inflate(R.layout.place_uncompleted_mark_view, null);
                                    TextView txtTaskCount = (TextView)pointView.findViewById(R.id.txtCount);
                                    txtTaskCount.setText(obj.task_count + "");
                                }

                            }
                            else {
                                if(obj.task_count < 0)
                                    pointView = LayoutInflater.from(MovingInfoActivity.this).inflate(R.layout.place_moving_mark_view, null);
                                else if(obj.task_count == 0)
                                    pointView = LayoutInflater.from(MovingInfoActivity.this).inflate(R.layout.place_moving_completed_mark_view, null);
                                else {
                                    pointView = LayoutInflater.from(MovingInfoActivity.this).inflate(R.layout.place_moving_uncompleted_mark_view, null);
                                    TextView txtTaskCount = (TextView)pointView.findViewById(R.id.txtCount);
                                    txtTaskCount.setText(obj.task_count + "");
                                }
                            }
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
                }
                else {
                    AppCommon.showToast(MovingInfoActivity.this, stRetMsg);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                AppCommon.showDebugToast(MovingInfoActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            pleaseWaitDialog.dismiss();
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
        }
    };

    AsyncHttpResponseHandler get_user_track_handler = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {

            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.
            stopProgress();

            try
            {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        UserTrackBean obj = parseGetUserTrackResponse((JSONObject) retdata.get(i));

                        if(obj.tracks.size() <= 0)
                            continue;

                        for(UserTrackBean.TrackPos item : obj.tracks) {
                            if(item.latitude <= 0 || item.longitude <= 0)
                                continue;
                            latLngPolygon.add(new LatLng(item.latitude, item.longitude));
                        }

                        if(obj.latitude <= 0 || obj.longitude <= 0)
                            continue;

                        OverlayOptions polylineOption = new PolylineOptions()
                                .width(5)
                                .color(Color.BLUE)
                                .points(latLngPolygon);
                        baiduMap.addOverlay(polylineOption);

                        LatLng point = new LatLng(obj.latitude, obj.longitude);
                        //构建Marker图标
                        View pointView = LayoutInflater.from(MovingInfoActivity.this).inflate(R.layout.pop_view, null);
                        ResolutionSet.instance.iterateChild(pointView, mScrSize.x, mScrSize.y);
                        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(pointView);
                        //构建MarkerOption，用于在地图上添加Marker
                        option = new MarkerOptions()
                                .position(point)
                                .zIndex(1)
                                .icon(bitmap);

                        mUserTrks.add(obj);
                        arrWatcherMarker.add((Marker) (baiduMap.addOverlay(option)));
                    }

                }
                else {
                    AppCommon.showToast(MovingInfoActivity.this, stRetMsg);
                }
            }
            catch (Exception ex)
            { ex.printStackTrace(); }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            pleaseWaitDialog.dismiss();
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
        }
    };

    private static UserTrackBean parseGetUserTrackResponse(JSONObject jsonObject) throws JSONException{
        try {
            UserTrackBean result = new UserTrackBean();
            result.userid = jsonObject.getInt("userid");
            result.name = jsonObject.getString("username");

            // parse all track pos
            JSONArray trackPos = jsonObject.getJSONArray("tracks");
            for (int i = 0; i < trackPos.length(); i++)
            {
                // parse one track pos
                JSONObject jsonOnePos = trackPos.getJSONObject(i);
                UserTrackBean.TrackPos oneTrk = new UserTrackBean.TrackPos();
                oneTrk.latitude = jsonOnePos.getDouble("latitude");
                oneTrk.longitude = jsonOnePos.getDouble("longitude");
                oneTrk.date = jsonOnePos.getString("date");
                // add track data
                result.tracks.add(oneTrk);

                result.latitude = oneTrk.latitude;
                result.longitude = oneTrk.longitude;
            }

            return result;

        }
        catch (JSONException ex) {
        }
        return null;
    }
}