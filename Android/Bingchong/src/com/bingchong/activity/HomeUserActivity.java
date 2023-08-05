package com.bingchong.activity;

import com.bingchong.AutoSizeActivity;
import com.bingchong.Constant;
import com.bingchong.R;
import com.bingchong.SuperActivity;
import com.bingchong.activity.create.CreateBlightActivity;
import com.bingchong.activity.create.CreateInfoActivity;
import com.bingchong.activity.db.DBPlaceSearchActivity;
import com.bingchong.activity.db.DBUserPointActivity;
import com.bingchong.activity.history.HistoryUserActivity;
import com.bingchong.activity.movie.MovieUserActivity;
import com.bingchong.activity.msg.MsgActivity;
import com.bingchong.activity.report.ReportActivity;
import com.bingchong.activity.setting.SettingActivity;
import com.bingchong.activity.setting.SettingHelpActivity;
import com.bingchong.activity.setting.SettingHelpInfoActivity;
import com.bingchong.activity.task.TaskUserActivity;
import com.bingchong.activity.video.VideoMainActivity;
import com.bingchong.bean.HelpBean;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeUserActivity extends SuperActivity implements OnClickListener {



	final String CLASS_NAME = getClass().getName();

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.home_user);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		switch (id) {
		case R.id.homeImgReport:
		case R.id.homeTxtReport:
			pushNewActivityAnimated(ReportActivity.class);
			break;
		case R.id.homeImgHistory:
		case R.id.homeTxtHistory:
			pushNewActivityAnimated(HistoryUserActivity.class);
			break;
		case R.id.homeImgWork:
		case R.id.homeTxtWork:
			pushNewActivityAnimated(TaskUserActivity.class);
			break;
        case R.id.homeImgMyposition:
        case R.id.homeTxtMyposition:
			pushNewActivityAnimated(MovingInfoUserActivity.class);
            break;
		case R.id.homeImgMovie:
		case R.id.homeTxtMovie:
            pushNewActivityAnimated(VideoMainActivity.class);
			break;
		case R.id.homeImgPredict:
		case R.id.homeTxtPredict:
			//pushNewActivityAnimated(CreateInfoActivity.class);
			pushNewActivityAnimated(CreateBlightActivity.class);
			break;
		case R.id.homeImgPlace:
		case R.id.homeTxtPlace:
			pushNewActivityAnimated(DBUserPointActivity.class);
			break;
		case R.id.homeImgHelp:
		case R.id.homeTxtHelp:
			Bundle bundle = new Bundle();
			bundle.putString(Constant.EXTRA_PARAM_MANUAL, String.valueOf(true));
			pushNewActivityAnimated(SettingHelpActivity.class, bundle);
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
        findViewById(R.id.homeImgReport).setOnClickListener(this);
        findViewById(R.id.homeTxtReport).setOnClickListener(this);
        findViewById(R.id.homeImgWork).setOnClickListener(this);
        findViewById(R.id.homeTxtWork).setOnClickListener(this);
        findViewById(R.id.homeImgHistory).setOnClickListener(this);
        findViewById(R.id.homeTxtHistory).setOnClickListener(this);

        findViewById(R.id.homeImgMyposition).setOnClickListener(this);
        findViewById(R.id.homeTxtMyposition).setOnClickListener(this);
        findViewById(R.id.homeImgMovie).setOnClickListener(this);
        findViewById(R.id.homeTxtMovie).setOnClickListener(this);
        findViewById(R.id.homeImgPredict).setOnClickListener(this);
        findViewById(R.id.homeTxtPredict).setOnClickListener(this);

        findViewById(R.id.homeImgPlace).setOnClickListener(this);
        findViewById(R.id.homeTxtPlace).setOnClickListener(this);
        findViewById(R.id.homeImgHelp).setOnClickListener(this);
        findViewById(R.id.homeTxtHelp).setOnClickListener(this);
        findViewById(R.id.homeImgMsg).setOnClickListener(this);
        findViewById(R.id.homeTxtMsg).setOnClickListener(this);

        findViewById(R.id.homeImgSetting).setOnClickListener(this);
        LoginActivity.setMainActivity(this);
    }
}