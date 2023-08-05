package com.bingchong.activity.db;

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
import android.util.Base64;
import org.json.JSONObject;


public class DBBlightInfoActivity extends SuperActivity implements OnClickListener{

	private WebView mView;
	private int mBlightId = -1;
	private Boolean isTemp = false;	

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
    	//mView.setInitialScale(140);
    	 // AppCacheMaxSize in 512 KB = 524288 bytes
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
        txtTitle.setText(R.string.titleInfoBlight);


        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = intent.getStringExtra(Constant.EXTRA_PARAM_INDEX);
            mBlightId = Integer.parseInt(str);
            str = intent.getStringExtra(Constant.EXTRA_PARAM_ISTEMP);
            if(str != null && str.length() > 0)
                isTemp = Boolean.valueOf(str);
        }


        initview();

        startProgress();
        if(isTemp) {
            txtTitle.setText(R.string.titleTempBlight);
            CommManager.getTempBlightInfo(mBlightId,
                    get_tempblightinfo_handler);
        }
        else {
            CommManager.getBlightInfo(AppCommon.loadUserID(),
                    mBlightId,
                    get_blightinfo_handler);
        }
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
                    //htmlContent = "<!doctype html><html><head>    <meta charset=\"utf-8\" /></head><body><h1 align=\"center\"> <font size=6 color=\"#7AB038\">蝼蛄</font></h1><p> </p><table width=\"300\" align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\"><tbody><tr> <td align=\"center\">     <p></p>     <img width = \"300\" src=\"http://218.25.54.56:2020/Content/DiseasePest/20150609185102152.jpg\" />     <p>图片</p> </td> <?php }?></tr><tr> <td align=\"center\">     <p></p>     <img width = \"300\" src=\"http://218.60.131.41:10230/Content/DiseasePest/20150602091735771.jpg\" />     <p>图片</p> </td> ";
                    mView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
                }
                else {
                    AppCommon.showToast(DBBlightInfoActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(DBBlightInfoActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(DBBlightInfoActivity.this, R.string.STR_CONN_ERROR);
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
                    AppCommon.showToast(DBBlightInfoActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(DBBlightInfoActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(DBBlightInfoActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}