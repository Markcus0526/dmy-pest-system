package com.bingchong.backend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.bingchong.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: COSMOS
 * Date: 8/18/13
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActivityGpsEnableDialog extends MyActivity {

    private Button m_btnSet;
    private Button  m_btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gps_enable_dialog);
        getControlVariables();
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
    protected void onStart() {
        super.onStart();
    }

    private void getControlVariables()
    {
        m_btnSet = (Button) findViewById(R.id.btnSet);
        m_btnCancel = (Button) findViewById(R.id.btnCancel);

        m_btnSet.setOnClickListener(btnSetClickListener);
        m_btnCancel.setOnClickListener(btnCancelClickListener);
    }

    private View.OnClickListener btnSetClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            ActivityGpsEnableDialog.this.startActivity(intent);

            ActivityGpsEnableDialog.this.finish();
        }
    };

    private View.OnClickListener btnCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         ActivityGpsEnableDialog.this.finish();
        }
    };
}
