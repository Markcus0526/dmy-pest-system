package com.bingchong.activity.review;

import java.util.ArrayList;

import android.content.Intent;
import android.widget.RelativeLayout;
import com.bingchong.*;
import com.bingchong.activity.HomeMgrActivity;
import com.bingchong.activity.HomeUserActivity;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleAcceptAdapter;
import com.bingchong.bean.BlightBean;
import com.bingchong.db.DataMgr;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserBlight;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReviewBlightActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener{

	// for list view
	private ListView mListView;
	private SingleAcceptAdapter mAdapter;
	private ArrayList<BlightBean> mList = new ArrayList<BlightBean>();
	private ArrayList<BlightBean> mAllList = new ArrayList<BlightBean>();
	private String mSearchKey = "";
	
	// for detail view
	private WebView mView;
	
	// for common
	private RelativeLayout mLayoutDetail = null;
	private LinearLayout mLayoutList   = null;
	public RadioGroup radioGroup = null;
	public BlightBean m_curObj = null;
	public boolean	m_bShowCompleteTask = false;	
	
	public boolean isReqUpdate = false;	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.review_blight);
	}
	
    public void initWebView()
    {
    	mView=(WebView)findViewById(R.id.wv_info);
    	
    	mView.setBackgroundColor(this.getResources().getColor(R.color.white));
    	WebSettings mWebSettings01;
    	mWebSettings01 = mView.getSettings();
    	
    	mView.getSettings().setBuiltInZoomControls(true);  
    	mView.getSettings().setSupportZoom(true); 
    	mView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        mView.getSettings().setDefaultTextEncodingName("utf-8");
    	mWebSettings01.setAppCacheMaxSize(524288);
    	mWebSettings01.setAppCacheEnabled(true);
    }		

	private void initUI() {
		mList.clear();
		
		for(int i = 0; i < mAllList.size(); i++)
		{
			BlightBean obj = mAllList.get(i);
			if(m_bShowCompleteTask)
			{
				if(obj.status != Constant.REVIEW_WAITING)
					mList.add(obj);
			}
			else{
				if(obj.status == Constant.REVIEW_WAITING)
					mList.add(obj);
			}
		}
		
		if(m_bShowCompleteTask){
			findViewById(R.id.btnAccept).setVisibility(View.GONE);
			findViewById(R.id.btnNoAccept).setVisibility(View.GONE);
		}
		else{
			findViewById(R.id.btnAccept).setVisibility(View.VISIBLE);
			findViewById(R.id.btnNoAccept).setVisibility(View.VISIBLE);
		}		
		
		if (mList != null) {
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	private void showList(boolean reload){
		mLayoutDetail.setVisibility(View.GONE);
		mLayoutList.setVisibility(View.VISIBLE);
		if(reload) {
            startProgress();
            CommManager.updateTempBlight(AppCommon.loadUserID(), m_curObj.id, m_curObj.status, update_tempblight_handler);
        }
		else {
			startProgress();
			CommManager.getTempBlights(AppCommon.loadUserID(), 0, "", get_tempblight_handler);
        }
	}
	
	private void showDetail(){
		mLayoutDetail.setVisibility(View.VISIBLE);
		mLayoutList.setVisibility(View.GONE);

        startProgress();
        CommManager.getTempBlightInfo(m_curObj.id, get_tempblightinfo_handler);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id)
		{
		case R.id.btnComplete:
			m_bShowCompleteTask = true;
			showList(false);
			break;
		case R.id.btnNoComplete:
			m_bShowCompleteTask = false;
			showList(false);
			break;
		case R.id.btnAccept:
			m_curObj.status = Constant.REVIEW_ACCEPT;
			isReqUpdate = true;
			showList(true);
			break;
		case R.id.btnNoAccept:
			m_curObj.status = Constant.REVIEW_NOACCEPT;
			isReqUpdate = true;
			showList(true);
			break;
		case R.id.btnBack:
			showList(false);
			break;
		}
	}
	
	@Override
	public void OnClickListener(int index)
	{
		m_curObj = mList.get(index);
		showDetail();
	}

    @Override
    public void initializeActivity() {
        // init common
        radioGroup = (RadioGroup) this.findViewById(R.id.main_radio_group);
        findViewById(R.id.btnComplete).setOnClickListener(this);
        findViewById(R.id.btnNoComplete).setOnClickListener(this);
        findViewById(R.id.btnAccept).setOnClickListener(this);
        findViewById(R.id.btnNoAccept).setOnClickListener(this);
        mLayoutDetail = (RelativeLayout)findViewById(R.id.task_detail_layout);
        mLayoutList = (LinearLayout)findViewById(R.id.list_layout);

        // init list view
        mListView = (ListView) findViewById(R.id.list_data);
        mAdapter = new SingleAcceptAdapter(this, DataMgr.m_curUser.isAdmin());
        mListView.setAdapter(mAdapter);
        mAdapter.setOnListener(this);

        initWebView();

        showList(false);
    }

    private AsyncHttpResponseHandler get_tempblight_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    mAllList.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        BlightBean obj = ParserBlight.parseJsonResponse(retdata.getJSONObject(i));
                        if(obj != null)
                            mAllList.add(obj);
                    }
                    initUI();
                }
                else {
                    AppCommon.showToast(ReviewBlightActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(ReviewBlightActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(ReviewBlightActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler get_tempblightinfo_handler = new AsyncHttpResponseHandler() {
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
                    AppCommon.showToast(ReviewBlightActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(ReviewBlightActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(ReviewBlightActivity.this, R.string.STR_CONN_ERROR);
        }
    };

	private AsyncHttpResponseHandler update_tempblight_handler = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			super.onSuccess(content);
			stopProgress();

			try {
				JSONObject result = new JSONObject(content);

				int nRetCode = result.getInt(ConstMgr.gRetCode);
				String stRetMsg = result.getString(ConstMgr.gRetMsg);
				if (nRetCode == ConstMgr.SVC_ERR_NONE) {
					AppCommon.showToast(ReviewBlightActivity.this, "操作成功");
					initUI();
				}
				else {
					AppCommon.showToast(ReviewBlightActivity.this, stRetMsg);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				AppCommon.showDebugToast(ReviewBlightActivity.this, ex.getMessage());
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			super.onFailure(error, content);
			stopProgress();

			AppCommon.showToast(ReviewBlightActivity.this, R.string.STR_CONN_ERROR);
		}
	};
}