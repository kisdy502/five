package cn.sdt.five.player;

/**
 * Created by Administrator on 2017/12/9.
 */

public abstract class Player implements IPlayer {
    //棋手的身份
    protected int playTag;

    public int getPlayTag() {
        return playTag;
    }

    public void setPlayTag(int playTag) {
        this.playTag = playTag;
    }

}
