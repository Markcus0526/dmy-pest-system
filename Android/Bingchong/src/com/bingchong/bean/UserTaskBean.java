package com.bingchong.bean;

import com.bingchong.utils.AppDateTimeUtils;


/**
 * This Class is the Getter/Setter for All User Info Fields.  
 */

public class UserTaskBean{
	public final static int STAT_ASSIGN = 0;
	public final static int STAT_COMPLET = 1;
	public int id;
	public int  user_id;
	public int	task_id;
	public String  task;
	public long	sDate;
	public long	eDate;
	public int stat;
	
	public String toString()
	{
		return "" + id + ":" + task;
	}
	
	public String getAcceptDate()
	{
		return AppDateTimeUtils.getSimpleDateTime(sDate);
	}
	public String getCompleteDate()
	{
		return AppDateTimeUtils.getSimpleDateTime(eDate);
	}
}
