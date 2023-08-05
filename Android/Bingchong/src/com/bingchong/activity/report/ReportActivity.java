package com.bingchong.activity.report;

import com.bingchong.AutoSizeActivity;
import com.bingchong.Constant;
import com.bingchong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.SuperActivity;

public class ReportActivity extends SuperActivity implements OnClickListener {


	final String CLASS_NAME = getClass().getName();

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.report);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		switch (id) {
		case R.id.reportImgDisease:
		case R.id.reportTxtDisease:
			Bundle bundle = new Bundle();
			bundle.putString(Constant.EXTRA_PARAM_BLIGHT_FLY, String.valueOf(false));
			pushNewActivityAnimated(ReportBlightListActivity.class, bundle);
			break;
		case R.id.reportImgFly:
		case R.id.reportTxtFly:
			Bundle bundle2 = new Bundle();
			bundle2.putString(Constant.EXTRA_PARAM_BLIGHT_FLY, String.valueOf(true));
			pushNewActivityAnimated(ReportBlightListActivity.class, bundle2);
			break;	
		}
	}

    @Override
    public void initializeActivity() {
        // set menu
        findViewById(R.id.reportImgDisease).setOnClickListener(this);
        findViewById(R.id.reportTxtDisease).setOnClickListener(this);
        findViewById(R.id.reportImgFly).setOnClickListener(this);
        findViewById(R.id.reportTxtFly).setOnClickListener(this);

    }
}