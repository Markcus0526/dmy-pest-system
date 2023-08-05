package com.bingchong.activity.history;

import com.bingchong.R;
import com.bingchong.SuperActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HistoryUserActivity extends SuperActivity implements OnClickListener{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.history_user);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
        Intent intent = null;
		switch(id)
		{
            case R.id.imgNotCompleted:
            case R.id.lblNotCompleted:
                pushNewActivityAnimated(HistoryUserNotCompletedActivity.class);
			break;
            case R.id.imgCompleted:
            case R.id.lblCompleted:
                pushNewActivityAnimated(HistoryUserCompletedActivity.class);
                break;
            case R.id.imgWaiting:
            case R.id.lblWaiting:
                pushNewActivityAnimated(HistoryUserWaitingActivity.class);
                break;
            case R.id.imgSaved:
            case R.id.lblSaved:
                pushNewActivityAnimated(HistoryUserSavedActivity.class);
                break;
		}
	}

    @Override
    public void initializeActivity() {

        findViewById(R.id.imgNotCompleted).setOnClickListener(this);
        findViewById(R.id.lblNotCompleted).setOnClickListener(this);
        findViewById(R.id.imgCompleted).setOnClickListener(this);
        findViewById(R.id.lblCompleted).setOnClickListener(this);
        findViewById(R.id.imgWaiting).setOnClickListener(this);
        findViewById(R.id.lblWaiting).setOnClickListener(this);
        findViewById(R.id.imgSaved).setOnClickListener(this);
        findViewById(R.id.lblSaved).setOnClickListener(this);
    }
}