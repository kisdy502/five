package cn.sdt.connect;

import android.content.Context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Administrator on 2017/12/9.
 */

public abstract class Communicatior implements ICommunication {

    protected int mPort;
    protected volatile boolean isConnected;
    protected InputStream input;
    protected OutputStream output;
    protected ReadThread mReadThread;
    protected WriteThread mWriteThread;

    protected Object mLock;
    protected Data mData;
    protected Context mContext;

    protected DataInputStream mInput;
    protected DataOutputStream mOutput;

    @Override
    public void stop() throws IOException {
        mReadThread.setRunning(false);
        mWriteThread.setRunning(false);
    }

    /**
     * 读线程
     */
    static class ReadThread extends Thread {

        Communicatior communicatior;
        private boolean isRunning = false;

        public boolean isRunning() {
            return isRunning;
        }

        public void setRunning(boolean running) {
            isRunning = running;
        }

        public ReadThread(Communicatior communicatior) {
            this.communicatior = communicatior;
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    communicatior.mData = communicatior.receive();
                    synchronized (communicatior.mLock) {
                        communicatior.mLock.notify();
                    }
                    MessageManager.sendReceivedMsg(communicatior.mContext, communicatior.mData.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写线程
     */
    static class WriteThread extends Thread {
        Communicatior communicatior;

        public ArrayBlockingQueue<Data> getQueue() {
            return queue;
        }

        private ArrayBlockingQueue<Data> queue;

        public boolean isRunning() {
            return isRunning;
        }

        public void setRunning(boolean running) {
            isRunning = running;
        }

        private boolean isRunning = false;

        public WriteThread(Communicatior communicatior) {
            this.communicatior = communicatior;
            queue = new ArrayBlockingQueue<Data>(8);
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    Data data = queue.take();
                    if (data != null) {
                        communicatior.send(data);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
