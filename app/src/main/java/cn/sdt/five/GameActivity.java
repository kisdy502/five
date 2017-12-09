package cn.sdt.five;

import android.os.Bundle;

import cn.sdt.connect.Common;
import cn.sdt.connect.Data;
import cn.sdt.connect.FClient;
import cn.sdt.connect.FServer;
import cn.sdt.five.player.BlackPlayer;
import cn.sdt.five.player.Player;
import cn.sdt.five.player.WhitePlayer;
import cn.sdt.five.widget.QiPanView;

public class GameActivity extends BaseActivity {

    Object mLock = new Object();
    private QiPanView mQipan;
    private boolean isServer = false;
    Player player;

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
        FClient fClient = new FClient("192.168.1.100", Common.F_PORT, getApplicationContext(), mLock);
        new Thread(fClient).start();

    }

    @Override
    protected void onMessageReceived(String msgType, String msgContent) {
        if (Common.CONNECT_MSG.equals(msgType)) {
            if (isServer) {
                player = new BlackPlayer(mQipan);
                mQipan.setPlayer(player);
            } else {
                player = new WhitePlayer(mQipan);
                mQipan.setPlayer(player);
            }
        } else if (Common.PLAY_MSG.equals(msgType)) {
            Data data = new Data(msgContent);
            player.play(data);
        }
    }
}
