package com.bingchong.widget;

import java.util.Calendar;

import com.bingchong.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;


public class DateTimePicker extends TextView implements View.OnClickListener {

	private int mYear;
	private int mMonth;
	private int mDay;
	
	private Context mContext;
	
	private OnDateChangeListener mChangeListener = null;

	private DateTimeDialog mDialog = null;
	private Boolean isEnableChange = true;

	public DateTimePicker(Context context) {
		super(context);
		mContext = context;
		init_items();
	}
	
	public void setOnChangeListener(OnDateChangeListener listner)
	{
		mChangeListener = listner;
	}
	
	public void setEnableChange(Boolean isChange){
		isEnableChange = isChange;
	}
	

	public DateTimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init_items();
	}

	public DateTimePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init_items();
	}

    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    }

	private void init_items() {
		this.setTextColor(Color.BLACK);
		this.setBackgroundResource(R.drawable.datetimepicker);
		this.setOnClickListener(this);
		this.initDate();
	}

	@Override
	public void onClick(View v) {
		if(isEnableChange)
			showDateTimePickerDialog();
	}

	public Calendar getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(mYear, mMonth - 1, mDay);
		return calendar;
	}
	
	public long getTimeInMillis() {
		return getDate().getTimeInMillis();
	}

	public void initDate() {
		setDate(Calendar.getInstance());
	}
	
	public String getDateString(){
		return ""+ mYear + "-" + mMonth + "-" + mDay;
	}
	
	public String getEndDateString(){
		return ""+ mYear + "-" + mMonth + "-" + mDay + " 23:59:00";
	}	

	public void setDate(final Calendar calendar) {
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH) + 1;
		mDay = calendar.get(Calendar.DAY_OF_MONTH);
		this.showData();
	}
	
	public void setDateByStr(String dateStr) {
		if (dateStr != null && dateStr != "") {
			String[] date_str = dateStr.split("-");
			if (date_str != null) {
				mYear = Integer.parseInt(date_str[0]);
				mMonth = Integer.parseInt(date_str[1]);
				mDay = Integer.parseInt(date_str[2]);
			}
		}			
		
		this.showData();
	}

	private void showDateTimePickerDialog() {
		mDialog = new DateTimeDialog(mContext);
		mDialog.setListener(mDateSetListener);		
		mDialog.setDateTime(mYear, mMonth, mDay);
		mDialog.show();
	}

	private void showData() {

//		String strDate = "" + mYear + mContext.getString(R.string.strYear) +
//				mMonth + mContext.getString(R.string.strMonth) +
//				mDay + mContext.getString(R.string.strDay);
		String strDate = "" + mYear + "/" +
				mMonth + "/" +
				mDay + "";
		this.setText(strDate);
	}

	private DateTimeDialogListener mDateSetListener = new DateTimeDialogListener() {
		@Override
		public void onDateSelected(int year, int month, int day) {
			mYear = year;
			mMonth = month;
			mDay = day;
			showData();
			if(mChangeListener != null)
				mChangeListener.onDateChanged();
		}
	};

	public static class DateTimeDialog extends Dialog {
		
		private DateTimeDialogListener mListener;
		private DatePicker mDatePicker;

		public DateTimeDialog(Context context) {
			super(context);
			init();
		}

		public DateTimeDialog(Context context, int theme) {
			super(context, theme);
			init();
		}

		public DateTimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
			super(context, cancelable, cancelListener);
			init();
		}

		private void init() {
			this.setTitle(R.string.strSelectDateTime);
			this.setContentView(R.layout.datetime_dialog);
			mDatePicker = (DatePicker)this.findViewById(R.id.datePicker);
			this.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onBtnClickOk();
				}
			});
		}

		public void setDateTime(int year, int month, int day) {
			mDatePicker.init(year, month-1, day, null);
		}

		public void setListener(DateTimeDialogListener listener) {
			mListener = listener;
		}

		private void onBtnClickOk() {
			mListener.onDateSelected(
					mDatePicker.getYear(),
					mDatePicker.getMonth() + 1,
					mDatePicker.getDayOfMonth());
			dismiss();
		}
	}

	public static interface DateTimeDialogListener {
		public void onDateSelected(int year, int month, int day);
	}
	
	public static interface OnDateChangeListener {
		public void onDateChanged();
	}	
}
