package com.bingchong.adapter;

import android.app.Activity;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bingchong.Global;
import com.bingchong.R;
import com.bingchong.bean.ReportBean;
import com.bingchong.utils.ResolutionSet;

import java.util.ArrayList;

/**
 * Created by DavidYin on 2015.03.23.
 */
public class LocalReportItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ViewHolder mHolder;
    private ArrayList mData = null;
    private OnClickItemAdapterListener mListener = null;

    public LocalReportItemAdapter(Activity context) {
        mInflater = context.getLayoutInflater();
    }

    public void setOnListener(OnClickItemAdapterListener listener)
    {
        mListener = listener;
    }

    public void setData(ArrayList data) {
        mData = data;
    }

    public int getCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn = null;
        CheckBox check = null;
        if (convertView == null) {
            mHolder = new ViewHolder();

            convertView = (LinearLayout) mInflater.inflate(R.layout.item_local_report, null);

            Point ptScreenSize = Global.getScreenSize(mInflater.getContext().getApplicationContext());
            ResolutionSet.instance.iterateChild(convertView, ptScreenSize.x, ptScreenSize.y);

            btn = (Button) convertView.findViewById(R.id.btn_name);
            btn.setTag(mHolder);

            check = (CheckBox) convertView.findViewById(R.id.check_report);
            check.setTag(mHolder);
        } else {
            btn = (Button) convertView.findViewById(R.id.btn_name);
            mHolder = (ViewHolder) btn.getTag();

            check = (CheckBox) convertView.findViewById(R.id.check_report);
        }
        convertView.setId(position);

        if(mData == null || mData.size() == 0)
            return convertView;

        Object obj = mData.get(position);
        if(obj == null)
            return convertView;

        btn.setText(mData.get(position).toString());
        ReportBean reportBean = (ReportBean)mData.get(position);
        check.setChecked(reportBean.checked);

        mHolder.index = position;
        btn.setOnClickListener(mClickListener);
        check.setOnCheckedChangeListener(mCheckedChangeListener);
        return convertView;
    }

    private class ViewHolder {
        int		 index;
    }

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder)v.getTag();
            int index = holder.index;
            if(mListener != null)
                mListener.OnClickListener(index);
        }
    };

    private final CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ViewHolder holder = (ViewHolder)buttonView.getTag();
            int index = holder.index;
            ((ReportBean)mData.get(index)).checked = isChecked;
        }
    };
}
