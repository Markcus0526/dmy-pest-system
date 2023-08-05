package com.bingchong.activity.review;

import com.bingchong.AutoSizeActivity;
import com.bingchong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.SuperActivity;

public class ReviewActivity extends SuperActivity implements OnClickListener {


	final String CLASS_NAME = getClass().getName();

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.review);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		switch (id) {
		case R.id.rvImgPlace:
		case R.id.rvTxtPlace:
			pushNewActivityAnimated(ReviewPlaceActivity.class);
			break;
		case R.id.rvImgBlight:
		case R.id.rvTxtBlight:
			pushNewActivityAnimated(ReviewBlightActivity.class);
			break;
		}
	}

    @Override
    public void initializeActivity() {
        findViewById(R.id.rvImgPlace).setOnClickListener(this);
        findViewById(R.id.rvTxtPlace).setOnClickListener(this);
        findViewById(R.id.rvImgBlight).setOnClickListener(this);
        findViewById(R.id.rvTxtBlight).setOnClickListener(this);
    }
}