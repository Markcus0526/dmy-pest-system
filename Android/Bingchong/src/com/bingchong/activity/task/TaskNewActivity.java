package com.bingchong.activity.task;

import java.util.ArrayList;

import android.content.Intent;
import com.bingchong.*;
import com.bingchong.activity.HomeMgrActivity;
import com.bingchong.activity.HomeUserActivity;
import com.bingchong.bean.ExtraTaskBean;
import com.bingchong.bean.UserBean;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserUser;
import com.bingchong.utils.ResolutionSet;
import com.bingchong.utils.Utilities;
import com.bingchong.widget.DateTimePicker;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import org.json.JSONArray;
import org.json.JSONObject;

public class TaskNewActivity extends SuperActivity implements OnClickListener, OnItemSelectedListener {

	private EditText edt_name;
	private DateTimePicker sDate;
	private DateTimePicker eDate;
	private EditText edt_note;
	
	private Spinner mSpinnerUsers;	
	private final int SPINNER_ID_USERS = 1;	
	private static int user_index  = 0;	
	private ArrayList<UserBean> mUsers = new ArrayList<UserBean>();
	
	private final static int REQ_GET_USER = 1;
	private final static int REQ_ADD_TASK = 2;
	
	private int req_type = 0;
	
	private ExtraTaskBean mCurTask = null;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.tasktmp_new);
	}
	
	private void refreshUI(Boolean ret){
		if(req_type == REQ_GET_USER){
			ArrayAdapter<UserBean> usersAA = new ArrayAdapter<UserBean>(this, R.layout.item_spinner_white, mUsers);
			usersAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        mSpinnerUsers.setAdapter(usersAA);
	        mSpinnerUsers.setSelection(user_index);				
		}
		if(req_type == REQ_ADD_TASK){
			if(ret)
			{
				finish();
			}
		}
	}
	
	
	public void AddTaskAndFinish()
	{
		mCurTask = new ExtraTaskBean();
		
		Boolean isAdd = true;
		
		mCurTask.name = edt_name.getText().toString().trim();
		if(mCurTask.name.length() == 0){
			AppCommon.showToast(TaskNewActivity.this, R.string.strTipNoName);
			isAdd = false;
		}
		
		mCurTask.note = edt_note.getText().toString().trim();
		if(mCurTask.note.length() == 0){
			AppCommon.showToast(TaskNewActivity.this, "没有描述任务");
			isAdd = false;
		}
		mCurTask.notice_date = sDate.getDateString();
		mCurTask.report_date = eDate.getEndDateString();
		
		if(sDate.getTimeInMillis() > eDate.getTimeInMillis() || sDate.getTimeInMillis() < System.currentTimeMillis()){
			AppCommon.showToast(TaskNewActivity.this, R.string.strTipErrorTime);
			isAdd = false;
		}
		if(mUsers.size() <= 0) {
			AppCommon.showToast(TaskNewActivity.this, "没有发布去向");
			isAdd = false;
		}
		mCurTask.watcher_id = (int)mUsers.get(user_index).getId();
		mCurTask.user_name = mUsers.get(user_index).getUsername();
		
		if(!isAdd)
			return;
		
		req_type = REQ_ADD_TASK;
		startProgress();
        CommManager.uploadExtraTask(AppCommon.loadUserID(),
                mCurTask.name,
                mCurTask.watcher_id,
                mCurTask.notice_date,
                mCurTask.report_date,
                mCurTask.note,
                upload_extratask_handler);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id)
		{
		case R.id.btnOk:
			AddTaskAndFinish();
			break;
		}
	}

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		Boolean ret =false;
		try{
			if(req_type == REQ_GET_USER){
				mUsers = DataMgr.getUsers("", 0);
				ret = true;
			}
			else if(req_type == REQ_ADD_TASK){
				ret = DataMgr.addExtraTask(mCurTask);
			}

		} catch (Exception e){}

		return ret;
	}
	
	@Override
	public void onTaskResult(int taskId, Object result) {
		closeWaitingDlg();
		Boolean ret = false;
		try{
			ret = Boolean.parseBoolean(result.toString());
		}
		catch(Exception ex){}
		refreshUI(ret);
	}
		*/

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.spiner_person){
			user_index = position;
		}
        ResolutionSet.instance.iterateChild(view, mScrSize.x, mScrSize.y);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void initializeActivity() {
        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_note = (EditText)findViewById(R.id.edt_note);
        sDate = (DateTimePicker)findViewById(R.id.edt_sdate);
        eDate = (DateTimePicker)findViewById(R.id.edt_edate);
        findViewById(R.id.btnOk).setOnClickListener(this);

        sDate.setEnableChange(false);

        // init spinner
        mSpinnerUsers = (Spinner) findViewById(R.id.spiner_person);

        req_type = REQ_GET_USER;
        startProgress();
        CommManager.getUsers(AppCommon.loadUserID(), -1, 0, 0, 0, "", get_users_handler);
    }

    private AsyncHttpResponseHandler get_users_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    mUsers.clear();
                    JSONArray arrItems = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < arrItems.length(); i++) {
                        UserBean userBean = ParserUser.parseJsonResponse(arrItems.getJSONObject(i));
                        if (userBean != null)
                            mUsers.add(userBean);
                    }
					if(mUsers.size() > 0)
						mSpinnerUsers.setOnItemSelectedListener(TaskNewActivity.this);
                    refreshUI(true);
                }
                else {
                    AppCommon.showToast(TaskNewActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(TaskNewActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(TaskNewActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler upload_extratask_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    AppCommon.showToast(TaskNewActivity.this, "发布成功");
                    finish();
                }
                else {
                    AppCommon.showToast(TaskNewActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(TaskNewActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(TaskNewActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}