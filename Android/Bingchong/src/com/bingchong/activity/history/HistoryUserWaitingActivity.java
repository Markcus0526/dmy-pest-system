package com.bingchong.activity.history;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.bingchong.*;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleTextListAdapter;
import com.bingchong.bean.TaskBean;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserTask;
import com.bingchong.widget.DateTimePicker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by DavidYin on 2015.03.18.
 */
public class HistoryUserWaitingActivity extends SuperActivity   implements View.OnClickListener, OnClickItemAdapterListener, DateTimePicker.OnDateChangeListener {
    // for list view
    private ListView mListView;
    private SingleTextListAdapter mAdapter;
    private ArrayList<TaskBean> mList = new ArrayList<TaskBean>();
    private DateTimePicker      sDatePicker;
    private DateTimePicker      eDatePicker;

    // for common
    public TaskBean	m_curTask = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.history_user_waiting);
    }

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
        m_curTask = mList.get(index);

        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_PARAM_TYPE, getString(R.string.history_user_type_waiting));
        bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(m_curTask.detail_id));
        bundle.putString(Constant.EXTRA_PARAM_POINT_ID, String.valueOf(m_curTask.point_id));
        bundle.putString(Constant.EXTRA_PARAM_REPORT_ID, String.valueOf(m_curTask.report_id));
        bundle.putString(Constant.EXTRA_PARAM_DATE, m_curTask.date);

        pushNewActivityAnimated(HistoryUserDetailActivity.class, bundle);
    }

    @Override
    public void onDateChanged() {
        refreshList();
    }

    private void initUI() {
        if (mList != null) {
            mAdapter.setData(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void refreshList() {
        String strStart = sDatePicker.getDateString();
        String strEnd = eDatePicker.getDateString();
        startProgress();
        CommManager.getTaskList(AppCommon.loadUserID(),
                2,
                -1,
                strStart,
                strEnd,
                get_tasklist_handler);
    }

    @Override
    public void initializeActivity() {

        // init list view
        mListView = (ListView) findViewById(R.id.list_data);
        mAdapter = new SingleTextListAdapter(this);
        mAdapter.setLargeHeight(true);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnListener(this);

        sDatePicker = (DateTimePicker) findViewById(R.id.startdate);
        sDatePicker.setOnChangeListener(this);
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        date.set(year, month, 1);
        sDatePicker.setDate(date);
        eDatePicker = (DateTimePicker) findViewById(R.id.enddate);
        eDatePicker.setOnChangeListener(this);

        refreshList();
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
                            obj.setDisplayType(TaskBean.DISP_TYPE_HISTORY_WAITING);
                            mList.add(obj);
                        }
                    }
                    initUI();
                }
                else {
                    AppCommon.showToast(HistoryUserWaitingActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(HistoryUserWaitingActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(HistoryUserWaitingActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}