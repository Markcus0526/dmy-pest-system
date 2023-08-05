package com.bingchong.activity.db;

import java.io.File;

import com.bingchong.*;
import com.bingchong.bean.PointBean;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.utils.Utilities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import org.json.JSONObject;

public class DBPlaceInfoActivity extends SuperActivity implements OnClickListener{

	private WebView mView;
	private int mIndex = -1;
	private Boolean isLocal = false;
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
        txtTitle.setText(R.string.titleInfoPlace);


        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = intent.getStringExtra(Constant.EXTRA_PARAM_INDEX);
            mIndex = Integer.parseInt(str);
            str = intent.getStringExtra(Constant.EXTRA_PARAM_LOCAL);
            if(str != null && str.length() > 0)
                isLocal = Boolean.valueOf(str);
            str = intent.getStringExtra(Constant.EXTRA_PARAM_ISTEMP);
            if(str != null && str.length() > 0)
                isTemp = Boolean.valueOf(str);
        }

        if(isTemp)
            txtTitle.setText(R.string.titleTempPoint);

        initview();
        String url;
        if(isTemp)
            url = Constant.URL_SERVER + Constant.URL_GET_TEMP_POINT_INFO + "?point_id=" + mIndex;
        else
            url = Constant.URL_SERVER + Constant.URL_GET_PLACE_INFO + "?point_id=" + mIndex;

        if(isLocal)
        {
            PointBean obj = DataMgr.getPointInfoFromDB(mIndex);
            String contents = obj.info;
            String path = Utilities.saveHtmlFileData(contents);
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            mView.loadUrl(uri.toString());
        }
        else
        {
            startProgress();
            CommManager.getPointInfo(AppCommon.loadUserID(), mIndex, get_pointinfo_handler);
        }
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
                    mView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
                }
                else {
                    AppCommon.showToast(DBPlaceInfoActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(DBPlaceInfoActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(DBPlaceInfoActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}