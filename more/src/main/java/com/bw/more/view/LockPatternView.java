package com.bw.more.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * 图案解锁
 */
public class LockPatternView extends View {

    private Paint mCirclePaint;//圆的画笔

    private Paint mLinePaint;//线的画笔
    //圆心数组
    private PointView[][] mPointViewArray = new PointView[3][3];
    //保持选中点的集合
    private ArrayList<PointView> mSelectedPointViewList;
    //图案监听器
    private OnPatternChangerListener monPatternChangerListener;

    //解锁图案的边长
    private int mPatterWidth;
    //半径
    private float mRadius;
    //每个圆圈的下标
    private int mindex = 1;
    //第一个点是否选中
    private boolean mIsSelected;
    /**
     * 是否绘制结束
     */
    private boolean mIsFinished;
    //正在滑动并且没有任何点选中
    private boolean mIsMovingWithoutCircle = false;

    private float mCurrentX, mCurrentY;
    /**
     * 正常状态的颜色
     */
    private static final int NORMAL_COLOR = 0xFF979797;
    /**
     * 选中状态的颜色
     */
    private static final int SELECTED_COLOR = 0xFFFF34B3;


    public LockPatternView(Context context) {
        super(context);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(NORMAL_COLOR);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setStrokeWidth(20);
        mLinePaint.setColor(SELECTED_COLOR);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        mSelectedPointViewList = new ArrayList<>();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPatterWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(mPatterWidth, mPatterWidth);
    }

    /**
     * 画线
     *
     * @param canvas 画布
     * @param pointA 第一个点
     * @param pointB 第二个点
     */
    private void drawLine(Canvas canvas, Point pointA, Point pointB) {
        canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, mLinePaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawCircle(canvas);
        for (PointView pointView : mSelectedPointViewList) {
            mCirclePaint.setColor(SELECTED_COLOR);
            canvas.drawCircle(pointView.x, pointView.y, mRadius, mCirclePaint);
            mCirclePaint.setColor(NORMAL_COLOR);
        }
        //点与点画线
        if (mSelectedPointViewList.size() > 0) {
            Point pointViewA = mSelectedPointViewList.get(0);  //第一个选中的点为A点
            for (int i = 0; i < mSelectedPointViewList.size(); i++) {
                Point pointViewB = mSelectedPointViewList.get(i);  //其他依次遍历出来的点为B点
                drawLine(canvas, pointViewA, pointViewB);
                pointViewA = pointViewB;
            }

            //点与鼠标当前位置绘制轨迹
            if (mIsMovingWithoutCircle & !mIsFinished) {
                drawLine(canvas, pointViewA, new PointView((int) mCurrentX, (int) mCurrentY));
            }
        }

        super.onDraw(canvas);

    }

    /**
     * 开始画圆
     * 从已有的里面相对于挨个画
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        //初始化点的位置
        for (int i = 0; i < mPointViewArray.length; i++) {
            for (int j = 0; j < mPointViewArray.length; j++) {
                //圆心的坐标
                //圆心的坐标
                int cx = mPatterWidth / 4 * (j + 1);
                int cy = mPatterWidth / 4 * (i + 1);
                //将圆心放在一个点数组中
                PointView pointView = new PointView(cx, cy);
                pointView.setIndex(mindex);
                mPointViewArray[i][j] = pointView;
                canvas.drawCircle(cx, cy, mRadius, mCirclePaint);
                mindex++;


            }
        }
        mindex = 1;

    }

    /**
     * 判断当前按下的位置是否在圆心数组中
     *
     * @return
     */
    private PointView checkSelectPoint() {
        for (int i = 0; i < mPointViewArray.length; i++) {
            for (int j = 0; j < mPointViewArray.length; j++) {
                PointView pointView = mPointViewArray[i][j];
                //判断点是否在圆内
                if (isWithinCircle(mCurrentX, mCurrentY, pointView.x, pointView.y, mRadius)) {
                    return pointView;
                }
            }
        }
        return null;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentX = event.getX();
        mCurrentY = event.getY();
        PointView selectedPointView = null;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重新绘制
                if (monPatternChangerListener != null) {
                    monPatternChangerListener.onPatternStarted(true);
                }

                mSelectedPointViewList.clear();
                mIsFinished = false;

                selectedPointView = checkSelectPoint();

                if (selectedPointView != null) {
                    //第一次按下的位置在圆内，被选中
                    mIsSelected = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsSelected) {
                    selectedPointView = checkSelectPoint();
                }

                if (selectedPointView == null) {
                    mIsMovingWithoutCircle = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsFinished = true;
                mIsSelected = false;
                break;
        }

        //将选中的点收集起来
        if (!mIsFinished && mIsSelected && selectedPointView != null) {
            if (!mSelectedPointViewList.contains(selectedPointView)) {
                mSelectedPointViewList.add(selectedPointView);
            }
        }

        if (mIsFinished) {
            if (mSelectedPointViewList.size() == 1) {
                mSelectedPointViewList.clear();
            } else if (mSelectedPointViewList.size() < 5 && mSelectedPointViewList.size() > 0) {
                //绘制错误
                if (monPatternChangerListener != null) {
                    monPatternChangerListener.onPatternChange(null);
                }
            } else {
                //绘制成功
                String patternPassword = "";
                if (monPatternChangerListener != null) {
                    for (PointView pointView : mSelectedPointViewList) {
                        patternPassword += pointView.getIndex();
                    }

                    if (!TextUtils.isEmpty(patternPassword)) {
                        monPatternChangerListener.onPatternChange(patternPassword);
                    }
                }
            }
        }

        invalidate();
        return true;
    }

    /**
     * 判断点是否在圆内
     *
     * @param x      点X轴坐标
     * @param y      点Y轴坐标
     * @param cx     圆心X坐标
     * @param cy     圆心Y坐标
     * @param radius 半径
     * @return true表示在圆内，false表示在圆外
     */
    public boolean isWithinCircle(float x, float y, float cx, float cy, float radius) {

        //如果点和圆心的距离小于半径，则证明点在园内
        if (Math.sqrt(Math.pow(x - cx, 2)) + Math.pow(y - cy, 2) <= radius*2) {
            return true;
        }

        return false;
    }


    /**
     * 设置图案监听器
     *
     * @param onPatternChangerListener
     */
    public void setMonPatternChangerListener(OnPatternChangerListener onPatternChangerListener) {
        if (onPatternChangerListener != null) {
            this.monPatternChangerListener = onPatternChangerListener;
        }
    }


    public interface OnPatternChangerListener {
        /**
         * 图案改变
         *
         * @param password 密码
         */
        void onPatternChange(String password);

        /**
         * 图案是否重写绘制
         *
         * @param isStarted true重写绘制  false 不重新绘制
         */
        void onPatternStarted(boolean isStarted);

    }


}
