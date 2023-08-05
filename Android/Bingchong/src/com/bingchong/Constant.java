package com.bingchong;

import java.text.SimpleDateFormat;

public class Constant {
	
	public static final String HTTP_USER_AGENT_PARAM = "Android HNYH 1.0";
	// url
    //public final static String URL_ROOT = "http://218.25.54.56:20211/";
    public final static String URL_ROOT = "http://192.168.1.41:20211/";
	//public final static String URL_SERVER = URL_ROOT + "service/";
    public final static String URL_SERVER = URL_ROOT + "Service.svc/";
	public final static String URL_LOGIN  = "loginUser";
	public final static String URL_GET_XIANS = "getxians";
	public final static String URL_GET_PLACE_SEARCH = "getpoints";
	public final static String URL_GET_PLACE_INFO = "getpointinfo";
	public final static String URL_GET_TEMP_POINT_INFO = "temp_point_info";
	public final static String URL_GET_BLIGHT_SEARCH = "getBlights";
	public final static String URL_GET_BLIGHT_INFO = "getblightinfo";
	public final static String URL_GET_TEMP_BLIGHT_INFO = "temp_blight_info";
	public final static String URL_GET_USERS = "getusers";
	public final static String URL_GET_USER_INFO = "getuserinfo";
	public final static String URL_GET_FORMS = "getforms";
	public final static String URL_GET_FIELDSS = "getfields";
	public final static String URL_SET_REPORT = "setReports";
	public final static String URL_GET_REPORTS = "getreports";
	public final static String URL_GET_REPORT_HISTORY = "get_report_history";
	public final static String URL_GET_REPORT_INFO = "getreportinfo";
	public final static String URL_GET_USER_TRACK = "getwatchertracks";	
	
	public final static String URL_GET_EXTRA_TASKS = "extra_tasks";
	public final static String URL_UPLOAD_EXTRA_TASK = "upload_extra_task";
	public final static String URL_UPLOAD_BLIGHT = "upload_blight";
	public final static String URL_UPLOAD_POINT = "upload_point";
	public final static String URL_UPLOAD_OPINION = "upload_opinion";
	public final static String URL_UPLOAD_WATCHER = "upload_watchers";	
	public final static String URL_UPLOAD_USER = "upload_user";	
	public final static String URL_GET_TABLE_ITEM = "get_item";	
	public final static String URL_GET_HELPS = "helps";
	public final static String URL_GET_NOTICES = "notices";	
	public final static String URL_SHOW_POLYGON = "show_polygon";	
	public final static String URL_GET_REPORT_POLYLINES = "get_report_values";	
	public final static String URL_GET_REPORT_BARS = "get_report_bars";	
	public final static String URL_GET_TASK_COUNT = "get_task_count";
	public final static String URL_GET_TASK_LIST = "get_task_list";
	public final static String URL_GET_TASK_INFO = "task_info";
	public final static String URL_GET_HELP_HTML = "get_help_html";
    public final static String URL_GET_VERSION = "get_version";
	
	
	// request return string
	public final static String STATUS_SUCCESS = "0";
	public final static String STATUS_FAIL = "1";
	public final static String STATUS_NODATA = "2";
	public final static String STATUS_NOPARAM = "3";
	
	// for local test purpos
	public final static Boolean isLocalTest = true;
	
	// communication check
	public final static String RESULT	= "Result";
	public final static String STATUS	= "Status";
	public final static String ERROR	= "Error";
	
	// extra name
	public final static String EXTRA_PARAM_INDEX="index";
	public final static String EXTRA_PARAM_ISTEMP="istemp";
	public final static String EXTRA_PARAM_LOCAL="local";
	public final static String EXTRA_PARAM_BLIGHT_FLY ="isFly";
	public final static String EXTRA_PARAM_MANUAL="manual";
	public final static String EXTRA_PARAM_NAME ="name";
	public final static String EXTRA_PARAM_TYPE ="type";
	public final static String EXTRA_PARAM_POINT_TYPE ="point_type";

	public final static String EXTRA_PARAM_FORM_ID ="form_id";
	public final static String EXTRA_PARAM_REPORT_ID ="report_id";
	public final static String EXTRA_PARAM_POINT_ID ="point_id";
	public final static String EXTRA_PARAM_BLIGHT_ID ="blight_id";
	public final static String EXTRA_PARAM_DATE ="date";
	
	
	// review accept
	public final static int	  REVIEW_FIX		= 0;
	public final static int	  REVIEW_WAITING	= 0;
	public final static int	  REVIEW_NOACCEPT	= 1;
	public final static int	  REVIEW_ACCEPT		= 2;
	
	// blight type
	public final static int		BLIGHT_FLY		= 0;
	public final static int		BLIGHT_DISEASE	= 1;
	
	public final static int 	COLOR_TEXT_GREEN		= 0xFF7AB038; // green color
	public final static int 	COLOR_TEXT_GREEN_LIGHT	= 0xFFBCD79B; // green color
	public final static int 	COLOR_MARK_RED			= 0xFFE03C31; // green color
	public final static int 	COLOR_TEXT_WHITE		= 0xFFFFFFFF; // white color
	
	public final static int 	COLOR_GRAY				= 0xFFB4B4B4; // gray color
	public final static int 	COLOR_WARN				= 0xFFC54D4D; // warning red color
	
	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	public final static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");	
	
	public static final String POST_DOWN_IMAGE_WIDTH = "image_width";
	public static final String POST_DOWN_IMAGE_HEIGHT = "image_height";

    //public static String VIDEO_APP_NAME = "com.seegle.top";
    public static String VIDEO_APP_NAME = "com.zbhs.top";

    //后台服务action
    public static final String ACTION_BACK_SERVICE="com.bairuitech.callcenter.backservice";
    public static final String ACTION_BACK_CANCELSESSION="com.bairuitech.callcenter.backcancelsession";
    public static final String ACTION_BACK_EQUESTSESSION="com.bairuitech.callcenter.backrequestsession";
    public static final String ACTION_BACK_CANCELNOTIFYTION="com.bairuitech.callcenter.backcancelnotifytion";

    //回到logincativity意图
    public static final int APP_EXIT=0x33;
    public static final int AGAIGN_LOGIN=0x34;
}
