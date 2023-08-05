package com.bingchong.activity.movie;

import com.bingchong.AutoSizeActivity;
import com.bingchong.R;

import android.os.Bundle;

public class MovieMgrActivity extends AutoSizeActivity{


	final String CLASS_NAME = getClass().getName();

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		checkAutoResize();

	}

}