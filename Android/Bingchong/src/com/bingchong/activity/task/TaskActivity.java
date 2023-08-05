package com.bingchong.activity.task;

import java.util.ArrayList;

import com.bingchong.*;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleAcceptAdapter;
import com.bingchong.bean.BlightBean;
import com.bingchong.bean.ExtraTaskBean;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserBlight;
import com.bingchong.parser.ParserExtraTask;
import com.bingchong.utils.AppDateTimeUtils;
import com.bingchong.utils.AppPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;

public class TaskActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener{

	// for list view
	private ListView mListView;
	private SingleAcceptAdapter mAdapter;
	private ArrayList<ExtraTaskBean> mList = new ArrayList<ExtraTaskBean>();
	private ArrayList<ExtraTaskBean> mAllList = new ArrayList<ExtraTaskBean>();
	
	// for detail view
	private TextView txtName;
	private TextView txtSDate;
	private TextView txtEDate;
	private TextView txtUser;
	private TextView txtNote;
	private TextView txtStatus;
	private Button	 btnComplete;
	
	// for common
	private TextView txtTitle;
	private LinearLayout mLayoutDetail = null;
	private LinearLayout mLayoutList   = null;
	public RadioGroup radioGroup = null;
	public ExtraTaskBean	m_curTask = null;
	public boolean	m_bShowCompleteTask = false;	
	public boolean  isAdmin = false;
	public boolean isReqUpdate = false;
	
	private AppPreferences appPreferences;	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.tasktmp);
	}

    @Override
    public void initializeActivity() {
        appPreferences = new AppPreferences(getApplicationContext());

        // init common
        radioGroup = (RadioGroup) this.findViewById(R.id.main_radio_group);
        txtTitle = (TextView) this.findViewById(R.id.txtTitle);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnComplete).setOnClickListener(this);
        findViewById(R.id.btnNoComplete).setOnClickListener(this);
        btnComplete = (Button)findViewById(R.id.btnTaskComplete);
        btnComplete.setOnClickListener(this);
        radioGroup.check(R.id.btnNoComplete);
        isAdmin = DataMgr.m_curUser.isAdmin();
        if(isAdmin)
        {
            findViewById(R.id.txtNew).setOnClickListener(this);
            btnComplete.setText(R.string.task_Complete);
            txtTitle.setText(getString(R.string.titleMyTask));
        }
        else
        {
            findViewById(R.id.txtNew).setVisibility(View.GONE);
            btnComplete.setText(R.string.task_Accept);
            txtTitle.setText(getString(R.string.titleMyTempTask));
        }

        mLayoutDetail = (LinearLayout)findViewById(R.id.extratask_detail_layout);
        mLayoutList = (LinearLayout)findViewById(R.id.list_layout);

        // init list view
        mListView = (ListView) findViewById(R.id.list_data);
        mAdapter = new SingleAcceptAdapter(this, DataMgr.m_curUser.isAdmin());
        mListView.setAdapter(mAdapter);
        mAdapter.setOnListener(this);

        // init detail view
        txtName = (TextView) findViewById(R.id.taskTxtName);
        txtSDate= (TextView) findViewById(R.id.taskTxtSDate);
        txtEDate= (TextView) findViewById(R.id.taskTxtEDate);
        txtUser = (TextView) findViewById(R.id.taskTxtUser);
        txtNote = (TextView) findViewById(R.id.taskTxtNote);
        txtStatus = (TextView) findViewById(R.id.taskTxtStatus);

        showList(false);
    }

	private void initUI() {
		mList.clear();
		for(int i = 0; i < mAllList.size(); i++)
		{
			ExtraTaskBean obj = mAllList.get(i);
			if(m_bShowCompleteTask)
			{
				if(obj.status == ExtraTaskBean.STAT_COMPLETE)
					mList.add(obj);
			}
			else{
				if(mAllList.get(i).status != ExtraTaskBean.STAT_COMPLETE)
					mList.add(obj);
			}
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
			if(AppCommon.isAdmin())
			{
				CommManager.updateExtraTask(AppCommon.loadUserID(), 0, m_curTask.id, m_curTask.status, update_extratask_handler);
			}
			else
			{
				CommManager.updateExtraTask(0, AppCommon.loadUserID(), m_curTask.id, m_curTask.status, update_extratask_handler);
			}
        }
		else {
			startProgress();
			if(AppCommon.isAdmin())
			{
				CommManager.getExtraTasks(AppCommon.loadUserID(), 0, get_extratask_handler);
			}
			else
			{
				CommManager.getExtraTasks(0, AppCommon.loadUserID(), get_extratask_handler);
			}
        }
	}
	
	private void showDetail(){
		mLayoutDetail.setVisibility(View.VISIBLE);
		mLayoutList.setVisibility(View.GONE);

		txtName.setText(m_curTask.name);
		txtUser.setText(m_curTask.user_name);
		txtNote.setText(m_curTask.note);
		txtSDate.setText(m_curTask.notice_date);
		txtEDate.setText(m_curTask.report_date);	
		
		Boolean isComplete = false;
		
		switch(m_curTask.status)
		{
		case ExtraTaskBean.STAT_NOACCEPT:
			txtStatus.setText(R.string.task_noAccept);
			findViewById(R.id.layoutBtnComplete).setVisibility(View.VISIBLE);
//			if(isAdmin)
//				findViewById(R.id.layoutBtnComplete).setVisibility(View.GONE);
//			else
//				findViewById(R.id.layoutBtnComplete).setVisibility(View.VISIBLE);
			break;
		case ExtraTaskBean.STAT_ACCEPT:
			txtStatus.setText(R.string.task_Accept);
			if(isAdmin)
				findViewById(R.id.layoutBtnComplete).setVisibility(View.VISIBLE);
			else
				findViewById(R.id.layoutBtnComplete).setVisibility(View.GONE);
			break;
		case ExtraTaskBean.STAT_COMPLETE:
			txtStatus.setText(R.string.task_Complete);
			findViewById(R.id.layoutBtnComplete).setVisibility(View.GONE);
			isComplete = true;
			break;
		}
		
	}

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		try{
			// update status
			if(isReqUpdate){
				DataMgr.updateExtraTask(m_curTask);
			}
			
			mAllList = DataMgr.getExtraTasks("", 0);
			
		} catch (Exception e){}
		
		isReqUpdate = false;

		return mList;
	}

	@Override
	public void onTaskResult(int taskId, Object result) {
		initUI();
	}
	*/
	
	@Override
	protected void onResume() {
		super.onResume();
		
		showList(false);
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
		case R.id.btnTaskComplete:
			if(isAdmin)
				m_curTask.status = ExtraTaskBean.STAT_COMPLETE;
			else
				m_curTask.status = ExtraTaskBean.STAT_ACCEPT;
			isReqUpdate = true;
			showList(true);
			break;
		case R.id.btnBack:
			showList(false);
			break;
		case R.id.txtNew:
		{
			pushNewActivityAnimated(TaskNewActivity.class);
		}
		break;
		}
	}
	
	@Override
	public void OnClickListener(int index)
	{
		m_curTask = mList.get(index);
		showDetail();
	}

    private AsyncHttpResponseHandler get_extratask_handler = new AsyncHttpResponseHandler() {
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
                    JSONArray arrItems = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < arrItems.length(); i++) {
                        ExtraTaskBean obj = ParserExtraTask.parseJsonResponse(arrItems.getJSONObject(i));
                        if(obj != null)
                            mAllList.add(obj);
                    }
                    initUI();
                }
                else {
                    AppCommon.showToast(TaskActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(TaskActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(TaskActivity.this, R.string.STR_CONN_ERROR);
        }
    };

	private AsyncHttpResponseHandler update_extratask_handler = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			super.onSuccess(content);
			stopProgress();

			try {
				JSONObject result = new JSONObject(content);

				int nRetCode = result.getInt(ConstMgr.gRetCode);
				String stRetMsg = result.getString(ConstMgr.gRetMsg);
				if (nRetCode == ConstMgr.SVC_ERR_NONE) {
					AppCommon.showToast(TaskActivity.this, "操作成功");
					initUI();
				}
				else {
					AppCommon.showToast(TaskActivity.this, stRetMsg);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				AppCommon.showDebugToast(TaskActivity.this, ex.getMessage());
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			super.onFailure(error, content);
			stopProgress();

			AppCommon.showToast(TaskActivity.this, R.string.STR_CONN_ERROR);
		}
	};
}