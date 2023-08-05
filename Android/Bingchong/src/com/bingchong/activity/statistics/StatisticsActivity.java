package com.bingchong.activity.statistics;

import com.bingchong.AutoSizeActivity;
import com.bingchong.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.SuperActivity;

public class StatisticsActivity extends SuperActivity implements OnClickListener {


	final String CLASS_NAME = getClass().getName();

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.st_main);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		switch (id) {
		case R.id.stImgPolygon:
		case R.id.stTxtPolygon:
			pushNewActivityAnimated(StatisticsPolygonActivity.class);
			break;
		case R.id.stImgGraph:
		case R.id.stTxtGraph:
			pushNewActivityAnimated(StatisticsGraphActivity.class);
			break;
		}
	}

    @Override
    public void initializeActivity() {
        findViewById(R.id.stImgPolygon).setOnClickListener(this);
        findViewById(R.id.stTxtPolygon).setOnClickListener(this);
        findViewById(R.id.stImgGraph).setOnClickListener(this);
        findViewById(R.id.stTxtGraph).setOnClickListener(this);

    }
}