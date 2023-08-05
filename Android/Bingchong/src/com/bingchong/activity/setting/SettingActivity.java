package com.bingchong.activity.setting;

import com.bingchong.AutoSizeActivity;
import com.bingchong.Constant;
import com.bingchong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.SuperActivity;

public class SettingActivity extends SuperActivity implements OnClickListener {


	final String CLASS_NAME = getClass().getName();


	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.setting);
	}


	@Override
	public void initializeActivity() {
		findViewById(R.id.btnAboutMe).setOnClickListener(this);
		findViewById(R.id.btnContactUs).setOnClickListener(this);
		findViewById(R.id.btnFunction).setOnClickListener(this);
		findViewById(R.id.btnOpinion).setOnClickListener(this);
		findViewById(R.id.btnHelp).setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		switch (id) {
		case R.id.btnAboutMe:
			pushNewActivityAnimated(SettingOwnerActivity.class, AnimConst.ANIMDIR_FROM_RIGHT);
			break;
		case R.id.btnContactUs:
			pushNewActivityAnimated(SettingContactActivity.class, AnimConst.ANIMDIR_FROM_RIGHT);
			break;
		case R.id.btnFunction:
			pushNewActivityAnimated(SettingFunctionActivity.class, AnimConst.ANIMDIR_FROM_RIGHT);
			break;
		case R.id.btnOpinion:
			pushNewActivityAnimated(SettingOpinionActivity.class, AnimConst.ANIMDIR_FROM_RIGHT);
			break;
		case R.id.btnHelp:
			Bundle extras = new Bundle();
			extras.putString(Constant.EXTRA_PARAM_MANUAL, String.valueOf(false));

			pushNewActivityAnimated(SettingHelpActivity.class, AnimConst.ANIMDIR_FROM_RIGHT, extras);
			break;
		}

	}

}