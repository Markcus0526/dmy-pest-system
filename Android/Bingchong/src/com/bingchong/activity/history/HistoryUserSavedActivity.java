package com.bingchong.activity.history;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import com.bingchong.*;
import com.bingchong.activity.report.ReportInfoActivity;
import com.bingchong.activity.task.TaskFixDetailActivity;
import com.bingchong.adapter.LocalReportItemAdapter;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleTextListAdapter;
import com.bingchong.bean.FieldBean;
import com.bingchong.bean.ReportBean;
import com.bingchong.bean.TaskBean;
import com.bingchong.bean.TaskReportBean;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserTask;
import com.bingchong.widget.DateTimePicker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DavidYin on 2015.03.18.
 */
public class HistoryUserSavedActivity extends SuperActivity  implements View.OnClickListener, OnClickItemAdapterListener, DateTimePicker.OnDateChangeListener  {
    // for list view
    private ListView mListView;
    private LocalReportItemAdapter mAdapter;
    private ArrayList<ReportBean> mList = new ArrayList<ReportBean>();
    private DateTimePicker      sDatePicker;
    private DateTimePicker      eDatePicker;

    public ReportBean	m_curReport = null;
    public int          mNowReportingIndex = -1;

    private Button btnReport = null;
    private CheckBox checkAll = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.history_user_saved);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id)
        {
            case R.id.btn_report:
                if(mList.size() > 0) {
                    uploadReports();
                }
                break;
            case R.id.check_all:
                for(ReportBean reportBean : mList) {
                    reportBean.checked = checkAll.isChecked();
                }
                initUI();
                break;
        }
    }

    @Override
    public void OnClickListener(int index)
    {
        m_curReport = mList.get(index);

        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_PARAM_TYPE, getString(R.string.history_user_type_saved));
        bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(m_curReport.id));
        pushNewActivityAnimated(ReportInfoActivity.class, bundle);
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
//        Date    startTime = sDatePicker.getDate().getTime();
//        Date    endTime = eDatePicker.getDate().getTime();
//
//        long st = Date.UTC(startTime.getYear(), startTime.getMonth(), startTime.getDate(), 0, 0, 0);
//        long et = Date.UTC(endTime.getYear(), endTime.getMonth(), endTime.getDate(), 23, 59, 59);
        ArrayList<ReportBean> tmpList = DataMgr.getReportsFromDB();
        mList.clear();
        for(ReportBean reportBean : tmpList)
        {
            if(reportBean.testDate >= sDatePicker.getTimeInMillis() && reportBean.testDate <= eDatePicker.getTimeInMillis())
                mList.add(reportBean);
        }
        initUI();
    }

    private void uploadReports()
    {
        mNowReportingIndex = 0;
        boolean reportExist = false;
        for(ReportBean reportBean : mList) {
            if(!reportBean.checked) {
                mNowReportingIndex++;
                continue;
            }

            ArrayList<FieldBean> fields = reportBean.reportForm.getFields();

            String strFields = "";

            for(int i = 0; i < fields.size(); i++)
            {
                FieldBean fi = fields.get(i);
                if(fi.fieldType == FieldBean.FIELD_PARENT)
                    continue;

                strFields = (strFields + fi.id + ":" + fi.val);
                if(i < fields.size() - 1)
                    strFields += ",";
            }

            startProgress();
            CommManager.setReports(AppCommon.loadUserID(),
                    reportBean.form_id,
                    reportBean.point_id,
                    reportBean.blight_id,
                    "",
                    Global.getDate(reportBean.testDate, "yyyy-MM-dd HH:mm"),
                    strFields,
                    set_report_handler);

            reportExist = true;

            break;
        }

        if(!reportExist)
            refreshList();
    }

    @Override
    public void initializeActivity() {

        // init list view
        mListView = (ListView) findViewById(R.id.list_data);
        mAdapter = new LocalReportItemAdapter(this);
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

        btnReport = (Button)findViewById(R.id.btn_report);
        btnReport.setOnClickListener(this);

        checkAll = (CheckBox)findViewById(R.id.check_all);
        checkAll.setOnClickListener(this);
        refreshList();
    }

    private AsyncHttpResponseHandler set_report_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    DataMgr.removeReportFromDB(mList.get(mNowReportingIndex).id);
                    mList.remove(mNowReportingIndex);
                    uploadReports();
                    boolean reportExist = false;
                    for(ReportBean reportBean : mList) {
                        if (reportBean.checked) {
                            reportExist = true;
                            break;
                        }
                    }
                    if(!reportExist) {
                        AppCommon.showToast(HistoryUserSavedActivity.this, "上报成功");
                    }
                }
                else {
                    AppCommon.showToast(HistoryUserSavedActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(HistoryUserSavedActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}