package com.lin.jiang.app.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 * Created by JiangLin.<br>
 * Date: 2019/08/23 15:21<br>
 * Description: ArcView <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class ArcView extends View {

    /**
     * 圆弧宽度
     */
    private static final float STROKE_WIDTH = 38;
    /**
     * 圆弧固定开始角度
     */
    private static final float ANGEL_START = 135;
    /**
     * 圆弧最大跨越角度
     */
    private static final float ANGEL_SWEEP = 270;
    /**
     * 绘制文案
     */
    private String mStepString = "步数";
    /**
     * 用户走的步数
     */
    private int mStepCount;
    private int mStepTotal;

    private int step;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
        invalidate();
    }

    private RectF mRect = new RectF();
    private Paint mYellowArcPaint;
    private Paint mRedArcPaint;
    private Paint mStepPaint;
    private Rect mStepBounds = new Rect();
    private Paint mTextPaint;
    private Rect mTextBounds = new Rect();

    public ArcView(Context context) {
        super(context);
        initPaint();
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = Math.min(getWidth(), getHeight());
        mRect.set(STROKE_WIDTH, STROKE_WIDTH, w - STROKE_WIDTH, w - STROKE_WIDTH);
        float degree = (float) step / mStepTotal * ANGEL_SWEEP;
        canvas.drawArc(mRect, ANGEL_START, ANGEL_SWEEP, false, mYellowArcPaint);
        canvas.drawArc(mRect, ANGEL_START, degree, false, mRedArcPaint);
        canvas.drawText(String.valueOf(step), w / 2f, w / 2f + mStepBounds.height() / 2f, mStepPaint);
        canvas.drawText(mStepString, w / 2f, w / 2f + mTextBounds.height() * 2, mTextPaint);
    }

    private void initPaint() {
        mYellowArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mYellowArcPaint.setColor(Color.YELLOW);
        mYellowArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mYellowArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mYellowArcPaint.setStyle(Paint.Style.STROKE);
        mYellowArcPaint.setStrokeWidth(STROKE_WIDTH);

        mRedArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRedArcPaint.setColor(Color.RED);
        mRedArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mRedArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mRedArcPaint.setStyle(Paint.Style.STROKE);
        mRedArcPaint.setStrokeWidth(STROKE_WIDTH);

        mStepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStepPaint.setTextAlign(Paint.Align.CENTER);
        mStepPaint.setTextSize(dipToPx(20));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mStepPaint.setTypeface(font);//字体风格
        mStepPaint.setColor(Color.RED);
        mStepBounds.setEmpty();
        mStepPaint.getTextBounds(String.valueOf(mStepCount), 0, String.valueOf(mStepCount).length(), mStepBounds);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(dipToPx(16));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.getTextBounds(mStepString, 0, mStepString.length(), mTextBounds);
    }

    public void setCurStep(int total, int user) {
        mStepCount = user;
        mStepTotal = total;
        mStepString = "步数|" + total;
        if (user > total) {
            user = total;
        }
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "step", 0, user);
        animator.setDuration(1500);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.start();
    }

    /**
     * dip 转换成px
     */
    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }
}
