package com.bingchong.activity;

import com.bingchong.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.SuperActivity;

public class HomeGetPasswordActivity extends SuperActivity implements OnClickListener {


	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.home_get_password);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {		

		}
	}

    @Override
    public void initializeActivity() {

    }
}