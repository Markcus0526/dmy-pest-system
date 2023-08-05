package com.bingchong.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import com.bingchong.R;
import com.bingchong.activity.video.ExpertsListActivity;


public class TabItem {

	private int backgroundID;
	private int tabViewID;
	private int tabNameID;
	private Class<? extends Activity> tabFragmentClass;

	public TabItem() {
	}

	public TabItem(int backgroundID, int tabViewID, int tabNameID,
			Class<? extends Activity> tabFragmentClass) {
		this.backgroundID = backgroundID;
		this.tabViewID = tabViewID;
		this.tabNameID = tabNameID;
		this.tabFragmentClass = tabFragmentClass;
	}

	public static List<TabItem> genTabItems() {
		List<TabItem> items = new ArrayList<TabItem>();
		items.add(new TabItem(R.drawable.tab_nong_selector_l, R.id.tv_tab_agriculture,
				R.string.agriculture, ExpertsListActivity.class));
		items.add(new TabItem(R.drawable.tab_mu_selector_l, R.id.tv_tab_ranching,
				R.string.ranching, ExpertsListActivity.class));
		/*items.add(new TabItem(R.drawable.tab_lin_click, R.id.tv_tab_forestry,
				R.string.forestry, ExpertsListActivity.class));*/
		return items;
	}

	public int getBackgroundID() {
		return backgroundID;
	}

	public void setBackgroundID(int backgroundID) {
		this.backgroundID = backgroundID;
	}

	public int getTabViewID() {
		return tabViewID;
	}

	public void setTabViewID(int tabViewID) {
		this.tabViewID = tabViewID;
	}

	public int getTabNameID() {
		return tabNameID;
	}

	public void setTabNameID(int tabNameID) {
		this.tabNameID = tabNameID;
	}

	public Class<? extends Activity> getTabFragmentClass() {
		return tabFragmentClass;
	}

	public void setTabFragmentClass(
			Class<? extends Activity> tabFragmentClass) {
		this.tabFragmentClass = tabFragmentClass;
	}
	
	
}
