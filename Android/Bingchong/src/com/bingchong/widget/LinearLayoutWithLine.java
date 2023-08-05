package com.bingchong.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LinearLayoutWithLine extends LinearLayout{
	
	public LinearLayoutWithLine(Context context) {
		super(context);
	}

	public LinearLayoutWithLine(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = getWidth();
		int h = getHeight();
		Paint paint = new Paint(); 
		paint.setColor(Color.RED);
		paint.setStrokeWidth(2);

		//Rect rt = new Rect(0,0,getWidth(), getHeight());
		//canvas.drawRect(rt, paint);
		
		canvas.drawLine(0, 0, 0, h, paint);
		canvas.drawLine(0, h, w, h, paint);
		canvas.drawLine(w, h, w, 0, paint);
		canvas.drawLine(w, 0, 0, 0, paint);
		super.onDraw(canvas);  
	}
}
