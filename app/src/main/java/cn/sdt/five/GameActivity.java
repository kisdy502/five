package cn.sdt.five;

import android.app.Activity;
import android.os.Bundle;

import cn.sdt.connect.Common;
import cn.sdt.connect.FClient;
import cn.sdt.connect.FServer;
import cn.sdt.five.widget.QiPanView;

public class GameActivity extends BaseActivity {

    Object mLock = new Object();
    private QiPanView mQipan;
    private boolean isServer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mQipan = findViewById(R.id.qipan);

        isServer = getIntent().getBooleanExtra("Is_Server", false);
        if (isServer) {
            initServer();
        } else {
            initClient();
        }
    }

    private void initServer() {
        FServer fServer = new FServer(mLock, getApplicationContext());
        new Thread(fServer).start();
    }

    private void initClient() {
        FClient fClient=new FClient("192.168.1.100", Common.F_PORT,getApplicationContext());
        fClient.connect();
    }

    @Override
    protected void onMessageReceived(String msgType, String msgContent) {
        if(Common.CONNECT_MSG.equals(msgType)){
            if(isServer){

            }
        }
    }
}
