package com.bingchong.activity.setting;

import com.bingchong.*;
import com.bingchong.bean.HelpBean;
import com.bingchong.db.DataMgr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import com.bingchong.net.AsyncHttpResponseHandler;
import org.json.JSONObject;

public class SettingHelpInfoActivity extends SuperActivity {
	private long uid = 0;
	private String title = "";
	private String contents = "";
	private TextView txtTitle = null;

	private WebView web_view = null;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.setting_help_detail);
	}


	@Override
	public void initializeActivity() {
		uid = getIntent().getLongExtra("uid", 0);
		title = getIntent().getStringExtra("title");

		txtTitle = (TextView)findViewById(R.id.txt_title);
		web_view = (WebView)findViewById(R.id.web_view);
		web_view.getSettings().setJavaScriptEnabled(true);

		startProgress();
        Intent intent = getIntent();
        String sIsManual = intent.getStringExtra(Constant.EXTRA_PARAM_MANUAL);
        if(sIsManual.equals("true")) {
			txtTitle.setText("测报员管理办法详情");
            CommManager.getHelpInfo(0, uid, detail_handler);
        }
        else {
			txtTitle.setText("帮助信息详情");
            CommManager.getHelpInfo(1, uid, detail_handler);
        }
	}


	private AsyncHttpResponseHandler detail_handler = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			super.onSuccess(content);
			stopProgress();

			try {
				JSONObject result = new JSONObject(content);

				int retcode = result.getInt("retcode");
				String retmsg = result.getString("retmsg");

				if (retcode == 0) {
					JSONObject retdata = result.getJSONObject("retdata");

					String contents = retdata.getString("content");
					SettingHelpInfoActivity.this.contents = contents;

					updateUI();
				} else {
					AppCommon.showToast(SettingHelpInfoActivity.this, retmsg);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}


		@Override
		public void onFailure(Throwable error) {
			super.onFailure(error);
			stopProgress();

			AppCommon.showToast(SettingHelpInfoActivity.this, R.string.STR_CONN_ERROR);
		}
	};


	private void updateUI() {
		web_view.loadData(contents, "text/html; charset=UTF-8", null);
	}

}