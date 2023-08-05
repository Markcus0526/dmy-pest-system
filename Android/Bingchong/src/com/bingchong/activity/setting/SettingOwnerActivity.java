package com.bingchong.activity.setting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.bingchong.*;
import com.bingchong.adapter.PointCheckListAdapter;
import com.bingchong.bean.PointBean;
import com.bingchong.bean.UserBean;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.utils.AppPreferences;
import com.bingchong.utils.Base64;
import com.bingchong.utils.Utilities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.bingchong.utils.image.SmartImageView;
import org.json.JSONObject;

public class SettingOwnerActivity extends SuperActivity implements OnClickListener {
	
	private static final int PICK_FROM_GALLERY = 1;	
	private final int PIC_CROP = 2;		
	private Bitmap preview_bitmap = null;	
	//selected picture uri
	private Uri picUri;	
	
	private ListView mListView;
	private PointCheckListAdapter mAdapter;
	private ArrayList<PointBean> mList = null;
	
	private UserBean		mUser;
	
	private EditText	edtPlace;
	private EditText	edtJob;
	private Boolean		isAdmin = false;
	private String		m_ptList = "";
	
	private LinearLayout layoutSelect;
	private SmartImageView imgUser = null;
	
	private AppPreferences appPreferences;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.setting_me);
	}

	public void onSave(){
		boolean ret = false;
		
		mUser.job = edtJob.getText().toString().trim();
		mUser.place = edtPlace.getText().toString().trim();		
		
		if(isAdmin){
			;
		}
		else{

			m_ptList = "";
			for(int i = 0; i < mList.size(); i++)
			{
				if(!mList.get(i).isCheck())
					continue;
				if(m_ptList.length() > 0)
					m_ptList = m_ptList + ",";
				m_ptList = m_ptList + mList.get(i).id; 
			}
			
			mUser.job = edtJob.getText().toString().trim();
			mUser.place = edtPlace.getText().toString().trim();
		}

        startProgress();
        CommManager.updateUserInfo(AppCommon.loadUserID(),
                mUser.phone,
                mUser.place,
                mUser.job,
				Global.encodeWithBase64(preview_bitmap),
                m_ptList,
                update_userinfo_handler);
	}

    /*
	@Override
	public Object onTaskRunning(int taskId, Object data) {
		Boolean ret = false;
		try{
			if(isAdmin){
				if(DataMgr.updateUserInfo())
					ret = true;
			}
			else{
				if(DataMgr.uploadCurentPointList(m_ptList))
				{
					if(DataMgr.updateUserInfo() && DataMgr.updatePointsToDB(mList))
						ret = true;
				}
			}
		} catch (Exception e){}

		return ret;
	}
	
	@Override
	public void onTaskResult(int taskId, Object result) {
		closeWaitingDlg();
		
		Boolean ret = Boolean.parseBoolean(result.toString());
		
		if(ret)
			Utilities.showAppToast(this, R.string.strSaveSuccess);
		else
			Utilities.showAppToast(this, R.string.strSaveFail);		
	}
	*/

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btnSave:
			onSave();
			break;
		case R.id.imgUser:
			openGalleryApp();
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case PICK_FROM_GALLERY: {
				
				try{
					Uri selectedImageUri = data.getData();

					preview_bitmap = Media.getBitmap(this.getContentResolver(),
							selectedImageUri);

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


					saveImage(preview_bitmap, file.getAbsolutePath());
				}
				catch(Exception ex){

				}

				performCrop();
			}
			break;
			case PIC_CROP:{
				//get the returned data
				Bundle extras = data.getExtras();
				//get the cropped bitmap
				preview_bitmap = extras.getParcelable("data");
				imgUser.setImageBitmap(preview_bitmap);
				saveImageThread(null, false, false);
			}
			break;
			}
		}
	}
	
	private void saveImage(Bitmap photo, String dstPath){
		try {
			OutputStream outStream = null;
			File file = new File(dstPath);
			outStream = new FileOutputStream(file);
			BitmapFactory.Options bfOptions = new BitmapFactory.Options();
			bfOptions.inSampleSize = 5;
			bfOptions.inDither = false; // Disable Dithering mode
			bfOptions.inPurgeable = true; // Tell to gc that whether
			bfOptions.inInputShareable = true; // Which kind of
			bfOptions.inTempStorage = new byte[32 * 1024]; // Allocate
			photo.compress(Bitmap.CompressFormat.PNG, 50,
					outStream);
			outStream.flush();
			outStream.close();
			preview_bitmap = photo;
		} catch (Exception e) {
			Log.v("Exception", "true");
		}		
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
					mUser.photoData = Base64.encodeBytes(byteArray);
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
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			    //retrieve data on return
			cropIntent.putExtra("return-data", true);
			    //start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);
		}
		catch(ActivityNotFoundException anfe){
			Utilities.showAppToast(this, "Device doesn't support the crop action!");
		}
	 
	}	
	
	private void openGalleryApp() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(
				Intent.createChooser(intent, "Complete action using"),
				PICK_FROM_GALLERY);
	}


    @Override
    public void initializeActivity() {
        findViewById(R.id.btnSave).setOnClickListener(this);
        mUser = DataMgr.m_curUser;
        isAdmin = DataMgr.m_curUser.isAdmin();

        edtPlace = (EditText)findViewById(R.id.me_place);
        edtJob = (EditText)findViewById(R.id.me_job);
        layoutSelect = (LinearLayout)findViewById(R.id.layoutSelect);

		imgUser = (SmartImageView)findViewById(R.id.imgUser);
		imgUser.setImageUrl(mUser.imgurl, R.drawable.person);
		imgUser.setOnClickListener(this);

        if(isAdmin)
            layoutSelect.setVisibility(View.GONE);
        else {
            mListView = (ListView) findViewById(R.id.list_data);

            mAdapter = new PointCheckListAdapter(this);
            mListView.setAdapter(mAdapter);
            mList = DataMgr.getPointsFromDB();
            mAdapter.setData(mList);
        }

        ((TextView)findViewById(R.id.me_name)).setText(mUser.real_name);
        ((TextView)findViewById(R.id.me_user)).setText(mUser.name);
        ((TextView)findViewById(R.id.me_phone)).setText(mUser.phone);
        ((TextView)findViewById(R.id.me_sheng)).setText(mUser.sheng);
        ((TextView)findViewById(R.id.me_shi)).setText(mUser.shi);
        ((TextView)findViewById(R.id.me_xian)).setText(mUser.xian);

        edtJob.setText(mUser.job);
        edtPlace.setText(mUser.place);
    }

    private AsyncHttpResponseHandler update_userinfo_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
					JSONObject retData = result.getJSONObject(ConstMgr.gRetData);

                    AppCommon.showToast(SettingOwnerActivity.this, "保存成功");
                    //DataMgr.uploadCurentPointList(m_ptList);
					mUser.imgurl = retData.getString("imgurl");
					AppCommon.saveUserInfo(mUser);
					AppPreferences appPreferences =  new AppPreferences(getApplicationContext());
					appPreferences.SetUserImgPath(mUser.imgurl);
                    DataMgr.updatePointsToDB(mList);
                    finish();
                }
                else {
                    AppCommon.showToast(SettingOwnerActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(SettingOwnerActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(SettingOwnerActivity.this, R.string.STR_CONN_ERROR);
        }
    };
}