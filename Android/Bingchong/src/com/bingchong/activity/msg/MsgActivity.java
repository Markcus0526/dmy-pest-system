package com.bingchong.activity.msg;

import java.util.ArrayList;
import java.util.Calendar;

import android.provider.ContactsContract;
import android.widget.*;
import com.bingchong.*;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleTextListAdapter;
import com.bingchong.bean.NoticeBean;
import com.bingchong.bean.TaskBean;
import com.bingchong.db.DataMgr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserNotice;
import com.bingchong.parser.ParserTask;
import com.bingchong.utils.ResolutionSet;
import com.bingchong.widget.LinearLayoutWithLine;
import org.json.JSONArray;
import org.json.JSONObject;

public class MsgActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener, OnItemSelectedListener {

	private ListView mListView;
	private SingleTextListAdapter mAdapter;

	private ArrayList<String> arrYears = new ArrayList<String>();
	private Spinner mSpYears;
    private ArrayList<String> arrTypes = new ArrayList<String>();
    private Spinner mSpTypes;
	
	private NoticeBean m_curObj = null;
	
	private ArrayList<NoticeBean> mList = new ArrayList<NoticeBean>();

    private Button btnPublish = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.msgs);
	}
	
	private void initSpinerYears()
	{
		mSpYears = (Spinner) findViewById(R.id.spinnerPos);
		mSpYears.setOnItemSelectedListener(this);
        mSpTypes = (Spinner) findViewById(R.id.spinner_type);
        mSpTypes.setOnItemSelectedListener(this);
		
		// init spinner
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		for(int i = year; i >= 2013; i--)
			arrYears.add(""+i);
        ArrayAdapter<String> AA = new ArrayAdapter<String>(this, R.layout.item_spinner_white, arrYears);
        AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpYears.setAdapter(AA);
        mSpYears.setSelection(0);

        arrTypes.add("上级通知");
        arrTypes.add("下级通知");
        ArrayAdapter<String> AA_type = new ArrayAdapter<String>(this, R.layout.item_spinner_white, arrTypes);
        AA_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpTypes.setAdapter(AA_type);
        mSpTypes.setSelection(0);

	}	

	private void initUI() {
		if (mList != null) {
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
            case R.id.btn_publish:
                pushNewActivityAnimated(MsgPublishActivity.class);
                break;
		}
	}
	
	@Override
	public void OnClickListener(int index)
	{
		m_curObj = mList.get(index);

        Bundle bundle = new Bundle();
		bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(m_curObj.id));

		pushNewActivityAnimated(MsgDetailActivity.class, bundle);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.spinnerPos) {
        }
        else if(parent.getId() == R.id.spinner_type)
        {
        }

        ResolutionSet.instance.iterateChild(view, mScrSize.x, mScrSize.y);

        startProgress();
        int nId = mSpYears.getSelectedItemPosition();
        String year = arrYears.get(nId);
        CommManager.getNotices(AppCommon.loadUserID(),
                Integer.parseInt(year),
                mSpTypes.getSelectedItemPosition(),
                get_notices_handler);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void initializeActivity() {

        if(AppCommon.loadUserInfo().rights_id == 0)  // watcher
        {
            LinearLayout rlType = (LinearLayout)findViewById(R.id.rlType);
            rlType.setVisibility(View.GONE);
        }

        mListView = (ListView) findViewById(R.id.list_data);

        initSpinerYears();

        mAdapter = new SingleTextListAdapter(this);
        mListView.setAdapter(mAdapter);

        mAdapter.setOnListener(this);

        btnPublish = (Button)findViewById(R.id.btn_publish);
        btnPublish.setOnClickListener(this);

        startProgress();
        int nId = mSpYears.getSelectedItemPosition();
        String year = arrYears.get(nId);
        CommManager.getNotices(AppCommon.loadUserID(),
                Integer.parseInt(year),
                mSpTypes.getSelectedItemPosition(),
                get_notices_handler);
    }

    private AsyncHttpResponseHandler get_notices_handler = new AsyncHttpResponseHandler() {
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
                        NoticeBean obj = ParserNotice.parseJsonResponse((JSONObject) array.get(i));
                        if(obj != null)
                            mList.add(obj);
                    }
                    initUI();
                }
                else {
                    AppCommon.showToast(MsgActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(MsgActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(MsgActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}