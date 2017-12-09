package cn.sdt.connect;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Administrator on 2017/12/7.
 */

public class FClient extends Communicatior implements Runnable {

    Socket mClientSocket;
    String mIp;

    public String getIp() {
        return mIp;
    }

    public int getPort() {
        return mPort;
    }

    public FClient(String ip, int port, Context context, Object lock) {
        this.mIp = ip;
        this.mPort = port;
        this.mContext = context;
        this.mLock = lock;
    }

    private void connect() {
        try {
            mClientSocket = new Socket();
            mClientSocket.setReuseAddress(true);
            mClientSocket.connect(new InetSocketAddress(mIp, mPort));
            //TODO  发送连接到服务器的本地广播消息
            MessageManager.sendConnecServertMsg(mContext, mIp);
            mInput = new DataInputStream(mClientSocket.getInputStream());
            //向服务器端发送数据
            mOutput = new DataOutputStream(mClientSocket.getOutputStream());
            mReadThread = new ReadThread(this);
            mReadThread.setRunning(true);
            mReadThread.start();
            mWriteThread = new WriteThread(this);
            mWriteThread.setRunning(true);
            mWriteThread.start();
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
        DataInputStream dinput = new DataInputStream(mInput);
        String ret = dinput.readUTF();
        d = new Data(ret);
        return d;
    }


    public void equeueData(final Data d) throws InterruptedException {
        mWriteThread.getQueue().put(d);
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
            DataOutputStream doutput = new DataOutputStream(mOutput);
            doutput.writeUTF(d.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void stop() throws IOException {
        super.stop();
        if (mClientSocket != null && mClientSocket.isConnected()) {
            mClientSocket.close();
        }
    }

    @Override
    public void run() {
        connect();
    }
}
