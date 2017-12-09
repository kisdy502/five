package cn.sdt.connect;

/**
 * Created by Administrator on 2017/12/8.
 */

public class IdGenerator {

    private static int send_id = 0;

    public static int genId() {
        send_id++;
        int id = send_id;
        return id;
    }

}
