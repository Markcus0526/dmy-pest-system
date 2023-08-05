package com.bingchong.activity.setting;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import com.bingchong.AutoSizeActivity;
import com.bingchong.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.SuperActivity;

public class SettingContactActivity extends SuperActivity {
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.setting_contactme);
	}


	@Override
	public void initializeActivity() {
		TextView tv = (TextView) findViewById(R.id.tvCompanyLink);
		tv.setText(Html.fromHtml("<a href=http://www.gcloudinfo.com>http://www.gcloudinfo.com"));
		tv.setMovementMethod(LinkMovementMethod.getInstance());
	}
}