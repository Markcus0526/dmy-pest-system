package com.bingchong.activity.video;

import java.util.ArrayList;
import java.util.List;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.bairuitech.anychat.AnyChatUserInfoEvent;
import com.bairuitech.anychat.AnyChatVideoCallEvent;
import com.bairuitech.bussinesscenter.BussinessCenter;
import com.bairuitech.bussinesscenter.UserItem;
import com.bingchong.*;
import com.bingchong.activity.TabItem;
import com.bingchong.adapter.ExpertsAdapter;
import com.bingchong.bean.Experts;
import com.bingchong.utils.ActivityUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @class：ForestryExpertsActivity.java 
 * @description: 专家预约
 * @author: 金铁钢
 * 
 * @history:
 *   	    日期  	     版本             担当者         修改内容
 *   2014-12-3  1.0    金铁钢            初版
 */
public class ExpertsListActivity extends Activity implements
	OnClickListener, AnyChatBaseEvent, AnyChatVideoCallEvent, AnyChatUserInfoEvent {
	
	/**
	 * 农业专家列表
	 */
	private ListView agricultureExpertsLv;
	/**
	 * 牧业专家列表
	 */
	private ListView ranchingExpertsLv;
	/**
	 * 林业专家列表
	 */
	private ListView forestryExpertsLv;
	private TextView expertsTv;
	/**
	 * 专家个数
	 */
	private TextView expertsCountTv;
	/**
	 * 设置按钮
	 */
	private ImageButton setBtn;
	/**
	 * 在线用户列表
	 */
	private ArrayList<UserItem> mUserDatas;
	/**
	 * 农业专家适配器
	 */
	private ExpertsAdapter agricultureExpertsAdapter;
	/**
	 * 牧业专家适配器
	 */
	private ExpertsAdapter ranchingExpertsAdapter;
	/**
	 * 林业专家适配器
	 */
	private ExpertsAdapter forestryExpertsAdapter;
	private Dialog dialog;
	
	/**
	 * anychatSDK
	 */
	public AnyChatCoreSDK anychat;
	/**
	 * 农业专家在线个数
	 */
	private int agricultureExpertOnlineCount = 0;
	/**
	 * 牧业专家在线个数 
	 */
	private int ranchingExpertOnlineCount = 0;
	/**
	 * 林业专家在线个数
	 */
	private int forestryExpertOnlineCount = 0;
	
	private Typeface face;
	
	private List<Experts> agricultureExpertsList;
	private List<Experts> ranchingExpertsList;
	private List<Experts> forestryExpertsList;
	
	private List<TabItem> tabItemList;
	
	private TabItem curTabItem;
	
	//private int position = 1;
	private ConfigVideo configDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initSdk();
		configDialog = new ConfigVideo();
        ConfigVideo.selectPosition = ConfigService.LoadQuality(this);
        configDialog.ApplyVideoConfig(this, ConfigVideo.selectPosition);
        BussinessCenter.getBussinessCenter().getOnlineFriendDatas();
        findViewById();
        if(Config.IS_SET_FONTS)
        	setTypeface();
        
        init();
        initTabView();
        switchTab(tabItemList.get(0));
        
        startBackServce();
	}
	
	/* 开始设置tab相关 */
	/**
	 * @Description 初始化tab
	 */
	private void initTabView() {
		tabItemList = TabItem.genTabItems();
		for (TabItem tabItem : tabItemList) {
			final TextView tabView = (TextView) findViewById(tabItem
					.getTabViewID());
			tabView.setBackgroundResource(tabItem.getBackgroundID());
			tabView.setText(tabItem.getTabNameID());
			tabView.setTag(tabItem);
			if(Config.IS_SET_FONTS)
				tabView.setTypeface(face);
			tabView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					TabItem tabItem = (TabItem) v.getTag();
					if (curTabItem.getTabNameID() != tabItem.getTabNameID()) {
						// 切换tab
						switchTab(tabItem);
					} else {// 点击当前tab 自动刷新
						//((BaseTabFragment) curFragment).autoRefresh();
					}
				}
			});
		}
		//tvNewWeiboCount = (TextView) findViewById(R.id.tv_new_weibo_count);
		//tvNewMsgCount = (TextView) findViewById(R.id.tv_new_msg_count);
	}
	
	/**
	 * @Description 切换tab
	 */
	private void switchTab(TabItem tabItem) {
		setCurTabViewToNormalStatus();
		setTabViewToSelectedStatus(tabItem);
		switch (tabItem.getTabNameID()) {
			case R.string.agriculture:
				//agricultureExpertsAct = (AgricultureExpertsActivity) curFragment;
				Log.e("tab" , "农业");
				//init(0);
				agricultureExpertsLv.setVisibility(View.VISIBLE);
				ranchingExpertsLv.setVisibility(View.GONE);
				forestryExpertsLv.setVisibility(View.GONE);
				expertsCountTv.setText(agricultureExpertOnlineCount + "/" + agricultureExpertsList.size());
				break;
			case R.string.ranching:
				//ranchingExpertsAct = (RanchingExpertsActivity) curFragment;
				Log.e("tab" , "牧业");
				agricultureExpertsLv.setVisibility(View.GONE);
				ranchingExpertsLv.setVisibility(View.VISIBLE);
				forestryExpertsLv.setVisibility(View.GONE);
				expertsCountTv.setText(ranchingExpertOnlineCount + "/" + ranchingExpertsList.size());
				break;
			case R.string.forestry:
				Log.e("tab" , "林业");
				agricultureExpertsLv.setVisibility(View.GONE);
				ranchingExpertsLv.setVisibility(View.GONE);
				forestryExpertsLv.setVisibility(View.VISIBLE);
				expertsCountTv.setText(forestryExpertOnlineCount + "/" + forestryExpertsList.size());
				//forestryExpertsAct = (RanchingExpertsActivity) curFragment;
				break;
	
			default:
				break;
		}
	}
	
	/**
	 * @Description 将一个当前的tab设置成正常状态
	 */
	private void setCurTabViewToNormalStatus() {
		if (curTabItem == null) {
			return;
		}
		View tabView = findViewById(curTabItem.getTabViewID());
		tabView.setSelected(false);
	}

	/**
	 * @Description 将一个tab设置成选择状态
	 * @param tabView
	 */
	private void setTabViewToSelectedStatus(TabItem tabItem) {
		View tabView = findViewById(tabItem.getTabViewID());
		tabView.setSelected(true);
		curTabItem = tabItem;
	}
	
	public void init(){
		BussinessCenter.mContext = this;
		mUserDatas=BussinessCenter.mOnlineFriendItems;
		Log.e("mUserDatas" , mUserDatas.size() + "");
		agricultureExpertOnlineCount = 0;
		ranchingExpertOnlineCount = 0;
		forestryExpertOnlineCount = 0;
		
		agricultureExpertsList = new ArrayList<Experts>();
		ranchingExpertsList = new ArrayList<Experts>();
		forestryExpertsList = new ArrayList<Experts>();
		for(int j = 0 ; j < Config.expertsList.size() ; j++){
			Experts experts = Config.expertsList.get(j);
			if(experts.getExpertTypeId() == 1){//农业专家
				agricultureExpertsList.add(experts);
			}else if(experts.getExpertTypeId() == 2){//牧业专家
				ranchingExpertsList.add(experts);
			}else if(experts.getExpertTypeId() == 3){//林业专家
				forestryExpertsList.add(experts);
			}
		}
		
		for(int i=0; i< mUserDatas.size() ; i++){
    		String userName = mUserDatas.get(i).getUserName();
    		for(int j=mUserDatas.size() - 1; j > i ; j--){
    			String userName1 = mUserDatas.get(j).getUserName();
    			if(userName.equals(userName1)){
    				mUserDatas.remove(j);
    			}
    		}
		}
		
    	for(int i=0; i< mUserDatas.size() ; i++)
    	{
    		int userId = mUserDatas.get(i).getUserId();
    		String userName = mUserDatas.get(i).getUserName();
    		Log.e("OnlineUser" , userName);
    		for(int j = 0 ; j < agricultureExpertsList.size() ; j++){
    			Experts experts = agricultureExpertsList.get(j);
    			if(experts.getUserName().equals(userName)){
    				Log.e("agricultureExpertOnlineCount" , agricultureExpertOnlineCount +userName );
    				experts.setExpertOnlineState(1);
    				experts.setAnyChatId(userId);
    				agricultureExpertsList.remove(j);
    				agricultureExpertsList.add(0, experts);
    				agricultureExpertOnlineCount++;
    				break;
    			}
    		}
    		for(int j = 0 ; j < ranchingExpertsList.size() ; j++){
    			Experts experts = ranchingExpertsList.get(j);
    			if(experts.getUserName().equals(userName)){
    				experts.setExpertOnlineState(1);
    				experts.setAnyChatId(userId);
    				ranchingExpertsList.remove(j);
    				ranchingExpertsList.add(0, experts);
    				ranchingExpertOnlineCount++;
    				break;
    			}
    		}
    		for(int j = 0 ; j < forestryExpertsList.size() ; j++){
    			Experts experts = forestryExpertsList.get(j);
    			if(experts.getUserName().equals(userName)){
    				experts.setExpertOnlineState(1);
    				experts.setAnyChatId(userId);
    				forestryExpertsList.remove(j);
    				forestryExpertsList.add(0, experts);
    				forestryExpertOnlineCount++;
    				break;
    			}
    		}
    	}
    	
    	expertsCountTv.setText(agricultureExpertOnlineCount + "/" + agricultureExpertsList.size());
    	
    	agricultureExpertsAdapter = new ExpertsAdapter(agricultureExpertsList , this , face);
    	ranchingExpertsAdapter = new ExpertsAdapter(ranchingExpertsList , this , face);
		forestryExpertsAdapter = new ExpertsAdapter(forestryExpertsList , this , face);
		
		agricultureExpertsLv.setAdapter(agricultureExpertsAdapter);
		ranchingExpertsLv.setAdapter(ranchingExpertsAdapter);
		forestryExpertsLv.setAdapter(forestryExpertsAdapter);
	}
	
	/**
	 * @description 初始化anychatSDK
	 */
	private void initSdk() {
		if (anychat == null) {
			anychat = new AnyChatCoreSDK();
		}
		anychat.SetBaseEvent(this);
		anychat.SetVideoCallEvent(this);
		anychat.SetUserInfoEvent(this);
		Log.i("ANYCHAT", "initSdk");
	}
	
	@Override
	protected void onResume() {
		BussinessCenter.mContext = this;
		initSdk();
		if (agricultureExpertsAdapter != null)
			agricultureExpertsAdapter.notifyDataSetChanged();
		if (ranchingExpertsAdapter != null)
			ranchingExpertsAdapter.notifyDataSetChanged();
		if (forestryExpertsAdapter != null)
			forestryExpertsAdapter.notifyDataSetChanged();
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
        anychat.Logout();
        anychat.Release();
		BussinessCenter.getBussinessCenter().realseData();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	/**
	 * @description 绑定控件
	 */
	private void findViewById() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.experts_list);
		agricultureExpertsLv = (ListView) findViewById(R.id.agriculture_experts_lv);
		ranchingExpertsLv = (ListView) findViewById(R.id.ranching_experts_lv);
		forestryExpertsLv = (ListView) findViewById(R.id.forestry_experts_lv);
		expertsTv = (TextView) findViewById(R.id.experts_list_tv);
		expertsCountTv = (TextView) findViewById(R.id.experts_count_tv);
		setBtn = (ImageButton) findViewById(R.id.set_btn);
		
		setBtn.setOnClickListener(this);
	}
	
	/**
     * @description 设置字体
     */
    public void setTypeface(){
    	face = Typeface.createFromAsset(this.getAssets(),Config.FONTS);
    	expertsCountTv.setTypeface(face);
    	//setBtn.setTypeface(face);
    	expertsTv.setTypeface(face);
    }
	
	@Override
	public void onClick(View v) {
		if (v == setBtn) {
			Intent intent = new Intent();
			intent.setClass(this, VideoConfigActivity.class);
			ActivityUtil.startActivityForResult(this , intent , 1000);
			configDialog.showConfigDialog(this, ConfigVideo.selectPosition);
		}
	}
	
	/**
	 * @description 开始后台服务
	 */
	protected void startBackServce() {
		Intent intent = new Intent();
		intent.setAction(Constant.ACTION_BACK_SERVICE);
		this.startService(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		if (dialog != null
				&& dialog.isShowing()
				&& DialogFactory.getCurrentDialogId() == DialogFactory.DIALOGID_RESUME) {
			dialog.dismiss();
		}
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		if(dwErrorCode==0)
		{
			BussinessCenter.selfUserId = dwUserId;
			BussinessCenter.selfUserName=anychat.GetUserName(dwUserId);
		} else {
			//BaseMethod.showToast(this.getString(R.string.str_login_failed) + "(ErrorCode:" + dwErrorCode + ")",	this);
		}
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		if (dwErrorCode == 0) {
			Intent intent = new Intent();
			intent.setClass(this, VideoActivity.class);
			ActivityUtil.startActivity(this , intent);
		} else {
			/*BaseMethod.showToast(this.getString(R.string.str_enterroom_failed),
					this);*/
		}
		if (dialog != null)
			dialog.dismiss();
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		
		
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		if (dwErrorCode == 0) {
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			dialog = DialogFactory.getDialog(DialogFactory.DIALOG_NETCLOSE,
					DialogFactory.DIALOG_NETCLOSE, this ,"");
			dialog.show();
		} else {
			/*BaseMethod.showToast(this.getString(R.string.str_serverlink_close),
					this);*/
            /*
			Intent intent = new Intent();
			intent.putExtra("INTENT", Constant.AGAIGN_LOGIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, LoginActivity.class);
			ActivityUtil.startActivity(this , intent);
			*/
			this.finish();
		}
		Log.i("ANYCHAT", "OnAnyChatLinkCloseMessage:" + dwErrorCode);
	}
	
	

	@Override
	public void OnAnyChatUserInfoUpdate(int dwUserId, int dwType) {
		// 同步完成服务器中的所有好友数据，可以在此时获取数据
		if (dwUserId == 0 && dwType == 0) {
			BussinessCenter.getBussinessCenter().getOnlineFriendDatas();
			Log.i("ANYCHAT", "OnAnyChatUserInfoUpdate");
		}
	}

	@Override
	public void OnAnyChatFriendStatus(int dwUserId, int dwStatus) {
		String strMsg = "";
		mUserDatas=BussinessCenter.mOnlineFriendItems;
		UserItem userItem = BussinessCenter.getBussinessCenter().getUserItemByUserId(dwUserId);
		if (userItem == null)
			return;
		String userName = userItem.getUserName();
		//if(dwStatus == 0){//下线
		for(int j = 0 ; j < agricultureExpertsList.size() ; j++){
			Experts experts = agricultureExpertsList.get(j);
			if(experts.getUserName().equals(userName)){
				if(dwStatus == 0){
					experts.setExpertOnlineState(0);
					agricultureExpertOnlineCount--;
					strMsg = experts.getExpertName() + "下线";
					agricultureExpertsList.remove(j);
					agricultureExpertsList.add(agricultureExpertsList.size(), experts);
					if(ExpertsDetailActivity.expertsOnlineStateTv != null && ExpertsDetailActivity.curUserName.equals(userName)){
						ExpertsDetailActivity.onlineState = 0;
						ExpertsDetailActivity.expertsOnlineStateTv.setText("不在线");
						ExpertsDetailActivity.expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_background1);
					}
				}else if(dwStatus == 1){
					experts.setExpertOnlineState(1);
    				experts.setAnyChatId(dwUserId);
    				agricultureExpertOnlineCount++;
    				strMsg = experts.getExpertName() + "上线";
    				agricultureExpertsList.remove(j);
					agricultureExpertsList.add(0, experts);
					if(ExpertsDetailActivity.expertsOnlineStateTv != null && ExpertsDetailActivity.curUserName.equals(userName)){
						ExpertsDetailActivity.onlineState = 1;
						ExpertsDetailActivity.expertsOnlineStateTv.setText("在线");
						ExpertsDetailActivity.expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_login);
					}
				}
				BaseMethod.showToast(strMsg, this);
				expertsCountTv.setText(agricultureExpertOnlineCount + "/" + agricultureExpertsList.size());
				agricultureExpertsAdapter.notifyDataSetChanged();
				break;
			}
		}
		for(int j = 0 ; j < ranchingExpertsList.size() ; j++){
			Experts experts = ranchingExpertsList.get(j);
			if(experts.getUserName().equals(userName)){
				if(dwStatus == 0){
					experts.setExpertOnlineState(0);
					ranchingExpertOnlineCount--;
					strMsg = experts.getExpertName() + "下线";
					ranchingExpertsList.remove(j);
					ranchingExpertsList.add(ranchingExpertsList.size(), experts);
					if(ExpertsDetailActivity.expertsOnlineStateTv != null && ExpertsDetailActivity.curUserName.equals(userName)){
						ExpertsDetailActivity.onlineState = 0;
						ExpertsDetailActivity.expertsOnlineStateTv.setText("不在线");
						ExpertsDetailActivity.expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_background1);
					}
				}else if(dwStatus == 1){
					experts.setExpertOnlineState(1);
    				experts.setAnyChatId(dwUserId);
    				ranchingExpertOnlineCount++;
    				strMsg = experts.getExpertName() + "上线";
    				ranchingExpertsList.remove(j);
					ranchingExpertsList.add(0, experts);
					if(ExpertsDetailActivity.expertsOnlineStateTv != null && ExpertsDetailActivity.curUserName.equals(userName)){
						ExpertsDetailActivity.onlineState = 1;
						ExpertsDetailActivity.expertsOnlineStateTv.setText("在线");
						ExpertsDetailActivity.expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_login);
					}
				}
				BaseMethod.showToast(strMsg, this);
				expertsCountTv.setText(ranchingExpertOnlineCount + "/" + ranchingExpertsList.size());
				ranchingExpertsAdapter.notifyDataSetChanged();
				break;
			}
		}
		for(int j = 0 ; j < forestryExpertsList.size() ; j++){
			Experts experts = forestryExpertsList.get(j);
			if(experts.getUserName().equals(userName)){
				if(dwStatus == 0){
					experts.setExpertOnlineState(0);
					forestryExpertOnlineCount--;
					strMsg = experts.getExpertName() + "下线";
					forestryExpertsList.remove(j);
					forestryExpertsList.add(forestryExpertsList.size(), experts);
					if(ExpertsDetailActivity.expertsOnlineStateTv != null && ExpertsDetailActivity.curUserName.equals(userName)){
						ExpertsDetailActivity.onlineState = 0;
						ExpertsDetailActivity.expertsOnlineStateTv.setText("不在线");
						ExpertsDetailActivity.expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_background1);
					}
				}else if(dwStatus == 1){
					experts.setExpertOnlineState(1);
    				experts.setAnyChatId(dwUserId);
    				forestryExpertOnlineCount++;
    				strMsg = experts.getExpertName() + "上线";
					forestryExpertsList.remove(j);
    				forestryExpertsList.add(0, experts);
    				if(ExpertsDetailActivity.expertsOnlineStateTv != null && ExpertsDetailActivity.curUserName.equals(userName)){
    					ExpertsDetailActivity.expertsOnlineStateTv.setText("在线");
    					ExpertsDetailActivity.expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_login);
    					ExpertsDetailActivity.onlineState = 1;
    				}
				}
				BaseMethod.showToast(strMsg, this);
				expertsCountTv.setText(forestryExpertOnlineCount + "/" + forestryExpertsList.size());
				forestryExpertsAdapter.notifyDataSetChanged();
				break;
			}
		}
		
		BussinessCenter.getBussinessCenter().onUserOnlineStatusNotify(dwUserId,
				dwStatus);
	}

	@Override
	public void OnAnyChatVideoCallEvent(int dwEventType, int dwUserId,
			int dwErrorCode, int dwFlags, int dwParam, String userStr) {
		switch (dwEventType) {

		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST:
			Log.e("AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST == " , "" + AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST);
			BussinessCenter.getBussinessCenter().onVideoCallRequest(
					dwUserId, dwFlags, dwParam, userStr);
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_REQUEST,
					dwUserId, this  , "");
			dialog.show();
			break;
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY:
			BussinessCenter.getBussinessCenter().onVideoCallReply(
					dwUserId, dwErrorCode, dwFlags, dwParam, userStr);
			Log.e("AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY == " , "" + AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY);
			if (dwErrorCode == AnyChatDefine.BRAC_ERRORCODE_SUCCESS) {
				dialog = DialogFactory.getDialog(
						DialogFactory.DIALOGID_CALLING, dwUserId,
						this , "");
				dialog.show();

			} else {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
			}
			break;
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_START:
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			Log.e("AnyChatDefine.BRAC_VIDEOCALL_EVENT_START == " , "" + AnyChatDefine.BRAC_VIDEOCALL_EVENT_START);
			BussinessCenter.getBussinessCenter().onVideoCallStart(
					dwUserId, dwFlags, dwParam, userStr);
			break;
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH:
			Log.e("AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH == " , "" + AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH);
			BussinessCenter.getBussinessCenter().onVideoCallEnd(dwUserId,
					dwFlags, dwParam, userStr);
			break;
		}
	}

}
