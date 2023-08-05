package com.bingchong.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LineView extends View {
	
	private Paint mPaint;
	private int width;
	private int height;
        
    public LineView(Context context, AttributeSet arr) {
		super(context, arr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFF7AB038);
	}
    

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	canvas.drawRect(0, 0, width, height, mPaint);
    }
}