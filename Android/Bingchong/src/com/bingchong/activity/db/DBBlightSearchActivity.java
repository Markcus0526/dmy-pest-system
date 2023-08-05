package com.bingchong.activity.db;

import java.util.ArrayList;

import com.bingchong.*;
import com.bingchong.activity.HomeMgrActivity;
import com.bingchong.activity.HomeUserActivity;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleTextListAdapter;
import com.bingchong.bean.BlightBean;
import com.bingchong.db.DataMgr;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserBlight;
import com.bingchong.utils.AppPreferences;
import com.bingchong.utils.ResolutionSet;
import org.json.JSONArray;
import org.json.JSONObject;

public class DBBlightSearchActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener, OnItemSelectedListener {

	private ListView mListView;
	private SingleTextListAdapter mAdapter;
	private ArrayList<BlightBean> mList = new ArrayList<BlightBean>();
	private ArrayList<BlightBean> mListAll = new ArrayList<BlightBean>();
	private String mSearchKey = "";
	
	private final int SPINNER_ID_BLIGHT_TYPE = 103;
	private static int INDEX_TYPE = 0;
	private Spinner mSpinnerBlightType;

    private EditText txtSearch = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.db_blight);
	}
	
	private void refreshList(){
		mSearchKey = txtSearch.getText().toString().trim();
		initUI();		
	}	


	private void initUI() {
		if (mListAll != null) {
			mList = new ArrayList<BlightBean>();
			for(int i = 0; i < mListAll.size(); i++)
			{
				BlightBean obj = mListAll.get(i);
				
				if(mSearchKey.length() > 0 && obj.toString().indexOf(mSearchKey)<0)
					continue;
				
				Boolean blAdd = false;
				if(obj.isFly())
				{
					if(INDEX_TYPE == 2)
						blAdd = true;
				}
				else
				{
					if(INDEX_TYPE == 1)
						blAdd = true;				
				}
				if(INDEX_TYPE == 0)
					blAdd = true;
				
				if(blAdd)
					mList.add(obj);
			}
			
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_search:
			refreshList();
			break;
		}
	}
	
	@Override
	public void OnClickListener(int index)
	{
		long id = mList.get(index).id;

		Bundle bundle = new Bundle();
		bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(id));

		pushNewActivityAnimated(DBBlightInfoActivity.class, bundle);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.spinner_blight_type){
			INDEX_TYPE = position;
			initUI();
		}

        ResolutionSet.instance.iterateChild(view, mScrSize.x, mScrSize.y);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void initializeActivity() {

        txtSearch = (EditText)findViewById(R.id.edt_search_str);

        findViewById(R.id.btn_search).setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.list_data);

        // init spinner
        mSpinnerBlightType = (Spinner) findViewById(R.id.spinner_blight_type);
        mSpinnerBlightType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.blight_types, R.layout.item_spinner_white);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlightType.setAdapter(adapter);
        mSpinnerBlightType.setSelection(INDEX_TYPE);

        mAdapter = new SingleTextListAdapter(this);
        mListView.setAdapter(mAdapter);
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

        mAdapter.setOnListener(this);

        startProgress();
        CommManager.getBlights(AppCommon.loadUserID(), -1, txtSearch.getText().toString(), get_blights_handler);
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
                    JSONArray arrItems = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < arrItems.length(); i++) {
                        BlightBean obj = ParserBlight.parseJsonResponse(arrItems.getJSONObject(i));
                        if(obj != null)
                            mListAll.add(obj);
                    }
                    initUI();
                }
                else {
                    AppCommon.showToast(DBBlightSearchActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(DBBlightSearchActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(DBBlightSearchActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}