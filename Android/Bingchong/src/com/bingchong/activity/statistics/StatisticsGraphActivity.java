package com.bingchong.activity.statistics;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.widget.*;
import com.bingchong.*;
import com.bingchong.adapter.FieldListAdapter;
import com.bingchong.bean.*;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserBlight;
import com.bingchong.parser.ParserField;
import com.bingchong.parser.ParserForm;
import com.bingchong.parser.ParserValue;
import com.bingchong.utils.ResolutionSet;
import com.bingchong.widget.GraphView;
import com.bingchong.widget.DateTimePicker;
import com.bingchong.widget.DateTimePicker.OnDateChangeListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StatisticsGraphActivity extends SuperActivity implements OnClickListener, OnItemSelectedListener, OnDateChangeListener {

    private Spinner mSpSheng;
    private Spinner mSpShi;
	private Spinner mSpXian;
	private Spinner mSpBlight;
	private Spinner mSpForm;
	private Spinner mSpField;

    private int id_sheng 	= 0;
    private int id_shi 	= 0;
	private int id_xian 	= 0;
	private long id_blight	= 0;
	private int id_form		= 0;
	private int id_field	= 0;
	
	private DateTimePicker sDate;

    private ArrayList<XianBean> 	arrSheng 	= new ArrayList<XianBean>();
    private ArrayList<XianBean> 	arrShi 	= new ArrayList<XianBean>();
	private ArrayList<XianBean> 	arrXian 	= new ArrayList<XianBean>();
	private ArrayList<BlightBean>   arrBlight 	= new ArrayList<BlightBean>();
	private ArrayList<FormBean> 	arrForm 	= new ArrayList<FormBean>();
	private ArrayList<FieldBean> 	arrField 	= new ArrayList<FieldBean>();
	
	private ArrayList<ValueBean>    arrValue	= new ArrayList<ValueBean>();

    private Boolean	isInitedSheng = false;
    private Boolean	isInitedShi = false;
	private Boolean	isInitedXian = false;
	private Boolean	isInitedBlight = false;
	private Boolean	isInitedForm = false;
	private Boolean	isInitedField = false;
	
	private Boolean isRefreshedGraph = false;
	
	private GraphView mView;

    private Button btnSearch = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.st_graph);
	}
	
    public void initview()
    {
    	LinearLayout layout = (LinearLayout)findViewById(R.id.layout_graph);
    	mView = new GraphView(this);
    	mView.setIsPolygon(false);
    	layout.addView(mView);
    }		
	
	private void initXian(){
		arrXian 	= new ArrayList<XianBean>();
		XianBean obj = new XianBean(0, this.getString(R.string.strSelectXian), 0);
		arrXian.add(obj);
	}

    private void initSpinerSheng()
    {
        if(isInitedSheng)
            return;
        ArrayAdapter<XianBean> AA = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
        AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpSheng.setAdapter(AA);
        if(arrSheng.size() > 0)
            mSpSheng.setSelection(0);
        id_sheng = 0;
        isInitedSheng = true;
    }

    private void initSpinerShi()
    {
        if(isInitedShi)
            return;
        ArrayAdapter<XianBean> AA = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrShi);
        AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpShi.setAdapter(AA);
        if(arrShi.size() > 0)
            mSpShi.setSelection(0);
        id_shi = 0;
        isInitedShi = true;
    }

	private void initSpinerXian()
	{
		if(isInitedXian)
			return;
		ArrayAdapter<XianBean> AA = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrXian);
		AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpXian.setAdapter(AA);
		if(arrXian.size() > 0)
			mSpXian.setSelection(0);
		id_xian = 0;
		isInitedXian = true;
	}

	private void initSpinerBlight()
	{
		if(isInitedBlight)
			return;
		id_blight = 0;
		ArrayAdapter<BlightBean> AA = new ArrayAdapter<BlightBean>(this, R.layout.item_spinner_white, arrBlight);
		AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpBlight.setAdapter(AA);
		if(arrBlight.size() > 0){
			mSpBlight.setSelection(0);
			id_blight = arrBlight.get(0).id;
		}
		isInitedBlight = true;
	}
	
	private void initSpinerForm()
	{
		if(isInitedForm)
			return;
		if(!isInitedBlight)
			return;
		id_form = 0;
		ArrayAdapter<FormBean> AA = new ArrayAdapter<FormBean>(this, R.layout.item_spinner_white, arrForm);
		AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpForm.setAdapter(AA);
		if(arrForm.size() > 0){
			mSpForm.setSelection(0);
			id_form = arrForm.get(0).id;
		}
		isInitedForm = true;
	}
	
	private void initSpinerField()
	{
		if(!isInitedForm)
			return;
		if(isInitedField)
			return;
		id_field = 0;

        /*
		arrField = new ArrayList<FieldBean>();
		ArrayList<FieldBean> listField = null;
		
		int nType = mSpForm.getSelectedItemPosition();
		if(nType < arrForm.size() && nType >= 0)
		{
			listField = arrForm.get(nType).getFields();
		}

		if(listField != null){
			for(int i = 0; i < listField.size(); i++){
				FieldBean obj = listField.get(i);
				if(obj.state > 0)
					arrField.add(obj);
			}
		}

		FieldListAdapter AA = new FieldListAdapter(this, R.layout.item_spinner_white, arrField);
		AA.setDropDownViewResource(R.layout.item_spinner_dropdown);
		mSpField.setAdapter(AA);
		if(arrField.size() > 0){
			mSpField.setSelection(0);
			id_field = arrField.get(0).id;
		}
		isInitedField = true;
		*/

        FieldListAdapter AA = new FieldListAdapter(this, R.layout.item_spinner_white, arrField);
        AA.setDropDownViewResource(R.layout.item_spinner_dropdown);
        mSpField.setAdapter(AA);
        if(arrField.size() > 0){
            mSpField.setSelection(0);
            id_field = arrField.get(0).id;
        }
        isInitedField = true;
	}
	
	private void initCanvas(){
		if(isRefreshedGraph)
			return;

		mView.SetValues(arrValue);

		isRefreshedGraph = true;
	}
	
	private void initUI() {
		if(isInitedXian && isInitedBlight && isInitedForm && isInitedField){
			initCanvas();
		}
		else{
			initSpinerXian();

			initSpinerForm();
			initSpinerBlight();

			initSpinerField();
			refreshState();
		}
	}

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		try{
			if(!isInitedXian){
				ArrayList<XianBean> listXians = DataMgr.getXians();
				initXian();
				if(listXians != null)
					arrXian.addAll(listXians);
			}
			
			if(!isInitedBlight){
				arrBlight = DataMgr.getBlights("");
				if(arrBlight == null)
					arrBlight = new ArrayList<BlightBean>();
			}
			
			if(!isInitedForm && isInitedBlight){
				arrForm = DataMgr.getForms(true, id_blight);
				if(arrForm == null)
					arrForm = new ArrayList<FormBean>();
			}
			if(!isRefreshedGraph && isInitedXian && isInitedBlight && isInitedForm && isInitedField){
				arrValue = DataMgr.getAnalysisValues(id_xian, id_form, id_field, sDate.getDateString());
			}			
		} catch (Exception e){}

		return null;
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
		switch (id) {
		}
	}
	
	private void refreshState(){
		if(!isInitedXian || !isInitedBlight || !isInitedForm || !isRefreshedGraph) {
            arrValue.clear();
            mView.SetValues(arrValue);
            startProgress();
            CommManager.getReportBar(AppCommon.loadUserID(),
                    id_sheng, id_shi, id_xian, id_blight, id_form, sDate.getDateString(), id_field, get_reportbar_handler);
        }
		else {
            initUI();
        }
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		int spiner_id = parent.getId();
		try{
            if (spiner_id == R.id.spinner_sheng){
                isInitedShi = false;
                int nId = mSpSheng.getSelectedItemPosition();
                id_sheng = arrSheng.get(nId).id;
                if(AppCommon.loadUserInfo().level == 0 ||
                        AppCommon.loadUserInfo().level == 4 ||
                        AppCommon.loadUserInfo().level == 3) {
                    startProgress();
                    CommManager.getShis(id_sheng, get_shis_handler);
                }
            }
            else if (spiner_id == R.id.spinner_shi){
                isInitedXian = false;
                int nId = mSpShi.getSelectedItemPosition();
                id_shi = arrShi.get(nId).id;
                if(AppCommon.loadUserInfo().level == 0 ||
                        AppCommon.loadUserInfo().level == 4 ||
                        AppCommon.loadUserInfo().level == 3 ||
                        AppCommon.loadUserInfo().level == 2) {
                    startProgress();
                    CommManager.getXians(id_shi, get_xians_handler);
                }
            }
            else if (spiner_id == R.id.spinner_xian){
                int nId = mSpXian.getSelectedItemPosition();
                id_xian = arrXian.get(nId).id;
            }
            else if (spiner_id == R.id.spinner_blight){
                isInitedForm = false;
                isInitedField = false;
                int nId = mSpBlight.getSelectedItemPosition();
                id_blight= arrBlight.get(nId).id;
                startProgress();
                CommManager.getForms(id_blight, get_forms_handler);
            }
            else if (spiner_id == R.id.spinner_form){
                isInitedField = false;
                int nId = mSpForm.getSelectedItemPosition();
                id_form = arrForm.get(nId).id;
                startProgress();
                CommManager.getFields(id_form, get_fields_handler);
            }
            else if (spiner_id == R.id.spinner_field){
                int nId = mSpField.getSelectedItemPosition();
                id_field = arrField.get(nId).id;
            }
            isRefreshedGraph = false;
		}
		catch(Exception ex){

		}

        ResolutionSet.instance.iterateChild(view, mScrSize.x, mScrSize.y);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDateChanged() {
		isRefreshedGraph = false;
	}

    @Override
    public void initializeActivity() {

        // init control
        sDate = (DateTimePicker)findViewById(R.id.edt_sdate);
        sDate.setOnChangeListener(this);

        // init spinner
        mSpSheng = (Spinner) findViewById(R.id.spinner_sheng);
        mSpSheng.setOnItemSelectedListener(this);
        mSpShi = (Spinner) findViewById(R.id.spinner_shi);
        mSpShi.setOnItemSelectedListener(this);
        mSpXian = (Spinner) findViewById(R.id.spinner_xian);
        mSpXian.setOnItemSelectedListener(this);
        mSpBlight = (Spinner) findViewById(R.id.spinner_blight);
        mSpBlight.setOnItemSelectedListener(this);
        mSpForm = (Spinner) findViewById(R.id.spinner_form);
        mSpForm.setOnItemSelectedListener(this);
        mSpField = (Spinner) findViewById(R.id.spinner_field);
        mSpField.setOnItemSelectedListener(this);

        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshState();
            }
        });
        initview();

        rightInit();
        CommManager.getBlights(AppCommon.loadUserID(), -1, "", get_blights_handler);
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

        switch (userInfo.level)
        {
            case 0: //超管
                startProgress();
                CommManager.getShengs(get_shengs_handler);
                break;
            case 1: //县级
                id_sheng = (int)userInfo.shengs_id;
                id_shi = (int)userInfo.shis_id;
                id_xian = (int)userInfo.xians_id;

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
                id_sheng = (int)userInfo.shengs_id;
                id_shi = (int)userInfo.shis_id;

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

                //startProgress();
                //CommManager.getXians(id_shi, get_xians_handler);
                break;
            case 3: //省级
                id_sheng = (int)userInfo.shengs_id;

                shengItem.name = userInfo.sheng;
                shengItem.id = (int)userInfo.shengs_id;
                arrSheng.add(shengItem);

                mSpSheng.setEnabled(false);

                adapterSheng = new ArrayAdapter<XianBean>(this, R.layout.item_spinner_white, arrSheng);
                adapterSheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpSheng.setAdapter(adapterSheng);
                mSpSheng.setSelection(0);

                //startProgress();
                //CommManager.getXians(id_shi, get_xians_handler);
                break;
            case 4: //国家级
                startProgress();
                CommManager.getShengs(get_shengs_handler);
                break;
        }
    }

    private AsyncHttpResponseHandler get_reportbar_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    arrValue.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        ValueBean obj = ParserValue.parseJsonResponse((JSONObject) retdata.get(i));
                        if(obj != null)
                            arrValue.add(obj);
                    }
                    mView.SetValues(arrValue);
                }
                else {
                    AppCommon.showToast(StatisticsGraphActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(StatisticsGraphActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(StatisticsGraphActivity.this, R.string.STR_CONN_ERROR);
        }
    };

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
                    XianBean tmpBean = new XianBean();
                    tmpBean.name = "全部";
                    tmpBean.id = 0;
                    arrSheng.add(tmpBean);
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        XianBean obj = parseGetXiansResponse((JSONObject) retdata.get(i));
                        if(DataMgr.m_curUser.rights_id == UserBean.RIGHT_XIAN_MGR &&
                                DataMgr.m_curUser.xians_id != 0 &&
                                obj.id != DataMgr.m_curUser.xians_id)
                            continue;
                        arrSheng.add(obj);
                    }

                    initSpinerSheng();
                }
                else {
                    AppCommon.showToast(StatisticsGraphActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(StatisticsGraphActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(StatisticsGraphActivity.this, R.string.STR_CONN_ERROR);
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
                    XianBean tmpBean = new XianBean();
                    tmpBean.name = "全部";
                    tmpBean.id = 0;
                    arrShi.add(tmpBean);
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        XianBean obj = parseGetXiansResponse((JSONObject) retdata.get(i));
                        if(DataMgr.m_curUser.rights_id == UserBean.RIGHT_XIAN_MGR &&
                                DataMgr.m_curUser.xians_id != 0 &&
                                obj.id != DataMgr.m_curUser.xians_id)
                            continue;
                        arrShi.add(obj);
                    }

                    initSpinerShi();
                }
                else {
                    AppCommon.showToast(StatisticsGraphActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(StatisticsGraphActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(StatisticsGraphActivity.this, R.string.STR_CONN_ERROR);
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
                    XianBean tmpBean = new XianBean();
                    tmpBean.name = "全部";
                    tmpBean.id = 0;
                    arrXian.add(tmpBean);
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        XianBean obj = parseGetXiansResponse((JSONObject) retdata.get(i));
                        if(DataMgr.m_curUser.rights_id == UserBean.RIGHT_XIAN_MGR &&
                                DataMgr.m_curUser.xians_id != 0 &&
                                obj.id != DataMgr.m_curUser.xians_id)
                            continue;
                        arrXian.add(obj);
                    }

                    initSpinerXian();
                }
                else {
                    AppCommon.showToast(StatisticsGraphActivity.this, stRetMsg);
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
                    arrBlight.clear();
                    JSONArray arrItems = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < arrItems.length(); i++) {
                        BlightBean obj = ParserBlight.parseJsonResponse(arrItems.getJSONObject(i));
                        if(obj != null)
                            arrBlight.add(obj);
                    }
                    initSpinerBlight();
                }
                else {
                    AppCommon.showToast(StatisticsGraphActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(StatisticsGraphActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(StatisticsGraphActivity.this, R.string.STR_CONN_ERROR);
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
                    arrForm.clear();
                    JSONArray arrItems = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < arrItems.length(); i++) {
                        FormBean obj = ParserForm.parseJsonResponse(arrItems.getJSONObject(i));
                        if(obj != null)
                            arrForm.add(obj);
                    }
                    initSpinerForm();
                }
                else {
                    AppCommon.showToast(StatisticsGraphActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(StatisticsGraphActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(StatisticsGraphActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler get_fields_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    arrField.clear();
                    JSONArray arrItems = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < arrItems.length(); i++) {
                        FieldBean obj = ParserField.parseJsonResponse(arrItems.getJSONObject(i));
                        if(obj != null) {
                            if(obj.fieldType == FieldBean.FIELD_NUMBER || obj.fieldType == FieldBean.FIELD_REAL)
                                arrField.add(obj);
                        }
                    }
                    initSpinerField();
                }
                else {
                    AppCommon.showToast(StatisticsGraphActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(StatisticsGraphActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(StatisticsGraphActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}