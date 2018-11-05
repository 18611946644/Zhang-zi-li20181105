package com.bwie.zhangzili20181105.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.bwie.zhangzili20181105.R;

/**
 * date:2018/11/5
 * author:张自力(DELL)
 * function: 疯狂转盘
 */

public class MyView extends View implements View.OnClickListener {

    //定义一个接口
    public interface OnClickMyViewListener {
        void onClick(RotateAnimation rotateAnimation, View v);
    }
    //提供一个接口回调的对象
    private OnClickMyViewListener mOnClickMyViewListener;
    //对外提供一个接口对象的方法
    public void setOnClickMyViewListener(OnClickMyViewListener listener){
        this.mOnClickMyViewListener = listener;
    }

    //定义变量  用来作为标识 小圆中的标识
    private int mPadding;
    private boolean isStart = false;
    private String str = "start";
    private String[] contents = new String[]{
       "一等奖","二等奖","三等奖","四等奖","参与奖","谢谢参与"
    };
    private RotateAnimation mRotateAnimation;
    private Paint mStartpaint;
    private Paint mPaint;
    private int mWidth;
    private RectF mRectF;
    private int mMainColor;

    //1 实现三个方法
    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义View 的自定义属性获取
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabButtonRecyclerView, defStyleAttr, 0);
        //默认color.xml中定义的mainColor
        mMainColor = typedArray.getColor(R.styleable.TabButtonRecyclerView_mainColor, getResources().getColor(R.color.colorPrimary));


        //1.2 初始化操作
        initPaint();
        //1.3 设置动画
        initAnim();
        //1.4 设置监听
        setOnClickListener(this);
    }

    /**
     * 1.3 设置动画方法
     * */
    private void initAnim() {
        //以View 的中心为参考点
        mRotateAnimation = new RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5F,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );
        //设置动画
        for (int i = 0; i < 6; i++) {
            //在不点击的情况下  无限转动
           mRotateAnimation.setRepeatCount(-1);
           mRotateAnimation.setFillAfter(true);
        }
    }

    /**
     * 1.2 笔的初始化
     * */
    private void initPaint() {
        mStartpaint = new Paint();
        //设置画笔
        mStartpaint.setStyle(Paint.Style.STROKE);//描边
        mStartpaint.setStrokeWidth(5);
        mStartpaint.setAntiAlias(true);
        mStartpaint.setColor(Color.WHITE);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);//描边
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    /**
     * 2 重写测量 OnMeasure（）方法
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        //2.2设置padding
        mPadding = 5;
        //2.3定义一个方法
        initRect();
    }

    private void initRect() {
        //按照左上右下的方式
        mRectF = new RectF(0, 0, mWidth, mWidth);
    }

    /**
     * 3 重写绘制方法 onDraw()
     * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆 （最外面的大圆）
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mWidth/2,mWidth/2,mWidth/2-mPadding,mPaint);

        //绘制六个椭圆
        mPaint.setStyle(Paint.Style.FILL);
        initArc(canvas);

        //绘制小圆
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth/2,mWidth/2,50,mPaint);

        //绘制文字
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(25);
        Rect rect = new Rect();
        mPaint.getTextBounds(str,0,str.length(),rect);
        int strWidth = rect.width();//文本宽
        int textheight = rect.height();
        canvas.drawText(str,mWidth/2-25+25-strWidth/2,mWidth/2+textheight/2,mPaint);

    }

    /**
     * 绘制六个椭圆
     * */
    private void initArc(Canvas canvas) {


        //创建一个颜色表
        for (int i = 0; i < 6; i++) {
            mPaint.setColor(colors[i]);
            canvas.drawArc(mRectF,(i-1)*60+60,60,true,mPaint);
        }
        for (int i = 0; i < 6; i++) {
            mPaint.setColor(Color.WHITE);
            Path path = new Path();
            path.addArc(mRectF,(i-1)*60+60,60);
            canvas.drawTextOnPath(contents[i],path,60,60,mPaint);
        }
    }
    //创建一个颜色表
    public int[] colors = new int[]{
      Color.parseColor("#8EE5EE"),
      Color.parseColor("#FFD700"),
      Color.parseColor("#FFD39B"),
      Color.parseColor("#FF8247"),
      Color.parseColor("#FF34B3"),
      Color.parseColor("#F0E68C"),
    };

    /**
     * 1.4 监听事件
     * */
    @Override
    public void onClick(View v) {
        if(!isStart){
           isStart = true;
           mRotateAnimation.setDuration(1000);
           //不停顿
            mRotateAnimation.setInterpolator(new LinearInterpolator());
            Toast.makeText(v.getContext(),"开始",Toast.LENGTH_SHORT).show();
            startAnimation(mRotateAnimation);
            /*mOnClickMyViewListener.onClick(mRotateAnimation,v);*/
        }else{
            isStart = false;
            Toast.makeText(v.getContext(),"停止",Toast.LENGTH_SHORT).show();
            stopAnim();//停止动画的方法
        }
    }

    //停止动画的方法
    private void stopAnim() {
        clearAnimation();
    }
}
