package com.bingchong.activity.review;

import java.util.ArrayList;

import com.bingchong.Constant;
import com.bingchong.R;
import com.bingchong.SuperActivity;
import com.bingchong.WaitingTaskActivity;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleAcceptAdapter;
import com.bingchong.bean.PointBean;
import com.bingchong.db.DataMgr;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

public class ReviewPlaceActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener{

	// for list view
	private ListView mListView;
	private SingleAcceptAdapter mAdapter;
	private ArrayList<PointBean> mList = new ArrayList<PointBean>();
	private ArrayList<PointBean> mAllList = new ArrayList<PointBean>();
	
	// for detail view
	private WebView mView;

	// for common
	private LinearLayout mLayoutDetail = null;
	private LinearLayout mLayoutList   = null;
	public RadioGroup radioGroup = null;
	public PointBean m_curPlace = null;
	public boolean	m_bShowCompleteTask = false;	
	
	public boolean isReqUpdate = false;	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.review_place);
	}
	
    public void initWebView()
    {
    	mView=(WebView)findViewById(R.id.wv_info);
    	
    	mView.setBackgroundColor(this.getResources().getColor(R.color.white));
    	WebSettings mWebSettings01;
    	mWebSettings01 = mView.getSettings();
    	
    	mView.getSettings().setBuiltInZoomControls(true);  
    	mView.getSettings().setSupportZoom(true); 
    	mView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
    	//mView.setInitialScale(140);
    	 // AppCacheMaxSize in 512 KB = 524288 bytes
    	mWebSettings01.setAppCacheMaxSize(524288);
    	mWebSettings01.setAppCacheEnabled(true);
    }		

	private void initUI() {
		
		mList.clear();
		
		for(int i = 0; i < mAllList.size(); i++)
		{
			PointBean obj = mAllList.get(i);
			if(m_bShowCompleteTask)
			{
				if(obj.status != Constant.REVIEW_WAITING)
					mList.add(obj);
			}
			else{
				if(obj.status == Constant.REVIEW_WAITING)
					mList.add(obj);
			}
		}
		
		if(m_bShowCompleteTask){
			findViewById(R.id.btnAccept).setVisibility(View.GONE);
			findViewById(R.id.btnNoAccept).setVisibility(View.GONE);
		}
		else{
			findViewById(R.id.btnAccept).setVisibility(View.VISIBLE);
			findViewById(R.id.btnNoAccept).setVisibility(View.VISIBLE);
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
            //startGetStatusTask();
        }
		else {
            initUI();
        }
	}
	
	private void showDetail(){
		mLayoutDetail.setVisibility(View.VISIBLE);
		mLayoutList.setVisibility(View.GONE);
		
		String url = Constant.URL_SERVER + Constant.URL_GET_TEMP_POINT_INFO + "?point_id=" + m_curPlace.id;
		mView.loadUrl(url);
	}

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		try{
			// update status
			if(isReqUpdate){
				DataMgr.updateExtraPoint(m_curPlace);
			}
			
			mAllList = DataMgr.getPoints("", 0, true);
			
			isReqUpdate = false;

			
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
		case R.id.btnComplete:
			m_bShowCompleteTask = true;
			showList(false);
			break;
		case R.id.btnNoComplete:
			m_bShowCompleteTask = false;
			showList(false);
			break;
		case R.id.btnAccept:
			m_curPlace.status = Constant.REVIEW_ACCEPT;
			isReqUpdate = true;
			showList(true);
			break;
		case R.id.btnNoAccept:
			m_curPlace.status = Constant.REVIEW_NOACCEPT;
			isReqUpdate = true;
			showList(true);
			break;
		case R.id.btnBack:
			showList(false);
			break;
		}
	}
	
	@Override
	public void OnClickListener(int index)
	{
		m_curPlace = mList.get(index);
		showDetail();
	}

    @Override
    public void initializeActivity() {
        // init common
        radioGroup = (RadioGroup) this.findViewById(R.id.main_radio_group);
        findViewById(R.id.btnComplete).setOnClickListener(this);
        findViewById(R.id.btnNoComplete).setOnClickListener(this);
        findViewById(R.id.btnAccept).setOnClickListener(this);
        findViewById(R.id.btnNoAccept).setOnClickListener(this);

        mLayoutDetail = (LinearLayout)findViewById(R.id.task_detail_layout);
        mLayoutList = (LinearLayout)findViewById(R.id.list_layout);

        // init list view
        mListView = (ListView) findViewById(R.id.list_data);
        mAdapter = new SingleAcceptAdapter(this, DataMgr.m_curUser.isAdmin());
        mListView.setAdapter(mAdapter);
        mAdapter.setOnListener(this);

        initWebView();

        showList(true);
    }
}