package com.bingchong;

import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;

import android.content.Context;
import android.content.DialogInterface;


/**
 * @class：ConfigVideo.java 
 * @description: 设置视频质量
 * @author: 金铁钢
 * 
 * @history:
 *   	    日期  	     版本             担当者         修改内容
 *   2014-12-13  1.0    金铁钢            初版
 */
public class ConfigVideo {
	private VideoQualityDialog dialog;
	
	public static int selectPosition = 0;
	
	public void showConfigDialog(final Context context , int position){
		
		VideoQualityDialog.setB(position);
		
		VideoQualityDialog.Builder customBuilder = new VideoQualityDialog.Builder(
				context);
		
		
		customBuilder
				.setTitle("请选择视频质量")
				  
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}
				)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								selectPosition = ApplyVideoConfig(context , VideoQualityDialog.getB());
							}
						});
		
		
		
		dialog = customBuilder.create();
		customBuilder.setSelectView(position);
		dialog.show();
		
	}
	
	/**
	 * @description 根据配置文件配置视频参数
	 */
	@SuppressWarnings("unused")
	public int ApplyVideoConfig(Context context , int seleted)
	{
		ConfigEntity configEntity = ConfigService.LoadConfig(context);
		switch (seleted) {
			case 0:
				// 设置本地视频编码的码率（如果码率为0，则表示使用质量优先模式）
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_BITRATECTRL, 60*1000);
				// 设置本地视频编码的质量
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_QUALITYCTRL, 2);
				// 设置本地视频编码的帧率
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FPSCTRL, 10);
				// 设置本地视频编码的关键帧间隔
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_GOPCTRL, 10*4);
				// 设置本地视频采集分辨率
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL, 320);
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL, 240);
				// 设置视频编码预设参数（值越大，编码质量越高，占用CPU资源也会越高）
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_PRESETCTRL, 3);
				break;
			case 1:
				// 设置本地视频编码的码率（如果码率为0，则表示使用质量优先模式）
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_BITRATECTRL, 300*1000);
				// 设置本地视频编码的质量
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_QUALITYCTRL, 3);
				// 设置本地视频编码的帧率
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FPSCTRL, 15);
				// 设置本地视频编码的关键帧间隔
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_GOPCTRL, 15*4);
				// 设置本地视频采集分辨率
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL, 720);
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL, 480);
				// 设置视频编码预设参数（值越大，编码质量越高，占用CPU资源也会越高）
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_PRESETCTRL, 3);
				break;
			case 2:
				// 设置本地视频编码的码率（如果码率为0，则表示使用质量优先模式）
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_BITRATECTRL, 500*1000);
				// 设置本地视频编码的质量
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_QUALITYCTRL, 3);
				// 设置本地视频编码的帧率
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FPSCTRL, 20);
				// 设置本地视频编码的关键帧间隔
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_GOPCTRL, 20*4);
				// 设置本地视频采集分辨率
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL, 1024);
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL, 720);
				// 设置视频编码预设参数（值越大，编码质量越高，占用CPU资源也会越高）
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_PRESETCTRL, 3);
				break;
	
			default:
				break;
		}
		// 让视频参数生效
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_APPLYPARAM, 1);
		// P2P设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_NETWORK_P2PPOLITIC, 1);
		// 本地视频Overlay模式设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_OVERLAY, 1);
		// 回音消除设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_ECHOCTRL, 1);
		// 平台硬件编码设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_USEHWCODEC, 0);
		// 视频旋转模式设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_ROTATECTRL, 0);
		// 视频采集驱动设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER, AnyChatDefine.VIDEOCAP_DRIVER_JAVA);
		// 本地视频采集偏色修正设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FIXCOLORDEVIA, 0);
		// 视频显示驱动设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL, AnyChatDefine.VIDEOSHOW_DRIVER_JAVA);
		// 音频播放驱动设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_PLAYDRVCTRL, AnyChatDefine.AUDIOPLAY_DRIVER_JAVA);
		// 音频采集驱动设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_RECORDDRVCTRL, AnyChatDefine.AUDIOREC_DRIVER_JAVA);
		// 视频GPU渲染设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_GPUDIRECTRENDER, 0);
		// 本地视频自动旋转设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION, 1);
		
		ConfigService.SaveQuality(context, seleted);
		
		return seleted;
	}
}
