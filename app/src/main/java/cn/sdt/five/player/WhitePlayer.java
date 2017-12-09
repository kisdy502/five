package cn.sdt.five.player;

import cn.sdt.five.widget.QiPanView;

/**
 * 棋手
 * Created by Administrator on 2017/12/9.
 */

public class WhitePlayer extends Player {


    public WhitePlayer(QiPanView qipanView) {
        super(qipanView);
        this.playTag = WHITE_TAG;
    }

}
