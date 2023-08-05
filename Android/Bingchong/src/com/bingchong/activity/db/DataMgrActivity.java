package com.bingchong.activity.db;

import com.bingchong.AutoSizeActivity;
import com.bingchong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.SuperActivity;

public class DataMgrActivity extends SuperActivity implements OnClickListener {


	final String CLASS_NAME = getClass().getName();

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.db_mgr);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		switch (id) {
		case R.id.dbTxtPlace:
		case R.id.dbImgPlace:
			pushNewActivityAnimated(DBPlaceSearchActivity.class);
			break;
		case R.id.dbTxtBlight:
		case R.id.dbImgBlight:
			pushNewActivityAnimated(DBBlightSearchActivity.class);
			break;	
		}
	}

    @Override
    public void initializeActivity() {
        // set menu
        findViewById(R.id.dbTxtPlace).setOnClickListener(this);
        findViewById(R.id.dbImgPlace).setOnClickListener(this);
        findViewById(R.id.dbTxtBlight).setOnClickListener(this);
        findViewById(R.id.dbImgBlight).setOnClickListener(this);
    }
}