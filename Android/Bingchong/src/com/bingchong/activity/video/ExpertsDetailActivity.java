package com.bingchong.activity.video;

import java.util.ArrayList;
import java.util.List;



import com.bairuitech.bussinesscenter.BussinessCenter;
import com.bairuitech.bussinesscenter.SessionItem;
import com.bingchong.BaseMethod;
import com.bingchong.Config;
import com.bingchong.ConfigService;
import com.bingchong.ConfigVideo;
import com.bingchong.DialogFactory;
import com.bingchong.R;
import com.bingchong.bean.Experts;
import com.bingchong.utils.ImageHolder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @class：ForestryExpertsActivity.java 
 * @description: 专家预约详细页
 * @author: 金铁钢
 * 
 * @history:
 *   	    日期  	     版本             担当者         修改内容
 *   2014-12-3  1.0    金铁钢            初版
 */
public class ExpertsDetailActivity extends Activity implements
	OnClickListener{
	
	TextView expertsDetailTv;
	ImageView expertsImg;
    ImageView expertsImg1;
    TextView expertsName;
    TextView expertsNameTv;
    TextView expertsSex;
    TextView expertsSexTv;
    TextView expertsOnlineState;
    public static TextView expertsOnlineStateTv;
    public static Button expertsAppointmentBtn;
    public static int onlineState = 0;
    public static String curUserName = "";
    TextView expertsUnit;
    TextView expertsUnitTv;
    Button phoneBtn;
    Button backBtn;
    TextView expertsDescription;
    TextView expertsDescriptionTv;
    private Typeface face;
    private Experts experts;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//initSdk();
        ConfigVideo.selectPosition = ConfigService.LoadQuality(this);
        BussinessCenter.getBussinessCenter().getOnlineFriendDatas();
        findViewById();
        if(Config.IS_SET_FONTS)
        	setTypeface();
        experts = getIntent().getParcelableExtra("experts");
        curUserName = experts.getUserName();
        initData();
	}
	
	/**
	 * @description 绑定控件
	 */
	private void findViewById() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.experts_detail);
		backBtn = (Button) findViewById(R.id.btn_back);
		expertsImg = (ImageView) findViewById(R.id.experts_img);
        expertsImg1 = (ImageView) findViewById(R.id.experts_img1);
        expertsDetailTv = (TextView)findViewById(R.id.experts_detail_tv);
        expertsName = (TextView)findViewById(R.id.experts_name);
        expertsNameTv = (TextView)findViewById(R.id.experts_name_tv);
        expertsSex = (TextView)findViewById(R.id.experts_sex);
        expertsSexTv = (TextView)findViewById(R.id.experts_sex_tv);
        expertsOnlineState = (TextView) findViewById(R.id.experts_online);
        expertsOnlineStateTv = (TextView) findViewById(R.id.experts_online_tv);
        expertsUnit = (TextView)findViewById(R.id.experts_unit);
        expertsUnitTv = (TextView) findViewById(R.id.experts_unit_tv);
        expertsAppointmentBtn = (Button)findViewById(R.id.appointment_btn);
        phoneBtn = (Button)findViewById(R.id.phone_btn);
        expertsDescription = (TextView)findViewById(R.id.experts_description);
        expertsDescriptionTv = (TextView)findViewById(R.id.experts_description_tv);
	}
	
	private void initData(){
		ImageHolder.setAvatar( expertsImg , experts.getExpertImg());
		
		if(experts.getExpertOnlineState() != 1){
			expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_background1);
			expertsOnlineStateTv.setText("不在线");
			onlineState = 0;
		}else{
			expertsOnlineStateTv.setText("在线");
			onlineState = 1;
			expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_login);
		}
		expertsImg1.setVisibility(View.GONE);
		expertsDetailTv.setText(experts.getExpertName());
    	expertsNameTv.setText(experts.getExpertName());
    	expertsSexTv.setText(experts.getExpertSex());
    	expertsUnitTv.setText(experts.getExpertUnit());
    	expertsDescriptionTv.setText(experts.getExpertDescription());
    	
    	expertsAppointmentBtn.setOnClickListener(this);
    	
    	phoneBtn.setOnClickListener(this);
    	
    	backBtn.setOnClickListener(this);
	}
	
	/**
     * @description 设置字体
     */
    public void setTypeface(){
    	face = Typeface.createFromAsset(this.getAssets(),Config.FONTS);
    	expertsDetailTv.setTypeface(face);
    	expertsName.setTypeface(face);
        expertsNameTv.setTypeface(face);
        expertsSex.setTypeface(face);
        expertsSexTv.setTypeface(face);
        expertsOnlineState.setTypeface(face);
        expertsOnlineStateTv.setTypeface(face);
        expertsUnit.setTypeface(face);
        expertsUnitTv.setTypeface(face);
        expertsAppointmentBtn.setTypeface(face);
        phoneBtn.setTypeface(face);
        expertsDescription.setTypeface(face);
        expertsDescriptionTv.setTypeface(face);
    }
	
	@Override
	public void onClick(View v) {
		if (v == backBtn) {
			finish();
		}
		if(v == phoneBtn){
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(experts.getPhoneNum()));    
			startActivity(intent);  
		}
		if(v == expertsAppointmentBtn){
			if(onlineState == 1 ){
				BussinessCenter.sessionItem = new SessionItem(0, experts.getAnyChatId(),
						BussinessCenter.selfUserId);
				Dialog dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_CALLRESUME,
						experts.getAnyChatId(), ExpertsDetailActivity.this , experts.getExpertName());
				dialog.show();
				Log.e("adapter",experts.getAnyChatId()+"");
				Log.e("adapter",experts.getExpertName()+"");
				/*Intent intent=new Intent();
			    intent.putExtra("UserID", experts.getAnyChatId());
			    intent.setClass(context, VideoActivity.class);
			    context.startActivity(intent);*/
        	}else{
        		BaseMethod.showToast("该专家不在线，无法预约！", ExpertsDetailActivity.this);
        	} 
		}
	}
	
	
}
