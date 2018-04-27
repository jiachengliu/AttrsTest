package com.android.attrstest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.android.attrstest.R;

/**
 * Created by liujiacheng on 2018/4/27.
 * 自定义View,只显示二行文字。
 */

public class CustomView extends View{

    /**
     * 绘制的文字
     */
    private String mText;
    private String mText1;
    /**
     * 文字的颜色
     */
    private int mTextColor;
    /**
     * 文字的大小
     */
    private int mTextSize;
    /**
     * 绘制控制文本的范围
     */
    private Rect mBound;

    private Paint mPaint;

    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.attrTest,defStyleAttr,0);//最后一个参数是代码默认res。
        float score = typedArray.getFloat(R.styleable.attrTest_score,0);
        int age = typedArray.getInteger(R.styleable.attrTest_age,0);
        String name = typedArray.getString(R.styleable.attrTest_name);
        int sex = typedArray.getInt(R.styleable.attrTest_sex,1);//默认1表示男
        String sexStr = "男";
        if (sex==2) sexStr = "女";
        mText = "姓名:"+name+",性别："+sexStr;
        mText1 = "年龄:"+age+",分数:"+score;
        mTextColor = Color.BLACK;
        mTextSize = 80;
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //获得绘制文本的宽和高
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 参数解释：text:待绘制的文字，x:绘制的起点x轴坐标，y:绘制的起点的y轴坐标，Paint:绘画的笔（其有对应的对个属性）。
         */
        canvas.drawText(mText, getWidth() / 2 - mBound.width() / 2, getHeight()/3 + mBound.height() / 2, mPaint);
        canvas.drawText(mText1, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }
}


