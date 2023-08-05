package com.bingchong.utils;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * @Author GL
 * @Description 用于加载网络图片
 * @Date 2012-5-8
 */
public class ImageLoader {
	private static ImageLoader loader;
	// 任务队列
	private ArrayList<Task> taskQueue;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 子线程中返回的下载完成的任务
			Task task = (Task) msg.obj;
			task.onPostDownloadBitmap();
		}
	};
	// 任务下载线程
	private Thread thread = new Thread() {

		@Override
		public void run() {
			// 任务轮询
			while (true) {
				// 当任务队列中还有未处理任务时，执行下载任务
				while (taskQueue.size() > 0) {
					// 获取第一条任务，并将之从任务队列移除
					Task task = taskQueue.get(0);
					try {
						// 下载图片
						task.bitmap = NetworkImageUtil.getBitmap(task.imageUrl);
						synchronized (this) {
							taskQueue.remove(task);
						}
						// 如果handler对象不为null
						if (task.bitmap != null) {
							// 创建消息对象，并将完成的任务添加到消息对象中
							Message msg = new Message();
							msg.obj = task;
							// 发送消息回主线程
							handler.sendMessage(msg);
						}
					} catch (IOException e) {
						synchronized (this) {
							taskQueue.remove(task);
						}
					}
				}

				// 如果任务队列为空，则令线程等待
				synchronized (this) {
					if (taskQueue.size() == 0) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	};

	// 创建对象时，初始化map
	private ImageLoader() {
		taskQueue = new ArrayList<ImageLoader.Task>();
		// 启动下载任务线程
		thread.start();
	}

	public static ImageLoader getInstance() {
		if (loader == null) {
			return new ImageLoader();
		}
		return loader;
	}
	
	public void loadAvatarImage(String imageUrl, ImageView imageView){
		loadImage(new AvatarImageTask(imageUrl), imageView);
	}
	
	public void loadThumbImage(String imageUrl, ImageView imageVeiw){
		loadImage(new ThumbImageTask(imageUrl), imageVeiw);
	}
	
	public void loadOriginalImage(String imageUrl, ImageView imageView){
		loadImage(new OriginalImageTask(imageUrl), imageView);
	}

	public void loadImage(Task task, ImageView imageView) {
		synchronized (thread) {
			int index = taskQueue.indexOf(task);
			if (index > -1) {
				task = taskQueue.get(index);
				synchronized (task.imageViews) {
					task.imageViews.add(imageView);
				}
			} else {
				synchronized (task.imageViews) {
					task.imageViews.add(imageView);
				}
				taskQueue.add(task);
				// 唤醒任务下载线程
				thread.notify();
			}
		}
	}

	/**
	 * @Author GL
	 * @Description 下载图片的任务
	 * @Date 2013-5-30
	 */
	public abstract class Task {
		protected String imageUrl;
		protected Bitmap bitmap;// 下载的图片
		protected List<ImageView> imageViews = new ArrayList<ImageView>();

		public Task(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public void onPostDownloadBitmap() {
			if (bitmap == null) {
				return;
			}
			synchronized (imageViews) {
				saveBitmap();
				for (ImageView imageView : imageViews) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public List<ImageView> getImageViews() {
			return imageViews;
		}

		public void setImageViews(List<ImageView> imageViews) {
			this.imageViews = imageViews;
		}

		protected abstract void saveBitmap();

		@Override
		public boolean equals(Object o) {
			if (o instanceof Task) {
				Task task = (Task) o;
				if (task.imageUrl.equals(this.imageUrl)) {
					return true;
				}
				return false;
			}
			return false;
		}
	}

	/**
	 * @Author GL
	 * @Description 下载头像的任务
	 * @Date 2013-5-30
	 */
	public class AvatarImageTask extends Task {

		public AvatarImageTask(String imageUrl) {
			super(imageUrl);
		}

		@Override
		protected void saveBitmap() {
			synchronized (ImageHolder.imageCache) {
				ImageHolder.imageCache.put(
						ImageHolder.getAvatarImageName(imageUrl),
						new SoftReference<Bitmap>(bitmap));
			}
			LocalImageCacheUtil.putAvatar(bitmap,
					ImageHolder.getAvatarImageName(imageUrl));
		}

	}

	/**
	 * @Author GL
	 * @Description 下载缩略图的任务
	 * @Date 2013-5-30
	 */
	public class ThumbImageTask extends Task {

		public ThumbImageTask(String imageUrl) {
			super(imageUrl);
		}

		@Override
		protected void saveBitmap() {
//			bitmap = ImageZoomUtil.clipImage(bitmap);
			synchronized (ImageHolder.imageCache) {
				bitmap  = ImageZoomUtil.scaleThumbBitmap(ImageZoomUtil.clipImage(bitmap));
				ImageHolder.imageCache.put(ImageHolder.getAttachImageName(imageUrl),
						new SoftReference<Bitmap>(bitmap));
			}
			LocalImageCacheUtil.putThumbPic(bitmap,
					ImageHolder.getAttachImageName(imageUrl));
		}

	}

	/**
	 * @Author GL
	 * @Description 下载原图的任务
	 * @Date 2013-5-30
	 */
	public class OriginalImageTask extends Task {

		public OriginalImageTask(String imageUrl) {
			super(imageUrl);
		}

		@Override
		protected void saveBitmap() {
			LocalImageCacheUtil.putOriginalPic(bitmap,
					ImageHolder.getAttachImageName(imageUrl));
		}

	}
}