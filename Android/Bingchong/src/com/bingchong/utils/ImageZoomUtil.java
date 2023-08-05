package com.bingchong.utils;

import com.bingchong.BingChongApp;
import com.bingchong.R;

import android.graphics.Bitmap;

/**
 * @Author GL
 * @Description 当原始图片的高度大于规定的最大高度的时候，按照原图片比例缩放图片
 * @Date 2012-11-23
 */
public class ImageZoomUtil {

	public static final float density;
	// public static final float ATTACH_PIC_MAX_HEIGHT = 110f;
	public static final int ATTACH_PIC_MAX_SIZE;
	public static final int AVATAR_SIZE;

	static {
		density = BingChongApp.getAppContext().getResources().getDisplayMetrics().density;
		ATTACH_PIC_MAX_SIZE = BingChongApp.getAppContext().getResources()
				.getDimensionPixelSize(R.dimen.attach_pic_size);
		AVATAR_SIZE = BingChongApp.getAppContext().getResources()
				.getDimensionPixelSize(R.dimen.list_avatar_size);

		// App.Logger.e("IMAGE_ZOOM_UTIL", "DENSITY " + density
		// + " ATTACH_PIC_MAX_HEIGHT " + ATTACH_PIC_MAX_HEIGHT
		// + " AVATAR_SIZE " + AVATAR_SIZE);
	}

	/**
	 * @Description 将缩略图按照规定的比例缩放
	 * @param bitmap
	 * @return
	 */
	public static Bitmap scaleThumbBitmap(Bitmap bitmap) {

		Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap,
				ATTACH_PIC_MAX_SIZE, ATTACH_PIC_MAX_SIZE, true);
		// bitmap.recycle();
		return newBitmap;
	}

	public static Bitmap clipImage(Bitmap sourceBitmap) {
//		if (sourceBitmap.isRecycled()) {
//			return null;
//		}

		int width = sourceBitmap.getWidth();
		int height = sourceBitmap.getHeight();

		if (width > height) {// 宽大于高
			Bitmap bitmap = Bitmap.createBitmap(sourceBitmap,
					(width - height) / 2, 0, height, height);
			sourceBitmap.recycle();
			return bitmap;
		} else if (width < height) {// 高大于宽
			Bitmap bitmap = Bitmap.createBitmap(sourceBitmap, 0,
					(height - width) / 2, width, width);
			sourceBitmap.recycle();
			return bitmap;
		} else {// 宽等于高
			return sourceBitmap;
		}
	}

	public static Bitmap scaleBitmap(Bitmap bitmap, int pix) {
		Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, pix, pix, true);
		// bitmap.recycle();
		return newBitmap;
	}
}
