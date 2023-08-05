package com.bingchong.adapter;

import java.util.ArrayList;

import com.bingchong.R;
import com.bingchong.bean.FieldBean;
import com.bingchong.bean.FormBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FieldListAdapter extends ArrayAdapter<FieldBean> {

	private LayoutInflater mInflater;
	
	ArrayList<FieldBean> array = null;

	public FieldListAdapter(Context ctx, int resId, ArrayList<FieldBean> items) {
		super(ctx, resId, items);
		this.array= items;
		mInflater = LayoutInflater.from(ctx);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			holder = new ViewHolder();
			
			convertView = mInflater.inflate(R.layout.item_spinner_white, null);
			holder.name = (TextView) convertView.findViewById(R.id.txt_name);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(array.get(position).toShortString());

		return convertView;
	}
	
	class ViewHolder {
		TextView name;
	}
	
	@Override
	public int getCount() {
		if(array != null) {
			return array.size();
		} else {
			return 0;
		}
	}

}