package com.bingchong.activity.msg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.bingchong.*;
import android.view.View.OnClickListener;
import com.bingchong.net.AsyncHttpResponseHandler;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DavidYin on 2015.03.19.
 */
public class MsgPublishActivity extends SuperActivity implements OnClickListener {

    EditText txtTitle = null;
    EditText txtSerial = null;
    EditText txtPublisher = null;
    EditText txtDate = null;
    EditText txtContent = null;
    Button btnPublish = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.msg_publish);
    }

    private boolean checkTextValid()
    {
        if(txtTitle.getText().length() <= 0) {
            AppCommon.showToast(MsgPublishActivity.this, "请输入标题");
            return false;
        }
        if(txtSerial.getText().length() <= 0) {
            AppCommon.showToast(MsgPublishActivity.this, "请输入文号");
            return false;
        }
        if(txtContent.getText().length() <= 0) {
            AppCommon.showToast(MsgPublishActivity.this, "请输入通知内容");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_publish:
                if(!checkTextValid())
                    break;
                startProgress();
                CommManager.publishNotice(txtTitle.getText().toString(),
                        txtSerial.getText().toString(),
                        AppCommon.loadUserID(),
                        txtContent.getText().toString(),
                        publish_notice_handler);
                break;
        }
    }

    @Override
    public void initializeActivity() {

        txtTitle = (EditText)findViewById(R.id.txtTitle);
        txtSerial = (EditText)findViewById(R.id.txtSerial);
        txtPublisher = (EditText)findViewById(R.id.txtPublisher);
        txtDate = (EditText)findViewById(R.id.txtDate);
        txtContent = (EditText)findViewById(R.id.txtContent);

        txtPublisher.setText(AppCommon.loadUserInfo().name);
        Date nowTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(format.format(nowTime));

        btnPublish = (Button)findViewById(R.id.btn_publish);
        btnPublish.setOnClickListener(this);
    }

    private AsyncHttpResponseHandler publish_notice_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    AppCommon.showToast(MsgPublishActivity.this, "发布成功");
                    finish();
                }
                else {
                    AppCommon.showToast(MsgPublishActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(MsgPublishActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(MsgPublishActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}