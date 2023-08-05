package com.bingchong.bean;

import java.io.Serializable;


/**
 * This Class is the Getter/Setter for All User Info Fields.  
 */

public class UserBean implements Serializable{

	private static final long serialVersionUID = -2748364356854641973L;
	public long id;
	public String user;
	public String password;
	public String name;
	public String real_name;
	public String phone;	
	public String place;
	public String job;
	public long rights_id;
	public String imgurl;
	public long shengs_id;
	public long shis_id;
	public long xians_id;
	public String status;
	public String sheng = "";
	public String shi = "";
	public String xian = "";
	public String serial = "";
	public String photoData = "";
    public int level = 0;
    public String point_list = "";
	public String token = "";
	
	public static final String ID = "uid";
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String REAL_NAME = "real_name";
	public static final String PHONE = "phone";	
	public static final String SEX = "sex";
	public static final String PLACE = "place";
	public static final String JOB = "job";
	public static final String RIGHTS_ID = "right_id";
	public static final String IMG_URL = "imgurl";
	public static final String SHENGS_ID = "sheng_id";
	public static final String SHIS_ID = "shi_id";
	public static final String XIANS_ID = "xian_id";
	public static final String STATUS = "status";	
	public static final String SHENG = "sheng_name";
	public static final String SHI = "shi_name";
	public static final String XIAN = "xian_name";
	public static final String SERIAL = "serial";
    public static final String LEVEL = "level";
    public static final String POINT_LIST = "point_list";
	public static final String TOKEN = "token";
	
	
	
	public static final int		RIGHT_ADMIN	= 1;
//	public static final int		RIGHT_SHENG_MGR	= 1;
//	public static final int		RIGHT_SHI_MGR	= 1;
	public static final int		RIGHT_XIAN_MGR	= 2;
	public static final int		RIGHT_TESTER	= 3;
	

	public String toString()
	{
		return name;
	}
	
	public boolean isAdmin(){
		return rights_id > 0;
	}
	public long getId(){
		return id;
	}
	public String getUsername(){
		return name;
	}
}
