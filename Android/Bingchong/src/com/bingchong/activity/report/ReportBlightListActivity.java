package com.bingchong.activity.report;

import java.util.ArrayList;

import com.bingchong.*;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.TwoTextListAdapter;
import com.bingchong.bean.BlightBean;
import com.bingchong.bean.FormBean;
import com.bingchong.db.DataMgr;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserBlight;
import com.bingchong.parser.ParserForm;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReportBlightListActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener{

	private ListView mListView;
	private TwoTextListAdapter mAdapter;
	private ArrayList<BlightBean> mList = null;
	private ArrayList<BlightBean> mAllList = new ArrayList<BlightBean>();
	private String mSearchKey = "";
	
	private boolean isFly = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.report_blight_list);
	}

	private void initUI() {
		EditText txtView = (EditText)findViewById(R.id.edt_search_str);
		mSearchKey = txtView.getText().toString().trim();
		
		if (mAllList != null) {
			mList = new ArrayList<BlightBean>();
			for(int i = 0; i < mAllList.size(); i++)
			{
				BlightBean obj = mAllList.get(i);
				if(mSearchKey.length() > 0 && obj.toString().indexOf(mSearchKey)<0)
					continue;				
				if(isFly && obj.isFly())
					mList.add(obj);
				if(!isFly && (!obj.isFly()))
					mList.add(obj);
			}
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	private void refreshList(){
		initUI();
	}

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		try{
			mAllList = DataMgr.getBlights("");
			DataMgr.updateBlightsToDB(mAllList);
			DataMgr.checkBlightInfoInDB(mAllList);
			
			ArrayList<FormBean> arrForm = DataMgr.getForms(true, 0);
			DataMgr.updateFormsToDB(arrForm);
		} catch (Exception e){}

		return null;
	}
	
	@Override
	public void onTaskResult(int taskId, Object result) {
		closeWaitingDlg();
		refreshList();
	}
	*/
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btnLoad:
            startProgress();
            CommManager.getBlights(AppCommon.loadUserID(), -1, "", get_blights_handler);
			break;
		case R.id.btn_search:
			refreshList();
			break;
		}
	}
	
	@Override
	public void OnClickListener(int index)
	{
		BlightBean bean = mList.get(index);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(bean.id));
        pushNewActivityAnimated(ReportBlightActivity.class, bundle);
	}

    @Override
    public void initializeActivity() {
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String str = intent.getStringExtra(Constant.EXTRA_PARAM_BLIGHT_FLY);
            isFly = Boolean.parseBoolean(str);
        }

        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btnLoad).setOnClickListener(this);
        TextView titleView = (TextView)findViewById(R.id.txtTitle);
        EditText edtSearch = (EditText)findViewById(R.id.edt_search_str);

        if(isFly){
            titleView.setText(R.string.blight_fly);
            String str = getString(R.string.strHintBlightF);
            edtSearch.setHint(str);
        }
        else{
            titleView.setText(R.string.blight_disease);
            String str = getString(R.string.strHintBlightD);
            edtSearch.setHint(str);
        }

        mListView = (ListView) findViewById(R.id.list_data);

        mAdapter = new TwoTextListAdapter(this);
        mListView.setAdapter(mAdapter);
        mAllList = DataMgr.getBlightsFromDB("");
        mAdapter.setOnListener(this);
        initUI();

        EditText txtView = (EditText)findViewById(R.id.edt_search_str);

        txtView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshList();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    private AsyncHttpResponseHandler get_blights_handler = new AsyncHttpResponseHandler() {
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
                        BlightBean obj = ParserBlight.parseJsonResponse(arrItems.getJSONObject(i));
                        if(obj != null)
                            mAllList.add(obj);
                    }
                    initUI();
                    DataMgr.updateBlightsToDB(mAllList);

                    startProgress();
                    CommManager.getForms(0, get_forms_handler);
                }
                else {
                    AppCommon.showToast(ReportBlightListActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(ReportBlightListActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(ReportBlightListActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler get_forms_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    ArrayList<FormBean> arrForm = new ArrayList<FormBean>();

                    JSONArray arrItems = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < arrItems.length(); i++) {
                        FormBean obj = ParserForm.parseJsonResponse(arrItems.getJSONObject(i));
                        if(obj != null)
                            arrForm.add(obj);
                    }
                    DataMgr.updateFormsToDB(arrForm);
                }
                else {
                    AppCommon.showToast(ReportBlightListActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(ReportBlightListActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(ReportBlightListActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}