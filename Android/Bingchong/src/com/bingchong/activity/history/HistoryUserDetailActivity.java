package com.bingchong.activity.history;

import com.bingchong.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import com.bingchong.activity.HomeMgrActivity;
import com.bingchong.activity.HomeUserActivity;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import org.json.JSONObject;


public class HistoryUserDetailActivity extends SuperActivity implements OnClickListener{

	private WebView mView;
    private String page_type = "";
	private int detail_id = 0;
	private int point_id = 0;
    private int report_id = 0;
	private String date;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.info);
	}
	
    public void initview()
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

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		}
	}

    @Override
    public void initializeActivity() {
        TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.titleTaskFixDetail));


        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        boolean check_reports = true;
        try{
            if (bundle != null) {
                page_type = intent.getStringExtra(Constant.EXTRA_PARAM_TYPE);
                if(page_type.equals(getString(R.string.history_user_type_notcompleted))) {
                    txtTitle.setText("未完成成工作详情");
                    check_reports = false;
                }
                else if(page_type.equals(getString(R.string.history_user_type_completed))) {
                    txtTitle.setText("历史工作完成情况");
                }
                else if(page_type.equals(getString(R.string.history_user_type_waiting))) {
                    txtTitle.setText("待审核工作详情");
                }
                String str = intent.getStringExtra(Constant.EXTRA_PARAM_INDEX);
                detail_id = Integer.parseInt(str);
                str = intent.getStringExtra(Constant.EXTRA_PARAM_POINT_ID);
                point_id = Integer.parseInt(str);
                str = intent.getStringExtra(Constant.EXTRA_PARAM_REPORT_ID);
                report_id = Integer.parseInt(str);
                date = intent.getStringExtra(Constant.EXTRA_PARAM_DATE);
            }
        }
        catch(Exception ex){}

        initview();

        startProgress();
        CommManager.getTaskInfo(detail_id, point_id, date, check_reports, report_id, get_taskinfo_handler);
    }

    private AsyncHttpResponseHandler get_taskinfo_handler = new AsyncHttpResponseHandler() {
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
                    AppCommon.showToast(HistoryUserDetailActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(HistoryUserDetailActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(HistoryUserDetailActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}