package com.bingchong.activity.report;

import com.bingchong.AutoSizeActivity;
import com.bingchong.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ReportPlaceActivity extends AutoSizeActivity implements OnClickListener {


	final String CLASS_NAME = getClass().getName();


	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		checkAutoResize();
		
	
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

	}

}