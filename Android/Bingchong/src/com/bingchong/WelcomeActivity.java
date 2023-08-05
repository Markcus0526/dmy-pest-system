package com.bingchong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bingchong.activity.LoginActivity;
import com.bingchong.R;
import com.bingchong.utils.AppPreferences;
import com.bingchong.utils.Utilities;

/**
 * This Class Launch/Start the Application .
 */
public class WelcomeActivity extends AutoSizeActivity {

	private final String CLASS_NAME = getClass().getName();
	private static final int SPLASH_DISPLAY_TIME = 2000;
	private final Handler handler = new Handler();

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		checkAutoResize();		
		handler.postDelayed(runnable, SPLASH_DISPLAY_TIME);
        AppCommon.appPreferences = new AppPreferences(getApplicationContext());
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Utilities.printStatement(CLASS_NAME, "Inside runnable");
			handler.removeCallbacks(runnable);
			Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		handler.removeCallbacks(runnable);
		finish();
	}
}