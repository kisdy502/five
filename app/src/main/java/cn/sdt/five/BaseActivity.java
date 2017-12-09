package cn.sdt.five;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import cn.sdt.connect.Common;
import cn.sdt.connect.MessageManager;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class BaseActivity extends Activity {

    private IntentFilter intentFilter;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msgType = intent.getStringExtra(MessageManager.KEY_TYPE);
            String msgContent = intent.getStringExtra(MessageManager.KEY_CONTENT);
            onMessageReceived(msgType, msgContent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentFilter = new IntentFilter();
        intentFilter.addAction(Common.LOCAL_MSG_ACTION);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReceiver);
        super.onDestroy();
    }


    protected abstract void onMessageReceived(String msgType, String msgContent);



}
