package com.bingchong.activity.video;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.bussinesscenter.BussinessCenter;
import com.bingchong.*;
import com.bingchong.bean.Experts;
import com.bingchong.utils.ActivityUtil;
import com.bingchong.utils.AppPreferences;
import com.bingchong.web.LinkBackWeb;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by DavidYin on 2015.03.20.
 */
public class VideoMainActivity extends SuperActivity implements AnyChatBaseEvent {

    private String mPhoneNum = "13804211070";
    private ConfigEntity configEntity;
    public AnyChatCoreSDK anychat;
    private boolean bNeedRelease = false;

    private boolean isExperts = false; //false : 游客 ； true :专家
    private String expertsName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.video_main_activity);
    }

    @Override
    public void initializeActivity() {
        InitialSDK();
        intParams();

        AppPreferences appPreferences = new AppPreferences(getApplicationContext());
        Login(appPreferences.GetUserName(), appPreferences.GetPassword());
    }

    protected void onDestroy() {
        super.onDestroy();

        /*
        if (bNeedRelease) {
            anychat.Logout();
            anychat.Release();
            //android.os.Process.killProcess(android.os.Process.myPid());
        }
        BussinessCenter.getBussinessCenter().realseData();
        */
    }

    /*
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        int tag=intent.getIntExtra("INTENT", Constant.AGAIGN_LOGIN);
        if(tag==Constant.AGAIGN_LOGIN)
        {
            if(anychat!=null)
            {
                anychat.Logout();
                anychat.SetBaseEvent(this);
            }
        }
        else if(tag==Constant.APP_EXIT)
        {
            this.finish();
        }
    }
    */

    /**
     * @description 加载anychatSDK
     */
    private void InitialSDK() {
        if (anychat == null) {
            anychat = new AnyChatCoreSDK();
            anychat.SetBaseEvent(this);
            anychat.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
            bNeedRelease = true;
        }
    }

    /**
     * @description 加载配置文件
     */
    protected void intParams() {
        configEntity = ConfigService.LoadConfig(this);
        BussinessCenter.getBussinessCenter();
    }

    public void Login(String userName , String userPassword){

        startProgress();
        configEntity.name = userName;
        this.anychat.Connect(configEntity.ip, configEntity.port);
        this.anychat.Login(userName, userPassword);
    }

    public class GetExpertsTask extends AsyncTask<Experts, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Experts... params) {
            try {
                Config.expertsList.clear();
                LinkBackWeb.getExpertsList();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if(result){
                pushNewActivityAnimated(ExpertsListActivity.class);
                finish();
            }
            stopProgress();
        }

    }

    @Override
    public void OnAnyChatConnectMessage(boolean bSuccess) {
        stopProgress();
    }

    @Override
    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
        stopProgress();
        if (dwErrorCode == 0) {
            BussinessCenter.selfUserId = dwUserId;
            if(isExperts){

            }else{
                BussinessCenter.selfUserName = mPhoneNum;
                startProgress();
                new GetExpertsTask().execute();
            }
        } else if (dwErrorCode == 200) {

        }
    }

    @Override
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {

    }

    @Override
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {

    }

    @Override
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {

    }

    @Override
    public void OnAnyChatLinkCloseMessage(int dwErrorCode) {

    }
}