package com.bingchong.activity.create;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import com.bingchong.*;
import com.bingchong.activity.db.DBBlightInfoActivity;
import com.bingchong.activity.db.DBPlaceInfoActivity;
import com.bingchong.adapter.OnClickItemAdapterListener;
import com.bingchong.adapter.SingleAcceptAdapter;
import com.bingchong.bean.BlightBean;
import com.bingchong.bean.PointBean;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserBlight;
import com.bingchong.utils.AppDeviceUtils;
import com.bingchong.utils.Base64;
import com.bingchong.utils.ResolutionSet;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bingchong.backend.ServiceBaiduMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class CreateBlightActivity extends SuperActivity implements OnClickListener, OnClickItemAdapterListener, OnItemSelectedListener{

	private static final int PICK_FROM_CAMERA = 1;
	private final int PIC_CROP = 2;	
	private Bitmap preview_bitmap = null;
	//captured picture uri
	private Uri picUri;
	
	private ListView mListView;
	private SingleAcceptAdapter mAdapter;

	private ImageView imgView;
	private ArrayList<BlightBean>	mList = new ArrayList<BlightBean>();
	
	BlightBean	mCurObj = new BlightBean();
	
	EditText edt_name;
	EditText edt_info1;
	EditText edt_info2;
	EditText edt_info3;
	EditText edt_long;
	EditText edt_lat;
	EditText edt_note;
	

	
	// spinner
	private final int SPINNER_ID_BLIGHT_TYPE = 103;
	private final static int TYPE_FLY = 1;
	private int index_type = 0;
	private Spinner mSpinnerBlightType;	
	
	private Boolean isReqReport = false;

	// for common
	private LinearLayout mLayoutInfo = null;
	private LinearLayout mLayoutNew = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.create_blight);
	}
	
	private void initSpinner(){
		// init spinner
		mSpinnerBlightType = (Spinner) findViewById(R.id.spinner_blight_type);
		mSpinnerBlightType.setOnItemSelectedListener(this);
		
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.blights, R.layout.item_spinner);		
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlightType.setAdapter(adapter);
        mSpinnerBlightType.setSelection(index_type);		
	}

	private void onSave()
	{
		if(mCurObj == null)
			return;
		try{
			mCurObj.longitude = Double.parseDouble(edt_long.getText().toString().trim());
			mCurObj.latitude = Double.parseDouble(edt_lat.getText().toString().trim());
		}
		catch(Exception ex)
		{
		}
		mCurObj.sheng_id = (int)DataMgr.m_curUser.shengs_id;
		mCurObj.shi_id = (int)DataMgr.m_curUser.shis_id;
		mCurObj.xian_id = (int)DataMgr.m_curUser.xians_id;
		mCurObj.name	= edt_name.getText().toString().trim();
		mCurObj.info1 = edt_info1.getText().toString().trim();
		mCurObj.info2 = edt_info2.getText().toString().trim();
		mCurObj.info3 = edt_info3.getText().toString().trim();
		mCurObj.note = edt_note.getText().toString().trim();

		mCurObj.setKind(index_type == TYPE_FLY);

		if(mCurObj.name.length() <= 0) {
			AppCommon.showToast(CreateBlightActivity.this, "请输入病虫害名称");
			return;
		}
		if(mCurObj.info1.length() <= 0) {
			AppCommon.showToast(CreateBlightActivity.this, "请输入乡镇");
			return;
		}
		if(mCurObj.info2.length() <= 0) {
			AppCommon.showToast(CreateBlightActivity.this, "请输入村组");
			return;
		}
		if(mCurObj.info3.length() <= 0) {
			AppCommon.showToast(CreateBlightActivity.this, "请输入发生作物");
			return;
		}
		if(preview_bitmap == null) {
			AppCommon.showToast(CreateBlightActivity.this, "请输入病虫害照片");
			return;
		}
		
		isReqReport = true;
        startProgress();
        CommManager.uploadTempBlight(AppCommon.loadUserID(),
                mCurObj.name,
                mCurObj.kind.equals("B") ? 0 : 1,
                mCurObj.longitude + "",
                mCurObj.latitude + "",
                mCurObj.info1,
                mCurObj.info2,
                mCurObj.info3,
                mCurObj.note,
				Global.encodeWithBase64(preview_bitmap),
                upload_tempblight_handler);
		//startGetStatusTask();
	}
	
	private void initUI() {
		isReqReport = false;
		if(mList == null)
			mList = new ArrayList<BlightBean>();
		
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

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		Boolean ret = true;
		if(isReqReport)
			ret = DataMgr.insertExtraBlight(mCurObj);
			
		mList = DataMgr.getBlights("", true);
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
	*/

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
					mCurObj.imgUri = Base64.encodeBytes(byteArray);
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
		long id = mList.get(index).id;

		Bundle bundle = new Bundle();
		bundle.putString(Constant.EXTRA_PARAM_INDEX, String.valueOf(id));
		bundle.putString(Constant.EXTRA_PARAM_ISTEMP, String.valueOf(true));

		pushNewActivityAnimated(DBBlightInfoActivity.class, bundle);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.spinner_blight_type){
			index_type = position;
		}

		ResolutionSet.instance.iterateChild(view, mScrSize.x, mScrSize.y);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void initializeActivity() {
        mLayoutInfo = (LinearLayout)findViewById(R.id.list_layout);
        mLayoutNew = (LinearLayout)findViewById(R.id.layout_blight_info);
        imgView = (ImageView)findViewById(R.id.imagePhoto);
        imgView.setOnClickListener(this);

        // button
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

        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_info1 = (EditText)findViewById(R.id.edt_info1);
        edt_info2 = (EditText)findViewById(R.id.edt_info2);
        edt_info3 = (EditText)findViewById(R.id.edt_info3);
        edt_long = (EditText)findViewById(R.id.edt_long);
        edt_lat = (EditText)findViewById(R.id.edt_lat);
        edt_long.setText(Double.toString(ServiceBaiduMap.mLong));
        edt_lat.setText(Double.toString(ServiceBaiduMap.mLat));
        edt_note = (EditText)findViewById(R.id.edt_note);

        initSpinner();

        showNewPage();
        startProgress();
        CommManager.getTempBlights(0, AppCommon.loadUserID(), "", get_tempblight_handler);
    }

    private AsyncHttpResponseHandler upload_tempblight_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    AppCommon.showToast(CreateBlightActivity.this, "提交成功");
                    finish();
                }
                else {
                    AppCommon.showToast(CreateBlightActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(CreateBlightActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(CreateBlightActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler get_tempblight_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    mList.clear();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for (int i = 0; i < retdata.length(); i++) {
                        BlightBean obj = ParserBlight.parseJsonResponse(retdata.getJSONObject(i));
                        if(obj != null)
                            mList.add(obj);
                    }
                    initUI();
                }
                else {
                    AppCommon.showToast(CreateBlightActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(CreateBlightActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(CreateBlightActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}