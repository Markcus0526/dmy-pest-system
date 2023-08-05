package com.bingchong.activity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.bingchong.Constant;
import com.bingchong.R;
import com.bingchong.SuperActivity;
import com.bingchong.activity.db.DataMgrActivity;
import com.bingchong.activity.history.HistoryActivity;
import com.bingchong.activity.msg.MsgActivity;
import com.bingchong.activity.review.ReviewBlightActivity;
import com.bingchong.activity.setting.SettingActivity;
import com.bingchong.activity.statistics.StatisticsActivity;
import com.bingchong.activity.task.TaskActivity;
import com.bingchong.activity.users.UsersActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.activity.video.ExpertsListActivity;
import com.bingchong.activity.video.VideoMainActivity;

public class HomeMgrActivity extends SuperActivity implements OnClickListener {


	final String CLASS_NAME = getClass().getName();

    boolean bInitialized = false;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.home_mgr);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		switch (id) {
		case R.id.homeImgData:
		case R.id.homeTxtData:
			pushNewActivityAnimated(DataMgrActivity.class);
			break;
		case R.id.homeImgWork:
		case R.id.homeTxtWork:
			pushNewActivityAnimated(TaskActivity.class);
			break;
		case R.id.homeImgMovingInfo:
		case R.id.homeTxtMovingInfo:
			pushNewActivityAnimated(MovingInfoActivity.class);
			break;
		case R.id.homeImgAnalysis:
		case R.id.homeTxtAnalysis:
			pushNewActivityAnimated(StatisticsActivity.class);
			break;
		case R.id.homeImgMovieMgr:
		case R.id.homeTxtMovieMgr:
            pushNewActivityAnimated(VideoMainActivity.class);
            break;
		case R.id.homeImgReview:
		case R.id.homeTxtReview:
			pushNewActivityAnimated(ReviewBlightActivity.class);
			break;
		case R.id.homeImgUserMgr:
		case R.id.homeTxtUserMgr:
			pushNewActivityAnimated(UsersActivity.class);
			break;
		case R.id.homeImgHistory:
		case R.id.homeTxtHistory:
			pushNewActivityAnimated(HistoryActivity.class);
			break;
		case R.id.homeImgMsg:
		case R.id.homeTxtMsg:
			pushNewActivityAnimated(MsgActivity.class);
			break;
		case R.id.homeImgSetting:
			pushNewActivityAnimated(SettingActivity.class);
			break;
		}
	}

    @Override
    public void initializeActivity() {
        // set menu
        findViewById(R.id.homeImgData).setOnClickListener(this);
        findViewById(R.id.homeTxtData).setOnClickListener(this);
        findViewById(R.id.homeImgWork).setOnClickListener(this);
        findViewById(R.id.homeTxtWork).setOnClickListener(this);
        findViewById(R.id.homeImgMovingInfo).setOnClickListener(this);
        findViewById(R.id.homeTxtMovingInfo).setOnClickListener(this);

        findViewById(R.id.homeImgAnalysis).setOnClickListener(this);
        findViewById(R.id.homeTxtAnalysis).setOnClickListener(this);
        findViewById(R.id.homeImgMovieMgr).setOnClickListener(this);
        findViewById(R.id.homeTxtMovieMgr).setOnClickListener(this);
        findViewById(R.id.homeImgReview).setOnClickListener(this);
        findViewById(R.id.homeTxtReview).setOnClickListener(this);

        findViewById(R.id.homeImgUserMgr).setOnClickListener(this);
        findViewById(R.id.homeTxtUserMgr).setOnClickListener(this);
        findViewById(R.id.homeImgHistory).setOnClickListener(this);
        findViewById(R.id.homeTxtHistory).setOnClickListener(this);
        findViewById(R.id.homeImgMsg).setOnClickListener(this);
        findViewById(R.id.homeTxtMsg).setOnClickListener(this);


        findViewById(R.id.homeImgSetting).setOnClickListener(this);

        LoginActivity.setMainActivity(this);

    }
}