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

public class TwoTextListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList mData = null;
	private ViewHolder mHolder;
	private OnClickItemAdapterListener mListener = null;

	public TwoTextListAdapter(Activity context) {
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
			return (mData.size() + 1) / 2;
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
			
			convertView = (LinearLayout) mInflater.inflate(R.layout.item_2string, null);

            Point ptScreenSize = Global.getScreenSize(mInflater.getContext().getApplicationContext());
            ResolutionSet.instance.iterateChild(convertView, ptScreenSize.x, ptScreenSize.y);

			mHolder.btn1 = (Button) convertView.findViewById(R.id.btn1);
			mHolder.btn2 = (Button) convertView.findViewById(R.id.btn2);
			
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		convertView.setId(position);
		
		int pos = position * 2;
		mHolder.btn1.setText(mData.get(pos).toString());
		mHolder.btn1.setTag(new btnIndex(pos));
		
		if(mData.size() > pos + 1)
		{
			mHolder.btn2.setVisibility(View.VISIBLE);
			mHolder.btn2.setText(mData.get(pos + 1).toString());
			mHolder.btn2.setTag(new btnIndex(pos+1));
		}
		else
			mHolder.btn2.setVisibility(View.GONE);
		mHolder.index = position;
		
		mHolder.btn1.setOnClickListener(mClickListener);
		mHolder.btn2.setOnClickListener(mClickListener);
		return convertView;
	}

	private class btnIndex{
		int btnIndex;
		public btnIndex(int id)
		{
			btnIndex = id;
		}
	}
	
	private class ViewHolder {
		Button  btn1;
		Button  btn2;
		int		index;
	}

	private final View.OnClickListener mClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			btnIndex holder = (btnIndex)v.getTag();
			int index = holder.btnIndex;
			if(mListener != null)
				mListener.OnClickListener(index);
		}
	};
}
