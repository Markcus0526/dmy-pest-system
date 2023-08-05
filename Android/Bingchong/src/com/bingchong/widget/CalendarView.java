package com.bingchong.widget;

import java.util.ArrayList;
import java.util.Calendar;

import com.bingchong.Constant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CalendarView extends View {
	
	private Paint ptGreen;
	private Paint ptGreenLight;
	private Paint ptRedMark;
	private Paint ptBlack;
	private Paint ptWhite;
	private Paint ptBlueMark;
	
	private int width;
	private int height;
	private long curDate;
	private long firstDate;
	private long lastDate;
	private long dayTime;
	
	private int curMonth = 1;
	
	private int	 textSize = 28;
	private float radius = 12.0f;

	private int taskPointType = 0;		// 0 - 固定测报点 ， 1 - 非固定测报点
	
	private OnClickDayListener mListener = null;
	
	private String[] week_names = {"SUN", "MON", "TUE", "WEN", "THI","FRI","SAT"};
	
	class ItemRect{
		RectF	rt;
		int		day;
		int		month;
		long	time;
		int		workCount = 0;
		boolean isToday = false;
	}
	
	private ArrayList<ItemRect> listItem = null;
        
    public CalendarView(Context context, AttributeSet arr) {
		super(context, arr);

		init();

	}
    
    public long getFirstDate(){
    	return firstDate;
    }
    
    public long getLastDate(){
    	return lastDate;
    }
    
    public void setWorksCount(ArrayList<String> list){
    	
    	int i = 0;
    	
    	while(i < listItem.size() && i < list.size()){
    		try{
    			String val = list.get(i);
    			int count = Integer.parseInt(val);
    			if(listItem.get(i).month == curMonth)
    				listItem.get(i).workCount = count;
    		}
    		catch(Exception ex){}
    		
    		i++;
    	}
    	
    	invalidate();
    }
    
    public void setWeekNames(String[] weekNames){
    	this.week_names = weekNames;
    }

	public void setPointType(int type)
	{
		taskPointType = type;
	}

    private void init(){
		ptGreen = new Paint();
		ptGreen.setColor(Constant.COLOR_TEXT_GREEN);
		ptGreen.setTextSize(textSize);
		ptGreen.setTextAlign(Align.CENTER);
		ptGreen.setAntiAlias(true);		
		
		ptGreenLight = new Paint();
		ptGreenLight.setColor(Constant.COLOR_TEXT_GREEN_LIGHT);
		ptRedMark = new Paint();
		ptRedMark.setColor(Constant.COLOR_MARK_RED);
		ptRedMark.setAntiAlias(true);
		
		ptBlack = new Paint();
		ptBlack.setColor(Color.BLACK);
		ptBlack.setTextSize(textSize);
		ptBlack.setTextAlign(Align.CENTER);	
		ptBlack.setAntiAlias(true);		

		ptWhite = new Paint();
		ptWhite.setColor(Color.WHITE);
		ptWhite.setTextSize(radius * 1.5f);
		ptWhite.setTextAlign(Align.CENTER);
		ptWhite.setAntiAlias(true);

		ptBlueMark = new Paint();
		ptBlueMark.setColor(Color.BLUE);
		ptBlueMark.setTextSize(radius * 1.5f);
		ptBlueMark.setTextAlign(Align.CENTER);
		ptBlueMark.setAntiAlias(true);

		dayTime = 24 * 3600 * 1000;
		
		setCurDate(System.currentTimeMillis());    	
    }
    
    public void setOnClickDayListener(OnClickDayListener listner)
    {
    	mListener = listner;
    }
    
    public void setCurDate(long dateInMiliSeconds)
    {
    	curDate = dateInMiliSeconds;
    	
    	Calendar date = Calendar.getInstance();
    	date.setTimeInMillis(dateInMiliSeconds);
		
    	date.set(Calendar.DATE, 1);
    	date.set(Calendar.HOUR, 0);
    	date.set(Calendar.MINUTE, 0);
    	
    	curMonth = date.get(Calendar.MONTH);
    	int day = date.get(Calendar.DAY_OF_YEAR);		
		int dayWeek = date.get(Calendar.DAY_OF_WEEK);
		int offset = dayWeek - Calendar.SUNDAY;
		
		date.set(Calendar.DAY_OF_YEAR, day - offset);
		firstDate = date.getTimeInMillis();
		
		date.setTimeInMillis(dateInMiliSeconds);
		date.set(Calendar.DAY_OF_YEAR, day - offset + 35);
		lastDate = date.getTimeInMillis();
		makeListItem();
		
		invalidate();
    }
    
    private void makeListItem(){
    	listItem = new ArrayList<ItemRect>();
    	ItemRect item = null;
    	float dx = width / 7.0f;
    	float dy = height / 7.0f;

    	float x = 0; 
    	float y = 10;
    	
    	Calendar date = Calendar.getInstance();
    	date.setTimeInMillis(curDate);

    	// get draw date
    	date.setTimeInMillis(firstDate);
    	long time = date.getTimeInMillis();
    	float leftX, topY;
			
    	for(int i = 0; i < 5; i++)
    	{
    		for(int k = 0; k < 7; k++)
    		{
    			leftX = x + k * dx;
    			topY = y + (i + 1.5f)* dy;
    			
    			item = new ItemRect();
    			item.rt = new RectF(leftX, topY, leftX + dx, topY + dy);
    			item.time = time;
    			date.setTimeInMillis(time);
    			item.day = date.get(Calendar.DAY_OF_MONTH);
    			item.month = date.get(Calendar.MONTH);
    			
    			item.workCount = 0;
    			
        		if(item.time < curDate && curDate < (item.time + dayTime))
        		{
        			item.isToday = true;
        		}       			
    			
    			listItem.add(item);
    			time = time + dayTime;
    		}
    	}    	
    }
    
    public void setWorkingDate(ArrayList<Long> listWorks)
    {
    	
    }
    

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        if(w > 0 && h > 0){
        	textSize = w * 2 / 30;
        	radius = w / 32.0f;        	
    		init();
        	makeListItem();
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	float dx = width / 7.0f;
    	float dy = height / 7.0f;

    	float x = 0; 
    	float y = 10;
    	
    	Calendar date = Calendar.getInstance();
    	date.setTimeInMillis(curDate);

    	// draw mark
    	canvas.drawRect(x, y, width, y + dy, ptGreenLight);    	
   		for(int i = 0; i < 7; i++)
   			canvas.drawText(week_names[i], x + (i + 0.5f) * dx, y + 0.7f * dy, ptBlack);
    	
    	// draw date
    	for(int i = 0; i < listItem.size(); i++)
    	{
    		ItemRect item = listItem.get(i);
    		if(item.isToday)
    		{
    			canvas.drawRect(item.rt, ptGreenLight);
    		}
    		
    		if(item.workCount > 0)
    		{
    			String strCount = "" + item.workCount;
				if(taskPointType == 0)
    				canvas.drawCircle(item.rt.right - radius, item.rt.top + radius, radius, ptRedMark);
				else
					canvas.drawCircle(item.rt.right - radius, item.rt.top + radius, radius, ptBlueMark);

				canvas.drawText(strCount, item.rt.right - radius, item.rt.top + radius * 1.5f, ptWhite);
    		}

    		String strDay = "" + item.day; 
    		canvas.drawText(strDay, item.rt.centerX(), item.rt.centerY() + textSize / 2, ptGreen);
    	}
    }
    
	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		int action = event.getAction();

		float x = event.getX();
		float y = event.getY();
		
		if(mListener == null)
			return true;
		
		if (action == MotionEvent.ACTION_DOWN){
			
			for(int i = 0; i < listItem.size(); i++)
			{
				ItemRect item = listItem.get(i);
				if(item.rt.contains(x, y)){
					if(item.workCount > 0)
						mListener.OnClickDayListener(item.time);
				}
			}

			return true;
		}		

		return true;
	}
	
	public interface OnClickDayListener {
		public void OnClickDayListener(long time);
	}

}