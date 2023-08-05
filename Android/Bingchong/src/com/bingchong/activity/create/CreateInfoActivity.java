package com.bingchong.activity.create;

import com.bingchong.AutoSizeActivity;
import com.bingchong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.bingchong.SuperActivity;

public class CreateInfoActivity extends SuperActivity implements OnClickListener {


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
			pushNewActivityAnimated(CreatePointActivity.class);
			break;
		case R.id.dbTxtBlight:
		case R.id.dbImgBlight:
			pushNewActivityAnimated(CreateBlightActivity.class);
			break;	
		}
	}

    @Override
    public void initializeActivity() {
        ((TextView)findViewById(R.id.txt_title)).setText(R.string.titleCreateInfo);
        ((TextView)findViewById(R.id.dbTxtPlace)).setText(R.string.str_extral_place);
        ((TextView)findViewById(R.id.dbTxtBlight)).setText(R.string.str_extral_blight);


        // set menu
        findViewById(R.id.dbTxtPlace).setOnClickListener(this);
        findViewById(R.id.dbImgPlace).setOnClickListener(this);
        findViewById(R.id.dbTxtBlight).setOnClickListener(this);
        findViewById(R.id.dbImgBlight).setOnClickListener(this);
    }
}