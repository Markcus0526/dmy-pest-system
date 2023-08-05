package com.bingchong.activity.db;

import java.util.ArrayList;

import com.bingchong.*;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.PointListAdapter;
import com.bingchong.adapter.SingleTextListAdapter;
import com.bingchong.bean.PointBean;
import com.bingchong.bean.UserBean;
import com.bingchong.bean.XianBean;
import com.bingchong.db.DataMgr;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserPlace;
import com.bingchong.utils.ResolutionSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBPlaceSearchActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener, OnItemSelectedListener {

	private ListView mListView;
	private PointListAdapter mAdapter;
	private String mSearchKey = "";

	private Spinner mSpLevel;
    private Spinner mSpType;
    private Spinner mSpSheng;
    private Spinner mSpShi;
    private Spinner mSpXian;

    private ArrayList<String> arrLevel = new ArrayList<String>();
    private ArrayList<String> arrType = new ArrayList<String>();
	private ArrayList<XianBean> arrSheng = new ArrayList<XianBean>();
    private ArrayList<XianBean> arrShi = new ArrayList<XianBean>();
    private ArrayList<XianBean> arrXian = new ArrayList<XianBean>();

	private ArrayList<PointBean> mList = new ArrayList<PointBean>();
	private ArrayList<PointBean> mAllList = new ArrayList<PointBean>();

    private int mNowLevel = -1;  // 0 - guojia, 1 - sheng, 2 - shi, 3 - xian
    private int mNowType = -1;   // 0 - guding, 1 - feiguding
    private int mNowShengId = 0;
    private int mNowShiId = 0;
    private int mNowXianId = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.db_place);
	}

    private void initSpinerPosition(int level)
    {
        if(level == 0) {
            ArrayAdapter<XianBean> adapterSheng = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
            adapterSheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpSheng.setAdapter(adapterSheng);
            mSpSheng.setSelection(0);
        }
        else if(level == 1) {
            ArrayAdapter<XianBean> adapterShi = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrShi);
            adapterShi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpShi.setAdapter(adapterShi);
            mSpShi.setSelection(0);
        }
        else if(level == 2) {
            ArrayAdapter<XianBean> adapterXian = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrXian);
            adapterXian.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpXian.setAdapter(adapterXian);
            mSpXian.setSelection(0);
        }
    }

	private void initUI() {
		EditText txtView = (EditText)findViewById(R.id.edt_search_str);
		mSearchKey = txtView.getText().toString().trim();

		mList = new ArrayList<PointBean>();
		for(int i = 0; i < mAllList.size();i++){
			PointBean obj = mAllList.get(i);
			Boolean isAdd = true;
			if(mSearchKey.length() > 0 && obj.toString().indexOf(mSearchKey)<0)
				isAdd = false;
			if(isAdd)
				mList.add(obj);
		}
		if (mList != null) {
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	private void refreshList(){
		EditText txtView = (EditText)findViewById(R.id.edt_search_str);
		mSearchKey = txtView.getText().toString().trim();
        startProgress();
        CommManager.getPoints(AppCommon.loadUserID(), mNowShengId, mNowShiId, mNowXianId, mSearchKey, mNowType, mNowLevel, "", get_points_handler);
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
		int id = mList.get(index).id;

        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(id));

		pushNewActivityAnimated(DBPlaceInfoActivity.class, bundle);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)parent;

        if (spinner.getId() == R.id.spinner_type){
            mNowType = position - 1;
            refreshList();
        }
        else if(spinner.getId() == R.id.spinner_level){
            mNowLevel = Global.getLevelIndex(arrLevel.get(position));
            refreshList();
        }
        else if(spinner.getId() == R.id.spinner_sheng) {
            mNowShengId = arrSheng.get(position).id;
            if(AppCommon.loadUserInfo().level == 0 ||
                    AppCommon.loadUserInfo().level == 4 ||
                    AppCommon.loadUserInfo().level == 3) {
                startProgress();
                CommManager.getShis(mNowShengId, get_shis_handler);
            }
        }
        else if(spinner.getId() == R.id.spinner_shi) {
            if(AppCommon.loadUserInfo().level == 0 ||
                    AppCommon.loadUserInfo().level == 4 ||
                    AppCommon.loadUserInfo().level == 3 ||
                    AppCommon.loadUserInfo().level == 2) {
                mNowShiId = arrShi.get(position).id;
                startProgress();
                CommManager.getXians(mNowShiId, get_xians_handler);
            }
        }
        else if (spinner.getId() == R.id.spinner_xian){
            mNowXianId = arrXian.get(position).id;
            refreshList();
        }

        ResolutionSet.instance.iterateChild(view, mScrSize.x, mScrSize.y);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void initializeActivity() {
        findViewById(R.id.btn_search).setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.list_data);

        mSpSheng = (Spinner) findViewById(R.id.spinner_sheng);
        mSpSheng.setOnItemSelectedListener(this);
        mSpShi = (Spinner) findViewById(R.id.spinner_shi);
        mSpShi.setOnItemSelectedListener(this);
        mSpXian = (Spinner) findViewById(R.id.spinner_xian);
        mSpXian.setOnItemSelectedListener(this);
        mSpLevel = (Spinner) findViewById(R.id.spinner_level);
        mSpLevel.setOnItemSelectedListener(this);
        mSpType = (Spinner) findViewById(R.id.spinner_type);
        mSpType.setOnItemSelectedListener(this);

        arrType.add("全部");
        arrType.add("固定");
        arrType.add("非固定");
        ArrayAdapter<String> adapterPointType = new ArrayAdapter<String>(this, R.layout.item_spinner_white, arrType);
        adapterPointType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpType.setAdapter(adapterPointType);
        mSpType.setSelection(0);


        mAdapter = new PointListAdapter(this);
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

        rightInit();
    }

    private void rightInit()
    {
        UserBean userInfo = AppCommon.loadUserInfo();
        XianBean shengItem = new XianBean();
        XianBean shiItem = new XianBean();
        XianBean xianItem = new XianBean();

        ArrayAdapter<XianBean> adapterSheng = null;
        ArrayAdapter<XianBean> adapterShi = null;
        ArrayAdapter<XianBean> adapterXian = null;

        arrLevel.add("全部");
        arrLevel.add("国家级");

        switch (userInfo.level)
        {
            case 0: //超管
                arrLevel.add("省级");
                arrLevel.add("市级");
                arrLevel.add("县级");
                startProgress();
                CommManager.getShengs(get_shengs_handler);
                break;
            case 1: //县级
                arrLevel.add("县级");
                mNowShengId = (int)userInfo.shengs_id;
                mNowShiId = (int)userInfo.shis_id;
                mNowXianId = (int)userInfo.xians_id;

                shengItem.name = userInfo.sheng;
                shengItem.id = (int)userInfo.shengs_id;
                arrSheng.add(shengItem);
                shiItem.name = userInfo.shi;
                shiItem.id = (int)userInfo.shis_id;
                arrShi.add(shiItem);
                xianItem.name = userInfo.xian;
                xianItem.id = (int)userInfo.xians_id;
                arrXian.add(xianItem);

                mSpSheng.setEnabled(false);
                mSpShi.setEnabled(false);
                mSpXian.setEnabled(false);

                adapterSheng = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
                adapterSheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpSheng.setAdapter(adapterSheng);
                mSpSheng.setSelection(0);

                adapterShi = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrShi);
                adapterShi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpShi.setAdapter(adapterShi);
                mSpShi.setSelection(0);

                adapterXian = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrXian);
                adapterXian.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpXian.setAdapter(adapterXian);
                mSpXian.setSelection(0);
                break;
            case 2: //市级
                arrLevel.add("市级");
                mNowShengId = (int)userInfo.shengs_id;
                mNowShiId = (int)userInfo.shis_id;

                shengItem.name = userInfo.sheng;
                shengItem.id = (int)userInfo.shengs_id;
                arrSheng.add(shengItem);
                shiItem.name = userInfo.shi;
                shiItem.id = (int)userInfo.shis_id;
                arrShi.add(shiItem);

                mSpSheng.setEnabled(false);
                mSpShi.setEnabled(false);

                adapterSheng = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
                adapterSheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpSheng.setAdapter(adapterSheng);
                mSpSheng.setSelection(0);

                adapterShi = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrShi);
                adapterShi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpShi.setAdapter(adapterShi);
                mSpShi.setSelection(0);

                startProgress();
                CommManager.getXians(mNowShiId, get_xians_handler);
                break;
            case 3: //省级
                arrLevel.add("省级");
                mNowShengId = (int)userInfo.shengs_id;

                shengItem.name = userInfo.sheng;
                shengItem.id = (int)userInfo.shengs_id;
                arrSheng.add(shengItem);

                mSpSheng.setEnabled(false);

                adapterSheng = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
                adapterSheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpSheng.setAdapter(adapterSheng);
                mSpSheng.setSelection(0);

                startProgress();
                CommManager.getXians(mNowShiId, get_xians_handler);
                break;
            case 4: //国家级
                arrLevel.add("省级");
                arrLevel.add("市级");
                arrLevel.add("县级");
                startProgress();
                CommManager.getShengs(get_shengs_handler);
                break;
        }

        ArrayAdapter<String> adapterPointLevel = new ArrayAdapter<String>(this, R.layout.item_spinner_white, arrLevel);
        adapterPointLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpLevel.setAdapter(adapterPointLevel);
        mSpLevel.setSelection(0);
    }

    private AsyncHttpResponseHandler get_shengs_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    arrSheng.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        XianBean obj = parseGetXiansResponse((JSONObject) retdata.get(i));
                        if(DataMgr.m_curUser.rights_id == UserBean.RIGHT_XIAN_MGR &&
                                DataMgr.m_curUser.xians_id != 0 &&
                                obj.id != DataMgr.m_curUser.xians_id)
                            continue;
                        arrSheng.add(obj);
                    }

                    initSpinerPosition(0);
                }
                else {
                    AppCommon.showToast(DBPlaceSearchActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(DBPlaceSearchActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(DBPlaceSearchActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler get_shis_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    arrShi.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        XianBean obj = parseGetXiansResponse((JSONObject) retdata.get(i));
                        if(DataMgr.m_curUser.rights_id == UserBean.RIGHT_XIAN_MGR &&
                                DataMgr.m_curUser.xians_id != 0 &&
                                obj.id != DataMgr.m_curUser.xians_id)
                            continue;
                        arrShi.add(obj);
                    }

                    initSpinerPosition(1);
                }
                else {
                    AppCommon.showToast(DBPlaceSearchActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(DBPlaceSearchActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(DBPlaceSearchActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    AsyncHttpResponseHandler get_xians_handler = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {

            stopProgress();
            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.

            try
            {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    arrXian.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        XianBean obj = parseGetXiansResponse((JSONObject) retdata.get(i));
                        if(DataMgr.m_curUser.rights_id == UserBean.RIGHT_XIAN_MGR &&
                                DataMgr.m_curUser.xians_id != 0 &&
                                obj.id != DataMgr.m_curUser.xians_id)
                            continue;
                        arrXian.add(obj);
                    }

                    initSpinerPosition(2);
                }
                else {
                    AppCommon.showToast(DBPlaceSearchActivity.this, stRetMsg);
                }
            }
            catch (Exception ex)
            { ex.printStackTrace(); }

        }

        @Override
        public void onFailure(Throwable error, String content) {
            stopProgress();
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
        }
    };

    private XianBean parseGetXiansResponse(JSONObject jsonObject) throws JSONException {
        XianBean obj = new XianBean();
        try {
            obj.id = jsonObject.getInt(XianBean.ID);
            obj.name = jsonObject.getString(XianBean.NAME);
            obj.shi_id = jsonObject.getInt(XianBean.SHI_ID);
            return obj;
        }
        catch (JSONException ex) {
        }
        return obj;
    }

    AsyncHttpResponseHandler get_points_handler = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {
            stopProgress();
            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.

            try
            {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    mAllList.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        PointBean obj = ParserPlace.parseJsonResponse((JSONObject) retdata.get(i));
                        if(obj != null)
                            mAllList.add(obj);
                    }

                    initUI();
                }
                else {
                    AppCommon.showToast(DBPlaceSearchActivity.this, stRetMsg);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                AppCommon.showDebugToast(DBPlaceSearchActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
        }
    };
}