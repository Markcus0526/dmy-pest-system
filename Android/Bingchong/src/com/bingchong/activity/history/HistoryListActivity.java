package com.bingchong.activity.history;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.bingchong.*;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleTextListAdapter;
import com.bingchong.bean.PointBean;
import com.bingchong.bean.TaskReportBean;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserTaskReport;
import com.bingchong.widget.DateTimePicker;
import com.bingchong.widget.DateTimePicker.OnDateChangeListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

public class HistoryListActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener, OnDateChangeListener {

	private ListView mListView;
	private SingleTextListAdapter mAdapter;
	private ArrayList<TaskReportBean> mAllList = new ArrayList<TaskReportBean>();
	private ArrayList<TaskReportBean> mList = new ArrayList<TaskReportBean>();
	private DateTimePicker sDate;
	private DateTimePicker eDate;
	private boolean mblFly = false;
	private int point_id = 0;
    private String startDate = "";
    private String endDate = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.history_list);
	}

	private void initUI() {
		ArrayList<TaskReportBean> allList = mAllList;
		mList = new ArrayList<TaskReportBean>();
		if(allList == null)
		{
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
			return;
		}
		
		for(int i = 0; i < allList.size(); i++)
		{
			TaskReportBean obj = allList.get(i);
			if(mblFly && obj.isFlay())
				mList.add(obj);
			else if(!mblFly && (!obj.isFlay()))
				mList.add(obj);
			
		}

		mAdapter.setData(mList);
		mAdapter.notifyDataSetChanged();
	}

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		try{
			mAllList = DataMgr.getReportHistory(point_id, sDate.getDateString(), eDate.getDateString());

		} catch (Exception e){}

		return null;
	}
	
	@Override
	public void onTaskResult(int taskId, Object result) {
		initUI();
	}
	*/
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btnFly:
			mblFly = true;
			initUI();
			break;
		case R.id.btnDisease:
			mblFly = false;
			initUI();
			break;
		}
	}
	
	@Override
	public void OnClickListener(int index)
	{
		TaskReportBean obj = mList.get(index);

		Bundle bundle = new Bundle();
		bundle.putString(Constant.EXTRA_PARAM_FORM_ID, String.valueOf(obj.form_id));
		bundle.putString(Constant.EXTRA_PARAM_POINT_ID, String.valueOf(point_id));
		bundle.putString(Constant.EXTRA_PARAM_DATE, obj.date);
        bundle.putString(Constant.EXTRA_PARAM_REPORT_ID, obj.id + "");

		pushNewActivityAnimated(HistoryDetailActivity.class, bundle);
	}

	@Override
	public void onDateChanged() {
        refreshList();
	}

    public void refreshList()
    {
        startDate = sDate.getDateString();
        endDate = eDate.getDateString();

        startProgress();
        CommManager.getReportHistory(AppCommon.loadUserID(), point_id, startDate, endDate, get_reporthistory_handler);
    }

    @Override
    public void initializeActivity() {
        findViewById(R.id.btnFly).setOnClickListener(this);
        findViewById(R.id.btnDisease).setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = intent.getStringExtra(Constant.EXTRA_PARAM_INDEX);
            point_id = Integer.parseInt(str);
        }


        // init control
        sDate = (DateTimePicker)findViewById(R.id.edt_sdate);
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        date.set(year, 0, 1);
        sDate.setDate(date);

        eDate = (DateTimePicker)findViewById(R.id.edt_edate);
        sDate.setOnChangeListener(this);
        eDate.setOnChangeListener(this);
        mListView = (ListView) findViewById(R.id.list_data);

        mAdapter = new SingleTextListAdapter(this);
        mAdapter.setLargeHeight(true);
        mListView.setAdapter(mAdapter);

        mAdapter.setOnListener(this);

        refreshList();
    }

    private AsyncHttpResponseHandler get_reporthistory_handler = new AsyncHttpResponseHandler() {
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
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        TaskReportBean obj = ParserTaskReport.parseJsonResponse((JSONObject) retdata.get(i));
                        if(obj != null)
                            mAllList.add(obj);
                    }

                    initUI();
                }
                else {
                    AppCommon.showToast(HistoryListActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(HistoryListActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}