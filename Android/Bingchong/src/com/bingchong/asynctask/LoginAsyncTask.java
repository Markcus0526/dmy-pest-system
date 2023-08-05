package com.bingchong.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;

import com.bingchong.activity.HomeMgrActivity;
import com.bingchong.activity.LoginActivity;
import com.bingchong.activity.HomeUserActivity;
import com.bingchong.bean.UserBean;
import com.bingchong.db.DataMgr;
import com.bingchong.Constant;
import com.bingchong.R;
import com.bingchong.net.HttpApi;
import com.bingchong.net.HttpParams;
import com.bingchong.parser.ParserUser;
import com.bingchong.utils.AppConstants;
import com.bingchong.utils.AppPreferences;
import com.bingchong.utils.MD5;
import com.bingchong.utils.Utilities;

public class LoginAsyncTask extends AsyncTask<Void, Void, String> {

	private ProgressDialog pleaseWaitDialog;
	private LoginActivity loginActivity;
	private String username, en_username;
	private String password, en_password;
	private AppPreferences appPreferences;
	private Boolean isEncryptionSuccess = true;

	private UserBean userBean = new UserBean();

	public LoginAsyncTask(Context ctx, String username, String password, boolean isCheck) {
		super();
		this.loginActivity = (LoginActivity) ctx;
		this.username = username;
		this.password = password;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		isEncryptionSuccess = true;
		appPreferences = new AppPreferences(loginActivity);

		try {
			//en_username = RSA.encryptString(username);
			en_username = username.toString();
		} catch (Exception e) {
			isEncryptionSuccess = false;
			e.printStackTrace();
		}

		try {
			//en_password = RSA.encryptString(password);
			en_password = MD5.getMD5(password);
		} catch (Exception e) {
			isEncryptionSuccess = false;
			e.printStackTrace();
		}

		pleaseWaitDialog = new ProgressDialog(loginActivity);
		pleaseWaitDialog.setIndeterminate(true);
		pleaseWaitDialog.setMessage("Signing In...");
		pleaseWaitDialog.show();
		pleaseWaitDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				cancel(true);
			}
		});
	}

	@Override
	protected String doInBackground(Void... param) {
		if (isEncryptionSuccess) {
			
			HttpParams	postParameters = new HttpParams();
			postParameters.addParam("name", en_username);
			postParameters.addParam("password", en_password);
            postParameters.addParam("devtoken", "");
			
			String response = "";
			response = HttpApi.sendRequestWithJson(Constant.URL_SERVER + Constant.URL_LOGIN, postParameters, null);
			return response;
		} else {
			cancel(true);
			return AppConstants.SERVER_BUSY;
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		pleaseWaitDialog.dismiss();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pleaseWaitDialog.dismiss();
		if(result == null || result.length() == 0)
			return;
		if (result.equals(AppConstants.SERVER_BUSY)) {
			Utilities.showAppAlert(loginActivity, "", R.string.server_busy);
		} else if (result.equals(AppConstants.NO_INTERNET)) {
			Utilities.showAppAlert(loginActivity, "", R.string.no_internet);
		} else {
			checkResponse(result);
		}
	}
	
	private void saveUserInfo(UserBean obj){
		
		appPreferences.SetUserId(obj.id);
		appPreferences.SetUserName(obj.user);
		appPreferences.SetUserFullName(obj.name);
		appPreferences.SetValue(UserBean.PHONE, obj.phone);
		appPreferences.SetValue(UserBean.PLACE, obj.place);
		appPreferences.SetValue(UserBean.JOB, obj.job);
		appPreferences.SetValue(UserBean.STATUS, obj.status);
		appPreferences.SetValue(UserBean.SHENG, obj.sheng);
		appPreferences.SetValue(UserBean.SHI, obj.shi);
		appPreferences.SetValue(UserBean.XIAN, obj.xian);
		appPreferences.SetValue(UserBean.SERIAL, obj.serial);
		appPreferences.SetIntValue(UserBean.RIGHTS_ID, (int)obj.rights_id);
		appPreferences.SetIntValue(UserBean.SHENGS_ID, (int)obj.shengs_id);
		appPreferences.SetIntValue(UserBean.SHIS_ID, (int)obj.shis_id);
		appPreferences.SetIntValue(UserBean.XIANS_ID, (int)obj.xians_id);
	}
	
	private UserBean loadUserInfo(){
		UserBean obj = new UserBean();

		obj.id = appPreferences.GetUserId();
		obj.user = appPreferences.GetUserName();
		obj.name = appPreferences.GetUserFullName();
		obj.phone = appPreferences.GetValue(UserBean.PHONE);
		obj.place = appPreferences.GetValue(UserBean.PLACE);
		obj.job = appPreferences.GetValue(UserBean.JOB);
		obj.status = appPreferences.GetValue(UserBean.STATUS);
		obj.sheng = appPreferences.GetValue(UserBean.SHENG);
		obj.shi = appPreferences.GetValue(UserBean.SHI);
		obj.xian = appPreferences.GetValue(UserBean.XIAN);
		obj.serial = appPreferences.GetValue(UserBean.SERIAL);
		obj.rights_id = appPreferences.GetIntValue(UserBean.RIGHTS_ID);
		obj.shengs_id = appPreferences.GetIntValue(UserBean.SHENGS_ID);
		obj.shis_id = appPreferences.GetIntValue(UserBean.SHIS_ID);
		obj.xians_id = appPreferences.GetIntValue(UserBean.XIANS_ID);
		
		return obj;
	}

	/**
	 * This function check the response received from Server into Json Format .
	 */

	private void checkResponse(String result) {
		try {
			userBean = ParserUser.getParsedOneResult(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String oldUser = appPreferences.GetUserName();
		String oldPhone = appPreferences.GetPhoneNumber();
		String oldPass = appPreferences.GetPassword();
		
		if(userBean == null){ // check already login user in case network fail
			if(username.length() > 0 && password.length() > 0 && password.equals(oldPass)){
				if(username.equals(oldUser) || username.equals(oldPhone)){
					userBean = loadUserInfo();
				}
			}
		}
		
		if(userBean != null && !username.equals(oldUser) && !username.equals(oldPhone)){
			DataMgr.clearAllDatas();
		}
		
		if (userBean != null) {
			long time = System.currentTimeMillis();			
			appPreferences.SetPassword(password);
			appPreferences.SetIsAdmin(userBean.isAdmin());
			saveUserInfo(userBean);
			DataMgr.m_curUser = userBean;
			appPreferences.SetLoginTime(time);
		}
		
		if(userBean == null)
		{
			Utilities.showAppAlert(loginActivity, "",
					R.string.login_alert_error);
			
			userBean = new UserBean();
			DataMgr.m_curUser = userBean;
			
			return;
		}
		
		Intent intent = null;

		if(userBean.isAdmin())
			intent = new Intent(loginActivity, HomeMgrActivity.class);
		else
			intent = new Intent(loginActivity, HomeUserActivity.class);

		loginActivity.startActivity(intent);
		loginActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}
}
