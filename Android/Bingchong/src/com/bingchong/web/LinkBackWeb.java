package com.bingchong.web;

import java.io.IOException;
import java.util.ArrayList;

import android.provider.ContactsContract;
import com.bingchong.Global;
import com.bingchong.bean.UserBean;
import com.bingchong.db.DataMgr;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Log;

import com.bingchong.Config;
import com.bingchong.bean.Experts;
import com.bingchong.bean.VersionDescription;

/**
 * @class：LinkBackWeb.java 
 * @description: 连接后台服务器
 * @author: 金铁钢
 * 
 * @history:
 *   	    日期  	     版本             担当者         修改内容
 *   2014-12-20  1.0    金铁钢            初版
 */
public class LinkBackWeb extends BaseWeb{
	
	/**
	 * @description 用户登录
	 * @param userName
	 * @param password
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static boolean login(String userName , String password) throws  XmlPullParserException, IOException{
		boolean b = false;
		String result = link("login","user",userName, "pwd",password , "key" ,"lvyunxinxi2014").getProperty(0).toString();
		b = Boolean.parseBoolean(result);
		return b;
	}
	
	/**
	 * @description 获取专家列表
	 * @throws IOException
	 * @throws XmlPullParserException 
	 */
	public static void getExpertsList() throws IOException, XmlPullParserException{
		//SoapObject detail = link("userlist" , "key" ,"lvyunxinxi2014");
		SoapObject detail = link("BCUserlist" , "key" ,"lvyunxinxi2014");

		ArrayList<UserBean> bcUserList = DataMgr.getUsersFromDB();
		if(bcUserList == null || bcUserList.size() <= 0)
			return;

		if(detail != null){
			Log.e("getPropertyCount" , detail.getPropertyCount()+"");
			SoapObject children = (SoapObject)detail.getProperty(0);
			if(detail != null && children.getPropertyCount() > 0){
				for(int i = 0 ; i < children.getPropertyCount(); i++){
					//获取SoapObject对象
					SoapObject child = (SoapObject)children.getProperty(i);
					Experts experts = new Experts();

					String strUserName = "";
					String strId = "";

					try {
						if(child.getProperty("username") != null)
							strUserName = child.getProperty("username").toString();
						if(child.getProperty("id") != null)
							strId = child.getProperty("id").toString();


						UserBean userBean = null;
						for(UserBean obj : bcUserList) {
							if(obj.name.equals(strUserName)) {
								userBean = obj;
								break;
							}
						}

						if(userBean == null)
							continue;

						if(!Global.checkIsSameLevel(userBean))
							continue;

						if(userBean.imgurl.length() <= 0)
							userBean.imgurl = "http://218.25.54.28:10230/Content/img/head/3.gif";
						experts.setExpertImg(userBean.imgurl);
						experts.setId(Integer.parseInt(strId));
						experts.setUserName(strUserName);
						experts.setExpertName(strUserName);
						experts.setExpertTypeId(1);
						experts.setPhoneNum(userBean.phone);
						//experts.setExpertImg("http://218.25.54.28:10230/Content/img/head/3.gif");

					}
					catch (Exception ex) {
						ex.printStackTrace();
					}

					/*
					String strUserName = child.getProperty(0).toString();
					String strId = child.getProperty(1).toString();
					String strExpertName = child.getProperty(2).toString();
					String strExpertUnit = child.getProperty(3).toString();
					String strExpertDescription = child.getProperty(4).toString();
					String strExpertResionName = child.getProperty(5).toString();
					String strExpertTypeName = child.getProperty(6).toString();
					String strExpertTypeId = child.getProperty(7).toString();
					String strExpertRegionId = child.getProperty(8).toString();
					String strExpertImg = child.getProperty(9).toString();
					String strExpertSex = child.getProperty(10).toString();
					String strBirthday = child.getProperty(12).toString();
					String strAddress = child.getProperty(13).toString();
					String strPhone = child.getProperty(14).toString();

					int id = Integer.parseInt(strId);
					int expertTypeId = Integer.parseInt(strExpertTypeId);
					int expertRegionId = Integer.parseInt(strExpertRegionId);
					experts.setId(id);
					experts.setUserName(strUserName);
					experts.setExpertName(strExpertName);
					experts.setExpertUnit(strExpertUnit);
					experts.setExpertDescription(strExpertDescription);
					experts.setExpertTypeName(strExpertTypeName);
					experts.setExpertTypeId(expertTypeId);
					experts.setExpertRegionId(expertRegionId);
					experts.setExpertRegionName(strExpertResionName);
					experts.setPhoneNum("tel:" + strPhone);
					experts.setBirthday(strBirthday);
					experts.setAddress(strAddress);
					Log.e("strExpertImg",strExpertImg + "");
					experts.setExpertImg(strExpertImg);
					experts.setExpertSex(strExpertSex);
					*/

					Config.expertsList.add(experts);
				}
			}
		}
	}
	
	/**
	 * @description 根据专家类型ID，获取专家类型信息
	 * @param typeId
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static void getType(int typeId) throws IOException, XmlPullParserException{
		SoapObject detail = link("GetType","id" ,typeId);
		if(detail != null && detail.getPropertyCount() > 0){
			String strId = detail.getProperty(0).toString();
			String strTypeName = detail.getProperty(1).toString();
			String strCodeId = detail.getProperty(2).toString();
			
			Log.e("strId == ", strId);
			Log.e("strTypeName == ", strTypeName);
			Log.e("strCodeId == ", strCodeId);
		}
	}
	
	/**
	 * @description 根据地区ID，获取地区信息
	 * @param regionId
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static void getRegion(int regionId) throws IOException, XmlPullParserException{
		SoapObject detail = link("GetRegion","id" ,regionId);
		if(detail != null && detail.getPropertyCount() > 0){
			String strId = detail.getProperty(0).toString();
			String strRegionName = detail.getProperty(1).toString();
			String strParentId = detail.getProperty(2).toString();
			String strSortOrder = detail.getProperty(3).toString();
			
			Log.e("strId == ", strId);
			Log.e("strRegionName == ", strRegionName);
			Log.e("strParentId == ", strParentId);
			Log.e("strSortOrder == ", strSortOrder);
		}
	}
	
	/**
	 * @description 获取服务器端最新版本
	 * @param context
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static VersionDescription getServerVerCode(Context context) throws IOException, XmlPullParserException{
		VersionDescription version = null;
		int newVerCode = Config.getVerCode(context);
		SoapObject detail = link("GetServerVerCode", "type" ,"2" , "key" ,"lvyunxinxi2014");
		if(detail != null && detail.getPropertyCount() > 0){
			SoapObject detail1 = (SoapObject) detail.getProperty(0);
			version = new VersionDescription();
			String strId= detail1.getProperty(0).toString();
			String strNewVerName = detail1.getProperty(1).toString();
			String strNewVerCode = detail1.getProperty(2).toString();
			String strTypeId = detail1.getProperty(3).toString();
			String strDescription = detail1.getProperty(4).toString();
			newVerCode = Integer.parseInt(strNewVerCode);
			version.setVersionCode(newVerCode);
			version.setVersionName(strNewVerName);
		}
		/*version = new VersionDescription();
		String strNewVerName = "2.0";
		version.setVersionCode(2);
		version.setVersionName(strNewVerName);*/
		return version;
	}
}
