package com.lin.jiang.app.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by JiangLin.<br>
 * Date: 2019/08/20 17:03<br>
 * Description: MyAnimView <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class MyAnimView extends View {

    private static final float RADIUS = 50f;
    private String color;
    private Point mCurPoint;
    private Paint mPaint;

    public MyAnimView(Context context) {
        super(context);
        init();
    }

    public MyAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    private void startAnimation() {
        Point start = new Point(RADIUS, RADIUS);
        Point end = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), start, end);
        animator.addUpdateListener(animation -> {
            mCurPoint = (Point) animation.getAnimatedValue();
            //            invalidate();
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator animator1 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#0000FF", "#FF0000");
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(5000);
        set.play(animator).with(animator1);
        set.start();
    }

    private void drawCircle(Canvas canvas) {
        float x = mCurPoint.getX();
        float y = mCurPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurPoint == null) {
            mCurPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

}
