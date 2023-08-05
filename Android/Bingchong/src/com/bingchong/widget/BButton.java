package com.bingchong.widget;

import com.bingchong.Constant;
import com.bingchong.R;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

public class BButton extends Button{
	
    public BButton(Context context, AttributeSet arr) {
		super(context, arr);
		setBackgroundResource(R.drawable.btn_menu);
	}
    
    @Override
    protected void onDraw(Canvas canvas) {
    	if(isPressed())
    		setTextColor(Constant.COLOR_TEXT_WHITE);
    	else
    		setTextColor(Constant.COLOR_TEXT_GREEN);
    	super.onDraw(canvas);
    }
}