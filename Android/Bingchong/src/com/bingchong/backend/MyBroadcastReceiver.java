package com.bingchong.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: COSMOS
 * Date: 8/19/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ((intent.getAction() != null) && (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")))
        {
            // Start the BaiduMap service
            context.startService(new Intent(context, ServiceBaiduMap.class));
        }
    }
}
