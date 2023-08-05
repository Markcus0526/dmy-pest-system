package com.bingchong.activity.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import com.bingchong.*;
import com.bingchong.bean.ReportBean;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import org.json.JSONObject;

/**
 * Created by DavidYin on 2015.03.23.
 */
public class ReportInfoActivity extends SuperActivity implements View.OnClickListener {

    private WebView mView;
    private String page_type = "";
    private int report_index = -1;

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
        try{
            if (bundle != null) {
                String str = intent.getStringExtra(Constant.EXTRA_PARAM_INDEX);
                report_index = Integer.parseInt(str);
                page_type = intent.getStringExtra(Constant.EXTRA_PARAM_TYPE);
                if(page_type.equals(getString(R.string.history_user_type_saved))) {
                    txtTitle.setText("保存未上报工作情况");
                }
            }
        }
        catch(Exception ex){}

        initview();

        if(report_index >= 0)
        {
            mView.loadDataWithBaseURL(null, DataMgr.getHtmlContentFromReport(DataMgr.getReportFromDB(report_index)), "text/html", "utf-8", null);
        }
    }
}