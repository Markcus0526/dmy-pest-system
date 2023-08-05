package com.bingchong;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @class：VideoQualityDialog.java 
 * @description: 视频质量弹出框
 * @author: 金铁钢
 * 
 * @history:
 *   	    日期  	     版本             担当者         修改内容
 *   2014-12-13  1.0    金铁钢            初版
 */
public class VideoQualityDialog extends Dialog {

	public static  Integer b = 2;

	public VideoQualityDialog(Context context, int theme) {
		super(context, theme);
	}

	public VideoQualityDialog(Context context) {
		super(context);
	}

	
	
	
	public static Integer getB() {
		return b;
	}

	public static void setB(Integer b) {
		VideoQualityDialog.b = b;
	}




	/**
	 * Helper class for creating a custom dialog
	 */
	public static class Builder {

		@SuppressWarnings("unused")
		private static final String ImageView = null;
		private ImageButton iv1;
		private ImageButton iv2;
		private ImageButton iv3;
		//private ImageButton iv4;

		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private LinearLayout lv1, lv2, lv3;
		private Typeface face;

		private DialogInterface.OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		/*
		 * public Builder setPositiveButton(int positiveButtonText,
		 * DialogInterface.OnClickListener listener) { this.positiveButtonText =
		 * (String) context .getText(positiveButtonText);
		 * this.positiveButtonClickListener = listener; return this; }
		 */
		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setImageView1(ImageButton ImageButton1,
				android.view.View.OnClickListener

				onClickListener) {
			this.iv1 = ImageButton1;
			return this;
		}

		public Builder setImageView2(DialogInterface.OnClickListener listener2) {
			return this;
		}

		public Builder setImageView3(ImageButton ImageButton3,
				DialogInterface.OnClickListener

				listener3) {
			this.iv3 = ImageButton3;
			return this;
		}

		/*public Builder setImageView4(ImageButton ImageButton4,
				DialogInterface.OnClickListener

				listener4) {
			this.iv4 = ImageButton4;
			return this;
		}*/

		/**
		 * Set the Dialog message from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		/**
		 * Set a custom content view for the Dialog. If a message is set, the
		 * contentView is not added to the Dialog...
		 * 
		 * @param v
		 * @return
		 */
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the positive button text and it's listener
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button resource and it's listener
		 * 
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button text and it's listener
		 * 
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		/**
		 * Create the custom dialog
		 */
		@SuppressWarnings("deprecation")
		public VideoQualityDialog create() {
			LayoutInflater inflater = LayoutInflater.from(context);
			// instantiate the dialog with the custom Theme
			final VideoQualityDialog dialog = new VideoQualityDialog(context,
					R.style.Dialog);
			dialog.setCanceledOnTouchOutside(false);
			View layout = inflater.inflate(R.layout.video_quality_dialog, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			Window dialogWindow = dialog.getWindow();
	        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
	        dialogWindow.setGravity(Gravity.CENTER);
	        lp.width = (int) (ScreenInfo.WIDTH * 0.8); // 宽度
	        dialogWindow.setAttributes(lp);
			
			TextView titleTv = (TextView) layout.findViewById(R.id.title);
			
			titleTv.setText(title);
			
			TextView tv1 = (TextView) layout.findViewById(R.id.tv1);
			TextView tv2 = (TextView) layout.findViewById(R.id.tv2);
			TextView tv3 = (TextView) layout.findViewById(R.id.tv3);
			
			if(Config.IS_SET_FONTS){
				titleTv.setTypeface(face);
				tv1.setTypeface(face);
				tv2.setTypeface(face);
				tv3.setTypeface(face);
			}
			
			iv1 = (ImageButton) layout.findViewById(R.id.iv1);
			iv2 = (ImageButton) layout.findViewById(R.id.iv2);
			iv3 = (ImageButton) layout.findViewById(R.id.iv3);
			//iv4 = (ImageButton) layout.findViewById(R.id.iv4);
			
			
			lv1 = (LinearLayout) layout.findViewById(R.id.ll_1);
			lv2 = (LinearLayout) layout.findViewById(R.id.ll_2);
			lv3 = (LinearLayout) layout.findViewById(R.id.ll_3);
			//lv4 = (LinearLayout) layout.findViewById(R.id.ll_4);
			// 设置监听器
			lv1.setOnClickListener(new LinearLayout.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					iv1.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw002));
					iv2.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv3.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					//iv4.setImageDrawable(context.getResources().getDrawable(
							//R.drawable.fw001));
					b=0;
				}

			});
			
			iv1.setOnClickListener(new LinearLayout.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					iv1.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw002));
					iv2.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv3.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					//iv4.setImageDrawable(context.getResources().getDrawable(
					//		R.drawable.fw001));
					b=0;
				}

			});
			 
			
			
			lv2.setOnClickListener(new LinearLayout.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					iv1.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv2.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw002));
					iv3.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					//iv4.setImageDrawable(context.getResources().getDrawable(
					//		R.drawable.fw001));
					b=1;
				}
			});
			iv2.setOnClickListener(new LinearLayout.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					iv1.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv2.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw002));
					iv3.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
				//	iv4.setImageDrawable(context.getResources().getDrawable(
				//			R.drawable.fw001));
					b=1;
				}
			});
			lv3.setOnClickListener(new LinearLayout.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					iv1.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv2.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv3.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw002));
				//	iv4.setImageDrawable(context.getResources().getDrawable(
				//			R.drawable.fw001));
					b=2;
				}
			});
			iv3.setOnClickListener(new LinearLayout.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					iv1.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv2.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv3.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw002));
				//	iv4.setImageDrawable(context.getResources().getDrawable(
				//			R.drawable.fw001));
					b=2;
				}
			});
			/*lv4.setOnClickListener(new LinearLayout.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					iv1.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv2.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv3.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv4.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw002));
					b=5;
				}
			});
			iv4.setOnClickListener(new LinearLayout.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					iv1.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv2.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv3.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw001));
					iv4.setImageDrawable(context.getResources().getDrawable(
							R.drawable.fw002));
					b=5;
				}
			});*/

			// set the confirm button
			if (positiveButtonText != null) {
				Button pb = ((Button) layout.findViewById(R.id.positiveButton));
						pb.setText(positiveButtonText);
				if(Config.IS_SET_FONTS){
					pb.setTypeface(face);
				}
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {

									positiveButtonClickListener.onClick(dialog,

									DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				Button nb = ((Button) layout.findViewById(R.id.negativeButton));
						nb.setText(negativeButtonText);
						if(Config.IS_SET_FONTS){
							nb.setTypeface(face);
						}
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {

									negativeButtonClickListener.onClick(dialog,

									DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
				
				
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			// set the content message
			if (message != null) {
				// ((TextView) layout.findViewById(
				// R.id.message)).setText(message);
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
			}
			
			dialog.setContentView(layout);
			return dialog;
		}
		
		public void setSelectView(int selected){
			switch (selected) {
			case 0:
				iv1.setImageDrawable(context.getResources().getDrawable(
						R.drawable.fw002));
				iv2.setImageDrawable(context.getResources().getDrawable(
						R.drawable.fw001));
				iv3.setImageDrawable(context.getResources().getDrawable(
						R.drawable.fw001));
				break;
			case 1:
				iv1.setImageDrawable(context.getResources().getDrawable(
						R.drawable.fw001));
				iv2.setImageDrawable(context.getResources().getDrawable(
						R.drawable.fw002));
				iv3.setImageDrawable(context.getResources().getDrawable(
						R.drawable.fw001));
				break;
			case 2:
				iv1.setImageDrawable(context.getResources().getDrawable(
						R.drawable.fw001));
				iv2.setImageDrawable(context.getResources().getDrawable(
						R.drawable.fw001));
				iv3.setImageDrawable(context.getResources().getDrawable(
						R.drawable.fw002));
				break;

			default:
				break;
			}
		}

	}
}