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
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DavidYin on 2015.03.18.
 */
public class HistoryUserNotCompletedActivity extends SuperActivity  implements View.OnClickListener, OnClickItemAdapterListener {
    // for list view
    private ListView mListView;
    private SingleTextListAdapter mAdapter;
    private ArrayList<TaskBean> mList = new ArrayList<TaskBean>();

    // for common
    public TaskBean	m_curTask = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.history_user_notcompleted);
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
        bundle.putString(Constant.EXTRA_PARAM_TYPE, getString(R.string.history_user_type_notcompleted));
        bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(m_curTask.detail_id));
        bundle.putString(Constant.EXTRA_PARAM_POINT_ID, String.valueOf(m_curTask.point_id));
        bundle.putString(Constant.EXTRA_PARAM_REPORT_ID, String.valueOf(m_curTask.report_id));
        bundle.putString(Constant.EXTRA_PARAM_DATE, m_curTask.date);
        pushNewActivityAnimated(HistoryUserDetailActivity.class, bundle);
    }

    private void initUI() {
        if (mList != null) {
            mAdapter.setData(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initializeActivity() {

        // init list view
        mListView = (ListView) findViewById(R.id.list_data);
        mAdapter = new SingleTextListAdapter(this);
        mAdapter.setLargeHeight(true);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnListener(this);

        Date nowTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        startProgress();
        CommManager.getTaskList(AppCommon.loadUserID(),
                0,
                -1,
                "2015-1-1",
                format.format(nowTime),
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
                            obj.setDisplayType(TaskBean.DISP_TYPE_HISTORY_NOTCOMPLETED);
                            mList.add(obj);
                        }
                    }
                    initUI();
                }
                else {
                    AppCommon.showToast(HistoryUserNotCompletedActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(HistoryUserNotCompletedActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(HistoryUserNotCompletedActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}