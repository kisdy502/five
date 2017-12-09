package cn.sdt.five.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import cn.sdt.five.player.IPlayer;
import cn.sdt.five.player.Player;

/**
 * 五子棋棋盘
 * Created by Administrator on 2017/9/30.
 */

public class QiPanView extends View {

    private Player player;

    public final static int ROW = 10;
    public final static int COLUMN = 10;

    public final static int NO_QIZI = 0;
    public final static int BLACK_QIZI = 1;
    public final static int WHITE_QIZI = 2;

    final static String TAG = "QiPanView";

    private int size;                    //棋盘尺寸
    private int qiziNum;                 //可以放棋子点的个数

    private float linePadding;               //最外面的线距离控件的距离
    private int qiziRadus;
    private int rowCount, columnCount;         //行数和列数
    private float rowDistance, columnDistance;

    Paint lPaint;

    Paint blackPaint, whitePaint;

    boolean isGameOver = false;

    int qipan[][];

    public void setQizi(int row, int column, int value) {
        if (qipan[row][column] == NO_QIZI) {
            Log.d(TAG, "棋子[" + row + "," + row + "]位置没有棋子可以落子");
            qipan[row][column] = value;
            invalidate(); //界面重绘
        }
    }

    public QiPanView(Context context) {
        this(context, null);
    }

    public QiPanView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public QiPanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rowCount = ROW;
        columnCount = COLUMN;
        qipan = new int[rowCount][columnCount];

        lPaint = new Paint();
        lPaint.setDither(true);
        lPaint.setColor(Color.YELLOW);
        lPaint.setStrokeWidth(2);

        blackPaint = new Paint();
        blackPaint.setDither(true);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.FILL);

        whitePaint = new Paint();
        whitePaint.setDither(true);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        /**
         *
         * MeasureSpec.EXACTLY match_parent 或者写成具体的值
         * MeasureSpec.AT_MOST wrap_content
         */

        Log.i(TAG, "widthMode is equals MeasureSpec.EXACTLY:" + (MeasureSpec.EXACTLY == widthMode));
        Log.i(TAG, "widthMode is equals MeasureSpec.UNSPECIFIED:" + (MeasureSpec.UNSPECIFIED == widthMode));
        Log.i(TAG, "widthMode is equals MeasureSpec.AT_MOST:" + (MeasureSpec.AT_MOST == widthMode));
        Log.d(TAG, "------------------------------------------------");
        Log.i(TAG, "heightMode is equals MeasureSpec.EXACTLY:" + (MeasureSpec.EXACTLY == heightMode));
        Log.i(TAG, "heightMode is equals MeasureSpec.UNSPECIFIED:" + (MeasureSpec.UNSPECIFIED == heightMode));
        Log.i(TAG, "heightMode is equals MeasureSpec.AT_MOST:" + (MeasureSpec.AT_MOST == heightMode));


        size = Math.min(widthSize, heightSize);
        setMeasuredDimension(size, size);

        Log.d(TAG, "size::" + size);

        rowDistance = size * 1.0f / rowCount;
        columnDistance = size * 1.0f / columnCount;
        linePadding = columnDistance / 2;
        qiziRadus = (int) (linePadding * 0.7f);

        Log.d(TAG, "rowDistance::" + rowDistance);
        Log.d(TAG, "columnDistance::" + columnDistance);
        Log.d(TAG, "linePadding::" + linePadding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#87CEFA"));
        drawLine(canvas);
        drawQizi(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float ex = 0, ey = 0;
                ex = event.getX();
                ey = event.getY();
                return handlePlay(ex, ey);
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    //绘制棋盘
    private void drawLine(Canvas canvas) {
        int row, line;
        float startX, startY, endX, endY;

        //水平线
        for (row = 0; row < rowCount; row++) {
            startX = linePadding;
            startY = linePadding + row * rowDistance;
            endX = size - linePadding;
            endY = startY;
//            Log.d(TAG, "startX::" + startX + ",startY::" + startY + ",endX::" + endX + ",endY::" + endY);
            canvas.drawLine(startX, startY, endX, endY, lPaint);
        }
        //垂直线
        for (line = 0; line < columnCount; line++) {
            startX = linePadding + line * columnDistance;
            startY = linePadding;
            endX = startX;
            endY = size - linePadding;
            canvas.drawLine(startX, startY, endX, endY, lPaint);
        }
    }

    //绘制棋子
    public void drawQizi(Canvas canvas) {

        int i, j;
        for (i = 0; i < rowCount; i++) {
            for (j = 0; j < columnCount; j++) {
                if (qipan[i][j] == 1) {
                    Log.d(TAG, "position:" + "[" + i + "," + j + "]");
                    canvas.drawCircle(j * columnDistance + linePadding, i * rowDistance + linePadding, qiziRadus, blackPaint);
                } else if (qipan[i][j] == 2) {
                    canvas.drawCircle(j * columnDistance + linePadding, i * rowDistance + linePadding, qiziRadus, whitePaint);
                }
            }
        }
    }

    /**
     * @param x 点击棋盘的坐标X
     * @param y 点击棋盘的坐标Y
     * @return
     */
    private int[] getPositionByXY(float x, float y) {
        int pos[] = new int[2];
        int row, column; //行数和列数
        row = (int) (0.5 + (y - linePadding) / rowDistance);
        column = (int) (0.5 + (x - linePadding) / columnDistance);
        pos[0] = row;
        pos[1] = column;
        return pos;
    }


    private boolean handlePlay(float ex, float ey) {
        Log.d(TAG, "ex:" + ex + ",ey:" + ey);
        int pos[] = getPositionByXY(ex, ey);
        Log.d(TAG, "着子点位置:" + pos[0] + "行," + pos[1] + "列");
        if (NO_QIZI == qipan[pos[0]][pos[1]]) {
            qipan[pos[0]][pos[1]] = player.getPlayTag();
            invalidate();
            return true;
        } else {
            return false;
        }
    }


    public interface OnHandlePlayListenr {
        void onHanle(int result);
    }

}
