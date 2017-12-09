package cn.sdt.five.player;

import cn.sdt.connect.Data;
import cn.sdt.five.widget.QiPanView;

/**
 * Created by Administrator on 2017/12/9.
 */

public abstract class Player implements IPlayer {
    //棋手的身份
    protected int playTag;
    protected QiPanView mQiPan;

    public Player(QiPanView qipanView) {
        this.mQiPan = qipanView;
    }

    public int getPlayTag() {
        return playTag;
    }

    public void setPlayTag(int playTag) {
        this.playTag = playTag;
    }

    @Override
    public void play(int posRow, int posColumn) {
        mQiPan.setQizi(posRow, posColumn, playTag);
    }

    @Override
    public void play(Data data) {
        play(data.getRow(), data.getCol());
    }

}
