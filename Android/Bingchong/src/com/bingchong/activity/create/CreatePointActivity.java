package com.bingchong.activity.create;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import com.bingchong.Constant;
import com.bingchong.R;
import com.bingchong.WaitingTaskActivity;
import com.bingchong.activity.db.DBPlaceInfoActivity;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleAcceptAdapter;
import com.bingchong.bean.PointBean;
import com.bingchong.db.DataMgr;
import com.bingchong.utils.AppDeviceUtils;
import com.bingchong.utils.Base64;
import com.bingchong.utils.Utilities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.bingchong.backend.ServiceBaiduMap;

public class CreatePointActivity extends WaitingTaskActivity implements OnClickListener, OnClickItemAdapterListener{

	private static final int PICK_FROM_CAMERA = 1;	
	private final int PIC_CROP = 2;	
	private Bitmap preview_bitmap = null;
	//captured picture uri
	private Uri picUri;

	private ListView mListView;
	private SingleAcceptAdapter mAdapter;

	private ImageView imgView;
	private ArrayList<PointBean>	mList = null;
	
	PointBean	mCurPoint = new PointBean();
	
	EditText edt_name;
	EditText edt_longitude;
	EditText edt_latitude;	
	EditText edt_info1;
	EditText edt_info2;
	EditText edt_info3;
	EditText edt_info4;
	EditText edt_info5;
	EditText edt_info6;
	EditText edt_note;	

	// for common
	private LinearLayout mLayoutInfo = null;
	private LinearLayout mLayoutNew = null;
	
	private Boolean isReqReport = false;	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.create_point);

		mLayoutInfo = (LinearLayout)findViewById(R.id.list_layout);
		mLayoutNew = (LinearLayout)findViewById(R.id.layout_point_info);
		imgView = (ImageView)findViewById(R.id.imagePhoto);

		// button
		imgView.setOnClickListener(this);
		findViewById(R.id.btnPhoto).setOnClickListener(this);
		findViewById(R.id.btnNew).setOnClickListener(this);
		findViewById(R.id.btnInfo).setOnClickListener(this);

		findViewById(R.id.btnSave).setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.list_data);

		mListView = (ListView) findViewById(R.id.list_data);
		mAdapter = new SingleAcceptAdapter(this, DataMgr.m_curUser.isAdmin());
		mListView.setAdapter(mAdapter);
		mAdapter.setOnListener(this);

		// for capture image
		String packageName = getApplicationContext().getPackageName();

		edt_name = (EditText)findViewById(R.id.edt_place_name);
		edt_longitude = (EditText)findViewById(R.id.edt_place_long);
		edt_latitude = (EditText)findViewById(R.id.edt_place_lat);
        edt_longitude.setText(Double.toString(ServiceBaiduMap.mLong));
        edt_latitude.setText(Double.toString(ServiceBaiduMap.mLat));
		edt_info1 = (EditText)findViewById(R.id.edt_place_info1);
		edt_info2 = (EditText)findViewById(R.id.edt_place_info2);
		edt_info3 = (EditText)findViewById(R.id.edt_place_info3);
		edt_info4 = (EditText)findViewById(R.id.edt_place_info4);
		edt_info5 = (EditText)findViewById(R.id.edt_place_info5);
		edt_info6 = (EditText)findViewById(R.id.edt_place_info6);
		edt_note = (EditText)findViewById(R.id.edt_place_note);			

		checkAutoResize();
		
		showNewPage();
		
		startGetStatusTask();
	}

	private void onSave()
	{
		if(mCurPoint == null)
			return;
		mCurPoint.name	= edt_name.getText().toString().trim();
		try{
			mCurPoint.longitude = Double.parseDouble(edt_longitude.getText().toString().trim());
			mCurPoint.latitude = Double.parseDouble(edt_latitude.getText().toString().trim());
		}
		catch(Exception ex)
		{
			
		}
		mCurPoint.sheng_id = (int)DataMgr.m_curUser.shengs_id;
		mCurPoint.shi_id = (int)DataMgr.m_curUser.shis_id;
		mCurPoint.xian_id = (int)DataMgr.m_curUser.xians_id;
		mCurPoint.info1 = edt_info1.getText().toString().trim();
		mCurPoint.info2 = edt_info2.getText().toString().trim();
		mCurPoint.info3 = edt_info3.getText().toString().trim();
		mCurPoint.info4 = edt_info4.getText().toString().trim();
		mCurPoint.info5 = edt_info5.getText().toString().trim();
		mCurPoint.info6 = edt_info6.getText().toString().trim();
		mCurPoint.note	= edt_note.getText().toString().trim();
		
		isReqReport = true;
		startGetStatusTask();
	}

	private void initUI() {
		if(mList == null)
			mList = new ArrayList<PointBean>();
		
		if(mList != null){
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
		}
	}

	private void showNewPage(){
		mLayoutInfo.setVisibility(View.GONE);
		mLayoutNew.setVisibility(View.VISIBLE);
	}

	private void showInfo(){
		mLayoutInfo.setVisibility(View.VISIBLE);
		mLayoutNew.setVisibility(View.GONE);
	}

	@Override
	public Object onTaskRunning(int taskId, Object data) {
		Boolean ret = true;
		if(isReqReport)		
			ret = DataMgr.insertExtraPoint(mCurPoint);
		mList = DataMgr.getPoints("", 0, true);
		return ret;
	}

	@Override
	public void onTaskResult(int taskId, Object result) {
		closeWaitingDlg();
		if(isReqReport){
			Boolean ret = Boolean.parseBoolean(result.toString());
			if(ret)
			{
				Utilities.showAppToast(getApplicationContext(), R.string.strReportSuccess);
			}
			else
				Utilities.showAppToast(getApplicationContext(), R.string.strReportFail);				
		}		
		initUI();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {

		case R.id.btnPhoto:
		case R.id.imagePhoto:
			openCameraApp();
			break;				
		case R.id.btnNew:
			showNewPage();
			break;				
		case R.id.btnInfo:
			showInfo();
			break;				
		case R.id.btnSave:
			onSave();
			break;				
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try{
			if (resultCode == RESULT_OK) {
				switch (requestCode) {
				case PICK_FROM_CAMERA: {
					//picUri = data.getData();
					performCrop();
				}
				break;
				case PIC_CROP:{
					//get the returned data
					Bundle extras = data.getExtras();
					//get the cropped bitmap
					preview_bitmap = extras.getParcelable("data");
					imgView.setImageBitmap(preview_bitmap);
					saveImageThread(null, false, false);
				}
				break;
				}
			}
		}
		catch(Exception ex){}
	}

	private void saveImageThread(final Bitmap photo,
			final Boolean isFromGallery, final Boolean isSketch) {

		new Thread() {
			@Override
			public void run() {
				try {
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					preview_bitmap.compress(Bitmap.CompressFormat.PNG,
							90, stream);
					byte[] byteArray = stream.toByteArray();
					mCurPoint.imgUri = Base64.encodeBytes(byteArray);
				} catch (Exception e) {
					Log.v("Exception", "true");
				}
			};

		}.start();
	}	
	
	private void performCrop(){
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
			    //indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			    //set crop properties
			cropIntent.putExtra("crop", "true");
			    //indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			    //indicate output X and Y
			cropIntent.putExtra("outputX", 255);
			cropIntent.putExtra("outputY", 255);
			    //retrieve data on return
			cropIntent.putExtra("return-data", true);
			    //start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);
		}
		catch(ActivityNotFoundException anfe){
			Utilities.showAppToast(this, "Device doesn't support the crop action!");
		}
	 
	}
	
	private void openCameraApp() {

		if (!AppDeviceUtils.isSdPresent())
			return;
		
	    String packageName = getApplicationContext().getPackageName();
		
		File imageDirectory = new File(
				android.os.Environment.getExternalStorageDirectory(),
				"Android/data/" + packageName);	
		
		if (!imageDirectory.isDirectory()) {
			imageDirectory.mkdirs();
		}
		
		if (!imageDirectory.isDirectory()){
			Utilities.showAppToast(this, R.string.sderror);
			return;
		}

		File file = new File(imageDirectory, "/photo.png");
		picUri = Uri.fromFile(file);
		String imgCameraPath = Uri.fromFile(file).getPath();

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			intent.putExtra("return-data", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
			startActivityForResult(intent, PICK_FROM_CAMERA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void OnClickListener(int index) {

		Intent intent = new Intent(this, DBPlaceInfoActivity.class);
		int id = mList.get(index).id;
		intent.putExtra(Constant.EXTRA_PARAM_INDEX, String.valueOf(id));
		intent.putExtra(Constant.EXTRA_PARAM_ISTEMP, String.valueOf(true));
		startActivity(intent);
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
		// TODO Auto-generated method stub

	}
}