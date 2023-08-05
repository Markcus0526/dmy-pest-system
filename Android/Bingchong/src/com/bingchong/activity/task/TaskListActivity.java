package com.bingchong.activity.task;

import java.util.ArrayList;

import android.widget.TextView;
import com.bingchong.*;
import com.bingchong.activity.HomeMgrActivity;
import com.bingchong.activity.HomeUserActivity;
import com.bingchong.adapter.FixTaskListAdapter;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleTextListAdapter;
import com.bingchong.bean.ExtraTaskBean;
import com.bingchong.bean.TaskBean;
import com.bingchong.db.DataMgr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserTask;
import org.json.JSONArray;
import org.json.JSONObject;

public class TaskListActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener{

	// for list view
    private TextView txtTitle = null;
	private ListView mListView;
	private FixTaskListAdapter mAdapter;
	private ArrayList<TaskBean> mList = new ArrayList<TaskBean>();
	private String date;
    private int mPointType = -1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.taskfix_list);
	}

	private void initUI() {
		if (mList != null) {
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
		}
	}

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		try{
			ArrayList<TaskBean> list = DataMgr.getTaskList(date);
			mList.clear();
			for(int i = 0; i < list.size(); i++)
			{
				mList.add(list.get(i));
			}
			
		} catch (Exception e){}

		return mList;
	}
	
	@Override
	public void onTaskResult(int taskId, Object result) {
		closeWaitingDlg();
		initUI();
	}
	*/
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id)
		{
		}
	}
	
	@Override
	public void OnClickListener(int index)
	{
		TaskBean	m_curTask = mList.get(index);
		//intent.putExtra(Constant.EXTRA_PARAM_INDEX, String.valueOf(m_curTask.detail_id));

        Bundle bundle = new Bundle();

        bundle.putString(Constant.EXTRA_PARAM_TYPE, "");
        bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(m_curTask.detail_id));
		bundle.putString(Constant.EXTRA_PARAM_POINT_ID, String.valueOf(m_curTask.point_id));
        bundle.putString(Constant.EXTRA_PARAM_BLIGHT_ID, String.valueOf(m_curTask.blight_id));
        bundle.putString(Constant.EXTRA_PARAM_FORM_ID, String.valueOf(m_curTask.form_id));
		bundle.putString(Constant.EXTRA_PARAM_DATE, date);

        pushNewActivityAnimated(TaskFixDetailActivity.class, bundle);
	}

    @Override
    public void initializeActivity() {

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            date = intent.getStringExtra(Constant.EXTRA_PARAM_DATE);
            mPointType = intent.getIntExtra(Constant.EXTRA_PARAM_POINT_TYPE, 0);
        }

        txtTitle = (TextView)findViewById(R.id.txtTitle);
        if(mPointType == 0) {
            txtTitle.setText("常规测报工作列表(固定)");
        }
        else {
            txtTitle.setText("常规测报工作列表(非固定)");
        }
        // init list view
        mListView = (ListView) findViewById(R.id.list_data);
        mAdapter = new FixTaskListAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnListener(this);

        startProgress();
        CommManager.getTaskList(AppCommon.loadUserID(),
                0,
                mPointType,
                date,
                date,
                get_tasklist_handler);
    }

    private AsyncHttpResponseHandler get_tasklist_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    JSONArray array = result.getJSONArray(ConstMgr.gRetData);
                    mList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        TaskBean obj = ParserTask.parseJsonResponse((JSONObject) array.get(i));
                        if(obj != null) {
                            obj.point_type = mPointType;
                            mList.add(obj);
                        }
                    }
                    initUI();
                }
                else {
                    AppCommon.showToast(TaskListActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(TaskListActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(TaskListActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}