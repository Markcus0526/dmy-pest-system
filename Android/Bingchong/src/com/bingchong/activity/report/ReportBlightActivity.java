package com.bingchong.activity.report;

import java.io.File;
import java.util.ArrayList;

import com.bingchong.*;
import com.bingchong.adapter.ReportItemAdapter;
import com.bingchong.backend.ServiceBaiduMap;
import com.bingchong.bean.*;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.utils.ResolutionSet;
import com.bingchong.utils.Utilities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import org.json.JSONObject;

public class ReportBlightActivity extends SuperActivity implements OnClickListener, OnItemSelectedListener{

	public static File imageDirectory;

	//private ListView mListView;
	private ReportItemAdapter mAdapter;
	private BlightBean	mBlight = null;

	private Spinner mSpPointType;
	private Spinner mSpPlace;
	private Spinner mSpReport;
	private TextView txtTable;
	private ArrayList<String> arrPointType = new ArrayList<String>();
	private ArrayList<PointBean>	arrPoints = new ArrayList<PointBean>();
	private ArrayList<FormBean> 	arrForms = new ArrayList<FormBean>();

	// for common
	private LinearLayout mLayoutInfo = null;
	private LinearLayout mLayoutTable = null;

	private int mNowType = -1;   // 0 - guding, 1 - feiguding
	private ReportBean	mCurReportBean = null;
	private LinearLayout layoutList = null;
	
	private ScrollView	scView;


	private static final int	REQ_REPORT = 1;
    private int select_point_index = -1;

	private WebView mView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.report_blight_detail);
	}

	public void initWebView()
	{
		mView=(WebView)findViewById(R.id.wv_info);

		mView.setBackgroundColor(this.getResources().getColor(R.color.white));
		WebSettings mWebSettings01;
		mWebSettings01 = mView.getSettings();

		mView.getSettings().setBuiltInZoomControls(true);  
		mView.getSettings().setSupportZoom(true); 
		mView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        mView.getSettings().setDefaultTextEncodingName("utf-8");
		mWebSettings01.setAppCacheMaxSize(524288);
		mWebSettings01.setAppCacheEnabled(true);

        startProgress();
        CommManager.getBlightInfo(AppCommon.loadUserID(), mBlight.id, get_blightinfo_handler);
	}

	private void onRefresh()
	{
		try{
			if(mCurReportBean == null)
			{
				mCurReportBean = new ReportBean();
				mCurReportBean.testDate = System.currentTimeMillis();
				mCurReportBean.reportDate = System.currentTimeMillis();
				mCurReportBean.blight_id = mBlight.id;
			}
			else {
				for(FieldBean obj : mCurReportBean.reportForm.getFields()) {
					obj.SetValue("");
				}
			}

			initReportField();
		}
		catch(Exception ex){
			Log.println(0, "REPORT error", ex.getMessage());
		}
	}

	private void onSave()
	{
		if(mCurReportBean == null)
			return;
		if(arrPoints.size() == 0)
			return;
		if(mSpPlace.getSelectedItemPosition() < 0)
			return;
		mCurReportBean.blight_id = mBlight.id;
		mCurReportBean.blightName = mBlight.name;
		mCurReportBean.blight_type = mBlight.kind;
		mCurReportBean.form_id = mCurReportBean.reportForm.id;
		mCurReportBean.form_name = mCurReportBean.reportForm.name;
		mCurReportBean.point_id = arrPoints.get(mSpPlace.getSelectedItemPosition()).id;
		mCurReportBean.user_id = (int)DataMgr.m_curUser.id;
		mCurReportBean.testDate = System.currentTimeMillis();
		mCurReportBean.reportDate = System.currentTimeMillis();
        mCurReportBean.longitude = ServiceBaiduMap.mLong;
        mCurReportBean.latitude = ServiceBaiduMap.mLat;

		ArrayList<ReportBean> tmpArrReports = new ArrayList<ReportBean>();
		tmpArrReports.add(mCurReportBean);
		DataMgr.updateReportsToDB(tmpArrReports);

		AppCommon.showToast(ReportBlightActivity.this, "保存成功");
	}
	
	private void onReport(){
		Boolean enableReport = true;
		if(arrPoints.size() == 0 || mCurReportBean.point_id <= 0 || mSpPlace.getSelectedItemPosition() < 0){
			Utilities.showAppToast(this, R.string.strTipNoPoint);
			enableReport = false;
		}
		if(mCurReportBean.form_id <= 0){
			Utilities.showAppToast(this, R.string.strTipNoForm);
			enableReport = false;
		}
		
		if(!enableReport)
			return;

        startProgress();

		ArrayList<FieldBean> fields = mCurReportBean.reportForm.getFields();
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
				mCurReportBean.form_id,
				mCurReportBean.point_id,
				mCurReportBean.blight_id,
				"",
				Global.getDate(mCurReportBean.testDate, "yyyy-MM-dd HH:mm"),
				strFields,
				set_report_handler);
	}

	private void initForms()
	{
		ArrayAdapter<FormBean> AA = new ArrayAdapter<FormBean>(this, R.layout.item_spinner, arrForms);
		AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpReport.setAdapter(AA);
		mSpReport.setSelection(0);				
	}		

	private void initUI() {
		initForms();
		initReportField();
	}

	private void showTable(){
		mLayoutInfo.setVisibility(View.GONE);
		mLayoutTable.setVisibility(View.VISIBLE);
	}

	private void showInfo(){
		mLayoutInfo.setVisibility(View.VISIBLE);
		mLayoutTable.setVisibility(View.GONE);
	}

	private void initReportField()
	{
		FormBean frm = null;
		int nType = mSpReport.getSelectedItemPosition();
		if(nType < arrForms.size() && nType >= 0)
		{
			frm = arrForms.get(nType);
		}
		else
			return;

		if(frm == null)
			return;

		txtTable.setText(frm.name);

		mCurReportBean.reportForm = frm;
		mCurReportBean.form_id = frm.id;

		mAdapter.setData(mCurReportBean.reportForm.getFields(), layoutList);
		scView.requestLayout();
		ResolutionSet.instance.iterateChild(scView, mScrSize.x, mScrSize.y);
	}

	private void initPointSelection()
	{
		arrPoints.clear();

		ArrayList<PointBean> allPoints = DataMgr.getPointsFromDB();
		if(allPoints == null || allPoints.size() <= 0) {
			AppCommon.showToast(ReportBlightActivity.this, "选择不了测报点，请您同步最新的测报点后关注一下您分管的测报点！");
			return;
		}

		if(mNowType == 0) {	// 固定测报点
			double nowLongitude = 0, nowLatitude = 0;
			nowLongitude = ServiceBaiduMap.mLong;
			nowLatitude = ServiceBaiduMap.mLat;
			for(int i = 0; i < allPoints.size(); i++) {
				PointBean pointItem = allPoints.get(i);
				if(!pointItem.isCheck) continue;
				if(pointItem.type == 1) continue;
				double diff_long = Math.abs(pointItem.longitude - nowLongitude);
				double diff_lat = Math.abs(pointItem.latitude - nowLatitude);
				if(diff_long < 0.013 && diff_lat < 0.013)
				{
					arrPoints.add(pointItem);
					//break;
				}
			}

			if(arrPoints == null || arrPoints.size() <= 0) {
				AppCommon.showToast(ReportBlightActivity.this, "定位不了测报点，您未到关注测报点范围内！");
			}
		}
		else { // 非固定测报点
			for(PointBean obj : allPoints) {
				if(obj.type == 1 && obj.isCheck)
					arrPoints.add(obj);
			}
		}

		ArrayAdapter<PointBean> AA = new ArrayAdapter<PointBean>(this, R.layout.item_spinner, arrPoints);
		AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpPlace.setAdapter(AA);
		if(arrPoints.size() > 0)
			mSpPlace.setSelection(0);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {

		case R.id.btnReport:
			//onSave();
			onReport();
			break;				
			
		case R.id.btnTable:
			showTable();
			break;				
		case R.id.btnInfo:
			showInfo();
			break;				
		case R.id.btnSave:
			onSave();
			break;				
		case R.id.btnRefresh:
			onRefresh();
			break;				
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.spinnerPointType){
			mNowType = position;
			initPointSelection();
		}
		else if (parent.getId() == R.id.spinnerPlace){
			mCurReportBean.point_id = arrPoints.get(position).id;
		}
		else if (parent.getId() == R.id.spinnerTable){
			initReportField();
		}

        ResolutionSet.instance.iterateChild(view, mScrSize.x, mScrSize.y);
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

    @Override
    public void initializeActivity() {
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = intent.getStringExtra(Constant.EXTRA_PARAM_INDEX);
            int index = Integer.parseInt(str);
            mBlight = DataMgr.getBlightFromDB(index);
        }

        mLayoutInfo = (LinearLayout)findViewById(R.id.layoutBlightInfo);
        mLayoutTable = (LinearLayout)findViewById(R.id.layoutReport);
        txtTable = (TextView)findViewById(R.id.txtTable);

        TextView titleView = (TextView)findViewById(R.id.txtTitle);
        if(mBlight.isFly())
            titleView.setText(R.string.blight_fly);
        else
            titleView.setText(R.string.blight_disease);

        scView = (ScrollView)findViewById(R.id.scrollView);

        // button
        findViewById(R.id.btnReport).setOnClickListener(this);
        findViewById(R.id.btnTable).setOnClickListener(this);
        findViewById(R.id.btnInfo).setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnRefresh).setOnClickListener(this);

        // spinner
        // init spinner

		mSpPointType = (Spinner) findViewById(R.id.spinnerPointType);
		mSpPointType.setOnItemSelectedListener(this);

        mSpPlace = (Spinner) findViewById(R.id.spinnerPlace);
        mSpPlace.setOnItemSelectedListener(this);
        //mSpPlace.setEnabled(false);
        //mSpPlace.setClickable(false);
        mSpReport = (Spinner) findViewById(R.id.spinnerTable);
        mSpReport.setOnItemSelectedListener(this);

        //mListView = (ListView) findViewById(R.id.list_data);
        layoutList = (LinearLayout)findViewById(R.id.layout_list);

        //mListView.setAdapter(mAdapter);

        // for capture image
        String packageName = getApplicationContext().getPackageName();

        imageDirectory = new File(
                android.os.Environment.getExternalStorageDirectory(),
                "Android/data/" + packageName);

		arrPointType.add("固定");
		arrPointType.add("非固定");
		ArrayAdapter<String> adapterPointType = new ArrayAdapter<String>(this, R.layout.item_spinner_white, arrPointType);
		adapterPointType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpPointType.setAdapter(adapterPointType);
		mSpPointType.setSelection(0);

        initPointSelection();

        arrForms  = DataMgr.getFormsFromDB(mBlight.id);

        initWebView();

		mAdapter = new ReportItemAdapter(this);
        initForms();
        onRefresh();

        showTable();
    }

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
                    mView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
                }
                else {
                    AppCommon.showToast(ReportBlightActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(ReportBlightActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(ReportBlightActivity.this, R.string.STR_CONN_ERROR);
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
					AppCommon.showToast(ReportBlightActivity.this, "上报成功");
					DataMgr.removeReportFromDB(mCurReportBean.id);
					finish();
                }
                else {
                    AppCommon.showToast(ReportBlightActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(ReportBlightActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(ReportBlightActivity.this, R.string.STR_CONN_ERROR);
        }
    };

}