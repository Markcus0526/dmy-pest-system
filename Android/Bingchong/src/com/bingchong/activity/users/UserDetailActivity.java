package com.bingchong.activity.users;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.bingchong.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.bingchong.activity.video.VideoMainActivity;
import com.bingchong.net.AsyncHttpResponseHandler;
import org.json.JSONObject;

public class UserDetailActivity extends SuperActivity implements OnClickListener{

	private WebView mView;
	private long mIndex = -1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.users_detail);
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
    
    public void onVideoChating(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PackageManager pm = getPackageManager();
        Intent launch = pm.getLaunchIntentForPackage(Constant.VIDEO_APP_NAME);
        String strClass = launch.getComponent().getClassName();
        intent.setComponent(new ComponentName(Constant.VIDEO_APP_NAME, strClass));
        startActivity(intent);
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_videochat:
            pushNewActivityAnimated(VideoMainActivity.class);
            break;
		}
	}

    @Override
    public void initializeActivity() {
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = intent.getStringExtra(Constant.EXTRA_PARAM_INDEX);
            mIndex = Long.parseLong(str);
        }

        findViewById(R.id.btn_videochat).setOnClickListener(this);

        initview();

        startProgress();
        CommManager.getUserInfo(mIndex, get_userinfo_handler);
    }

    private AsyncHttpResponseHandler get_userinfo_handler = new AsyncHttpResponseHandler() {
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
                    AppCommon.showToast(UserDetailActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(UserDetailActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(UserDetailActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}