package com.bingchong.activity.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.bingchong.*;
import com.bingchong.activity.HomeMgrActivity;
import com.bingchong.activity.HomeUserActivity;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserObject;
import com.bingchong.utils.AppDateTimeUtils;
import com.bingchong.utils.MyUtils;
import com.bingchong.utils.ResolutionSet;
import com.bingchong.widget.CalendarView;
import com.bingchong.widget.CalendarView.OnClickDayListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import org.json.JSONObject;

public class TaskUserActivity  extends SuperActivity implements OnClickListener, AdapterView.OnItemSelectedListener {


	private TextView txtDay;
	
	private CalendarView viewCalendar;
	private Calendar	m_curDay;
	private ArrayList<String> mListCount = new ArrayList<String>();
	
	private String startDay = "";
	private String endDay = "";

	private int mNowType = 0;   // 0 - guding, 1 - feiguding
	private ArrayList<String> arrType = new ArrayList<String>();
	private Spinner mSpType;
	
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.task_me);
	}

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		try{
			mListCount = DataMgr.getTaskCounts(startDay, endDay);
		} catch (Exception e){}

		return null;
	}


	@Override
	public void onTaskResult(int taskId, Object result) {

		viewCalendar.setWorksCount(mListCount);
	}
	*/
	
	public void setDay(long time)
	{
		m_curDay.setTimeInMillis(time);
		
		String date = "" + m_curDay.get(Calendar.YEAR) + "    " +
				(m_curDay.get(Calendar.MONTH) + 1);
		
		txtDay.setText(date);
		
		viewCalendar.setCurDate(m_curDay.getTimeInMillis());
		long tick = viewCalendar.getFirstDate();
		startDay = AppDateTimeUtils.getDate(tick, Constant.dayFormat);
		tick = viewCalendar.getLastDate();
		endDay= AppDateTimeUtils.getDate(tick, Constant.dayFormat);
		
        refreshCalendar();
	}
	
	private void changeMonth(boolean inc)
	{
		int month = m_curDay.get(Calendar.MONTH);
		int year = m_curDay.get(Calendar.YEAR);
		if(inc)
		{
			if(month == Calendar.DECEMBER)
			{
				month = Calendar.JANUARY;
				year = year + 1;
			}
			else
				month = month + 1;
		}
		else
		{
			if(month == Calendar.JANUARY)
			{
				month = Calendar.DECEMBER;
				year = year - 1;
			}
			else
				month = month - 1;			
		}
		
		m_curDay.set(Calendar.MONTH, month);
		m_curDay.set(Calendar.YEAR, year);
		
		setDay(m_curDay.getTimeInMillis());
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id)
		{
		case R.id.btn_left:
			changeMonth(false);
			break;
		case R.id.btn_right:
			changeMonth(true);
			break;
		case R.id.btn_task_temp:
		{
			pushNewActivityAnimated(TaskActivity.class);
		}
		break;
		}
	}

    @Override
    public void initializeActivity() {
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);
        findViewById(R.id.btn_task_temp).setOnClickListener(this);

        m_curDay = Calendar.getInstance();
        txtDay = (TextView)findViewById(R.id.txt_day);
        viewCalendar = (CalendarView)findViewById(R.id.calendar_view);

        String[] weekNames = getResources().getStringArray(R.array.week_name);
        viewCalendar.setWeekNames(weekNames);

        setDay(System.currentTimeMillis());

        viewCalendar.setOnClickDayListener(new OnClickDayListener() {
            @Override
            public void OnClickDayListener(long time) {
                String date = AppDateTimeUtils.getDate(time, Constant.dayFormat);
				Bundle bundle = new Bundle();
                bundle.putString(Constant.EXTRA_PARAM_DATE, date);
				bundle.putInt(Constant.EXTRA_PARAM_POINT_TYPE, mNowType);
				pushNewActivityAnimated(TaskListActivity.class, bundle);
            }
        });

		mSpType = (Spinner) findViewById(R.id.spinner_type);
		mSpType.setOnItemSelectedListener(this);

		arrType.add("固定测报点");
		arrType.add("非固定测报点");
		ArrayAdapter<String> adapterPointType = new ArrayAdapter<String>(this, R.layout.item_spinner_white, arrType);
		adapterPointType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpType.setAdapter(adapterPointType);
		mSpType.setSelection(0);
    }

	private void refreshCalendar()
	{
		startProgress();
		CommManager.getTaskCount(AppCommon.loadUserID(),
				mNowType,
				startDay,
				endDay,
				get_taskcount_handler);
	}

    private AsyncHttpResponseHandler get_taskcount_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

				mListCount.clear();

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    String retdata = result.getString(ConstMgr.gRetData);
                    for(String count : retdata.split(","))
                    {
                        mListCount.add(count);
                    }
                }
                else {
                    AppCommon.showToast(TaskUserActivity.this, stRetMsg);
                }

				viewCalendar.setWorksCount(mListCount);
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(TaskUserActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(TaskUserActivity.this, "现在没有任务,请您查看测报点选择是否有问题。");
        }
    };

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Spinner spinner = (Spinner)parent;

		if (spinner.getId() == R.id.spinner_type){
			if(mNowType != position) {
				mNowType = position;
				viewCalendar.setPointType(mNowType);
				refreshCalendar();
			}
		}

		ResolutionSet.instance.iterateChild(view, mScrSize.x, mScrSize.y);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}