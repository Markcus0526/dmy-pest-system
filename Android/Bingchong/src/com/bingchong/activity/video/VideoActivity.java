package com.bingchong.activity.video;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bairuitech.anychat.*;
import com.bairuitech.bussinesscenter.BussinessCenter;
import com.bairuitech.bussinesscenter.UserItem;
import com.bingchong.*;
import com.bingchong.bean.Experts;

/**
 * @class：VideoActivity.java 
 * @description: 视频窗口页
 * @author: 金铁钢
 * 
 * @history:
 *   	    日期  	     版本             担当者         修改内容
 *   2014-12-3  1.0    金铁钢            初版
 */
public class VideoActivity extends Activity implements AnyChatBaseEvent,
		OnClickListener, OnTouchListener, AnyChatVideoCallEvent, AnyChatUserInfoEvent , AnyChatRecordEvent{
	private SurfaceView mSurfaceSelf;
	private SurfaceView mSurfaceRemote;
	private ProgressBar mProgressSelf;
	private ProgressBar mProgressRemote;
	private ImageView mImgSwitch;
	private TextView mTxtTitle;
	private TextView mTxtTime;
	private Button mBtnEndSession;
	//private Button mRecordBtn;
	private Dialog dialog;

	private AnyChatCoreSDK anychat;
	private Handler mHandler;
	private Timer mTimerCheckAv;
	private Timer mTimerShowVideoTime;
	private TimerTask mTimerTask;
	@SuppressWarnings("unused")
	private ConfigEntity configEntity;

	boolean bSelfVideoOpened = false;
	boolean bOtherVideoOpened = false;
	boolean bVideoViewLoaded = false;
	public static final int MSG_CHECKAV = 1;
	public static final int MSG_TIMEUPDATE = 2;
	public static final int PROGRESSBAR_HEIGHT = 5;

	int dwTargetUserId;
	int videoIndex = 0;
	int videocallSeconds = 0;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		initSdk();
		dwTargetUserId = BussinessCenter.sessionItem
				.getPeerUserItem(BussinessCenter.selfUserId);
		initView();
		if(Config.IS_SET_FONTS)
			setTypeface();
		
		anychat.EnterRoom(BussinessCenter.sessionItem.roomId, "");
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case MSG_CHECKAV:
					CheckVideoStatus();
					updateVolume();
					break;
				case MSG_TIMEUPDATE:
					
					mTxtTime.setText(BaseMethod
							.getTimeShowString(videocallSeconds++));
					break;
				}

			}
		};
		initTimerCheckAv();
		initTimerShowTime();

	}

	@Override
	protected void onPause() {
		//结束视频录制
		BussinessCenter.getBussinessCenter().videoRecord(dwTargetUserId , 0);
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// 如果是采用Java视频显示，则需要绑定用户
		if (AnyChatCoreSDK
				.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
			videoIndex = anychat.mVideoHelper.bindVideo(mSurfaceRemote
					.getHolder());
			anychat.mVideoHelper.SetVideoUser(videoIndex, dwTargetUserId);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		BussinessCenter.mContext = this;
		//开始视频录制
		BussinessCenter.getBussinessCenter().videoRecord(dwTargetUserId , 1);
		anychat.SetBaseEvent(this);
		anychat.SetRecordSnapShotEvent(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		anychat.UserCameraControl(-1, 0);
		anychat.UserSpeakControl(-1, 0);
		anychat.UserSpeakControl(dwTargetUserId, 0);
		anychat.UserCameraControl(dwTargetUserId, 0);
		mTimerCheckAv.cancel();
		mTimerShowVideoTime.cancel();
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
		anychat.LeaveRoom(-1);
		

	}

	private void initSdk() {
		if (anychat == null)
			anychat = new AnyChatCoreSDK();
		anychat.SetBaseEvent(this);
		anychat.SetVideoCallEvent(this);
		anychat.SetUserInfoEvent(this);
		anychat.mSensorHelper.InitSensor(this);
		// 初始化Camera上下文句柄
		AnyChatCoreSDK.mCameraHelper.SetContext(this);

	}

	private void initTimerShowTime() {
		if (mTimerShowVideoTime == null)
			mTimerShowVideoTime = new Timer();
		mTimerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(MSG_TIMEUPDATE);
			}
		};
		mTimerShowVideoTime.schedule(mTimerTask, 100, 1000);
	}

	private void initTimerCheckAv() {
		if (mTimerCheckAv == null)
			mTimerCheckAv = new Timer();
		mTimerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(MSG_CHECKAV);
			}
		};
		mTimerCheckAv.schedule(mTimerTask, 1000, 100);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_ENDCALL,
					dwTargetUserId, this , "");
			dialog.show();
		}

		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		this.setContentView(R.layout.video_activity);
		mSurfaceSelf = (SurfaceView) findViewById(R.id.surface_local);
		mSurfaceRemote = (SurfaceView) findViewById(R.id.surface_remote);
		mProgressSelf = (ProgressBar) findViewById(R.id.progress_local);
		mProgressRemote = (ProgressBar) findViewById(R.id.progress_remote);
		mImgSwitch = (ImageView) findViewById(R.id.img_switch);
		mTxtTitle = (TextView) findViewById(R.id.txt_title);
		UserItem userItem = BussinessCenter.getBussinessCenter().getUserItemByUserId(dwTargetUserId);
		if (userItem != null){
			for(int i = 0 ; i < Config.expertsList.size(); i++){
				Experts experts = Config.expertsList.get(i);
				if(experts.getUserName().equals(userItem.getUserName())){
					mTxtTitle.setText("与" + experts.getExpertName()  + "视频通话");
					break;
				}else{
					mTxtTitle.setText("与" + userItem.getUserName()  + "视频通话");
				}
			}
		}
		mTxtTime = (TextView) findViewById(R.id.txt_time);
		mBtnEndSession = (Button) findViewById(R.id.btn_endsession);
		/*mRecordBtn = (Button) findViewById(R.id.record_btn);
		mRecordBtn.setOnClickListener(this);*/
		mBtnEndSession.setOnClickListener(this);
		mImgSwitch.setOnClickListener(this);
		mSurfaceRemote.setTag(dwTargetUserId);
		configEntity = ConfigService.LoadConfig(this);
		mSurfaceSelf.getHolder().setType(
				SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		/*if (configEntity.videoOverlay != 0) {
		}*/
		mSurfaceSelf.setZOrderOnTop(true);
		// 如果是采用Java视频采集，则设置Surface的CallBack
		if (AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
			mSurfaceSelf.getHolder().addCallback(AnyChatCoreSDK.mCameraHelper);
			Log.i("ANYCHAT", "VIDEOCAPTRUE---" + "JAVA");
		}

		// 如果是采用Java视频显示，则设置Surface的CallBack
		if (AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
			videoIndex = anychat.mVideoHelper.bindVideo(mSurfaceRemote.getHolder());
			anychat.mVideoHelper.SetVideoUser(videoIndex, dwTargetUserId);
			Log.i("ANYCHAT", "VIDEOSHOW---" + "JAVA");
		}

		final View layoutLocal = (View) findViewById(R.id.frame_local_area);
		// 得到xml布局中视频区域的大小
		layoutLocal.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						if (!bVideoViewLoaded) {
							bVideoViewLoaded = true;
						}
					}
				});
		// 判断是否显示本地摄像头切换图标
		if (AnyChatCoreSDK
				.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
			if (AnyChatCoreSDK.mCameraHelper.GetCameraNumber() > 1) {
				mImgSwitch.setVisibility(View.VISIBLE);
				// 默认打开前置摄像头
				AnyChatCoreSDK.mCameraHelper.SelectVideoCapture(AnyChatCoreSDK.mCameraHelper.CAMERA_FACING_FRONT);
			}
		} else {
			String[] strVideoCaptures = anychat.EnumVideoCapture();
			if (strVideoCaptures != null && strVideoCaptures.length > 1) {
				mImgSwitch.setVisibility(View.VISIBLE);
				// 默认打开前置摄像头
				for (int i = 0; i < strVideoCaptures.length; i++) {
					String strDevices = strVideoCaptures[i];
					if (strDevices.indexOf("Front") >= 0) {
						anychat.SelectVideoCapture(strDevices);
						break;
					}
				}
			}
		}
		// 根据屏幕方向改变本地surfaceview的宽高比
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			adjustLocalVideo(true);
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			adjustLocalVideo(false);
		}
		
	}
	
	/**
     * @description 设置字体
     */
    public void setTypeface(){
    	Typeface face = Typeface.createFromAsset(getAssets(),Config.FONTS);
    	mTxtTime.setTypeface(face);
    	mBtnEndSession.setTypeface(face);
    }

	/***
	 * 调整本地视频区域宽度为界面宽度大小的1/4。竖屏时，本地预览的surfaceview的宽高比例为分辨率高宽比例;横屏时，
	 * 本地预览的surfaceview的宽高比例为分辨率宽高比例
	 * 
	 * @param width
	 *            宽
	 * 
	 */
	public void adjustLocalVideo(boolean bLandScape) {
		float width;
		float height = 0;
		DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		width = (float) dMetrics.widthPixels / 4;
		LinearLayout layoutLocal = (LinearLayout) this
				.findViewById(R.id.frame_local_area);
		FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) layoutLocal
				.getLayoutParams();
		if (bLandScape) {

			if (AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL) != 0)
				height = width * AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL)
						/ AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL)
						+ PROGRESSBAR_HEIGHT;
			else
				height = (float) 3 / 4 * width + PROGRESSBAR_HEIGHT;
		} else {

			if (AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL) != 0)
				height = width * AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL)
						/ AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL)
						+ PROGRESSBAR_HEIGHT;
			else
				height = (float) 4 / 3 * width + PROGRESSBAR_HEIGHT;
		}
		layoutParams.width = (int) width;
		layoutParams.height = (int) height;
		layoutLocal.setLayoutParams(layoutParams);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			adjustLocalVideo(true);
			AnyChatCoreSDK.mCameraHelper.setCameraDisplayOrientation();
		} else {
			adjustLocalVideo(false);
			AnyChatCoreSDK.mCameraHelper.setCameraDisplayOrientation();
		}

	}

	// 判断视频是否已打开
	private void CheckVideoStatus() {
		if (!bOtherVideoOpened) {
			if (anychat.GetCameraState(dwTargetUserId) == 2
					&& anychat.GetUserVideoWidth(dwTargetUserId) != 0) {
				SurfaceHolder holder = mSurfaceRemote.getHolder();
				// 如果是采用内核视频显示（非Java驱动），则需要设置Surface的参数
				if (AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) != AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
					holder.setFormat(PixelFormat.RGB_565);
					holder.setFixedSize(anychat.GetUserVideoWidth(-1), anychat.GetUserVideoHeight(-1));
				}
				Surface s = holder.getSurface();
				if (AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
					anychat.mVideoHelper.SetVideoUser(videoIndex, dwTargetUserId);
				} else
					anychat.SetVideoPos(dwTargetUserId, s, 0, 0, 0, 0);
				bOtherVideoOpened = true;
			}
		}
		if (!bSelfVideoOpened) {
			if (anychat.GetCameraState(-1) == 2 && anychat.GetUserVideoWidth(-1) != 0) {
				SurfaceHolder holder = mSurfaceSelf.getHolder();
				if (AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) != AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
					holder.setFormat(PixelFormat.RGB_565);
					holder.setFixedSize(anychat.GetUserVideoWidth(-1), anychat.GetUserVideoHeight(-1));
				}
				Surface s = holder.getSurface();
				anychat.SetVideoPos(-1, s, 0, 0, 0, 0);
				bSelfVideoOpened = true;
			}
		}
	}

	private void updateVolume() {
		mProgressSelf.setProgress(anychat.GetUserSpeakVolume(-1));
		mProgressRemote.setProgress(anychat.GetUserSpeakVolume(dwTargetUserId));
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		// TODO Auto-generated method stub
		if (dialog != null
				&& dialog.isShowing()
				&& DialogFactory.getCurrentDialogId() == DialogFactory.DIALOGID_RESUME) {
			dialog.dismiss();
		}
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		// TODO Auto-generated method stub
		if (dwErrorCode == 0) {
			BussinessCenter.selfUserId = dwUserId;
			BussinessCenter.selfUserName = anychat.GetUserName(dwUserId);
		}
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub
		if (dwErrorCode == 0) {
			anychat.UserCameraControl(-1, 1);
			anychat.UserSpeakControl(-1, 1);
			bSelfVideoOpened = false;
		}
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
		anychat.UserCameraControl(dwTargetUserId, 1);
		anychat.UserSpeakControl(dwTargetUserId, 1);
		bOtherVideoOpened = false;
	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub
		anychat.UserCameraControl(dwTargetUserId, 1);
		anychat.UserSpeakControl(dwTargetUserId, 1);
		bOtherVideoOpened = false;

	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// TODO Auto-generated method stub
		anychat.UserCameraControl(-1, 0);
		anychat.UserSpeakControl(-1, 0);
		anychat.UserSpeakControl(dwTargetUserId, 0);
		anychat.UserCameraControl(dwTargetUserId, 0);
		if (dwErrorCode == 0) {
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			BaseMethod.showToast(this.getString(R.string.session_end), this);
			dialog = DialogFactory.getDialog(DialogFactory.DIALOG_NETCLOSE,
					DialogFactory.DIALOG_NETCLOSE, this , "");
			dialog.show();
		} else {
			BaseMethod.showToast(this.getString(R.string.str_serverlink_close),
					this);
            /*
			Intent intent = new Intent();
			intent.putExtra("INTENT", Constant.AGAIGN_LOGIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, LoginActivity.class);
			this.startActivity(intent);
			*/
			this.finish();
		}
		Log.i("ANYCHAT", "OnAnyChatLinkCloseMessage:" + dwErrorCode);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == mBtnEndSession) {
			dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_ENDCALL,
					dwTargetUserId, this , "");
			dialog.show();
		}
		if (v == mImgSwitch) {
			// 如果是采用Java视频采集，则在Java层进行摄像头切换
			if (AnyChatCoreSDK
					.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
				AnyChatCoreSDK.mCameraHelper.SwitchCamera();
				return;
			}
			String strVideoCaptures[] = anychat.EnumVideoCapture();
			String temp = anychat.GetCurVideoCapture();
			for (int i = 0; i < strVideoCaptures.length; i++) {
				if (!temp.equals(strVideoCaptures[i])) {
					anychat.UserCameraControl(-1, 0);
					anychat.SelectVideoCapture(strVideoCaptures[i]);
					anychat.UserCameraControl(-1, 1);
					bSelfVideoOpened = false;
					break;
				}
			}
		}
		/*if(v == mRecordBtn){//录像
			
		}*/
	}

	@Override
	public void OnAnyChatVideoCallEvent(int dwEventType, int dwUserId,
			int dwErrorCode, int dwFlags, int dwParam, String userStr) {
		// TODO Auto-generated method stub
		this.finish();
	}

	@Override
	public void OnAnyChatUserInfoUpdate(int dwUserId, int dwType) {
		// TODO Auto-generated method stub
		if (dwUserId == 0 && dwType == 0) {
			BussinessCenter.getBussinessCenter().getOnlineFriendDatas();
		}
	}

	@Override
	public void OnAnyChatFriendStatus(int dwUserId, int dwStatus) {
		// TODO Auto-generated method stub
		BussinessCenter.getBussinessCenter().onUserOnlineStatusNotify(dwUserId, dwStatus);
	}
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void OnAnyChatRecordEvent(int dwUserId, String lpFileName,
			int dwElapse, int dwFlags, int dwParam, String lpUserStr) {
		//录制完成事件
		Log.e("AnyChatRecord" , "dwUserId == " + dwUserId);
		Log.e("AnyChatRecord" , "lpFileName == " + lpFileName);
		Log.e("AnyChatRecord" , "dwElapse == " + dwElapse);
		Log.e("AnyChatRecord" , "dwFlags == " + dwFlags);
		Log.e("AnyChatRecord" , "dwParam == " + dwParam);
		Log.e("AnyChatRecord" , "lpUserStr == " + lpUserStr);
	}

	@Override
	public void OnAnyChatSnapShotEvent(int dwUserId, String lpFileName,
			int dwFlags, int dwParam, String lpUserStr) {
		// 图像抓拍事件
		
	}

}
