package com.bingchong.net;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.bingchong.Constant;

import android.util.Log;

public class HttpApi {
	private static final String LOG = "bingchong.HttpApi";
	private static CookieStore cookieStore = null;

	public static DefaultHttpClient defaultHttpClient() {
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(basicHttpParams, 20000);
		HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
		HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
		HttpClientParams.setRedirecting(basicHttpParams, true);
		HttpProtocolParams.setUserAgent(basicHttpParams, Constant.HTTP_USER_AGENT_PARAM);
		return new DefaultHttpClient(basicHttpParams);
	}
	
	public static String sendXml(String xmldata, String url, String xml) {
		HttpParams params = null;
		if (xml != null) {
			params = new HttpParams();
			params.addParam(xmldata, xml);
		}

		return HttpApi.sendRequest(url, params, null);
	}
	public static String sendRequestWithJson(String serverUrl, HttpParams params,
			int[] statusCode)
	{
		String ret = "";
		try{
			ret = sendRequest(serverUrl, params, statusCode);
			
			int index = ret.indexOf("{");
			if(index > 0)
				ret = ret.substring(ret.indexOf("{"));
		}
		catch(Exception ex){
			ret = "";
		}
			
		return ret;
	}

	public static String sendRequest(String serverUrl, HttpParams params,
			int[] statusCode) {
		HttpPost httpRequest = new HttpPost(serverUrl);
		int status = -1;

		try {
			/** Makes an HTTP request request */
			if (params != null) {
				httpRequest.setEntity(new UrlEncodedFormEntity(params
						.getParams(), HTTP.UTF_8));
			}

			/** Create an HTTP client */
			//DefaultHttpClient httpClient = new DefaultHttpClient();
			DefaultHttpClient httpClient = defaultHttpClient();

			/** Set Cookie information */
			if (cookieStore != null) {
				httpClient.setCookieStore(cookieStore);
			}

			/** Gets the HTTP response response */
			HttpResponse httpresponse = httpClient.execute(httpRequest);

			status = httpresponse.getStatusLine().getStatusCode();

			/** If the status code 200 response successfully */
			Log.v(LOG, new Integer(status).toString());
			if (status == 200) {
				/** Remove the response string */
				String strResponse = EntityUtils.toString(
						httpresponse.getEntity(), HTTP.UTF_8);
				//Log.v(LOG, strResponse);
				if (statusCode != null)
					statusCode[0] = status;

				cookieStore = httpClient.getCookieStore();
				return strResponse.trim();
			}
		} catch (Exception e) {
			Log.v(LOG, "send request error");
			status = -1;
		}

		if (statusCode != null)
			statusCode[0] = status;
		return null;
	}
}
