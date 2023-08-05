package com.bingchong.activity.task;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.bingchong.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import com.bingchong.adapter.ReportItemAdapter;
import com.bingchong.backend.ServiceBaiduMap;
import com.bingchong.bean.*;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.utils.ResolutionSet;
import org.json.JSONObject;

import java.util.ArrayList;


public class TaskFixDetailActivity extends SuperActivity implements OnClickListener{

	private WebView mPointInfoView;
    private WebView mBlightInfoView;
    private String page_type = "";
	private int detail_id = 0;
	private int point_id = 0;
    private int blight_id = 0;
    private int form_id = 0;
	private String date;

    private ScrollView scView;
    private ReportItemAdapter mReportAdapter;
    private LinearLayout reportLayoutList = null;
    private ReportBean mReportBean = null;
    private BlightBean mBlight;
    private FormBean mForm = null;
    private PointBean mPoint = null;

    private LinearLayout mLayoutReportInfo = null;
    private LinearLayout mLayoutReportTable = null;
    private ImageView imgPointType = null;
    private TextView txtPointNo = null;
    private TextView txtTable = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.task_fix_detail);
	}
	
    public void initWebView()
    {
        mPointInfoView =(WebView)findViewById(R.id.wvPointInfo);
        mBlightInfoView =(WebView)findViewById(R.id.wv_info);

        mPointInfoView.setBackgroundColor(this.getResources().getColor(R.color.white));
    	WebSettings mWebSettings01;
    	mWebSettings01 = mPointInfoView.getSettings();
        mWebSettings01.setAppCacheMaxSize(524288);
        mWebSettings01.setAppCacheEnabled(true);
        mPointInfoView.getSettings().setBuiltInZoomControls(true);
        mPointInfoView.getSettings().setSupportZoom(true);
        mPointInfoView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        mPointInfoView.getSettings().setDefaultTextEncodingName("utf-8");

        mBlightInfoView.setBackgroundColor(this.getResources().getColor(R.color.white));
        WebSettings mWebSettings02;
        mWebSettings02 = mBlightInfoView.getSettings();
        mWebSettings02.setAppCacheMaxSize(524288);
        mWebSettings02.setAppCacheEnabled(true);
        mBlightInfoView.getSettings().setBuiltInZoomControls(true);
        mBlightInfoView.getSettings().setSupportZoom(true);
        mBlightInfoView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        mBlightInfoView.getSettings().setDefaultTextEncodingName("utf-8");
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
            case R.id.btnSave:
                onSave();
                break;
            case R.id.btnRefresh:
                onRefresh();
                break;
            case R.id.btnReport:
                onReport();
                break;
            case R.id.btnTable:
                showReportTable();
                break;
            case R.id.btnInfo:
                showReportInfo();
                break;
		}
	}

    private void showReportTable(){
        mLayoutReportInfo.setVisibility(View.GONE);
        mLayoutReportTable.setVisibility(View.VISIBLE);
    }

    private void showReportInfo(){
        mLayoutReportInfo.setVisibility(View.VISIBLE);
        mLayoutReportTable.setVisibility(View.GONE);
    }

    private void onReport(){

        if(mReportBean.point_id <= 0) {
            AppCommon.showToast(TaskFixDetailActivity.this, "您未到目标测报点");
            return;
        }

        startProgress();

        ArrayList<FieldBean> fields = mReportBean.reportForm.getFields();
        String strFields = "";
        for(int i = 0; i < fields.size(); i++)
        {
            FieldBean fi = fields.get(i);
            if(fi.fieldType == FieldBean.FIELD_PARENT)
                continue;

            strFields = (strFields + fi.id + ":" + fi.val);
            if(i < fields.size() - 1)
                strFields += ",";
        }

        CommManager.setReports(AppCommon.loadUserID(),
                mReportBean.form_id,
                mReportBean.point_id,
                mReportBean.blight_id,
                "",
                Global.getDate(mReportBean.testDate, "yyyy-MM-dd HH:mm"),
                strFields,
                set_report_handler);
    }

    private void onSave()
    {
        mReportBean.testDate = System.currentTimeMillis();
        mReportBean.reportDate = System.currentTimeMillis();
        mReportBean.longitude = ServiceBaiduMap.mLong;
        mReportBean.latitude = ServiceBaiduMap.mLat;

        ArrayList<ReportBean> tmpArrReports = new ArrayList<ReportBean>();
        tmpArrReports.add(mReportBean);
        DataMgr.updateReportsToDB(tmpArrReports);

        AppCommon.showToast(TaskFixDetailActivity.this, "保存成功");
    }

    private void onRefresh()
    {
        try{
            if (mReportBean.reportForm != null) {
                for(FieldBean obj : mReportBean.reportForm.getFields()) {
                    obj.SetValue("");
                }
                mReportAdapter.setData(mReportBean.reportForm.getFields(), reportLayoutList);
            }
            mReportAdapter.setData(mReportBean.reportForm.getFields(), reportLayoutList);
            scView.requestLayout();
            ResolutionSet.instance.iterateChild(scView, mScrSize.x, mScrSize.y);
        }
        catch(Exception ex){
            Log.println(0, "REPORT error", ex.getMessage());
        }
    }

    @Override
    public void initializeActivity() {
        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.titleTaskFixDetail));


        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        try{
            if (bundle != null) {
                String str = intent.getStringExtra(Constant.EXTRA_PARAM_INDEX);
                detail_id = Integer.parseInt(str);
                str = intent.getStringExtra(Constant.EXTRA_PARAM_POINT_ID);
                point_id = Integer.parseInt(str);
                str = intent.getStringExtra(Constant.EXTRA_PARAM_BLIGHT_ID);
                blight_id = Integer.parseInt(str);
                mBlight = DataMgr.getBlightFromDB(blight_id);
                str = intent.getStringExtra(Constant.EXTRA_PARAM_FORM_ID);
                form_id = Integer.parseInt(str);
                date = intent.getStringExtra(Constant.EXTRA_PARAM_DATE);
            }
        }
        catch(Exception ex){}

        initWebView();

        if(mBlight == null) {
            AppCommon.showToast(TaskFixDetailActivity.this, "您还未同步病虫害及上报表格数据!");
            return;
        }

        ArrayList<PointBean> arrPoints = DataMgr.getPointsFromDB();
        if(arrPoints == null || arrPoints.size() <= 0) {
            AppCommon.showToast(TaskFixDetailActivity.this, "您还未同步测报点数据!");
            return;
        }
        for(PointBean pointItem : arrPoints) {
            if(pointItem.id == point_id) {
                mPoint = pointItem;
                break;
            }
        }
        if (mPoint == null) {
            AppCommon.showToast(TaskFixDetailActivity.this, "请同步最新的测报点数据一下！");
            return;
        }

        mReportBean = new ReportBean();
        mReportBean.blight_id = blight_id;
        mReportBean.blightName = mBlight.name;
        mReportBean.blight_type = mBlight.kind;
        mReportBean.form_id = form_id;
        mReportBean.point_id = point_id;
        mReportBean.user_id = (int)AppCommon.loadUserID();
        mReportBean.testDate = System.currentTimeMillis();
        mReportBean.reportDate = System.currentTimeMillis();
        mReportBean.blight_id = blight_id;

        imgPointType = (ImageView)findViewById(R.id.imgPointType);
        if(mPoint.type == 0) {
            imgPointType.setImageDrawable(getResources().getDrawable(R.drawable.place_mark));
        }
        else {
            imgPointType.setImageDrawable(getResources().getDrawable(R.drawable.place_moving_mark));
        }

        txtPointNo = (TextView) findViewById(R.id.txtPointNo);
        if(mPoint.type == 0) {
            double diff_long = Math.abs(mPoint.longitude - ServiceBaiduMap.mLong);
            double diff_lat = Math.abs(mPoint.latitude - ServiceBaiduMap.mLat);
            if (diff_long < 0.013 && diff_lat < 0.013) {
                txtPointNo.setText(mPoint.toString());
            } else {
                txtPointNo.setText("您未到目标测报点!");
                mReportBean.point_id = -1;
            }
        }
        else {
            txtPointNo = (TextView) findViewById(R.id.txtPointNo);
            txtPointNo.setText(mPoint.toString());
        }

        ArrayList<FormBean> arrForms  = DataMgr.getFormsFromDB(-1);
        if(arrForms == null || arrForms.size() <= 0) {
            AppCommon.showToast(TaskFixDetailActivity.this, "您还未同步病虫害、上报表格数据!");
            return;
        }

        for(FormBean formItem : arrForms) {
            if(formItem.id == form_id) {
                mForm = formItem;
                mReportBean.reportForm = formItem;
                mReportBean.form_name = formItem.name;
                break;
            }
        }

        if(mForm == null) {
            AppCommon.showToast(TaskFixDetailActivity.this, "请同步最新的上报表格数据一下！");
            return;
        }

        txtTable = (TextView)findViewById(R.id.txtTable);
        txtTable.setText(mForm.name);

        scView = (ScrollView)findViewById(R.id.scroll_view);
        reportLayoutList = (LinearLayout)findViewById(R.id.layout_list);
        mReportAdapter = new ReportItemAdapter(this);
        mReportAdapter.setData(mReportBean.reportForm.getFields(), reportLayoutList);
        scView.requestLayout();
        ResolutionSet.instance.iterateChild(scView, mScrSize.x, mScrSize.y);

        findViewById(R.id.btnTable).setOnClickListener(this);
        findViewById(R.id.btnInfo).setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnRefresh).setOnClickListener(this);
        findViewById(R.id.btnReport).setOnClickListener(this);

        mLayoutReportInfo = (LinearLayout)findViewById(R.id.layoutBlightInfo);
        mLayoutReportTable = (LinearLayout)findViewById(R.id.layoutReport);
        showReportTable();

        startProgress();
        CommManager.getPointInfo(AppCommon.loadUserID(), point_id, get_pointinfo_handler);
        CommManager.getBlightInfo(AppCommon.loadUserID(), blight_id, get_blightinfo_handler);
    }

    private AsyncHttpResponseHandler get_pointinfo_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    JSONObject retdata = result.getJSONObject(ConstMgr.gRetData);
                    String htmlContent = retdata.getString("content");
                    mPointInfoView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
                }
                else {
                    AppCommon.showToast(TaskFixDetailActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(TaskFixDetailActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(TaskFixDetailActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler get_blightinfo_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    JSONObject retdata = result.getJSONObject(ConstMgr.gRetData);
                    String htmlContent = retdata.getString("content");
                    mBlightInfoView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
                }
                else {
                    AppCommon.showToast(TaskFixDetailActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(TaskFixDetailActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(TaskFixDetailActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler set_report_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    AppCommon.showToast(TaskFixDetailActivity.this, "上报成功");
                    DataMgr.removeReportFromDB(mReportBean.id);
                    finish();
                }
                else {
                    AppCommon.showToast(TaskFixDetailActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(TaskFixDetailActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(TaskFixDetailActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}