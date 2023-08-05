package com.bingchong.activity.req;

import com.bingchong.AutoSizeActivity;
import com.bingchong.R;
import com.bingchong.activity.db.DBBlightSearchActivity;
import com.bingchong.activity.db.DBPlaceSearchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ReqMainActivity extends AutoSizeActivity implements OnClickListener {


	final String CLASS_NAME = getClass().getName();

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.req_main);
		
		// set menu
		findViewById(R.id.layoutPlace).setOnClickListener(this);
		findViewById(R.id.layoutBlight).setOnClickListener(this);
		
		checkAutoResize();
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = null;
		switch (id) {
		case R.id.layoutPlace:
			intent = new Intent(this, DBPlaceSearchActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		case R.id.layoutBlight:
			intent = new Intent(this, DBBlightSearchActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;	
		}
	}

}