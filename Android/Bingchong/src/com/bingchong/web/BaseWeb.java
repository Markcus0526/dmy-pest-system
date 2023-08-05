package com.bingchong.web;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import android.util.Log;



public class BaseWeb {
	//public static final String API_URL = "http://120.193.233.26:9999/index.asmx";
	public static final String API_URL = "http://120.193.233.26:1234/index.asmx";
	public static final String UPDATE_URL = "http://www.gcloudinfo.com/down";
	public static final String NAMESPACE = "http://tempuri.org/";
	public static final String UPDATE_APKNAME = "/zjspyy1.apk";
	
	/**
	 * @description 连接服务器
	 * @param methodName 方法名
	 * @param params 参数
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static SoapObject link(String methodName, Object... params) throws IOException, XmlPullParserException{
		//创建HttpTransportSE对象,通过HttpTransportSE类的构造方法可以指定WebService的url 
		 
		HttpTransportSE transport = new HttpTransportSE(API_URL); 
		transport.debug = true;  
		 
		//指定WebService的命名空间和函数名
		SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
		
		if (params.length % 2 != 0) {
			throw new IllegalArgumentException("Invalid parameters count: "
					+ params.length);
		}
		for(int i= 0 ; i < params.length ; i+=2){
			//设置调用方法参数的值 
			soapObject.addProperty((String) params[i], params[i+1]); //sessionID
		}
		//soapObject.addProperty("pwd", userPassword); //cds是需要传递的对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.encodingStyle="UTF-8";
		envelope.setOutputSoapObject(soapObject);
		 
		//使用call方法调用WebService方法
		transport.call(NAMESPACE + methodName, envelope);
		 
		if (envelope.bodyIn instanceof SoapFault) {
		    String sb= ((SoapFault) envelope.bodyIn).faultstring;
		    Log.i("sb == ", sb);
		} else {
			SoapObject detail =(SoapObject) envelope.bodyIn;
			 //Log.d("detail", detail.getProperty(0).toString());
			 return detail;
		}
		return null;
	}
	
}
