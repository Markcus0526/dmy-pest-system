package com.bingchong.adapter;

import java.util.ArrayList;

import android.graphics.Point;
import android.widget.*;
import com.bingchong.AppCommon;
import com.bingchong.BingChongApp;
import com.bingchong.Global;
import com.bingchong.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bingchong.bean.PointBean;
import com.bingchong.utils.ResolutionSet;

public class PointListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	private ArrayList mData = null;
	private OnClickItemAdapterListener mListener = null;
	
	public PointListAdapter(Activity context) {
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
		ImageView imgMark = null;
		if (convertView == null) {
			mHolder = new ViewHolder();

			convertView = (LinearLayout) mInflater.inflate(R.layout.item_point, null);

            Point ptScreenSize = Global.getScreenSize(mInflater.getContext().getApplicationContext());
            ResolutionSet.instance.iterateChild(convertView, ptScreenSize.x, ptScreenSize.y);

			btn = (Button) convertView.findViewById(R.id.btn_name);
			btn.setTag(mHolder);

			imgMark = (ImageView) convertView.findViewById(R.id.img_mark);
			imgMark.setTag(mHolder);
		} else {
			btn = (Button) convertView.findViewById(R.id.btn_name);
			mHolder = (ViewHolder) btn.getTag();

			imgMark = (ImageView) convertView.findViewById(R.id.img_mark);
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

		PointBean pointBean = (PointBean)obj;
		if(pointBean.type == 0)
			imgMark.setImageDrawable(BingChongApp.getAppContext().getResources().getDrawable(R.drawable.place_mark));
		else
			imgMark.setImageDrawable(BingChongApp.getAppContext().getResources().getDrawable(R.drawable.place_moving_mark));
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
