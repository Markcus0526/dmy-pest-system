package com.bingchong.widget;

import java.util.ArrayList;

import com.bingchong.bean.ValueBean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraphView extends View {

	private int width = 400;
	private int height = 800;
	private Paint piText;
	private Paint piAxis;
	private Paint piVal;
	private float maxVal;
	private float stepY;
	
	private Bitmap  mBitmap = null;
	private Paint   mBitmapPaint;
	private Canvas  mCanvas;	

	private long sTick = 0;

	private float animateStep = 1.0f;

	param mOrgParam = new param();
	param mParam = new param();


	ArrayList<ValueBean> values = null;

	public class param{
		public int colorText = Color.RED;
		public int colorAxis = Color.GRAY;
		public int colorVal  =  Color.RED;
		public int padLeft	 = 50;
		public int padRight	 = 20;
		public int padBottom = 70;
		public int padTop	 = 40;
		public int rotX		 = 0;


		public long	animationTime = 2000;
		public Boolean isBar = true;

		public int lineCount = 5;
		private int textSize = 25;
	}

	public GraphView(Context context) {
		super(context);

		setBackgroundColor(Color.WHITE);

		initPaints();
	}
	
	public GraphView(Context context, AttributeSet arr) {
		super(context, arr);
		initPaints();
	}
	
	public void setIsPolygon(Boolean isPolygon){
		mParam.isBar = !isPolygon;
	}

	private void initPaints(){
		piText = new Paint();
		piText.setColor(mParam.colorText);
		piText.setTextSize(mParam.textSize);
		piText.setTextAlign(Paint.Align.CENTER);
		piText.setAntiAlias(true);
		piAxis = new Paint();
		piAxis.setColor(mParam.colorAxis);
		piAxis.setTextSize(mParam.textSize / 2);
		piAxis.setTextAlign(Paint.Align.RIGHT);
		piAxis.setAntiAlias(true);
		piVal = new Paint();
		piVal.setColor(mParam.colorVal);
		piVal.setTextSize(mParam.textSize);
		piVal.setTextAlign(Paint.Align.CENTER);
		piVal.setStrokeWidth(2.0f);
		piVal.setAntiAlias(true);
		mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);		
	}

	public void SetValues(ArrayList<ValueBean> listVal){
		values = listVal;
		preCalcu();
		startAnimate();
	}
	
	private void startAnimate(){
		sTick = 0;
		invalidate();
	}

	private String getYLabel(float val){
		String str = "";
		int valInt = (int)val;
		if(Math.abs(val - valInt) < 0.001)
			str = String.format("%d", valInt);
		else
			str = String.format("%.3f", val);

		return str;
	}

	private void preCalcu(){
		if(values == null || values.size() == 0)
			return;
		ValueBean obj = values.get(0);
		maxVal = obj.val;   

		for(int i = 1; i < values.size(); i++){
			obj = values.get(i);
			if(maxVal < obj.val) maxVal = obj.val;
		}

		// get y axis value
		stepY = maxVal / mParam.lineCount;
		if(stepY == 0)
			stepY = 1;

		float tmpY = stepY;
		float scale = 1.0f;

		if((stepY * scale) > 10){
			while((stepY * scale) > 10){
				scale = scale / 10;
			}
		}
		else if((stepY * scale) < 1){
			while ((stepY * scale) < 1)
				scale = scale * 10;
		}

		tmpY = stepY * scale;

		if(tmpY < 2)
			tmpY = 1;
		else if(tmpY < 5)
			tmpY = 2;
		else 
			tmpY = 5;

		stepY = tmpY / scale;

		maxVal = ((int)(maxVal / stepY)) * stepY + stepY;
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
		if(w > 0 && h > 0){
			
			float scale = 1.0f * w / 400;
			
			mParam.padBottom = (int)(mOrgParam.padBottom * scale);
			mParam.padTop = (int)(mOrgParam.padTop * scale);
			mParam.padRight = (int)(mOrgParam.padRight * scale);
			mParam.padLeft = (int)(mOrgParam.padLeft * scale);
			mParam.textSize = (int)(mOrgParam.textSize * scale);
			
			initPaints();
			
			if(mBitmap != null)
				mBitmap.recycle();
			mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			preCalcu();
		}
	}

	protected void drawGraph(Canvas canvas) {

		// check animation time
		long curTick = System.currentTimeMillis();
		if(sTick == 0){ // firstCase
			sTick = curTick;
		}

		animateStep = 1.0f * (curTick - sTick) / mParam.animationTime; 
		if(animateStep >= 1.0f)
			animateStep = 1.0f;
		// end check

		float delta = 5;
		float oX = mParam.padLeft + delta;
		float oY = height - mParam.padBottom - delta;

		// draw axis
		canvas.drawLine(mParam.padLeft, oY, width - mParam.padRight, oY, piAxis);
		canvas.drawLine(oX, mParam.padTop, oX, height - mParam.padBottom, piAxis);

		if(values == null || values.size() == 0)
			return;

		float scaleX = 1.0f * (width - oX - mParam.padRight) / values.size();
		float scaleY = 1.0f * (oY - mParam.padTop)/maxVal;
		float x, y;

		String str;
		int i;

		// draw x mark
		for(i = 0; i < values.size(); i++){
			x = oX + (i + 0.5f) * scaleX;
			y = oY;

			float rot = -90;
			if(mParam.isBar){
				float xx = oX + (i + 1) * scaleX;
				canvas.drawLine(xx, y, xx, y + delta, piAxis);
			}
			else
				canvas.drawLine(x, y, x, y + delta, piAxis);
			canvas.rotate(rot, x, y);
			canvas.drawText(values.get(i).name, x - delta, y + mParam.textSize / 3, piAxis);
			canvas.rotate(-rot, x, y);
		}

		// draw y mark
		int count = (int)(maxVal / stepY);
		for(i = 1; i < count; i++){
			x = oX;
			y = oY - i * stepY * scaleY;   

			str = getYLabel(i * stepY);

			canvas.drawLine(mParam.padLeft, y, width - mParam.padRight, y, piAxis);
			canvas.drawText(str, x - delta, y + mParam.textSize / 3, piAxis);
		}

		// draw graph

		if(mParam.isBar){
			for(i = 0; i < values.size(); i++){
				float val = values.get(i).val;
				x = oX + (i + 0.5f) * scaleX;
				y = oY - val * scaleY;
				float rate = 1;
				if(animateStep < 0.5)
					rate = 2 * animateStep * animateStep;
				else{
					float t = animateStep - 0.5f;
					rate = 0.5f + 2 * t  - 2 * t * t;
				}
				float yy = oY - val * scaleY * rate;
				float dx = scaleX * 0.4f;

				canvas.drawRect(x - dx, yy, x + dx, oY, piVal);
				if(animateStep > 0.9f){
					str = getYLabel(values.get(i).val);
					canvas.drawText(str, x, y - mParam.textSize, piVal);
				}
			}
		}
		else{
			float oldX = 0;
			float oldY = 0;

			float stepCount = (values.size() - 1) * animateStep;
			count = (int)stepCount + 2;
			if(count >= values.size())
				count = values.size();
			for(i = 0; i < count; i++){
				x = oX + (i + 0.5f) * scaleX;
				y = oY - values.get(i).val * scaleY;
				if(i > 0){
					if(i <= stepCount)
						canvas.drawLine(oldX, oldY, x, y, piVal);
					else{
						float rate = stepCount - (int)stepCount;
						float xx = oldX + rate * (x - oldX);
						float yy = oldY + rate * (y - oldY);
						canvas.drawLine(oldX, oldY, xx, yy, piVal);
					}
				}

				if(i <= stepCount){
					canvas.drawCircle(x, y, 3, piVal);
					str = getYLabel(values.get(i).val);
					canvas.drawText(str, x, y - mParam.textSize, piVal);
				}
				oldX = x;
				oldY = y;
			}        	
		}
		
		if(animateStep < 1.0f)
			invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//drawGraph(mCanvas);
		//canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		drawGraph(canvas);
	}
}