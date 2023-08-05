package com.bingchong;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.bingchong.activity.LoginActivity;
import com.bingchong.utils.ResolutionSet;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;



/**
 * Created with IntelliJ IDEA.
 * User: KHM
 * Date: 14-7-29
 * Time: 上午8:43
 * To change this template use File | Settings | File Templates.
 */
public abstract class SuperActivity extends FragmentActivity
{
	/**
	 * Class for the activity transition animation constants
	 */
	public class AnimConst {
		public static final int ANIMDIR_NONE			= -1;
		public static final int ANIMDIR_FROM_LEFT		= 0;
		public static final int ANIMDIR_FROM_RIGHT		= 1;

		public static final String EXTRA_ANIMDIR		= "anim_direction";
	}


	// Used for the progress dialog
	public com.bingchong.utils.ProgressDialog dlg_prog	= null;

	// When click back button on last activity, must process double click back button in 3 seconds to finish activity
	private final int BACK_PRESS_MAX_INTERVAL			= 3000;
	private boolean isPressedBack						= false;
	private long backPressedTime						= 0;
	////////////////////////////////////////////////////////////////////////////////////////////


	// When update the app, at first download the new apk file. The file path for the .
	private String download_app_path					= "";


	// Variable to save the top activity instance
	public static SuperActivity mInstance				= null;


	protected void onCreate(Bundle savedInstanceState, int layout_id) {
		super.onCreate(savedInstanceState);
		SuperActivity.this.setContentView(layout_id);

		mInstance = this;

		// Initialize for the image loader
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.build();
		ImageLoader.getInstance().init(config);


		/**
		 "http://site.com/image.png"					// from Web
		 "file:///mnt/sdcard/image.png"					// from SD card
		 "file:///mnt/sdcard/video.mp4"					// from SD card (video thumbnail)
		 "content://media/external/images/media/13"		// from content provider
		 "content://media/external/video/media/13"		// from content provider (video thumbnail)
		 "assets://image.png"							// from assets
		 "drawable://" + R.drawable.img					// from drawables (non-9patch images)
		 */


		// To catch unhandled exceptions
		Thread.setDefaultUncaughtExceptionHandler(new MyUnhandledExceptionHandler());

		// Initialize resolution
		initResolution();

		// Initialize common handler
		initControls();

		// Initialize animation
		int nDir = getIntent().getIntExtra(AnimConst.EXTRA_ANIMDIR, -1);
		if (nDir == AnimConst.ANIMDIR_FROM_LEFT)
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
		else if (nDir == AnimConst.ANIMDIR_FROM_RIGHT)
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
		else
			overridePendingTransition(0, 0);


		// Initialize activity
		initializeActivity();
	}


	@Override
	protected void onResume() {
		super.onResume();

		// Hide keyboard when the activity is shown
		Global.hideKeyboardsInView(getRootView(), SuperActivity.this);
	}


	@Override
	protected void onPause() {
		super.onPause();

		// Hide keyboard when the activity is paused
		Global.hideKeyboardsInView(getRootView(), SuperActivity.this);
	}

    public void initControls() {
		View btn_back = SuperActivity.this.findViewById(R.id.btn_back);
		if (btn_back != null) {
			btn_back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					popOverCurActivityAnimated();
				}
			});
		}
    }

	public abstract void initializeActivity();


	///////////////////////// Activity transition methods begin /////////////////////////
	public void pushNewActivityAnimated(Class dstClass) {
		pushNewActivityAnimated(dstClass, AnimConst.ANIMDIR_FROM_RIGHT, 0, null, -1);
	}

	public void pushNewActivityAnimated(Class dstClass, Bundle bundle) {
		pushNewActivityAnimated(dstClass, AnimConst.ANIMDIR_FROM_RIGHT, 0, bundle, -1);
	}

	public void pushNewActivityAnimated(Class dstClass, Bundle bundle, int req_code) {
		pushNewActivityAnimated(dstClass, AnimConst.ANIMDIR_FROM_RIGHT, 0, bundle, req_code);
	}

	public void pushNewActivityAnimated(Class dstClass, int animation, Bundle bundle) {
		pushNewActivityAnimated(dstClass, animation, 0, bundle, -1);
	}

	public void pushNewActivityAnimated(Class dstClass, int animation) {
		pushNewActivityAnimated(dstClass, animation, 0, null, -1);
	}

	public void pushNewActivityAnimated(Class dstClass, int animation, int req_code) {
		pushNewActivityAnimated(dstClass, animation, 0, null, req_code);
	}

	public void pushNewActivityAnimated(Class dstClass,
										int animation,
										int activity_flags,
										int req_code) {
		pushNewActivityAnimated(dstClass, animation, activity_flags, null, req_code);
	}

	/**
	 * Method to show new activity with animation.
	 * Now animation only supports two types - cover from right and from left.
	 *
	 * @param dstClass			Destination activity class.
	 * @param animation			Push activity animation. See AnimConst class.
	 * @param activity_flags    Used for the startActivityForResult(...) method
	 * @param extras            Used to pass extra parameters to activity
	 *
	 * @see SuperActivity.AnimConst
	 * @see android.content.Intent
	 * @see android.os.Bundle
	 */
	public void pushNewActivityAnimated(final Class dstClass,
										final int animation,
										final int activity_flags,
										final Bundle extras,
										final int req_code) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SuperActivity.this, dstClass);
				intent.putExtra(AnimConst.EXTRA_ANIMDIR, animation);

				if (activity_flags != 0)
					intent.addFlags(activity_flags);

				if (extras != null)
					intent.putExtras(extras);

				SuperActivity.this.startActivityForResult(intent, req_code);
			}
		});
	}


	/**
	 * Method to dismiss current activity without animation
	 */
	public void popOverCurActivityNonAnimated() {
		SuperActivity.this.finish();
		overridePendingTransition(0, 0);
	}


	/**
	 * Method to dismiss current activity with animation
	 */
	public void popOverCurActivityAnimated() {
		SuperActivity.this.finish();

		int nDir = getIntent().getIntExtra(AnimConst.EXTRA_ANIMDIR, -1);
		if (nDir == AnimConst.ANIMDIR_FROM_LEFT)
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
		else if (nDir == AnimConst.ANIMDIR_FROM_RIGHT)
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
		else
			overridePendingTransition(0, 0);
	}


	public View getRootView() {
		return ((FrameLayout)getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
	}


	public void initResolution()
	{
		// Get the root layout
		ViewGroup parent_layout = (ViewGroup)getRootView();
		parent_layout.getViewTreeObserver().addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				Point ptTemp = getScreenSize();
				boolean bNeedUpdate = false;

				if (mScrSize.x == 0 && mScrSize.y == 0) {
					mScrSize = ptTemp;
					bNeedUpdate = true;
				} else if (mScrSize.x != ptTemp.x || mScrSize.y != ptTemp.y) {
					mScrSize = ptTemp;
					bNeedUpdate = true;
				}

				if (bNeedUpdate) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ResolutionSet.instance.iterateChild(getRootView(), mScrSize.x, mScrSize.y);
						}
					});
				}
			}
		});
	}


	// Progress dialog methods
	public void startProgress()
	{
		startProgress(getResources().getString(R.string.qingshaohou));
	}


	public void startProgress(String szMsg)
	{
		if (dlg_prog != null && dlg_prog.isShowing())
			return;

		if (dlg_prog == null)
		{
			dlg_prog = new com.bingchong.utils.ProgressDialog(SuperActivity.this);
			dlg_prog.setMessage(szMsg);
			dlg_prog.setCancelable(true);
		}

		dlg_prog.show();
	}

	public void stopProgress()
	{
		if (dlg_prog != null)
		{
			dlg_prog.dismiss();
			dlg_prog = null;
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (isLastActivity())
			{
				try
				{
					if (!isPressedBack)
					{
						AppCommon.showToast(SuperActivity.this, R.string.app_confirm_exit, Gravity.BOTTOM);
						backPressedTime = Calendar.getInstance().getTimeInMillis();
						isPressedBack = true;
					}
					else
					{
						if (Calendar.getInstance().getTimeInMillis() - backPressedTime < BACK_PRESS_MAX_INTERVAL)
						{
							popOverCurActivityNonAnimated();
							System.exit(1);
						}
						else
						{
							AppCommon.showToast(SuperActivity.this, R.string.app_confirm_exit, Gravity.BOTTOM);
							backPressedTime = Calendar.getInstance().getTimeInMillis();
						}
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			else
			{
				popOverCurActivityAnimated();
			}

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}


	// Method to detect if last activity
	public boolean isLastActivity()
	{
		ActivityManager mngr = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

		if (taskList.get(0).numActivities != 1 || !taskList.get(0).topActivity.getClassName().equals(this.getClass().getName()))
			return false;

		return true;
	}

	// Method to get phone screen size
	public Point mScrSize = new Point(0, 0);
	public Point getScreenSize()
	{
		Point ptTemp = Global.getScreenSize(getApplicationContext());
		ptTemp.y -= Global.statusBarHeight(this);

		return ptTemp;
	}


	public void logout(String szMsg)
	{
		AppCommon.clearUserInfo(getApplicationContext());

		pushNewActivityAnimated(LoginActivity.class, AnimConst.ANIMDIR_FROM_LEFT, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
	}


	private class MyUnhandledExceptionHandler implements Thread.UncaughtExceptionHandler
	{
		private Thread.UncaughtExceptionHandler defaultUEH;

		public MyUnhandledExceptionHandler() {
			this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		}

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			ex.printStackTrace();
			defaultUEH.uncaughtException(thread, ex);
		}

	}


	public void installNewApp(final String szUrl)
	{
		Thread thr = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					int nBytesRead = 0, nByteWritten = 0;
					byte[] buf = new byte[1024];

					URLConnection urlConn = null;
					URL fileUrl = null;
					InputStream inStream = null;
					OutputStream outStream = null;

					File dir_item = null, file_item = null;

					// Show progress dialog
					runOnUiThread(runnable_showProgress);

					// Downloading file from address
					fileUrl = new URL(szUrl);
					urlConn = fileUrl.openConnection();
					inStream = urlConn.getInputStream();
					download_app_path = szUrl.substring(szUrl.lastIndexOf("/") + 1);
					dir_item = new File(Environment.getExternalStorageDirectory(), "download");
					dir_item.mkdirs();
					file_item = new File(dir_item, download_app_path);

					outStream = new BufferedOutputStream(new FileOutputStream(file_item));

					while ((nBytesRead = inStream.read(buf)) != -1)
					{
						outStream.write(buf, 0, nBytesRead);
						nByteWritten += nBytesRead;
						updateProgress(nByteWritten);
					}

					updateProgress(getResources().getString(R.string.upgrade_success));

					inStream.close();
					outStream.flush();
					outStream.close();
					/////////////////////////////////////////////////////////////////////////

					// Hide progress dialog
					runOnUiThread(runnable_hideProgress);

					// Finish downloading and install
					runOnUiThread(runnable_finish_upgrade);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					runOnUiThread(runnable_upgrade_error);
				}
			}
		});

		thr.start();
	}


	private void updateProgress(int nValue)
	{
		updateProgress("" + nValue + "Bytes...");
	}

	private void updateProgress(final String szMsg)
	{
		Runnable runnable_update = new Runnable() {
			@Override
			public void run() {
				dlg_prog.setMessage(szMsg);
			}
		};
		runOnUiThread(runnable_update);
	}

	private Runnable runnable_showProgress = new Runnable() {
		@Override
		public void run() {
			startProgress();
		}
	};

	private Runnable runnable_hideProgress = new Runnable() {
		@Override
		public void run() {
			stopProgress();
		}
	};

	Runnable runnable_finish_upgrade = new Runnable()
	{
		public void run()
		{
			// Install update app
			Intent intent_install = new Intent( Intent.ACTION_VIEW);
			intent_install.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/download/" + download_app_path)), "application/vnd.android.package-archive");
			intent_install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent_install);

			// Uninstall current app
			Intent intent_uninstall = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", SuperActivity.this.getPackageName(), null));
			intent_uninstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent_uninstall);

			SuperActivity.this.finish();
		}
	};

	Runnable runnable_upgrade_error = new Runnable() {
		@Override
		public void run() {
			AppCommon.showToast(SuperActivity.this, R.string.upgrade_error);
			stopProgress();
		}
	};

}
