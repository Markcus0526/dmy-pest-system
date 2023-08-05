package com.bingchong.adapter;

import java.util.List;

import android.graphics.Point;
import android.widget.RelativeLayout;
import com.bingchong.Constant;
import com.bingchong.Global;
import com.bingchong.R;
import com.bingchong.bean.BlightBean;
import com.bingchong.bean.PointBean;
import com.bingchong.bean.ExtraTaskBean;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bingchong.utils.ResolutionSet;

public class SingleAcceptAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	private List mData = null;
	private OnClickItemAdapterListener mListener = null;
	private Boolean isAdmin = false;

	public SingleAcceptAdapter(Activity context, boolean isAdmin) {
		mInflater = context.getLayoutInflater();
		this.isAdmin = isAdmin;
	}
	
	public void setOnListener(OnClickItemAdapterListener listener)
	{
		mListener = listener;
	}

	public void setData(List data) {
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
			convertView = (RelativeLayout) mInflater.inflate(R.layout.item_string_assign, null);

            Point ptScreenSize = Global.getScreenSize(mInflater.getContext().getApplicationContext());
            ResolutionSet.instance.iterateChild(convertView, ptScreenSize.x, ptScreenSize.y);

			mHolder.txtName = (TextView) convertView.findViewById(R.id.item_name);
			mHolder.txtAccept = (TextView) convertView.findViewById(R.id.item_accept);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		convertView.setId(position);
		
		if(mData == null || mData.size() == 0)
			return convertView;
		
		Object obj = mData.get(position);
		String str = obj.toString();
		mHolder.txtName.setText(obj.toString());
		
		if(obj instanceof ExtraTaskBean)
		{
			ExtraTaskBean bean = (ExtraTaskBean)obj;
		
			switch(bean.status)
			{
			case ExtraTaskBean.STAT_NOACCEPT:
				mHolder.txtAccept.setText(R.string.task_noAccept);
				if(isAdmin)
					mHolder.txtAccept.setBackgroundColor(Constant.COLOR_TEXT_GREEN);
				else
					mHolder.txtAccept.setBackgroundColor(Constant.COLOR_WARN);
				break;
			case ExtraTaskBean.STAT_ACCEPT:
				mHolder.txtAccept.setText(R.string.task_Accept);
				if(isAdmin)
					mHolder.txtAccept.setBackgroundColor(Constant.COLOR_WARN);
				else
					mHolder.txtAccept.setBackgroundColor(Constant.COLOR_TEXT_GREEN);
				break;
			case ExtraTaskBean.STAT_COMPLETE:
				mHolder.txtAccept.setText(R.string.task_Complete);
				mHolder.txtAccept.setBackgroundColor(Constant.COLOR_GRAY);
				break;
			}
		}
		
		if(obj instanceof PointBean)
		{
			PointBean bean = (PointBean)obj;
			
			int nStat = bean.status;
		
			switch(nStat)
			{
			case Constant.REVIEW_WAITING:
				mHolder.txtAccept.setText(R.string.review_Wait);
				mHolder.txtAccept.setBackgroundColor(Constant.COLOR_TEXT_GREEN);
				break;
			case Constant.REVIEW_ACCEPT:
				mHolder.txtAccept.setText(R.string.review_Accept);
				mHolder.txtAccept.setBackgroundColor(Constant.COLOR_GRAY);
				break;
			case Constant.REVIEW_NOACCEPT:
				mHolder.txtAccept.setText(R.string.review_NoAccept);
				mHolder.txtAccept.setBackgroundColor(Constant.COLOR_WARN);
				break;
			}
		}
		
		if(obj instanceof BlightBean)
		{
			BlightBean bean = (BlightBean)obj;
			
			int nStat = bean.status;
		
			switch(nStat)
			{
			case Constant.REVIEW_WAITING:
				mHolder.txtAccept.setText(R.string.review_Wait);
				mHolder.txtAccept.setBackgroundColor(Constant.COLOR_TEXT_GREEN);
				break;
			case Constant.REVIEW_ACCEPT:
				mHolder.txtAccept.setText(R.string.review_Accept);
				mHolder.txtAccept.setBackgroundColor(Constant.COLOR_GRAY);
				break;
			case Constant.REVIEW_NOACCEPT:
				mHolder.txtAccept.setText(R.string.review_NoAccept);
				mHolder.txtAccept.setBackgroundColor(Constant.COLOR_WARN);
				break;
			}
		}		
		mHolder.index = position;
		convertView.setOnClickListener(mClickListener);
		return convertView;
	}

	private class ViewHolder {
		TextView txtName;
		TextView txtAccept;
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
