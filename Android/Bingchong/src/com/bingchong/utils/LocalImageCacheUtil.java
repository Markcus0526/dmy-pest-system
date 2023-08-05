package com.bingchong.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * @Author GL
 * @Description 操作应用程序data目录中存储的图片
 * @Date 2012-11-28
 */
public class LocalImageCacheUtil {

	/**
	 * @Description 获取头像Bitmap
	 * @param avatarName
	 * @return
	 */
	public static Bitmap getAvatar(String avatarName) {
		return getPicFromLocalImageCache(new File(
				FileUtil.getAvatarPicCacheDir(), avatarName));
	}

	/**
	 * @Description 获取缩略图
	 * @param attachPicName
	 * @return
	 */
	public static Bitmap getThumbPic(String attachPicName) {
		return getPicFromLocalImageCache(new File(
				FileUtil.getThumbPicCacheDir(), attachPicName));
	}

	/**
	 * @Description 获取原图
	 * @param OriginalPicName
	 * @return
	 */
	public static Bitmap getOriginalPic(String OriginalPicName) {
		return getPicFromLocalImageCache(new File(
				FileUtil.getOriginalPicCacheDir(), OriginalPicName));

	}

	/**
	 * @Description 从本地缓存中获取图片
	 * @param file
	 * @return
	 */
	private static Bitmap getPicFromLocalImageCache(File file) {
		if (file.exists() && file.isFile()) {
			try {
				return BitmapFactory.decodeStream(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * @Description 存储头像
	 * @param bitmap
	 * @param imageName
	 */
	public static void putAvatar(Bitmap bitmap, String imageName) {
		putPicToLocalImageCache(bitmap,
				new File(FileUtil.getAvatarPicCacheDir(), imageName));
	}

	/**
	 * @Description 存储缩略图
	 * @param bitmap
	 * @param imageName
	 */
	public static void putThumbPic(Bitmap bitmap, String imageName) {
		putPicToLocalImageCache(bitmap, new File(
				FileUtil.getThumbPicCacheDir(), imageName));
	}

	/**
	 * @Description 存储原图
	 * @param bitmap
	 * @param imageName
	 */
	public static void putOriginalPic(Bitmap bitmap, String imageName) {
		putPicToLocalImageCache(bitmap,
				new File(FileUtil.getOriginalPicCacheDir(), imageName));
	}

	/**
	 * @Description 将图片存放到本地缓存中
	 * @param bitmap
	 * @param file
	 */
	private static void putPicToLocalImageCache(Bitmap bitmap, File file) {
		try {
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Log.e("SAVE_IMAGE_TO_LOCAL_CACHE_THROW_EXCEPTION",
					"将图片存放到本地缓存中出现异常", e);
		}
	}
}
