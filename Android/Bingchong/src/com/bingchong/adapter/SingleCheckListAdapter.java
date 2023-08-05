package com.bingchong.adapter;

import java.util.ArrayList;

import android.graphics.Point;
import com.bingchong.Global;
import com.bingchong.R;
import com.bingchong.bean.CheckObjInterface;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import com.bingchong.utils.ResolutionSet;

public class SingleCheckListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	private ArrayList mData = null;

	public SingleCheckListAdapter(Activity context) {
		mInflater = context.getLayoutInflater();
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
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = (LinearLayout) mInflater.inflate(R.layout.item_check, null);

            Point ptScreenSize = Global.getScreenSize(mInflater.getContext().getApplicationContext());
            ResolutionSet.instance.iterateChild(convertView, ptScreenSize.x, ptScreenSize.y);

			mHolder.txtCheck = (CheckBox) convertView.findViewById(R.id.item_name);
			convertView.setTag(mHolder);
			mHolder.txtCheck.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		convertView.setId(position);
		
		if(mData == null || mData.size() == 0)
			return convertView;
		
		mHolder.txtCheck.setText(mData.get(position).toString());
		if(mData.get(position) instanceof CheckObjInterface)
		{
			CheckObjInterface obj = (CheckObjInterface)mData.get(position);
			mHolder.txtCheck.setChecked(obj.isCheck());
		}		
		mHolder.index = position;
		mHolder.txtCheck.setOnClickListener(mClickListener);
		return convertView;
	}

	private class ViewHolder {
		CheckBox txtCheck;
		int		 index;
	}

	private final View.OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			ViewHolder holder = (ViewHolder)v.getTag();
			int index = holder.index;
			if(mData.get(index) instanceof CheckObjInterface)
			{
				CheckObjInterface obj = (CheckObjInterface)mData.get(index);
				obj.setCheck(holder.txtCheck.isChecked());
			}			
		}		

	};

}
