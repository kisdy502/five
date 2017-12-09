package cn.sdt.five.player;

import cn.sdt.five.widget.QiPanView;

/**
 * 棋手
 * Created by Administrator on 2017/12/9.
 */

public class BlackPlayer extends Player {

    QiPanView mQiPan;

    public BlackPlayer(QiPanView qipanView) {
        this.playTag = BLACK_TAG;
        this.mQiPan = qipanView;
    }

    @Override
    public void play(int posRow, int posColumn) {

    }
}
