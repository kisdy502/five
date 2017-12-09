package cn.sdt.five.player;

/**
 * Created by Administrator on 2017/12/9.
 */

public interface IPlayer {
    public final static int BLACK_TAG = 1;
    public final static int WHITE_TAG = 2;

    /**
     * 下棋
     * 参数位置 在棋盘中的行和列
     *
     * @param posRow
     * @param posColumn
     */
    void play(int posRow, int posColumn);
}
