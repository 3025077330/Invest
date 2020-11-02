package com.bw.invest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bw.framwork.R;

public class MyProgress extends View {
    private int circleColor;
    private int arcColor;
    private int textColor;
    private float textSize;
    private float strokeWidth;
    private float roundSize;//圆形的大小
    //progressView的宽度和高度
    private int progressViewWidth;
    private int progressViewHeight;
    private int progress; //占比
    private int offseAngle;
    private int startAngle = 0;
    private int stepAngle = 1;
    private int progressAngle;
    private boolean flagBig = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            invalidate();//再次触发绘制方法   刷新UI
            if (progressAngle <= offseAngle) {
                //进度累加
                progressAngle += stepAngle;
                handler.sendEmptyMessageDelayed(1, 20);//延时20毫秒再次递归
            }

        }
    };

    public MyProgress(Context context) {
        super(context);
        init(context, null);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //获取属性的值,属性值是从布局文件里设置的
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyProgress);
        circleColor = typedArray.getColor(R.styleable.MyProgress_circle_color, Color.BLUE);
        arcColor = typedArray.getColor(R.styleable.MyProgress_arc_color, Color.RED);
        textColor = typedArray.getColor(R.styleable.MyProgress_text_color, Color.RED);
        textSize = typedArray.getDimension(R.styleable.MyProgress_text_size, 15.0f);
        strokeWidth = typedArray.getInt(R.styleable.MyProgress_stroke_width, 5);
        roundSize = typedArray.getDimension(R.styleable.MyProgress_round_size, 200.0f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        progressViewWidth = getMeasuredWidth();//控件的  宽
        progressViewHeight = getMeasuredHeight();//控件的高
        int widthSize = 0;
        int heightSize = 0;
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthmode == MeasureSpec.AT_MOST) {
            //表示该空件在布局文件中，它的高度是wrap_content
            if (flagBig) {
                widthSize = 500;
                progressViewWidth = 500;
                roundSize = 400;
            } else {
                widthSize = 300;
                progressViewWidth = 300;
            }
        } else {
            //就不需要适配直接获取目前的值
            widthSize = MeasureSpec.getSize(widthMeasureSpec);
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            //表示该空件在布局文件中，它的高度是wrap_content
            if (flagBig) {
                heightSize = 500;
                roundSize = 400;
                progressViewHeight = 500;
            } else {
                heightSize = 300;
                progressViewHeight = 300;
            }//针对高度是wrap_content, 给高度设置一个默认高度是300

        } else {
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
        }


        setMeasuredDimension(widthSize, heightSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取圆形的圆心
        int cX = progressViewWidth / 2;
        int cY = progressViewHeight / 2;
        float radius = roundSize / 2;//半径
        Paint paint = new Paint();
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(cX, cY, radius, paint);
        //接下来画弧形
        if (progress == 0) {
            //代表没有进度，不需要画弧形
            return;
        }
        //弧形对象
        RectF rectF = new RectF(progressViewWidth / 2 - roundSize / 2, progressViewHeight / 2 - roundSize / 2, progressViewWidth / 2 + roundSize / 2, progressViewHeight / 2 + roundSize / 2);
        paint.setColor(arcColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(rectF, 0, progressAngle, false, paint);
        String progressText = (progressAngle * 100) / 360 + "%";
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(2);
        Rect rect = new Rect();
        paint.getTextBounds(progressText, 0, progressText.length(), rect);
        canvas.drawText(progressText, progressViewWidth / 2 - rect.width() / 2, progressViewHeight / 2 + rect.height() / 2, paint);

    }

    public void setProgress(int progress) {
        this.progress = progress;
        //扇形总角度
        offseAngle = (360 * progress) / 100;
        progressAngle = startAngle;
        handler.sendEmptyMessage(1);
    }

    //将dp转化为px
    public int dp2px(int dp) {
        //比例
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

}
