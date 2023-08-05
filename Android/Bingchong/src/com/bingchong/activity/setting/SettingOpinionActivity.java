package com.bingchong.activity.setting;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.widget.*;
import com.bingchong.*;
import com.bingchong.bean.OpinionBean;
import com.bingchong.bean.OpinionKindBean;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpClient;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.utils.Utilities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import org.json.JSONArray;
import org.json.JSONObject;

public class SettingOpinionActivity extends SuperActivity {
	private ArrayList<OpinionKindBean>	arrKinds = new ArrayList<OpinionKindBean>();

	private int cur_id = 0;

	private Button btnConfirm = null;
	private EditText edtContents = null;

	private final int type_preset = 0;
	private final int type_other = 1;

	private int cur_type = type_preset;

	private ImageButton btn_radio_type = null;
	private TextView txt_type = null;
	private ImageButton btn_type = null;


	private ImageButton btn_radio_other = null;
	private EditText edt_type_other = null;


	private TextView txt_type_other = null;
	private TextView txt_type_preset = null;


	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.setting_opinion);
	}

	@Override
	public void initializeActivity() {
		btnConfirm = (Button)findViewById(R.id.btn_confirm);
		edtContents = (EditText)findViewById(R.id.edt_content);

		btn_radio_type = (ImageButton)findViewById(R.id.btn_radio_type);
		txt_type = (TextView)findViewById(R.id.txt_type);
		btn_type = (ImageButton)findViewById(R.id.btn_type);


		btn_radio_other = (ImageButton)findViewById(R.id.btn_radio_other);
		edt_type_other = (EditText)findViewById(R.id.edt_type_other);

		// Initialize status
		{
			btn_radio_type.setImageResource(R.drawable.bk_radio_sel);
			btn_radio_other.setImageResource(R.drawable.bk_radio_normal);

			btn_type.setEnabled(true);
			edt_type_other.setEnabled(false);
		}


		txt_type_preset = (TextView)findViewById(R.id.lbl_type);
		txt_type_preset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickPreset();
			}
		});
		btn_radio_type.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickPreset();
			}
		});

		txt_type_other = (TextView)findViewById(R.id.txt_type_other);
		txt_type_other.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickOther();
			}
		});
		btn_radio_other.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickOther();
			}
		});

		btn_type.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickType();
			}
		});

		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onUpload();
			}
		});

		startProgress();
		CommManager.getOpinionKind(new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				stopProgress();

				try {
					JSONObject result = new JSONObject(content);

					int nRetCode = result.getInt("retcode");
					String szRetMsg = result.getString("retmsg");

					if (nRetCode == 0) {
						JSONArray arr_data = result.getJSONArray("retdata");

						for (int i = 0; i < arr_data.length(); i++) {
							JSONObject item = arr_data.getJSONObject(i);

							OpinionKindBean item_info = new OpinionKindBean();
							item_info.id = item.getInt("uid");
							item_info.name = item.getString("name");

							arrKinds.add(item_info);
						}

						if (arrKinds.size() > 0) {
							txt_type.setText(arrKinds.get(0).name);
							cur_id = arrKinds.get(0).id;
						}
					} else {
						AppCommon.showToast(SettingOpinionActivity.this, szRetMsg);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable error) {
				super.onFailure(error);
				stopProgress();

				AppCommon.showToast(SettingOpinionActivity.this, R.string.STR_CONN_ERROR);
			}
		});
	}



	private void onClickType() {
		ArrayList<String> arr_items = new ArrayList<String>();
		if (arrKinds == null || arrKinds.size() == 0)
			return;

		for (int i = 0; i < arrKinds.size(); i++)
		{
			arr_items.add(arrKinds.get(i).name);
		}

		SelectSingleDialog dialog = new SelectSingleDialog(SettingOpinionActivity.this);
		dialog.initItemData(arr_items);
		dialog.delegate = new SelectSingleDialog.SelectItemListener() {
			@Override
			public void onItemClicked(String item_data, int index) {
				OpinionKindBean item = arrKinds.get(index);
				txt_type.setText(item.name);

				cur_id = item.id;
			}
		};
		dialog.show();
	}


	private void onUpload() {
		if (cur_type == type_other && edt_type_other.getText().toString().equals("")) {
			AppCommon.showToast(SettingOpinionActivity.this, "请输入题目");
			Global.selectAllText(edtContents);
			return;
		}

		if (edtContents.getText().toString().equals("")) {
			AppCommon.showToast(SettingOpinionActivity.this, "请输入意见内容");
			Global.selectAllText(edtContents);
			return;
		}

		startProgress();
		CommManager.uploadOpinion(cur_id,
				AppCommon.loadUserID(),
				cur_id > 0 ? txt_type.getText().toString() : edt_type_other.getText().toString(),
				edtContents.getText().toString(),
				upload_handler);
	}



	private AsyncHttpResponseHandler upload_handler = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			super.onSuccess(content);
			stopProgress();

			try {
				JSONObject result = new JSONObject(content);

				int nRetCode = result.getInt("retcode");
				String szRetMsg = result.getString("retmsg");

				AppCommon.showToast(SettingOpinionActivity.this, szRetMsg);

				if (nRetCode == 0)
					popOverCurActivityAnimated();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void onFailure(Throwable error) {
			super.onFailure(error);
			stopProgress();

			AppCommon.showToast(SettingOpinionActivity.this, R.string.STR_CONN_ERROR);
		}
	};


	private void onClickOther() {
		btn_radio_type.setImageResource(R.drawable.bk_radio_normal);
		btn_radio_other.setImageResource(R.drawable.bk_radio_sel);

		cur_id = 0;

		btn_type.setEnabled(false);
		edt_type_other.setEnabled(true);
	}



	private void onClickPreset() {
		btn_radio_type.setImageResource(R.drawable.bk_radio_sel);
		btn_radio_other.setImageResource(R.drawable.bk_radio_normal);

		btn_type.setEnabled(true);
		edt_type_other.setEnabled(false);
	}



}










