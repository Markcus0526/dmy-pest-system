package com.bingchong.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.graphics.Point;
import com.bingchong.Global;
import com.bingchong.R;
import com.bingchong.bean.FieldBean;
import com.bingchong.utils.ResolutionSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bingchong.widget.DateTimePicker;

public class ReportItemAdapter{

	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	private ArrayList<FieldBean> mData = null;
	private Context mContext;
	private int mEditPosition;
	private String[] mEditEnums;
	private Dialog mDiag;
	private int btnIndex = 0;	
	private LinearLayout mlayout = null;
	private View[] listViews = null;
	
	public ReportItemAdapter(Activity context) {
		mInflater = context.getLayoutInflater();
		mContext = context;
	}

	public void setData(ArrayList<FieldBean> data, LinearLayout parent) {
		mData = data;
		
		if(mlayout != null)
			parent.removeView(mlayout);
		
		if(data == null)
			return;
		
		mlayout = new LinearLayout(mContext);
		mlayout.setOrientation(LinearLayout.VERTICAL);
		listViews = new View[data.size()];
		
		for(int i = 0; i < data.size(); i++){
			View v = getView(i, null, mlayout);
			listViews[i] = v;
			mlayout.addView(v);
		}
		parent.addView(mlayout);
	}

	public int getCount() {
		if (mData == null)
			return 0;
		return mData.size();
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
			convertView = (LinearLayout) mInflater.inflate(R.layout.item_report_edit, null);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.pos = position;

		convertView.setId(position);

		TextView itemView = (TextView) convertView.findViewById(R.id.txtLabel);
		TextView itemInput= (TextView) convertView.findViewById(R.id.txtEdt);
		LinearLayout layEdt = (LinearLayout)convertView.findViewById(R.id.layoutEdt);
		
		if(mData == null || mData.size() == 0)
			return convertView;

		FieldBean item = mData.get(position);
		int offset = 0;
		
		int width = ResolutionSet.instance.getBaseWidth() / 2;
		int off = width / 9;
		
		if(item.level == 1)
			itemView.setWidth(width - off * 3);
		else if(item.level == 2)
			itemView.setWidth(width - off * 2);
		else
			itemView.setWidth(width - off);
		
		//itemView.setText(item.fieldIndex + " " + item.fieldName);
		itemView.setText(item.fieldName);
		
		if(item.fieldType != FieldBean.FIELD_PARENT){
			layEdt.setVisibility(View.VISIBLE);
			itemInput.setText(item.val);
			itemInput.setTag(mHolder);
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ViewHolder holder = (ViewHolder)v.getTag();
					OnEditValueOfTag(holder.pos);
				}
			});
		}
		else{
			layEdt.setVisibility(View.GONE);
			itemView.setWidth(200);
		}

		return convertView;
	}

	private void SetValue(String val)
	{
		try{
			FieldBean item = mData.get(mEditPosition);
			item.val = val;
			getView(mEditPosition, listViews[mEditPosition], mlayout);
			CalculateSumFields();
		}
		catch(Exception ex){
			
		}
	}

	private void CalculateSumFields()
	{
		double sum = 0;
		int index = 0;

		for(FieldBean item : mData) {
			if(item.fieldType == FieldBean.FIELD_SUM) {
				item.val = String.format("%.2f", sum);
				sum = 0;
				getView(index, listViews[index], mlayout);
			}
			else if(item.fieldType == FieldBean.FIELD_PARENT) {
				sum = 0;
			}
			else if(item.fieldType == FieldBean.FIELD_NUMBER ||
					item.fieldType == FieldBean.FIELD_REAL) {
				try {
					sum += Double.parseDouble(item.val);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			index++;
		}
	}

	private void OnEditValueOfTag(int position)
	{
		FieldBean item = mData.get(position);
		int valueType = item.fieldType;
		if(valueType ==FieldBean.FIELD_NONE)
			return;
		mEditPosition = position;
		AlertDialog.Builder builder;
		
		builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.setting_value);

		Point ptScreenSize = Global.getScreenSize(mInflater.getContext().getApplicationContext());

		switch(valueType)
		{
			case FieldBean.FIELD_SELECT:
				mEditEnums = item.values.toArray(new String[0]);
				int index = 0;
				if(mEditEnums.length > 0) {
					while (index < mEditEnums.length) {
						if(mEditEnums[index].equals(item.val))
							break;
						index++;
					}
				}
				if(index >= mEditEnums.length)
					index = 0;

				btnIndex = index;

				builder.setSingleChoiceItems(mEditEnums, index, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						btnIndex = whichButton;
					}
				});
				builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						SetValue(mEditEnums[btnIndex]);
						dialog.dismiss();
					}
				});
				builder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});
				break;
			case FieldBean.FIELD_NUMBER:
			case FieldBean.FIELD_REAL:
			case FieldBean.FIELD_STRING:
			case FieldBean.FIELD_SUM:
				// This example shows how to add a custom layout to an AlertDialog
				final View textEntryView = mInflater.inflate(R.layout.dialog_text_edit, null);
				TextView txtName = (TextView)textEntryView.findViewById(R.id.txt_label);
				EditText txtValue = (EditText )textEntryView.findViewById(R.id.txt_value);

				txtName.setText(item.fieldName);
				txtValue.setText(item.val);

				builder.setView(textEntryView);

				builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						EditText txtValue = (EditText )textEntryView.findViewById(R.id.txt_value);
						SetValue(txtValue.getText().toString());
						dialog.dismiss();
					}
				});
				builder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				ResolutionSet.instance.iterateChild(textEntryView, ptScreenSize.x, ptScreenSize.y);
				break;
			case FieldBean.FIELD_DATE:
				final View dateEntryView = mInflater.inflate(R.layout.dialog_date_edit, null);
				TextView txtDateName = (TextView)dateEntryView.findViewById(R.id.txt_label);
				DateTimePicker datePicker = (DateTimePicker)dateEntryView.findViewById(R.id.edt_date);

				txtDateName.setText(item.fieldName);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					calendar.setTime(dateFormat.parse(item.val));
					datePicker.setDate(calendar);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}

				builder.setView(dateEntryView);

				builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						DateTimePicker datePicker = (DateTimePicker) dateEntryView.findViewById(R.id.edt_date);
						SetValue(datePicker.getDateString());
						dialog.dismiss();
					}
				});
				builder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				ResolutionSet.instance.iterateChild(dateEntryView, ptScreenSize.x, ptScreenSize.y);
				break;
			default:
				break;
		}

		mDiag = builder.create();
		mDiag.show();
	}

	private class ViewHolder {
		int pos;
	}

}
