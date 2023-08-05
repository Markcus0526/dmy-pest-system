package com.bingchong.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import com.bingchong.R;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @Author GL
 * @Description 图片持有者
 * @Date 2012-11-28
 */
public class ImageHolder {

	/**
	 * 图片缓存
	 */
	public static HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

	private static ImageLoader imageLoader = ImageLoader.getInstance();

	/**
	 * @description 缩略图
	 * @param imageView
	 * @param imageUrl
	 */
	public static void setThumbImage(ImageView imageView, String imageUrl) {
		String imageName = getAttachImageName(imageUrl);
		//从缓存中获取bitmap
		Bitmap bitmap = getBitmapFromImageCache(imageName);
		if (bitmap == null) {//如果缓存中bitmap 是 null，就从本地文件系统中获取
			bitmap = LocalImageCacheUtil.getThumbPic(imageName);
			if (bitmap != null) {
				imageCache.put(imageName, new SoftReference<Bitmap>(bitmap));
			}
		}
		
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}else {
//			imageView.setImageResource(R.drawable.default_attach_pic);
			imageLoader.loadThumbImage(imageUrl, imageView);
		}
	}

	/**
	 * @description 原图
	 * @param imageView
	 * @param imageUrl
	 */
	public static void setOriginalImage(ImageView imageView, String imageUrl) {
		String imageName = getAttachImageName(imageUrl);
		Bitmap bitmap = LocalImageCacheUtil.getOriginalPic(imageName);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}else {
//			imageView.setImageResource(R.drawable.default_attach_pic);
			imageLoader.loadOriginalImage(imageUrl, imageView);
		}
	}

	/**
	 * @description 头像
	 * @param imageView
	 * @param imageUrl
	 */
	public static void setAvatar(ImageView imageView, String imageUrl, int defaultDrawableRes) {
		String imageName = getAvatarImageName(imageUrl);
		//从缓存中获取bitmap
		Bitmap bitmap = getBitmapFromImageCache(imageName);
		if (bitmap == null) {//如果缓存中bitmap 是 null，就从本地文件系统中获取
			bitmap = LocalImageCacheUtil.getAvatar(imageName);
			if (bitmap != null) {
				imageCache.put(imageName, new SoftReference<Bitmap>(bitmap));
			}
		}
		
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}else {
			imageView.setImageResource(defaultDrawableRes);
			imageLoader.loadAvatarImage(imageUrl, imageView);
		}
	}
	
	public static void setAvatar(ImageView imageView, String imageUrl) {
		setAvatar(imageView, imageUrl, R.drawable.default_avatar);
	}

	public static String getAttachImageName(String imageUrl) {
		String[] strs = imageUrl.split("/");
		return strs[strs.length - 1];
	}

	public static String getAvatarImageName(String imageUrl) {
		String[] strs = imageUrl.split("/");
		return strs[strs.length - 2] + "_" + strs[strs.length - 1];
	}


	/**
	 * @Description 从缓存中获取bitmap
	 * @param imageName
	 * @return
	 */
	private static Bitmap getBitmapFromImageCache(String imageName) {
		if (imageCache.containsKey(imageName)) {
			return imageCache.get(imageName).get();
		} else {
			return null;
		}
	}
}
