package com.bingchong.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Base64;
import com.bingchong.backend.callbacks.Google2BaiduLocationCallback;
import com.bingchong.backend.callbacks.IntegerResultCallback;
import com.bingchong.utils.MyLog;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;


import com.bingchong.Constant;
import com.bingchong.utils.AppConstants;
import com.bingchong.utils.AppDeviceUtils;
import com.google.gson.Gson;

public class WebService {

	private static final String CLASS_NAME = "WebService";
	private Context context;
	
	private final String	TAG = "WEB_SERVICE";

	DefaultHttpClient httpClient;
	HttpContext localContext;
	private String ret;

	HttpResponse response = null;
	HttpPost httpPost = null;
	HttpGet httpGet = null;

	public WebService(Context ctx) {
		context = ctx;
		httpClient = defaultHttpClient();
	}
	
	public static DefaultHttpClient defaultHttpClient() {
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(basicHttpParams, 20000);
		HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
		HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
		HttpClientParams.setRedirecting(basicHttpParams, true);
		HttpProtocolParams.setUserAgent(basicHttpParams, Constant.HTTP_USER_AGENT_PARAM);
		return new DefaultHttpClient(basicHttpParams);
	}	
	
	// The serviceName should be the name of the Service you are going to be
	// using.
	public WebService() {
		httpClient = defaultHttpClient();
		localContext = new BasicHttpContext();
	}

	// RequestType is Post
	public String postData(String functionName, String params) {
		HttpURLConnection connection = null;
		String downloadedString = "";

		if (AppDeviceUtils.isOnline(context)) {
			try {
				URL url = new URL(Constant.URL_SERVER + functionName + params);
				System.out.println(CLASS_NAME + " Final URL = " + url);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(10000);
				connection.setUseCaches(false);
				connection.connect();
				int responsecode = connection.getResponseCode();
				Log.v("responsecode", "" + responsecode);
				if (responsecode == HttpURLConnection.HTTP_OK) {
					StringBuilder content = new StringBuilder();
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(connection.getInputStream()),
							8);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						content.append(line + "\n");
					}
					bufferedReader.close();
					downloadedString = content.toString();
					System.out.println(CLASS_NAME + " downloaded String" + downloadedString);
					if (downloadedString == null || downloadedString == "" || downloadedString.length() == 0) {
						downloadedString = AppConstants.SERVER_BUSY;
					}
				}
				connection.disconnect();
			} catch (MalformedURLException ee) {
				ee.printStackTrace();
				System.out.println("malformed url");
			} catch (IOException eee) {
				System.out.println("io excp");
				eee.printStackTrace();
			}
		} else {
			System.out.println("Sorry,Please check your data package/wifi settings.");
			downloadedString = AppConstants.NO_INTERNET;
		}
		System.out.println("WebService downloaded string length" + downloadedString.length());
		return downloadedString;
	}
	
	// added by admin
	public String postData(String host, String functionName, String params) {
		HttpURLConnection connection = null;
		String downloadedString = "";

		if (AppDeviceUtils.isOnline(context)) {
			try {
				URL url = new URL(host + functionName + "?" + params);
				System.out.println(CLASS_NAME + " Final URL = " + url);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(10000);
				connection.setUseCaches(false);
				connection.connect();
				int responsecode = connection.getResponseCode();
				Log.v("responsecode", "" + responsecode);
				if (responsecode == HttpURLConnection.HTTP_OK) {
					StringBuilder content = new StringBuilder();
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(connection.getInputStream()),
							8);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						content.append(line + "\n");
					}
					bufferedReader.close();
					downloadedString = content.toString();
					System.out.println(CLASS_NAME + " downloaded String" + downloadedString);
					if (downloadedString == null || downloadedString == ""
							|| downloadedString.length() == 0) {
						downloadedString = AppConstants.SERVER_BUSY;
					}
				}
				connection.disconnect();
			} catch (MalformedURLException ee) {
				ee.printStackTrace();
				System.out.println("malformed url");
			} catch (IOException eee) {
				System.out.println("io excp");
				eee.printStackTrace();
			}
		} else {
			System.out.println("Sorry,Please check your data package/wifi settings.");
			downloadedString = AppConstants.NO_INTERNET;
		}
		System.out.println("WebService downloaded string length" + downloadedString.length());
		return downloadedString;
	}
	
	// added by admin
	public String postData(String urlStr) {
		HttpURLConnection connection = null;
		String downloadedString = "";
		
		// for test
		if(true)
			return "no connect";

		if (AppDeviceUtils.isOnline(context)) {
			try {
				URL url = new URL(urlStr);
				System.out.println(CLASS_NAME + " Final URL = " + url);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(10000);
				connection.setUseCaches(false);
				connection.connect();
				int responsecode = connection.getResponseCode();
				Log.v("responsecode", "" + responsecode);
				if (responsecode == HttpURLConnection.HTTP_OK) {
					StringBuilder content = new StringBuilder();
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(connection.getInputStream()),
							8);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						content.append(line + "\n");
					}
					bufferedReader.close();
					downloadedString = content.toString();
					System.out.println(CLASS_NAME + " downloaded String" + downloadedString);
					if (downloadedString == null || downloadedString == ""
							|| downloadedString.length() == 0) {
						downloadedString = AppConstants.SERVER_BUSY;
					}
				}
				connection.disconnect();
			} catch (MalformedURLException ee) {
				ee.printStackTrace();
				System.out.println("malformed url");
			} catch (IOException eee) {
				System.out.println("io excp");
				eee.printStackTrace();
			} catch (Exception ex) {
				System.out.println("excp");
				ex.printStackTrace();
			}
		} else {
			System.out.println("Sorry,Please check your data package/wifi settings.");
			downloadedString = AppConstants.NO_INTERNET;
		}
		System.out.println("WebService downloaded string length" + downloadedString.length());
		return downloadedString;
	}

	// RequestType is Post With NameValuePair
	public String postData(String functionName, List<NameValuePair> nameValuePair) {

		String downloadedString = "";

		if (AppDeviceUtils.isOnline(context)) {

			BufferedReader in = null;
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(Constant.URL_SERVER + functionName);

				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

				for (int i = 0; i < nameValuePair.size(); i++) {
					postParameters.add(new BasicNameValuePair(nameValuePair.get(i)
									.getName(), nameValuePair.get(i).getValue()));
				}

				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
				request.setEntity(formEntity);
				HttpResponse response = client.execute(request);
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 8);
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
				downloadedString = sb.toString();
				System.out.println("downloadedString  = " + downloadedString);
				if (downloadedString == null || downloadedString == ""
						|| downloadedString.length() == 0) {
					downloadedString = AppConstants.SERVER_BUSY;
				}
			} catch (Exception e) {
			}
		} else {
			System.out.println("Sorry,Please check your data package/wifi settings.");
			downloadedString = AppConstants.NO_INTERNET;
		}
		System.out.println("WebService downloaded string length" + downloadedString.length());
		return downloadedString;
	}

	// Use this method to do a HttpPost\WebInvoke on a Web Service
	public String webInvoke(String webServiceUrl, String methodName, Map<String, Object> params) {
		Gson gson = new Gson();
		return webInvoke(webServiceUrl, methodName, gson.toJson(params), "application/json");
	}

	public String webInvoke(String webServiceUrl, String methodName, String data, String contentType) {
		ret = null;
		Log.v("DATA", data);
		// httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
		// CookiePolicy.RFC_2109);

		httpPost = new HttpPost(webServiceUrl + methodName);
		response = null;
		StringEntity tmp = null;
		// httpPost.setHeader("User-Agent", "SET YOUR USER AGENT STRING HERE");
		// httpPost.setHeader(
		// "Accept",
		// "text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");

		if (contentType != null) {
			httpPost.setHeader("Content-Type", contentType);
		} else {
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		}

		try {
			tmp = new StringEntity(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e("GPSLogger", "HttpUtils : UnsupportedEncodingException : " + e);
		}

		httpPost.setEntity(tmp);
		Log.d("GPSLogger", webServiceUrl + "?" + data);
		try {
			response = httpClient.execute(httpPost, localContext);
			if (response != null) {
				ret = EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			Log.e("GPSLogger", "HttpUtils: " + e);
		}
		return ret;
	}
	
	// RequestType is Post With NameValuePair
	public String webGetWithJson(String url, List<NameValuePair> nameValuePair) {
		String getUrl = url;
		
		try{
			if (nameValuePair == null || nameValuePair.size() == 0) {
				HttpGet get = new HttpGet(url);
				response = httpClient.execute(get);
			} else {
				HttpPost post = new HttpPost(url);
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

				for (int i = 0; i < nameValuePair.size(); i++) {
					postParameters.add(new BasicNameValuePair(nameValuePair.get(i)
							.getName(), nameValuePair.get(i).getValue()));
				}

				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
				post.setEntity(formEntity);
				response = httpClient.execute(post);
			}
		}
		catch(Exception ex)
		{
			Log.e(TAG, ex.getMessage());
			return "";
		}
		
		// we assume that the response body contains the error message
		try {
			ret = EntityUtils.toString(response.getEntity(), "utf-8");
			int index = ret.indexOf("{");
			if(index > 0)
				ret = ret.substring(ret.indexOf("{"));
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return ret;
	}
	
	public String webGet(String url, List<NameValuePair> nameValuePair) {
		String getUrl = url;
		
		try{
			if (nameValuePair == null || nameValuePair.size() == 0) {
				HttpGet get = new HttpGet(url);
				response = httpClient.execute(get);
			} else {
				String param = "";

				for (int i = 0; i < nameValuePair.size(); i++) {
					if(i == 0)
						param = param + "?" + nameValuePair.get(i).getName() + "=" +
								nameValuePair.get(i).getValue();
					else
						param = param + "&" + nameValuePair.get(i).getName() + "=" +
								nameValuePair.get(i).getValue();
				}

				HttpGet get = new HttpGet(url + param);
				response = httpClient.execute(get);
			}
		}
		catch(Exception ex)
		{
			ret = ex.getMessage();
			return ret;
		}
		
		// we assume that the response body contains the error message
		try {
			ret = EntityUtils.toString(response.getEntity(), "utf-8");
			int index = ret.indexOf("{");
			if(index > 0)
				ret = ret.substring(ret.indexOf("{"));
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return ret;
	}	
	

	// Use this method to do a HttpGet/WebGet on the web service
	public String webGet(String webServiceUrl, String methodName, Map<String, String> params) {
		String getUrl = webServiceUrl + methodName;

		int i = 0;
		if (null != params) {
			for (Map.Entry<String, String> param : params.entrySet()) {
				if (i == 0) {
					getUrl += "?";
				} else {
					getUrl += "&";
				}

				try {
					getUrl += param.getKey() + "=" + URLEncoder.encode(param.getValue(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				i++;
			}
		}

		httpGet = new HttpGet(getUrl);
		Log.e("WebGetURL: ", getUrl);

		try {
			response = httpClient.execute(httpGet);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		// we assume that the response body contains the error message
		try {
			ret = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return ret;
	}
	
	public String webGet(String webServiceUrl, String params) {
		String getUrl = webServiceUrl + params;

		httpGet = new HttpGet(getUrl);
		Log.e("WebGetURL: ", getUrl);

		try {
			response = httpClient.execute(httpGet);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		// we assume that the response body contains the error message
		try {
			ret = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return ret;
	}	

	public static JSONObject Object(Object o) {
		try {
			return new JSONObject(new Gson().toJson(o));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public InputStream getHttpStream(String urlString) throws IOException {
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			response = httpConn.getResponseCode();

			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (Exception e) {
			throw new IOException("Error connecting");
		} // end try-catch

		return in;
	}

	public void clearCookies() {
		httpClient.getCookieStore().clear();
	}

	public void abort() {
		try {
			if (httpClient != null) {
				System.out.println("Abort.");
				httpPost.abort();
			}
		} catch (Exception e) {
			System.out.println("Your App Name Here" + e);
		}
	}

    private Google2BaiduLocationCallback google2BaiduLocationCallback = null;
    private IntegerResultCallback trackCallback = null;

    private final static String  OUT_GET_BAIDU_LOCATION_ERROR = "error";
    private final static String  OUT_GET_BAIDU_LOCATION_X = "x";
    private final static String  OUT_GET_BAIDU_LOCATION_Y = "y";
    public final static int  STATUS_SUCCESS = 1;
    public final static int  STATUS_FAILURE = 0;
    public final static int  STATUS_TIMEOUT = 2;
    public final static int  STATUS_PARSE_ERR = 3;

    private final static int  CONNECTION_TIMEOUT = 4  * 1000; // 4 Seconds
    private final static String  IN_GET_BAIDU_LOCATION_FROM = "from";
    private final static String  IN_GET_BAIDU_LOCATION_TO = "to";
    private final static String  IN_GET_BAIDU_LOCATION_X = "x";
    private final static String  IN_GET_BAIDU_LOCATION_Y = "y";

    private final static String  REQ_BAIDUMAP_PREFIX = "http://api.map.baidu.com/";
    private final static String  REQ_GET_BAIDU_LOCATION = REQ_BAIDUMAP_PREFIX + "ag/coord/convert";

    private final static String  REQ_PREFIX = Constant.URL_SERVER;
    //  private final static String  REQ_PREFIX = "http://58.18.162.74/Service/";
    private final static String  REQ_ACE_TYPE_INFO = REQ_PREFIX + "aceTypeInfo.ashx";
    private final static String  REQ_ACTIVITY_REPORT = REQ_PREFIX + "activityReport.ashx";
    private final static String  REQ_ATTENDANCE = REQ_PREFIX + "attendance.ashx";
    private final static String  REQ_CHANGE_PASS = REQ_PREFIX + "changePass.ashx";
    private final static String  REQ_CUSTOMER_VISIT = REQ_PREFIX + "customerVisit.ashx";
    private final static String  REQ_GET_AGENCY = REQ_PREFIX + "getAgency.ashx";
    private final static String  REQ_GET_AGENCY_LIST = REQ_PREFIX + "getAgencyList.ashx";
    private final static String  REQ_GET_AREAS = REQ_PREFIX + "getAreas.ashx";
    private final static String  REQ_GET_BRAND = REQ_PREFIX + "getBrand.ashx";
    private final static String  REQ_GET_CITY_LIST = REQ_PREFIX + "getCityList.ashx";
    private final static String  REQ_GET_DEPARTMENT = REQ_PREFIX + "getDepartment.ashx";
    private final static String  REQ_GET_INFORMS = REQ_PREFIX + "getInforms.ashx";
    private final static String  REQ_GET_PAPER = REQ_PREFIX + "getPaper.ashx";
    private final static String  REQ_GET_SIGN_STATUS = REQ_PREFIX + "getSignStatus.ashx";
    private final static String  REQ_GET_SIGN_TIME = REQ_PREFIX + "getSignTime.ashx";
    private final static String  REQ_GET_TIME = REQ_PREFIX + "getTime.ashx";
    private final static String  REQ_GET_VILLAGE_LIST = REQ_PREFIX + "getVillageList.ashx";
    private final static String  REQ_GUIGE_INFO = REQ_PREFIX + "guiGeInfo.ashx";
    private final static String  REQ_LOGIN_SYSTEM = REQ_PREFIX + "loginSystem.ashx";
    private final static String  REQ_MOBILE_INFO = REQ_PREFIX + "mobileInfo.ashx";
    private final static String  REQ_NEW_STORE = REQ_PREFIX + "newStore.ashx";
    private final static String  REQ_POP_REPORT = REQ_PREFIX + "popReport.ashx";
    private final static String  REQ_PRODUCT_INFO = REQ_PREFIX + "productInfo.ashx";
    private final static String  REQ_QUESTION_HANDLE = REQ_PREFIX + "questionHandle.ashx";
    private final static String  REQ_ROUTING_INSPECTION = REQ_PREFIX + "routingInspection.ashx";
    private final static String  REQ_SET_INFORM = REQ_PREFIX + "setInform.ashx";
    private final static String  REQ_SALE_REPORT = REQ_PREFIX + "saleReport.ashx";
    private final static String  REQ_SET_PAPER = REQ_PREFIX + "setPaper.ashx";
    private final static String  REQ_STORE_INFO = REQ_PREFIX + "storeInfo.ashx";
    private final static String  REQ_STORE_TRACK = REQ_PREFIX + "storeTrack.ashx";
    private final static String  REQ_TRACK = REQ_PREFIX + "upload_watcher_track";
    private final static String  REQ_VIE_REPORT = REQ_PREFIX + "vieReport.ashx";
    private final static String  REQ_VISIT_HOUSE_INFO = REQ_PREFIX + "visitHouseInfo.ashx";
    private final static String  REQ_WORK_LOG = REQ_PREFIX + "workLog.ashx";
    private final static String  REQ_ZERO_SALE_REPORT = REQ_PREFIX + "zeroSaleReport.ashx";
    private final static String  REQ_GET_VERSION = REQ_PREFIX + "getVersion.ashx";

    private final static String  IN_USERID = "user_id";
    private final static String  IN_USERNAME = "user_name";
    private final static String  OUT_RET = "Ret";
    public final static int  OUT_RET_SUCCESS = 1;
    public final static int  OUT_RET_FAILURE = 0;
    private final static String  OUT_ARRAY_SIZE = "Result";
    private final static String  OUT_RESULT = "Result";
    private final static String  IN_TRACK_LONGITUDE = "longitude";
    private final static String  IN_TRACK_LATITUDE = "latitude";

    private JsonHttpResponseHandler getBaiduLocationHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(JSONObject response) {
            super.onSuccess(response);

            int  ret = -1;
            double  x = 0.0, y = 0.0;

            try {
                ret = response.getInt(OUT_GET_BAIDU_LOCATION_ERROR);
            } catch ( JSONException e ) {
                if ( google2BaiduLocationCallback != null )
                    google2BaiduLocationCallback.callback ( STATUS_PARSE_ERR, 0.0, 0.0 );
                return;
            }

            if ( ret == 0 ) { // success

                try {
                    String  xstr, ystr, xstr1, ystr1, xstr2, ystr2, xstr4, ystr4, xstr8, ystr8, xstr16, ystr16;
                    xstr = new String(Base64.decode(response.getString(OUT_GET_BAIDU_LOCATION_X), Base64.DEFAULT));
                    ystr = new String(Base64.decode(response.getString(OUT_GET_BAIDU_LOCATION_Y), Base64.DEFAULT));

                    x = Double.parseDouble(xstr);
                    y = Double.parseDouble(ystr);
                } catch ( JSONException e ) {
                    if ( google2BaiduLocationCallback != null )
                        google2BaiduLocationCallback.callback ( STATUS_PARSE_ERR, 0.0, 0.0 );
                    return;
                }

                if ( google2BaiduLocationCallback != null )
                    google2BaiduLocationCallback.callback ( STATUS_SUCCESS, x, y );
            } else {
                google2BaiduLocationCallback.callback ( STATUS_FAILURE, 0.0, 0.0 );
            }
        }

        @Override
        public void onFailure(Throwable error) {
            super.onFailure(error);

            if ( google2BaiduLocationCallback != null )
                google2BaiduLocationCallback.callback ( STATUS_FAILURE, 0.0, 0.0 );
        }

        @Override
        public void onFailure(Throwable e, JSONObject errorResponse) {
            super.onFailure(e, errorResponse);

            if ( google2BaiduLocationCallback != null ) {
                if ( errorResponse != null ) {
                    google2BaiduLocationCallback.callback ( STATUS_FAILURE, 0.0, 0.0 );
                } else {
                    google2BaiduLocationCallback.callback ( STATUS_TIMEOUT, 0.0, 0.0 );
                }
            }
        }
    };

    public void Google2BaiduLocationProxy ( double x, double y, Google2BaiduLocationCallback google2BaiduLocationCallback ) {
        this.google2BaiduLocationCallback = google2BaiduLocationCallback;

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(CONNECTION_TIMEOUT);

        RequestParams requestParams = new RequestParams();
        requestParams.put(IN_GET_BAIDU_LOCATION_FROM, "2");
        requestParams.put(IN_GET_BAIDU_LOCATION_TO, "4");
        requestParams.put(IN_GET_BAIDU_LOCATION_X, String.format("%.12f",x));
        requestParams.put(IN_GET_BAIDU_LOCATION_Y, String.format("%.12f",y));

        client.get(REQ_GET_BAIDU_LOCATION, requestParams, getBaiduLocationHandler);
    }

    private JsonHttpResponseHandler trackHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(JSONObject response) {
            super.onSuccess(response);

            MyLog.d(TAG, "Return: " + response.toString());

            int  ret = -1;

            try {
                ret = response.getInt(OUT_RET);
            } catch ( JSONException e ) {
                if ( trackCallback != null )
                    trackCallback.callback ( ret, STATUS_FAILURE );
                MyLog.e(TAG,"failed to get a value in trackHandler()");
                return;
            }

            if ( trackCallback != null )
                trackCallback.callback ( ret, STATUS_SUCCESS );
        }

        @Override
        public void onFailure(Throwable error) {
            super.onFailure(error);

            if ( trackCallback != null ) {
                trackCallback.callback ( -1, STATUS_PARSE_ERR );
            }
        }

        @Override
        public void onFailure(Throwable e, JSONObject errorResponse) {
            super.onFailure(e, errorResponse);

            if ( trackCallback != null ) {
                if ( errorResponse != null ) {
                    trackCallback.callback ( -1, STATUS_FAILURE );
                } else {
                    trackCallback.callback ( -1, STATUS_TIMEOUT );
                }
            }
        }
    };

    public void TrackProxy ( long userid, String username, final double longitude, final double latitude, IntegerResultCallback trackCallback ) {
        this.trackCallback = trackCallback;

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(CONNECTION_TIMEOUT);

        RequestParams requestParams = new RequestParams();
        requestParams.put(IN_USERID, String.valueOf(userid));
        requestParams.put(IN_USERNAME, String.valueOf(username));
        requestParams.put(IN_TRACK_LONGITUDE, String.format("%.12f", longitude));
        requestParams.put(IN_TRACK_LATITUDE, String.format("%.12f",latitude));

        client.post(REQ_TRACK, requestParams, trackHandler);

        String logmsg = String.format("Service call: %s \n\t%s:%d\n\t%s:%.8f\n\t%s:%.8f",
                REQ_TRACK, IN_USERID, userid,
                IN_TRACK_LONGITUDE, longitude,
                IN_TRACK_LATITUDE, latitude);
        MyLog.d(TAG, logmsg);

        /*
        if ( ServiceBaiduMap.TEST_MODE == true ) {
            ActivityAttendReport  aar = (ActivityAttendReport) EmpWorkMgrApp.attendActivity;
            if ( aar == null )
                return;
            aar.runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(EmpWorkMgrApp.attendActivity, String.format("%.6f %.6f", latitude, longitude), Toast.LENGTH_SHORT).show();
                }
            });
        }
        */
    }
}
