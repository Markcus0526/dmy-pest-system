package com.bingchong.activity.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.*;
import com.bingchong.*;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleTextListAdapter;
import com.bingchong.bean.HelpBean;
import com.bingchong.db.DataMgr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.bingchong.net.AsyncHttpClient;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.utils.ResolutionSet;
import org.json.JSONArray;
import org.json.JSONObject;


public class SettingHelpActivity extends SuperActivity {
	private ListView help_listview = null;
	private HelpAdapter help_adapter = null;
	private ArrayList<HelpBean> arrHelps = new ArrayList<HelpBean>();
    private String sIsManual = "true";
	private TextView txtTitle = null;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.setting_help);
	}

	@Override
	public void initializeActivity() {
		txtTitle = (TextView)findViewById(R.id.txt_title);
		help_listview = (ListView)findViewById(R.id.help_listview);

		help_adapter = new HelpAdapter(SettingHelpActivity.this, arrHelps);
		help_listview.setAdapter(help_adapter);

		startProgress();
        Intent intent = getIntent();
        sIsManual = intent.getStringExtra(Constant.EXTRA_PARAM_MANUAL);
        if(sIsManual.equals("true")) {
			txtTitle.setText("测报员管理办法");
            CommManager.getHelps(0, AppCommon.loadUserID(), helps_handler);
        }
        else {
			txtTitle.setText("帮助信息");
            CommManager.getHelps(1, AppCommon.loadUserID(), helps_handler);
        }

	}


	public class STViewHolder {
		public TextView txt_item = null;
		public ImageButton btn_item = null;
	}


	public class HelpAdapter extends ArrayAdapter<HelpBean> {
		public HelpAdapter(Context context, List<HelpBean> objects) {
			super(context, 0, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HelpBean help_info = arrHelps.get(position);
			STViewHolder holder = null;

			if (convertView == null) {
				RelativeLayout item_layout = (RelativeLayout)getLayoutInflater().inflate(R.layout.item_help, null);
				ResolutionSet.instance.iterateChild(item_layout, getScreenSize().x, getScreenSize().y);

				holder = new STViewHolder();

				holder.txt_item = (TextView)item_layout.findViewById(R.id.txt_item);
				holder.btn_item = (ImageButton)item_layout.findViewById(R.id.btn_item);
				holder.btn_item.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						HelpBean help_info = (HelpBean)v.getTag();
						onItemSelected(help_info);
					}
				});

				item_layout.setTag(holder);

				convertView = item_layout;
			} else {
				holder = (STViewHolder)convertView.getTag();
			}

			holder.txt_item.setText(help_info.title);
			holder.btn_item.setTag(help_info);

			return convertView;
		}
	}


	private void onItemSelected(HelpBean help_info) {
		Bundle extras = new Bundle();
		extras.putLong("uid", help_info.id);
		extras.putString("title", help_info.title);
        extras.putString(Constant.EXTRA_PARAM_MANUAL, sIsManual);

		pushNewActivityAnimated(SettingHelpInfoActivity.class, AnimConst.ANIMDIR_FROM_RIGHT, extras);
	}



	private AsyncHttpResponseHandler helps_handler = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			super.onSuccess(content);
			stopProgress();

			try {
				JSONObject result = new JSONObject(content);

				int retcode = result.getInt("retcode");
				String retmsg = result.getString("retmsg");

				if (retcode == 0) {
					JSONArray arr_retdata = result.getJSONArray("retdata");

					for (int i = 0; i < arr_retdata.length(); i++) {
						JSONObject item_json = arr_retdata.getJSONObject(i);

						HelpBean help_info = new HelpBean();
						help_info.id = item_json.getLong("uid");
						help_info.title = item_json.getString("name");
						arrHelps.add(help_info);
					}

					help_adapter.notifyDataSetChanged();
				} else {
					AppCommon.showToast(SettingHelpActivity.this, retmsg);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void onFailure(Throwable error) {
			super.onFailure(error);
			stopProgress();

			AppCommon.showToast(SettingHelpActivity.this, R.string.STR_CONN_ERROR);
		}
	};


}