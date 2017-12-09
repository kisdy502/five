package cn.sdt.connect;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MessageManager {

    public final static String KEY_ID = "id";
    public final static String KEY_TYPE = "type";
    public final static String KEY_CONTENT = "content";
    public final static String TYPE_CONN = "conn";


    public final static int MSG_TYPE_CONNECT = 0;
    public final static int MSG_TYPE_RECEIVED = 1;

    public static void sendConnectMsg(Context context, String clientIp) {
        Intent intent = new Intent(Common.LOCAL_MSG_ACTION);
        intent.putExtra(KEY_ID, IdGenerator.genId());
        intent.putExtra(KEY_TYPE, MSG_TYPE_CONNECT);
        intent.putExtra(KEY_CONTENT, clientIp);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendReceivedMsg(Context context, String msg) {
        Intent intent = new Intent(Common.LOCAL_MSG_ACTION);
        intent.putExtra(KEY_ID, IdGenerator.genId());
        intent.putExtra(KEY_TYPE, MSG_TYPE_RECEIVED);
        intent.putExtra(KEY_CONTENT, msg);
    }



}
