package cn.sdt.five.player;

import cn.sdt.connect.Data;
import cn.sdt.five.widget.QiPanView;

/**
 * 棋手
 * Created by Administrator on 2017/12/9.
 */

public class BlackPlayer extends Player {

    public BlackPlayer(QiPanView qipanView) {
        super(qipanView);
        this.playTag = BLACK_TAG;
    }

}
