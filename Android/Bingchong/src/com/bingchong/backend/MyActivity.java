package com.bingchong.backend;

import android.app.Activity;
import android.os.Bundle;
import com.bingchong.utils.MyUtils;
import com.bingchong.BingChongApp;
import com.bingchong.R;

/**
 * Created with IntelliJ IDEA.
 * User: ChungJin.Sim
 * Date: 13-9-12
 * Time: 
 * To change this template use File | Settings | File Templates.
 */
public class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( savedInstanceState != null )
            BingChongApp.loadSession(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        BingChongApp.saveSession(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected boolean checkNetwork () {
        boolean  bNetExist = MyUtils.isNetworkConnected(MyActivity.this);

        if ( bNetExist == false ) {
            MyUtils.showMyToast(MyActivity.this, R.string.no_network);
        }

        return bNetExist;
    }
}
