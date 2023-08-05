package com.bingchong.bean;

public class TaskBean implements Comparable<TaskBean> {
	
	public final static int	STATUS_NOCOMPLETE = 0;
	public final static int	STATUS_COMPLETE = 1;
	
	public final static String ID = "uid";
	public final static String NAME = "name";
	public final static String POINT_ID = "point_id";
	public final static String FORM_ID = "form_id";
	public final static String FORM_NAME = "form_name";
	public final static String POINT_NAME = "point_name";
	public final static String BLIGHT_ID = "blight_id";
	public final static String BLIGHT_NAME = "blight_name";
	public final static String DATE = "task_date";
	public final static String REPORT_ID = "report_id";
	
	public final static int DISP_TYPE_NORMAL = 0;
	public final static int DISP_TYPE_HISTORY = 1;
    public final static int DISP_TYPE_HISTORY_NOTCOMPLETED = 2;
    public final static int DISP_TYPE_HISTORY_COMPLETED = 3;
    public final static int DISP_TYPE_HISTORY_WAITING = 4;
	
	public int detail_id;
	public String name = "";
	public int point_id = 0;
	public int blight_id = 0;
	public int form_id = 0;
	public int report_id = 0;
	public String blight_name = "";
	public String point_name = "";
	public int point_type = 0;
	public String form_name = "";
	public String date = "";
	
	public int dispType;
	
	public void setDisplayType(int type){
		dispType = type;
	}
	
	public String toString()
	{
		if(dispType == DISP_TYPE_NORMAL)
			return name + " " + blight_name + " " + point_name;
		else if(dispType == DISP_TYPE_HISTORY)
			return date + " " + point_name + "\n" + form_name + " " + blight_name;
		else if(dispType == DISP_TYPE_HISTORY_NOTCOMPLETED)
            return name + " " + point_name + "\n" + date;
        else if(dispType == DISP_TYPE_HISTORY_COMPLETED)
            return name + " " + point_name + "\n" + date;
        else if(dispType == DISP_TYPE_HISTORY_WAITING)
            return name + " " + point_name + "\n" + date;

		return name + " " + point_name + "\n" + date;
	}
	public int compareTo(TaskBean another) {
		if (another == null) return 1;
		// sort descending, most recent first
		return another.date.compareTo(date);
	}	
}
