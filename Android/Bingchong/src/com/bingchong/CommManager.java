package com.bingchong;

import android.content.Entity;
import com.bingchong.net.AsyncHttpClient;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.net.RequestParams;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/*
 * Created with IntelliJ IDEA.
 * User: KHM
 * Date: 13-12-28
 * Time: 下午22:12
 * To change this template use File | Settings | File Templates.
*/
public class CommManager {
	public static int getTimeOut() {
		return 10 * 1000;
	}

	//public static String getServiceBaseUrl() { return "http://192.168.1.41:20211/Service.svc/"; }
    //public static String getServiceBaseUrl() { return "http://120.193.233.26:10112/Service.svc/"; }
    //public static String getServiceBaseUrl() { return "http://218.25.54.56:10112/Service.svc/"; }
    public static String getServiceBaseUrl() { return "http://222.74.122.2:8012/Service.svc/"; }

	public static void calcDistWithBaidu(double lat1,
										 double lng1,
										 double lat2,
										 double lng2,
										 String org_city,
										 String dst_city,
										 AsyncHttpResponseHandler handler) {
		String url = "http://api.map.baidu.com/direction/v1";

		try {
			RequestParams params = new RequestParams();

			params.put("mode", "driving");
			params.put("output", "json");
			params.put("origin", "" + lat1 + "," + lng1);
			params.put("destination", "" + lat2 + "," + lng2);
			params.put("origin_region", org_city);
			params.put("destination_region", dst_city);
			params.put("ak", AppCommon.getBaiduKey());

			AsyncHttpClient client = new AsyncHttpClient();
			client.setTimeout(getTimeOut());
			client.get(url, params, handler);
		} catch (Exception ex) {
			ex.printStackTrace();

			if (handler != null)
				handler.onFailure(null, ex.getMessage());
		}
	}
	////////////////////////////////////////////////////////////////////////////////////

	public static void loginUser(String username,
								 String password,
								 String devtoken,
								 AsyncHttpResponseHandler handler)
	{
		String url = getServiceBaseUrl() + "loginUser";

		try {
			RequestParams params = new RequestParams();

			params.put("name", username);
			params.put("password", password);
			params.put("devtoken", devtoken);

			AsyncHttpClient client = new AsyncHttpClient();
			client.setTimeout(getTimeOut());
			client.get(url, params, handler);
		} catch (Exception ex) {
			ex.printStackTrace();

			if (handler != null)
				handler.onFailure(null, ex.getMessage());
		}
	}

    public static void getBlights(long userid,
                                 int kind,
                                 String search,
                                 AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getBlights";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("kind", kind + "");
            params.put("search", search);
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getBlightInfo(long userid,
                                  long blight_id,
                                  AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getBlightInfo";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("blight_id", blight_id + "");
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getExtraTasks(long admin_id,
                                     long watcher_id,
                                   AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getExtraTasks";

        try {
            RequestParams params = new RequestParams();

            params.put("admin_id", admin_id + "");
            params.put("watcher_id", watcher_id + "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getTaskCount(long userid,
                                    int point_type,
                                    String start_time,
                                    String end_time,
                                    AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getTaskCount";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("point_type", point_type + "");
            params.put("start_time", start_time);
            params.put("end_time", end_time);
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getTaskList(long userid,
                                   int type,
                                   int point_type,
                                   String start_time,
                                   String end_time,
                                   AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getTaskList";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("type", type + "");
            params.put("point_type", point_type + "");
            params.put("start_time", start_time);
            params.put("end_time", end_time);
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getTaskInfo(long task_detail_id,
                                   long point_id,
                                   String stime,
                                   boolean check_reports,
                                   long report_id,
                                   AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getTaskInfo";

        try {
            RequestParams params = new RequestParams();

            params.put("task_detail_id", task_detail_id + "");
            params.put("point_id", point_id + "");
            params.put("stime", stime);
            params.put("check_reports", check_reports + "");
            params.put("report_id", report_id + "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void uploadExtraTask(long admin_id,
                                       String name,
                                       long watcher_id,
                                       String notice_date,
                                       String report_date,
                                       String note,
                                       AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "uploadExtraTask";

        try {
            RequestParams params = new RequestParams();

            params.put("admin_id", admin_id + "");
            params.put("name", name);
            params.put("watcher_id", watcher_id + "");
            params.put("notice_date", notice_date);
            params.put("report_date", report_date);
            params.put("note", note);

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void updateExtraTask(long admin_id,
                                       long watcher_id,
                                       long task_id,
                                       int status,
                                       AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "updateExtraTask";

        try {
            JSONObject params = new JSONObject();

            params.put("admin_id", admin_id + "");
            params.put("watcher_id", watcher_id + "");
            params.put("task_id", task_id + "");
            params.put("status", status + "");

            StringEntity entity = new StringEntity(params.toString(), "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.post(null, url, entity, "application/json", handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getUsers(long admin_id,
                                int level,
                                long sheng_id,
                                long shi_id,
                                long xian_id,
                                String search,
                                AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getUsers";

        try {
            RequestParams params = new RequestParams();

            params.put("admin_id", admin_id + "");
            params.put("level", level + "");
            params.put("sheng_id", sheng_id + "");
            params.put("shi_id", shi_id + "");
            params.put("xian_id", xian_id + "");
            params.put("search", search);

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getUserInfo(long userid,
                                AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getUserInfo";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void updateUserInfo(long userid,
                                      String phone,
                                      String place,
                                      String job,
                                      String photo,
                                      String point_list,
                                      AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "updateUserInfo";

        try {
            JSONObject params = new JSONObject();

            params.put("userid", userid + "");
            params.put("phone", phone);
            params.put("place", place);
            params.put("job", job);
            params.put("photo", photo);
            params.put("point_list", point_list);
            params.put("token", AppCommon.loadUserToken());

            StringEntity entity = new StringEntity(params.toString(), "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.post(null, url, entity, "application/json", handler);
            //client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getPoints(long userid,
                                long sheng_id,
                                long shi_id,
                                long xian_id,
                                String search,
                                int type,
                                int level,
                                String time,
                                AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getPoints";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("sheng_id", sheng_id + "");
            params.put("shi_id", shi_id + "");
            params.put("xian_id", xian_id + "");
            params.put("type", type + "");
            params.put("level", level + "");
            params.put("search", search);
            params.put("time", time);
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getPointInfo(long userid,
                                     long point_id,
                                     AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getPointInfo";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("point_id", point_id + "");
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getShengs(AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getShengs";

        try {
            RequestParams params = new RequestParams();

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getShis(long sheng_id,
                               AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getShis";

        try {
            RequestParams params = new RequestParams();

            params.put("sheng_id", sheng_id + "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getXians(long shi_id,
                               AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getXians";

        try {
            RequestParams params = new RequestParams();

            params.put("shi_id", shi_id + "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void setReports(long userid,
                                  long form_id,
                                  long point_id,
                                  long blight_id,
                                  String photo,
                                  String watch_time,
                                  String fields,
                                  AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "setReports";

        try {
            JSONObject params = new JSONObject();

            params.put("userid", userid + "");
            params.put("form_id", form_id + "");
            params.put("point_id", point_id + "");
            params.put("blight_id", blight_id + "");
            params.put("photo", photo + "");
            params.put("watch_time", watch_time + "");
            params.put("fields", fields + "");
            params.put("token", AppCommon.loadUserToken());

            StringEntity entity = new StringEntity(params.toString(), "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.post(null, url, entity, "application/json", handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getReportLine(long userid,
                                     long sheng_id,
                                     long shi_id,
                                     long xian_id,
                                     long blight_id,
                                     long form_id,
                                     long point_id,
                                     String start_time,
                                     String end_time,
                                     long field_id,
                                     AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getReportLine";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("sheng_id", sheng_id + "");
            params.put("shi_id", shi_id + "");
            params.put("xian_id", xian_id + "");
            params.put("blight_id", blight_id + "");
            params.put("form_id", form_id + "");
            params.put("point_id", point_id + "");
            params.put("start_time", start_time + "");
            params.put("end_time", end_time + "");
            params.put("field_id", field_id + "");
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getReportBar(long userid,
                                     long sheng_id,
                                     long shi_id,
                                     long xian_id,
                                     long blight_id,
                                     long form_id,
                                     String time,
                                     long field_id,
                                     AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getReportBar";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("sheng_id", sheng_id + "");
            params.put("shi_id", shi_id + "");
            params.put("xian_id", xian_id + "");
            params.put("blight_id", blight_id + "");
            params.put("form_id", form_id + "");
            params.put("time", time + "");
            params.put("field_id", field_id + "");
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getReportHistory(long userid,
                                        long point_id,
                                        String start_time,
                                        String end_time,
                                        AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getReportHistory";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("point_id", point_id + "");
            params.put("start_time", start_time + "");
            params.put("end_time", end_time + "");
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getReportInfo(long userid,
                                     long report_id,
                                     AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getReportInfo";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", userid + "");
            params.put("report_id", report_id + "");
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getTempBlights(long admin_id,
                                      long watcher_id,
                                      String search,
                                      AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getTempBlights";

        try {
            RequestParams params = new RequestParams();

            params.put("admin_id", admin_id + "");
            params.put("watcher_id", watcher_id + "");
            params.put("search", search + "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getTempBlightInfo(long blight_id,
                                      AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getTempBlightInfo";

        try {
            RequestParams params = new RequestParams();

            params.put("blight_id", blight_id + "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void uploadTempBlight(long watcher_id,
                                        String name,
                                        int kind,
                                        String longitude,
                                        String latitude,
                                        String info1,
                                        String info2,
                                        String info3,
                                        String note,
                                        String photo,
                                        AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "uploadTempBlight";

        try {
            JSONObject params = new JSONObject();

            params.put("watcher_id", watcher_id + "");
            params.put("name", name + "");
            params.put("kind", kind + "");
            params.put("longitude", longitude + "");
            params.put("latitude", latitude + "");
            params.put("info1", info1 + "");
            params.put("info2", info2 + "");
            params.put("info3", info3 + "");
            params.put("note", note + "");
            params.put("photo", photo + "");

            StringEntity entity = new StringEntity(params.toString(), "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.post(null, url, entity, "application/json", handler);

        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void updateTempBlight(long admin_id,
                                        long blight_id,
                                        int status,
                                        AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "updateTempBlight";

        try {
            JSONObject params = new JSONObject();

            params.put("admin_id", admin_id + "");
            params.put("blight_id", blight_id + "");
            params.put("status", status + "");

            StringEntity entity = new StringEntity(params.toString(), "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.post(null, url, entity, "application/json", handler);

        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void uploadWatcherTrack(long userid,
                                        String longitude,
                                        String latitude,
                                        AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "uploadWatcherTrack";

        try {
            JSONObject params = new JSONObject();

            params.put("userid", userid + "");
            params.put("longitude", longitude);
            params.put("latitude", latitude);
            params.put("token", AppCommon.loadUserToken());

            StringEntity entity = new StringEntity(params.toString(), "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.post(null, url, entity, "application/json", handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getWatcherTracks(long sheng_id,
                                        long shi_id,
                                        long xian_id,
                                        String date,
                                        AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getWatcherTracks";

        try {
            RequestParams params = new RequestParams();

            params.put("sheng_id", sheng_id + "");
            params.put("shi_id", shi_id + "");
            params.put("xian_id", xian_id + "");
            params.put("date", date);

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getForms(long blight_id,
                                AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getForms";

        try {
            RequestParams params = new RequestParams();

            params.put("blight_id", blight_id + "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getFields(long form_id,
                                AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "getFields";

        try {
            RequestParams params = new RequestParams();

            params.put("form_id", form_id + "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }



    public static void getOpinionKind(AsyncHttpResponseHandler handler) {
        String url = getServiceBaseUrl() + "getOpinionKind";

        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, null, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }



    public static void uploadOpinion(long kind_id,
                                     long user_id,
                                     String title,
                                     String content,
                                     AsyncHttpResponseHandler handler) {
        String url = getServiceBaseUrl() + "uploadOpinion";

        try {
            JSONObject params = new JSONObject();

            params.put("kind_id", kind_id);
            params.put("user_id", "" + user_id);
            params.put("title", title);
            params.put("content", content);

            StringEntity entity = new StringEntity(params.toString(), "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.post(null, url, entity, "application/json", handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }


    public static void getHelps(int type,
                                long userid,
                                AsyncHttpResponseHandler handler) {
        String url = getServiceBaseUrl() + "getHelps";

        try {
            RequestParams params = new RequestParams();

            params.put("type", "" + type);
            params.put("userid", "" + userid);

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }



    public static void getHelpInfo(int type,
                                   long help_id,
                                   AsyncHttpResponseHandler handler) {
        String url = getServiceBaseUrl() + "getHelpInfo";

        try {
            RequestParams params = new RequestParams();

            params.put("type", "" + type);
            params.put("help_id", "" + help_id);

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getNotices(long userid,
                                   int year,
                                   int type,
                                   AsyncHttpResponseHandler handler) {

        String url = getServiceBaseUrl() + "getNotices";

        try {
            RequestParams params = new RequestParams();

            params.put("userid", "" + userid);
            params.put("year", "" + year);
            params.put("type", "" + type);
            params.put("token", AppCommon.loadUserToken());

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getNoticeInfo(long notice_id,
                                  AsyncHttpResponseHandler handler) {

        String url = getServiceBaseUrl() + "getNoticeInfo";

        try {
            RequestParams params = new RequestParams();

            params.put("notice_id", "" + notice_id);

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void publishNotice(String title,
                                  String serial,
                                  long user_id,
                                  String content,
                                  AsyncHttpResponseHandler handler)
    {
        String url = getServiceBaseUrl() + "publishNotice";

        try {
            JSONObject params = new JSONObject();

            params.put("title", title + "");
            params.put("serial", serial + "");
            params.put("user_id", user_id + "");
            params.put("content", content + "");

            StringEntity entity = new StringEntity(params.toString(), "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.post(null, url, entity, "application/json", handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getUserPhotos(AsyncHttpResponseHandler handler) {

        String url = getServiceBaseUrl() + "getUserPhotos";

        try {
            RequestParams params = new RequestParams();

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(url, params, handler);
        } catch (Exception ex) {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }
}
