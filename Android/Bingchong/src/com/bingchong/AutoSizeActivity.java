package com.bingchong;

import com.bingchong.utils.ResolutionSet;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class AutoSizeActivity extends Activity{


	final String CLASS_NAME = getClass().getName();

	private ViewGroup mainLayout;
    //boolean bInitialized = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    Resources resources = this.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    //ResolutionSet.instance.dpToPt = metrics.densityDpi / 160f;
	}
    
    protected void setAutoResize(ViewGroup view)
    {
    	mainLayout = view;
    	
    	mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(
    			new ViewTreeObserver.OnGlobalLayoutListener() {
    				public void onGlobalLayout() {
    					resizeAll();
    				}
    			}
    			);	
    }
    
    protected void resizeAll(){
		//if (bInitialized == false)
		{
			Rect r = new Rect();
			mainLayout.getLocalVisibleRect(r);
			ResolutionSet.instance.setResolution(r.width(), r.height());
			//ResolutionSet.instance.iterateChild(mainLayout);
			//bInitialized = true;
		}
    	
    }
    
    protected void checkAutoResize(){
    	View v = findViewById(R.id.mainLayout);
    	
    	if(!(v instanceof ViewGroup))
    		return;
    	setAutoResize((ViewGroup)v);
    }
}