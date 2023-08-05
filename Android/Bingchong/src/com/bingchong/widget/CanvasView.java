package com.bingchong.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {
	
	private Paint mPaint;
        
    private Bitmap  mBitmap;
    private Canvas  mCanvas;
    private android.graphics.Path mPath;
    private Paint   mBitmapPaint;
    private int MIN_WIDTH	= 200;
    private int MIN_HEIGHT = 200;
   
    public CanvasView(Context c,int width,int height) {
        super(c);
        
        if(width < MIN_WIDTH)
        	width = MIN_WIDTH;
        if(height < MIN_HEIGHT)
        	height = MIN_HEIGHT;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFF000000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);	
    }
    
    public CanvasView(Context context, AttributeSet arr) {
		super(context, arr);

	}
    
    public void setPaintMode(){
    	mPaint.setColor(0xFF000000);
    	mPaint.setStrokeWidth(5);
    }
    
    public void setEraseMode(){
    	mPaint.setColor(0xFFFFFFFF);
    	mPaint.setStrokeWidth(30);
    }
	
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFFFFFFF);      
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPaint);
    }
    
    public void drawBitmap(Bitmap bmp){
    	mCanvas.drawBitmap(bmp, 0, 0, mBitmapPaint);
    }
    
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 8;
    
    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    private void touch_up() {
        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        mPath.reset();
    }
    
    public void eraseAll(){
    	mCanvas.drawColor(0xFFFFFFFF);
    	if(mPath!=null){
    		mPath.reset();
    	}
    	invalidate();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }
    
}