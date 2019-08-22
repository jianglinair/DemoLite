package com.lin.jiang.app.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by JiangLin.<br>
 * Date: 2019/08/21 19:48<br>
 * Description: FailedView <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class FailView extends View {
    private static final int STROKE_WIDTH = 10;
    private static final int PADDING = 20;

    private Paint mCirclePaint;
    private Paint mLinePaint;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;
    private RectF mRect = new RectF();
    private AnimatorSet mAnimatorSet;
    private ObjectAnimator mScaleAnimator;

    //---------- 自定义属性START ----------//

    /**
     * ObjectAnimator 使用的属性，用于画圆
     */
    private float degree;

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
        invalidate();
    }

    /**
     * ObjectAnimator 使用的属性，用于画左边直线
     */
    private float leftValue;

    public float getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(float leftValue) {
        this.leftValue = leftValue;
        invalidate();
    }

    /**
     * ObjectAnimator 使用的属性，用于画右边直线
     */
    private float rightValue;

    public float getRightValue() {
        return rightValue;
    }

    public void setRightValue(float rightValue) {
        this.rightValue = rightValue;
        invalidate();
    }

    //---------- 自定义属性END ----------//

    public FailView(Context context) {
        super(context);
        initPaint();
    }

    public FailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public FailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public void startAnimation() {
        reset();
        initAnimator();
        if (mAnimatorSet != null && !mAnimatorSet.isRunning()) {
            mAnimatorSet.start();
        }
    }

    private void initPaint() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        mCirclePaint.setStrokeWidth(STROKE_WIDTH);
        mCirclePaint.setColor(Color.WHITE);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mLinePaint.setStrokeWidth(STROKE_WIDTH);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = getMeasuredWidth() / 2f;
        mCenterY = getMeasuredHeight() / 2f;
        mRadius = Math.min(mCenterX, mCenterY) - PADDING;
        mRect.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (degree > 0) {
            canvas.drawArc(mRect, 0, degree, false, mCirclePaint);
        }
        if (leftValue > 0) {
            canvas.drawLine(mCenterX - mRadius / 2, mCenterY - mRadius / 2,
                    mCenterX - mRadius / 2 + leftValue, mCenterY - mRadius / 2 + leftValue, mLinePaint);
        }
        if (rightValue > 0) {
            canvas.drawLine(mCenterX + mRadius / 2, mCenterY - mRadius / 2,
                    mCenterX + mRadius / 2 - rightValue, mCenterY - mRadius / 2 + rightValue, mLinePaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimatorSet != null) {
            mAnimatorSet.end();
            mAnimatorSet = null;
        }
        if (mScaleAnimator != null) {
            mScaleAnimator.end();
            mScaleAnimator = null;
        }
        clearAnimation();
    }

    private void initAnimator() {
        if (mAnimatorSet != null) {
            return;
        }
        ObjectAnimator circleAnimator = ObjectAnimator.ofFloat(this, "degree", 0, 360);
        circleAnimator.setDuration(700);
        ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(this, "leftValue", 0, mRadius);
        leftAnimator.setDuration(200);
        ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(this, "rightValue", 0, mRadius);
        rightAnimator.setDuration(200);
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("scaleX", 1, 1.05f, 1);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("scaleY", 1, 1.05f, 1);
        mScaleAnimator = ObjectAnimator.ofPropertyValuesHolder(this, xHolder, yHolder);
        mScaleAnimator.setDuration(350);
        mScaleAnimator.setInterpolator(new BounceInterpolator());
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playSequentially(circleAnimator, leftAnimator, rightAnimator);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCirclePaint.setColor(Color.RED);
                mLinePaint.setColor(Color.RED);
                mScaleAnimator.start();
            }
        });
    }

    private void reset() {
        degree = 0;
        leftValue = 0f;
        rightValue = 0f;
        mCirclePaint.setColor(Color.WHITE);
        mLinePaint.setColor(Color.WHITE);
    }
}
