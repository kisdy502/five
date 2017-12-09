package cn.sdt.connect;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/12/7.
 */

public class FServer extends Communicatior implements Runnable {

    public FServer(Object lock, Context context) {
        this(Common.F_PORT, lock, context);
    }

    public FServer(int port, Object lock, Context context) {
        this.mPort = port;
        mLock = lock;
        this.mContext = context;
    }

    private void startServer() {
        ServerSocket serverSocket = null;
        Socket client = null;
        try {
            serverSocket = new ServerSocket(mPort);
            while (true) {
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                client = serverSocket.accept();
                if (client.isConnected()) {
                    //TODO 发送本地广播
                    MessageManager.sendConnectMsg(mContext, client.getInetAddress().getHostAddress());
                    input = client.getInputStream();
                    output = client.getOutputStream();
                    // 处理这次连接
                    mReadThread = new ReadThread(this);
                    mReadThread.setRunning(true);
                    mReadThread.start();
                    mWriteThread = new WriteThread(this);
                    mWriteThread.setRunning(true);
                    mWriteThread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收消息
     *
     * @return
     */
    @Override
    public Data receive() throws IOException {
        //将缓冲区准备为数据传出状态,执行以上方法后,输出通道会从数据的开头而不是末尾开始.回绕保持缓冲区中的数据不变,只是准备写入而不是读取.
        Data d = null;
        //读取服务器端数据
        mInput = new DataInputStream(input);
        String ret = mInput.readUTF();
        d = new Data(ret);
        return d;
    }

    /**
     * 发送消息
     *
     * @param d
     * @return
     */
    @Override
    public boolean send(final Data d) {
        try {
            mOutput = new DataOutputStream(output);
            mOutput.writeUTF(d.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        startServer();
    }


}
