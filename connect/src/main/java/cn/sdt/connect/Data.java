package cn.sdt.connect;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 客户端服务器通信的数据格式
 * Created by Administrator on 2017/9/21.
 */

public class Data implements Serializable {

    private static final long serialVersionUID = 8334218180710421731L;

    private int type;//0,命令；1，数据;2,聊天内容。

    private String content;//内容。

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Data(int t, String c) {
        type = t;
        content = c;
    }

    public Data(String s) {
        if (s == null)
            return;
        type = Integer.parseInt(s.substring(0, 1));
        content = s.substring(1);
    }

    public String toString() {
        return type + content;
    }

    public int getRow() {
        if (type == 1)
            return Integer.parseInt(content.substring
                    (content.indexOf('(') + 1, content.indexOf(',')));
        return -1;
    }

    public int getCol() {
        if (type == 1)
            return Integer.parseInt(content.substring
                    (content.indexOf(',') + 1, content.indexOf(')')));
        return -1;
    }


    public static void main(String args[]) {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println(ip);
            int first = ip.indexOf('.');
            int second = ip.indexOf('.', first + 1);
            int third = ip.indexOf('.', second + 1);
            String ip_pre = ip.substring(0, third + 1);
            System.out.println("first = " + first + " second = " + second
                    + " third = " + third + "\n " + ip_pre);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Data d = new Data("1(3,4)");
        System.out.println("type=" + d.getType() + ",content=" + d.getContent());
        System.out.println("x=" + d.getRow() + ",y=" + d.getCol());
    }
}