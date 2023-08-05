package com.bingchong.adapter;

import com.bairuitech.bussinesscenter.BussinessCenter;
import com.bairuitech.bussinesscenter.SessionItem;
import com.bingchong.BaseMethod;
import com.bingchong.DialogFactory;
import com.bingchong.R;
import com.bingchong.activity.video.ExpertsDetailActivity;
import com.bingchong.bean.Experts;
import com.bingchong.utils.ImageHolder;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ExpertsAdapter extends BaseAdapter {
	
	private List<Experts> expertsList;
	private ViewHolder holder = null;
	private Activity activity;
	private Typeface face;
	
	public ExpertsAdapter(List<Experts> expertsList , Activity activity , Typeface face){
		this.expertsList = expertsList;
		this.activity = activity;
		this.face = face;
	}

	@Override
	public int getCount() {
		return expertsList.size();
	}

	@Override
	public Experts getItem(int arg0) {
		return expertsList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		
        if (v == null) {
            v = View.inflate(activity, R.layout.experts_list_item, null);
            holder = new ViewHolder();
            holder.expertsImg = (ImageView) v
                    .findViewById(R.id.experts_img);
            holder.expertsImg1 = (ImageView) v
            		.findViewById(R.id.experts_img1);
            holder.detailLl = (LinearLayout) v.findViewById(R.id.detail_ll);
            holder.expertsName = (TextView) v.findViewById(R.id.experts_name);
            holder.expertsNameTv = (TextView) v.findViewById(R.id.experts_name_tv);
            holder.expertsSex = (TextView) v.findViewById(R.id.experts_sex);
            holder.expertsSexTv = (TextView) v.findViewById(R.id.experts_sex_tv);
            holder.expertsUnit = (TextView) v.findViewById(R.id.experts_unit);
            holder.expertsUnitTv = (TextView) v.findViewById(R.id.experts_unit_tv);
            holder.expertsAppointmentBtn = (Button) v.findViewById(R.id.appointment_btn);
            holder.phoneBtn = (Button) v.findViewById(R.id.phone_btn);
            //holder.jianjieBtn = (Button) v.findViewById(R.id.jianjie_btn);
            holder.expertsDescription = (TextView) v.findViewById(R.id.experts_description);
            holder.expertsDescriptionTv = (TextView) v.findViewById(R.id.experts_description_tv);
            
            holder.expertsName.setTypeface(face);
            holder.expertsNameTv.setTypeface(face);
            holder.expertsSex.setTypeface(face);
            holder.expertsSexTv.setTypeface(face);
            holder.expertsUnit.setTypeface(face);
            holder.expertsUnitTv.setTypeface(face);
            holder.expertsAppointmentBtn.setTypeface(face);
            holder.phoneBtn.setTypeface(face);
            //holder.jianjieBtn.setTypeface(face);
            holder.expertsDescription.setTypeface(face);
            holder.expertsDescriptionTv.setTypeface(face);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        if(expertsList.size() > 0){
        	final Experts experts = expertsList.get(position);
        	/*if(experts.getExpertImg() != null && !("".equals(experts.getExpertImg()))){
        		holder.expertsImg.setImageURI(uri);
        	}*/
        	//holder.expertsImg.setImageResource(experts.getExpertImg());
        	// 设置头像
    		ImageHolder.setAvatar(holder.expertsImg , experts.getExpertImg());
    		
    		if(experts.getExpertOnlineState() != 1){
    			holder.expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_background1);
    			holder.expertsImg1.setVisibility(View.VISIBLE);
    		}else{
    			holder.expertsAppointmentBtn.setBackgroundResource(R.drawable.btn_login);
    			holder.expertsImg1.setVisibility(View.GONE);
    		}
        	holder.expertsNameTv.setText(experts.getExpertName());
        	holder.expertsSexTv.setText(experts.getExpertSex());
        	holder.expertsUnitTv.setText(experts.getExpertUnit());
        	holder.expertsDescriptionTv.setText(experts.getExpertDescription());
        	
        	holder.expertsAppointmentBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(experts.getExpertOnlineState() == 1 ){
						BussinessCenter.sessionItem = new SessionItem(0, experts.getAnyChatId(),
								BussinessCenter.selfUserId);
						Dialog dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_CALLRESUME,
								experts.getAnyChatId(), activity , experts.getExpertName());
						dialog.show();
						Log.e("adapter",experts.getAnyChatId()+"");
						Log.e("adapter",experts.getExpertName()+"");
						/*Intent intent=new Intent();
					    intent.putExtra("UserID", experts.getAnyChatId());
					    intent.setClass(context, VideoActivity.class);
					    context.startActivity(intent);*/
		        	}else{
		        		BaseMethod.showToast("该专家不在线，无法预约！", activity);
		        	}
				}
			});
        	
        	holder.phoneBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(experts.getPhoneNum() == null || experts.getPhoneNum().length() <= 0) {
						BaseMethod.showToast("账户没有电话号码！", activity);
						return;
					}
					try {
						Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + experts.getPhoneNum()));
						activity.startActivity(intent);
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
        	holder.detailLl.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(activity , ExpertsDetailActivity.class);
					i.putExtra("experts", experts);
					activity.startActivity(i);				}
			});
        	/*holder.jianjieBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(activity , ExpertsDetailActivity.class);
					i.putExtra("experts", experts);
					activity.startActivity(i);				}
			});*/
        	
        	/*v.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(activity , ExpertsDetailActivity.class);
					i.putExtra("experts", experts);
					activity.startActivity(i);				}
			});*/
        	
        }
		return v;
	}
	
	static class ViewHolder {
		LinearLayout detailLl;
        ImageView expertsImg;
        ImageView expertsImg1;
        TextView expertsName;
        TextView expertsNameTv;
        TextView expertsSex;
        TextView expertsSexTv;
        TextView expertsUnit;
        TextView expertsUnitTv;
        Button expertsAppointmentBtn;
        Button phoneBtn;
        //Button jianjieBtn;
        TextView expertsDescription;
        TextView expertsDescriptionTv;
    }

}
