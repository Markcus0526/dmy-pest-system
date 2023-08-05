package com.bingchong.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class NetworkImageUtil {
	/**
	 * 根据一个网络连接(URL)获取bitmapDrawable图像
	 * @param imageUri
	 * @return
	 */
	public static BitmapDrawable getFriendIcon(URL imageUri) {

		BitmapDrawable icon = null;
		try {
			HttpURLConnection hp = (HttpURLConnection) imageUri
					.openConnection();
			icon = new BitmapDrawable(hp.getInputStream());// 将输入流转换成bitmap
			hp.disconnect();// 关闭连接
		} catch (Exception e) {
		}
		return icon;
	}

	/**
	 * 根据一个网络连接(String)获取bitmapDrawable图像
	 * @param imageUri
	 * @return
	 */
	public static BitmapDrawable getContentPic(String imageUri) {
		URL imgUrl = null;
		try {
			imgUrl = new URL(imageUri);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		BitmapDrawable icon = null;
		try {
			HttpURLConnection hp = (HttpURLConnection) imgUrl.openConnection();
			icon = new BitmapDrawable(hp.getInputStream());// 将输入流转换成bitmap
			hp.disconnect();// 关闭连接
		} catch (Exception e) {
		}
		return icon;
	}

	/**
	 *  根据一个网络连接(URL)获取bitmap图像
	 * @param imageUri
	 * @return
	 */
	public static Bitmap getUserIcon(URL imageUri) {
		// 显示网络上的图片
		URL myFileUrl = imageUri;
		Bitmap bitmap = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	/**
	 *  根据一个网络连接(String)获取bitmap图像
	 * @param imageUri
	 * @return
	 * @throws MalformedURLException 
	 */
	public static Bitmap getBitmap(String imageUri) throws MalformedURLException {
		// 显示网络上的图片
		return getBitmap(imageUri, false);
	}
	
	public static Bitmap getBitmap(String imageUri, boolean isBigImage) throws MalformedURLException{
		// 显示网络上的图片
//		App.Logger.e("IMAGE_URL", imageUri);
		URL myFileUrl = new URL(imageUri);
		Bitmap bitmap = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(15000);
			conn.connect();
			InputStream is = conn.getInputStream();
			
			if(!isBigImage){
				bitmap = BitmapFactory.decodeStream(is);
			}else{
				File file = new File(FileUtil.getAppRootCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
				HttpUtil.downloadFile(is, file);
				bitmap = Compress.compressPicToBitmap(file);
				file.delete();
			}
			is.close();
		} catch (IOException e) {
			bitmap = null;
		} 
		
		return bitmap;
	}

}