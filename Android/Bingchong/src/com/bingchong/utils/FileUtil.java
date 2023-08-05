package com.bingchong.utils;

import java.io.File;
import java.io.IOException;

import com.bingchong.BingChongApp;
import com.bingchong.Config;


import android.content.Context;
import android.os.Environment;

/**
 * @Author GL
 * @Description 文件工具类
 * @Date 2013-11-20
 */
public class FileUtil {

	public static final Context context = BingChongApp.getAppContext();

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return boolean
	 */
	public static boolean checkSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description 获得应用程序缓存的根目录
	 * @return
	 */
	public static File getAppRootCacheDir() {
		File rootFile;
		if (checkSDCard()) {
			rootFile = new File(Environment.getExternalStorageDirectory(),
					Config.CACHE_ROOT_NAME);
		} else {
			rootFile = new File(context.getCacheDir(),
					Config.CACHE_ROOT_NAME);
		}
		if (!rootFile.exists() || !rootFile.isDirectory()) {
			rootFile.mkdir();
		}
		return rootFile;
	}

	/**
	 * @Description 获得音频缓存目录
	 * @return
	 */
	public static File getAudioCacheDir() {
		File audioDir = new File(getAppRootCacheDir(),
				Config.CACHE_AUDIO_ROOT_NAME);
		if (!audioDir.exists() || !audioDir.isDirectory()) {
			audioDir.mkdir();
		}
		return audioDir;
	}
	
	/**
	 * @Description 获得录音的临时文件
	 * @return
	 */
	public static File getRecordTempFile() {
		File recordTempFile = new File(getAudioCacheDir(),
				"startCityTempAudio.amr");
		try {
			if (!recordTempFile.exists() || recordTempFile.isDirectory()) {
				recordTempFile.createNewFile();
			}
		} catch (IOException e) {
			throw new RuntimeException(
					"create temp_audio_file for record failure!");
		}
		return recordTempFile;
	}

	/**
	 * @Description 获得图片缓存目录
	 * @return
	 */
	public static File getPicCacheDir() {
		File picDir = new File(getAppRootCacheDir(),
				Config.CACHE_PIC_ROOT_NAME);
		if (!picDir.exists() || !picDir.isDirectory()) {
			picDir.mkdir();
		}
		return picDir;
	}

	/**
	 * @Description 获得头像缓存目录
	 * @return
	 */
	public static File getAvatarPicCacheDir() {
		File avatarDir = new File(getPicCacheDir(),
				Config.AVATAR_PIC_FOLDER_NAME);
		if (!avatarDir.exists() || !avatarDir.isDirectory()) {
			avatarDir.mkdir();
		}
		return avatarDir;
	}

	/**
	 * @Description 获得缩略图缓存目录
	 * @return
	 */
	public static File getThumbPicCacheDir() {
		File thumbDir = new File(getPicCacheDir(),
				Config.THUMB_PIC_FOLDER_NAME);
		if (!thumbDir.exists() || !thumbDir.isDirectory()) {
			thumbDir.mkdir();
		}
		return thumbDir;
	}

	/**
	 * @Description 获得原图缓存目录
	 * @return
	 */
	public static File getOriginalPicCacheDir() {
		File originalDir = new File(getPicCacheDir(),
				Config.ORIGINAL_PIC_FOLDER_NAME);
		if (!originalDir.exists() || !originalDir.isDirectory()) {
			originalDir.mkdir();
		}
		return originalDir;
	}

	/**
	 * @Description 获得保存图片缓存目录
	 * @return 
	 */
	public static File getSavedPicCacheDir() {
		File savedDir = new File(getPicCacheDir(),
				Config.SAVED_PIC_FOLDER_NAME);
		if (!savedDir.exists() || !savedDir.isDirectory()) {
			savedDir.mkdir();
		}
		return savedDir;
	}
}
