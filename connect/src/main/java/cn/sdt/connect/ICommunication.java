package cn.sdt.connect;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/9.
 */

public interface ICommunication {
    Data receive() throws IOException;
    boolean send(Data d);
    void stop() throws IOException;
}
