package com.bingchong.activity.db;

import java.util.ArrayList;

import com.bingchong.*;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.PointListAdapter;
import com.bingchong.adapter.SingleTextListAdapter;
import com.bingchong.bean.PointBean;
import com.bingchong.bean.UserBean;
import com.bingchong.bean.WatcherBean;
import com.bingchong.db.DataMgr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserPlace;
import org.json.JSONArray;
import org.json.JSONObject;

public class DBUserPointActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener{

	private ListView mListView;
	private PointListAdapter mAdapter;
	private ArrayList<PointBean> mList = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.db_user_place);
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
			ArrayList<PointBean> list = DataMgr.getPointsWithInfoAndUpdateDB(0);
			if(list == null || list.size() == 0) // case network fail
				return null;
			
			mList = list;
			WatcherBean bean = DataMgr.getCurentPointList();
			
			if(bean != null){
				for(int i = 0; i < mList.size(); i++)
				{
					PointBean obj = mList.get(i);
					if(bean.isContainID(obj.id))
						obj.setCheck(true);
				}
			}

			DataMgr.updatePointsToDB(mList);
//			DataMgr.checkPointInfoInDB(mList);
			
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
		case R.id.btnLoad:
            startProgress();
            UserBean userInfo = AppCommon.loadUserInfo();
            CommManager.getPoints(AppCommon.loadUserID(), 0, 0, 0, "", -1, -1, "", get_points_handler);
			//startGetStatusTask();
			break;
		}
	}
	
	@Override
	public void OnClickListener(int index)
	{
		int id = mList.get(index).id;

        Bundle bundle = new Bundle();

		bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(id));
		bundle.putString(Constant.EXTRA_PARAM_LOCAL, String.valueOf(false));

        pushNewActivityAnimated(DBPlaceInfoActivity.class, bundle);
	}

    @Override
    public void initializeActivity() {
        findViewById(R.id.btnLoad).setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.list_data);

        mAdapter = new PointListAdapter(this);
        mListView.setAdapter(mAdapter);

        mAdapter.setOnListener(this);

        mList = DataMgr.getPointsFromDB();

        initUI();
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
                    mList.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        PointBean obj = ParserPlace.parseJsonResponse((JSONObject) retdata.get(i));
                        obj.setDisplayType(PointBean.DISP_TYPE_NICKNAME);
                        UserBean userInfo = AppCommon.loadUserInfo();
                        String[] arrMyPoints = userInfo.point_list.split(",");
                        for(String pointItem : arrMyPoints)
                        {
                            if(pointItem.length() <= 0)
                                continue;
                            if(obj.id == Integer.parseInt(pointItem))
                                obj.setCheck(true);
                        }
                        if(obj != null)
                            mList.add(obj);
                    }

                    initUI();
                    DataMgr.updatePointsToDB(mList);
                }
                else {
                    AppCommon.showToast(DBUserPointActivity.this, stRetMsg);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                AppCommon.showDebugToast(DBUserPointActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            stopProgress();
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
        }
    };
}