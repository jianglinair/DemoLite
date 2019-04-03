package com.lin.jiang.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.blankj.ALog;

/**
 * Created by JiangLin.<br>
 * Date: 2018/11/16 10:31<br>
 * Description: TestButton
 *
 * @author JiangLin
 */
public class TestButton extends androidx.appcompat.widget.AppCompatButton {
    private static final String TAG = "TestButton";
    public TestButton(Context context) {
        super(context);
    }

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ALog.d(TAG, "onMeasure: IN " + System.currentTimeMillis());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ALog.d(TAG, "onMeasure: OUT " + System.currentTimeMillis());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        ALog.d(TAG, "onLayout: IN " + System.currentTimeMillis());
        super.onLayout(changed, left, top, right, bottom);
        ALog.d(TAG, "onLayout: OUT " + System.currentTimeMillis());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ALog.d(TAG, "onDraw: IN " + System.currentTimeMillis());
        super.onDraw(canvas);
        ALog.d(TAG, "onDraw: OUT " + System.currentTimeMillis());
    }

    @Override
    protected void onFinishInflate() {
        ALog.d(TAG, "onFinishInflate: IN " + System.currentTimeMillis());
        super.onFinishInflate();
        ALog.d(TAG, "onFinishInflate: OUT " + System.currentTimeMillis());
    }

    @Override
    protected void onAttachedToWindow() {
        ALog.d(TAG, "onAttachedToWindow: IN " + System.currentTimeMillis());
        super.onAttachedToWindow();
        ALog.d(TAG, "onAttachedToWindow: OUT " + System.currentTimeMillis());
    }

    @Override
    protected void onDetachedFromWindow() {
        ALog.d(TAG, "onDetachedFromWindow: IN " + System.currentTimeMillis());
        super.onDetachedFromWindow();
        ALog.d(TAG, "onDetachedFromWindow: OUT " + System.currentTimeMillis());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        ALog.d(TAG, "onWindowFocusChanged: IN hasFocus = " + hasFocus + " " + System.currentTimeMillis());
        super.onWindowFocusChanged(hasFocus);
        ALog.d(TAG, "onWindowFocusChanged: OUT hasFocus = " + hasFocus + " " + System.currentTimeMillis());
    }
}
