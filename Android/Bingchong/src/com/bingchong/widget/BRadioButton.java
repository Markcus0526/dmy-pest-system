package com.bingchong.widget;

import com.bingchong.Constant;
import com.bingchong.R;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class BRadioButton extends RadioButton{
	
    public BRadioButton(Context context, AttributeSet arr) {
		super(context, arr);
		setBackgroundResource(R.drawable.btn_menu);
	}
    
    @Override
    protected void onDraw(Canvas canvas) {
    	if(isPressed() || isChecked())
    		setTextColor(Constant.COLOR_TEXT_WHITE);
    	else
    		setTextColor(Constant.COLOR_TEXT_GREEN);
    	super.onDraw(canvas);
    }
}