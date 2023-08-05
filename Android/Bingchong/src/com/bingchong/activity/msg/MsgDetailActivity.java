package com.bingchong.activity.msg;

import com.bingchong.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import com.bingchong.net.AsyncHttpResponseHandler;
import org.json.JSONObject;

public class MsgDetailActivity extends SuperActivity implements OnClickListener{

	private WebView mView;
	private int mIndex = -1;

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
        txtTitle.setText(getString(R.string.titleInfoMessage));


        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = intent.getStringExtra(Constant.EXTRA_PARAM_INDEX);
            mIndex = Integer.parseInt(str);
        }

        initview();

        startProgress();
        CommManager.getNoticeInfo(mIndex, get_noticeinfo_handler);
    }

    private AsyncHttpResponseHandler get_noticeinfo_handler = new AsyncHttpResponseHandler() {
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
                    AppCommon.showToast(MsgDetailActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(MsgDetailActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(MsgDetailActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}