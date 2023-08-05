package com.bingchong;

/**
 * Created by KimHM on 2015/1/11.
 */
public class ConstMgr {
	public static String	gRetCode = "retcode";
	public static String	gRetMsg = "retmsg";
	public static String	gRetData = "retdata";

	public static int		SVC_ERR_NONE 															= 0;
	public static int		SVC_ERR_INTERNAL_EXCEPTION 									= -1;
	public static int		SVC_ERR_INVALID_USER 												= -2;
	public static int		SVC_ERR_USERNAME_CONFLICT 									= -3;
	public static int		SVC_ERR_USER_NAMEPORPASSWORD_NOMATCH 			= -4;
	public static int		SVC_ERR_USER_NOT_ACCEPTED 									= -5;
	public static int		SVC_ERR_INVALID_PARAM 											= -6;
	public static int		SVC_ERR_INSUFFICIENT_PERMISSION 							= -7;
	public static int		SVC_ERR_NO_CONTENT 												= -8;
	public static int		SVC_ERR_DEVTOKEN_MISMATCH 								= -9;
	public static int		SVC_ERR_DEVTOKEN_NOREGISTERED 							= -10;
	public static int		SVC_EXCEED_MONEY 													= -11;
	public static int		SVC_INVITECODE_NOMATCH 										= -12;
}
