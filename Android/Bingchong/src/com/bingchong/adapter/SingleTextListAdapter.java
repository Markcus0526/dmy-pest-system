package com.bingchong.adapter;

import java.util.ArrayList;

import android.graphics.Point;
import com.bingchong.Global;
import com.bingchong.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bingchong.utils.ResolutionSet;

public class SingleTextListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	private ArrayList mData = null;
	private OnClickItemAdapterListener mListener = null;
	
	private Boolean isLargeHeight = false;

	public SingleTextListAdapter(Activity context) {
		mInflater = context.getLayoutInflater();
	}
	
	public void setOnListener(OnClickItemAdapterListener listener)
	{
		mListener = listener;
	}
	
	public void setLargeHeight(Boolean isLarge){
		isLargeHeight = isLarge;
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
		if (convertView == null) {
			mHolder = new ViewHolder();
			if(isLargeHeight)
				convertView = (LinearLayout) mInflater.inflate(R.layout.item_string_1, null);
			else
				convertView = (LinearLayout) mInflater.inflate(R.layout.item_string, null);

            Point ptScreenSize = Global.getScreenSize(mInflater.getContext().getApplicationContext());
            ResolutionSet.instance.iterateChild(convertView, ptScreenSize.x, ptScreenSize.y);

			btn = (Button) convertView.findViewById(R.id.btn_name);
			btn.setTag(mHolder);
		} else {
			btn = (Button) convertView.findViewById(R.id.btn_name);
			mHolder = (ViewHolder) btn.getTag();
		}
		convertView.setId(position);
		
		if(mData == null || mData.size() == 0)
			return convertView;
		
		Object obj = mData.get(position);
		if(obj == null)
			return convertView;
		
		btn.setText(mData.get(position).toString());
		mHolder.index = position;
		btn.setOnClickListener(mClickListener);
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
}
