package com.bingchong.activity;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bingchong.*;
import com.bingchong.asynctask.LoginAsyncTask;
import com.bingchong.bean.UserBean;
import com.bingchong.db.DataMgr;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.parser.ParserUser;
import com.bingchong.utils.*;
import com.bingchong.utils.image.SmartImageView;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This Class is For App User/Admin Login Authorization .  
 */

public class LoginActivity extends SuperActivity implements OnClickListener {
    
	private final String CLASS_NAME = getClass().getName();
	
	//private Spinner spnUser;
	private EditText edtPassword, edtUser;
	private String strUsername,strPassword;
	private CheckBox chkSavePassword;
	
	private AppPreferences appPreferences;
    private SmartImageView imgUser;
	
	private static Context	mainActivity = null;

	private BingChongApp m_app;

    private int m_selfVersionCode = 1;
    private String m_selfVersionName = null;
    private ProgressDialog  m_prgDialog = null;
    private String downloadVersionUrl = null;

    private UserBean userBean = new UserBean();

	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.login);

        mainActivity = getApplicationContext();
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		String url = appPreferences.GetUserImgPath();
        imgUser.setImageUrl(url, R.drawable.person);
	}

    @Override
    public void initializeActivity() {
        appPreferences = new AppPreferences(getApplicationContext());

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPass);
        chkSavePassword = (CheckBox)findViewById(R.id.chkSavePass);

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.txtForget).setOnClickListener(this);

        strUsername = appPreferences.GetUserName();
        strPassword = appPreferences.GetPassword();

        if(appPreferences.IsSavePassWord())
        {
            edtUser.setText(strUsername);
            edtPassword.setText(strPassword);
            edtUser.setSelection(strUsername.length());
            edtPassword.setSelection(strPassword.length());
            chkSavePassword.setChecked(true);
        }

        // set image
        imgUser = (SmartImageView)findViewById(R.id.imgUser);

        String url = appPreferences.GetUserImgPath();
        imgUser.setImageUrl(url, R.drawable.person);

        // set temporary directory
        String pkName = getPackageName();
        File pkFile = this.getDir(pkName, 1);
        String path = pkFile.getAbsolutePath() + "/temp";
        Utilities.setTempPath(path);

        m_app = (BingChongApp)getApplication();

        DataMgr.getLatestVersion(get_version_handler);
    }

    public static int compareVersions(String v1, String v2) {
        float value1 = 0, value2 = 0;
        try {
            value1 = Float.parseFloat(v1.substring(0, 2));
            value2 = Float.parseFloat(v2.substring(0, 2));
        } catch (NumberFormatException e) {
            return -1;
        }
        if (value1 >= value2)
            return 1;
        return 0;
    }

    void checkVersionUpdate(int versionCode, String versionName, final String apkPath)
    {
        try {
            m_selfVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            m_selfVersionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch(PackageManager.NameNotFoundException e) {
            m_selfVersionCode = 1;
            m_selfVersionName = "1.0";
        }

        int versionCompareResult =compareVersions(m_selfVersionName, versionName);

        if(m_selfVersionCode >= versionCode) {
            Utilities.showAppToast(getApplicationContext(), R.string.nonewversion);
            return;
        } else if(versionCompareResult == -1) {
            Utilities.showAppToast(getApplicationContext(), R.string.alert_version_format_error);
            return;
        }

        showVersionUpdateDialog(versionName, apkPath);
    }

    private void showVersionUpdateDialog(String versionName, final String apkPath){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(LoginActivity.this);
        alt_bld.setMessage(getString(R.string.askupdate1) + m_selfVersionName
                +getString(R.string.askupdate2) + versionName
                +getString(R.string.askupdate3)).setCancelable(false).setPositiveButton(
                getString(R.string.alert_dialog_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                m_prgDialog = ProgressDialog.show(
                        LoginActivity.this,
                        getString(R.string.download),
                        getString(R.string.connect),
                        true,
                        true,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                finish();
                            }
                        }
                );

                m_prgDialog.setCancelable(false);
                m_prgDialog.setCanceledOnTouchOutside(false);

                // download new version
                new Thread(new Runnable() {

                    public void run() {

                        InputStream is = null;
                        OutputStream os = null;
                        URLConnection URLConn = null;

                        try {
                            URL fileUrl;
                            byte[] buf;
                            int ByteRead = 0;
                            int ByteWritten = 0;
                            downloadVersionUrl = Constant.URL_ROOT + apkPath;

                            fileUrl = new URL(downloadVersionUrl);

                            URLConn = fileUrl.openConnection();

                            is = URLConn.getInputStream();
                            String fileName = downloadVersionUrl.substring(downloadVersionUrl.lastIndexOf("/") + 1);

                            File f = new File(Environment.getExternalStorageDirectory(), "download");
                            f.mkdirs();
                            File ff = new File(f, fileName);

                            os = new BufferedOutputStream(new FileOutputStream(ff));

                            buf = new byte[81920];

                            while ((ByteRead = is.read(buf)) != -1) {

                                os.write(buf, 0, ByteRead);
                                ByteWritten += ByteRead;

                                final double fBytesWritten = ByteWritten / 1000.0;
                                runOnUiThread(new Runnable() {
                                    // draw download bytes
                                    public void run() {
                                        m_prgDialog.setMessage(""+ fBytesWritten +" KB");
                                    }
                                });
                            }

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    m_prgDialog.setTitle(getString(R.string.downloadok));
                                }
                            });

                            is.close();
                            os.flush();
                            os.close();

                            Thread.sleep(1000);
                            m_prgDialog.dismiss();

                            // install update app
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/download/" + fileName)),
                                    "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            Intent intent1 = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", "com.bingchong", null));
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent1);

                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                            m_prgDialog.dismiss();
                        }

                    }
                }).start();
            }
        }).setNegativeButton(getString(R.string.alert_dialog_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.setTitle(getString(R.string.alert_dialog_title));
        alert.setIcon(R.drawable.ic_launcher);
        alert.show();
    }
    
    public static void setMainActivity(Activity actvity)
    {
    	//mainActivity = actvity;
    }
    
    public static Context getMainActivity(){
    	return mainActivity;
    }

    /**
	 * This Function check the all required input field validation for Login into App .  
	 */
    
	private boolean isValid(){
		Boolean isValidInput = true;
		
		if(edtUser.length()==0){
			isValidInput = false;
			Utilities.showAppAlert(LoginActivity.this,"", R.string.login_alert_username);
			edtUser.requestFocus();
		}
		else if(edtPassword.length()==0){
			isValidInput = false;
			Utilities.showAppAlert(LoginActivity.this,"", R.string.login_alert_password);
			edtPassword.requestFocus();
		}

		return isValidInput;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.btnLogin){
			
			if(isValid()){
				
				appPreferences.SetSavePassWord(chkSavePassword.isChecked());
				//if(chkSavePassword.isChecked())
				{
					strUsername = edtUser.getText().toString().trim();
					strPassword = edtPassword.getText().toString().trim();
				}
				
				Utilities.printStatement(CLASS_NAME, strUsername+","+strPassword);
				//new LoginAsyncTask(LoginActivity.this, strUsername, strPassword, chkSavePassword.isChecked()).execute();
                startProgress();
                CommManager.loginUser(strUsername,
                        strPassword,
                        Global.getDeviceIMEI(getApplicationContext()),
                        login_handler);
			}
		}
		
		if(id == R.id.txtForget)
		{
            pushNewActivityAnimated(HomeGetPasswordActivity.class);
		}
	}

    private AsyncHttpResponseHandler login_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    JSONObject retdata = result.getJSONObject(ConstMgr.gRetData);

                    /*
                    userBean.id = retdata.getLong("uid");
                    userBean.name = retdata.getString("name");
                    userBean.phone = retdata.getString("phone");
                    userBean.place = retdata.getString("place");
                    userBean.rights_id = retdata.getLong("right_id");
                    userBean.shengs_id = retdata.getLong("sheng_id");
                    userBean.shis_id = retdata.getLong("shi_id");
                    userBean.xians_id = retdata.getLong("xian_id");
                    userBean.point_list = retdata.getString("point_list");
                    */

                    userBean = ParserUser.parseJsonResponse(retdata);

                    long time = System.currentTimeMillis();
                    appPreferences.SetPassword(strPassword);
                    appPreferences.SetIsAdmin(userBean.isAdmin());
                    DataMgr.m_curUser = userBean;
                    appPreferences.SetLoginTime(time);
                    AppCommon.saveUserInfo(userBean);

                    startProgress();
                    CommManager.getUserPhotos(get_userphotos_handler);
                }
                else if( nRetCode == ConstMgr.SVC_ERR_DEVTOKEN_NOREGISTERED )
                {

                }
                else {
                    AppCommon.showToast(LoginActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(LoginActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(LoginActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    private AsyncHttpResponseHandler get_userphotos_handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
            stopProgress();

            try {
                JSONObject result = new JSONObject(content);

                int nRetCode = result.getInt(ConstMgr.gRetCode);
                String stRetMsg = result.getString(ConstMgr.gRetMsg);
                if (nRetCode == ConstMgr.SVC_ERR_NONE) {
                    ArrayList<UserBean> userList = new ArrayList<UserBean>();
                    JSONArray retdata = result.getJSONArray(ConstMgr.gRetData);
                    for(int i = 0; i < retdata.length(); i++) {
                        UserBean obj = ParserUser.parseJsonResponse(retdata.getJSONObject(i));
                        if(obj != null)
                            userList.add(obj);
                    }
                    if(userList.size() > 0)
                        DataMgr.updateUsersToDB(userList);


                    Context ctx = getApplicationContext();

                    if(userBean.rights_id == 0)
                    {
                        if (!AppDeviceUtils.isGPSEnable(ctx)) {
                            if(!Utilities.showGPSAlert(LoginActivity.this, ctx.getText(R.string.msg_welcome),
                                    R.string.msg_gps_alert));
                            return;
                        }

                        pushNewActivityAnimated(HomeUserActivity.class,
                                AnimConst.ANIMDIR_FROM_RIGHT);
                    }
                    else
                    {
                        pushNewActivityAnimated(HomeMgrActivity.class,
                                AnimConst.ANIMDIR_FROM_RIGHT);
                    }


                }
                else {
                    AppCommon.showToast(LoginActivity.this, stRetMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AppCommon.showDebugToast(LoginActivity.this, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
            stopProgress();

            AppCommon.showToast(LoginActivity.this, R.string.STR_CONN_ERROR);
        }
    };

    AsyncHttpResponseHandler get_version_handler = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {

            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.

            try {
                content = MyUtils.getValidJsonString(content);
                JSONObject mainJsonObject = new JSONObject(content);
                String success =mainJsonObject.getString("Status").trim();
                if(success.equalsIgnoreCase(Constant.STATUS_SUCCESS)){
                    JSONObject jsonVersion = mainJsonObject.getJSONArray("Result").getJSONObject(0);

                    checkVersionUpdate(jsonVersion.getInt("number"), jsonVersion.getString("name"),  jsonVersion.getString("path"));
                }
                else{

                }
            }
            catch (Exception ex)
            {
            }

        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
        }
    };
}