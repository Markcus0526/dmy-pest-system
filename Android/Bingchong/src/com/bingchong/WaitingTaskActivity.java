package com.bingchong;

import com.bingchong.asynctask.BaseTask;
import com.bingchong.asynctask.BaseTask.TaskListener;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class WaitingTaskActivity extends AutoSizeActivity implements TaskListener{

	protected BaseTask mBaseTask;
	private ProgressDialog mPleaseWaitDialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View v = findViewById(R.id.menu_back);
		if(v != null)
			v.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
	}

	protected void startGetStatusTask() {
		if(mBaseTask != null)
			return;
		mBaseTask = new BaseTask();
		mBaseTask.setListener(this);
		mBaseTask.execute();
	}
	
	public void onTaskPrepare(int taskId, Object data) {
		String msg = this.getString(R.string.strLoading);
		mPleaseWaitDialog = new ProgressDialog(this);
		mPleaseWaitDialog.setIndeterminate(true);
		mPleaseWaitDialog.setMessage(msg);
		mPleaseWaitDialog.setCancelable(false);
		mPleaseWaitDialog.show();
	}
	
	public Object onTaskRunning(int taskId, Object data) {
		String ret = null;
		return ret;
	}
	public void onTaskProgress(int taskId, Object... values) {
	}
	
	protected void closeWaitingDlg()
	{
		if (mPleaseWaitDialog != null) {
			mPleaseWaitDialog.dismiss();
			mPleaseWaitDialog = null;
		}
		mBaseTask = null;
	}

	public void onTaskResult(int taskId, Object result) {
		closeWaitingDlg();
		mBaseTask = null;
	}
	
	public void onTaskCancelled(int taskId) {
		closeWaitingDlg();
	}
}