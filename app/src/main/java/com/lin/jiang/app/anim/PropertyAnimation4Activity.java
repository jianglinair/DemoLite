package com.lin.jiang.app.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lin.jiang.app.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class PropertyAnimation4Activity extends AppCompatActivity {

    private ImageView mIvGood;
    private ImageView mIvShopCar;

    private PointF mStartPoint;
    private PointF mEndPoint;
    private PointF mCtrlPoint;
    private float x, y;

    private AnimatorSet mAnimatorSet;
    private ObjectAnimator mScaleAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation4);

        mIvGood = findViewById(R.id.iv_good);
        mIvShopCar = findViewById(R.id.iv_shop_car);
        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            getPoints();
            startAnimation();
        });
    }

    private void getPoints() {
        reset();

        mStartPoint = new PointF();
        mStartPoint.x = mIvGood.getX();
        mStartPoint.y = mIvGood.getY();
        x = mStartPoint.x;
        y = mStartPoint.y;

        mEndPoint = new PointF();
        mEndPoint.x = mIvShopCar.getX();
        mEndPoint.y = mIvShopCar.getY();

        mCtrlPoint = new PointF();
        mCtrlPoint.x = mStartPoint.x + (mEndPoint.x - mStartPoint.x) * 0.75f;
        mCtrlPoint.y = mStartPoint.y + (mEndPoint.y - mStartPoint.y) * 0.25f;
    }

    private void startAnimation() {
        ValueAnimator pathAnimator = ValueAnimator.ofObject(new PointEvaluator(mCtrlPoint), mStartPoint, mEndPoint);
        pathAnimator.addUpdateListener(animation -> {
            PointF cur = (PointF) animation.getAnimatedValue();
            mIvGood.setX(cur.x);
            mIvGood.setY(cur.y);
        });
        pathAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mScaleAnimator != null) {
                    mScaleAnimator.end();
                    mScaleAnimator = null;
                }
                mIvGood.setVisibility(View.GONE);
                PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 1, 1.1f, 1);
                PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 1, 1.1f, 1);
                mScaleAnimator = ObjectAnimator.ofPropertyValuesHolder(mIvShopCar, x, y);
                mScaleAnimator.setDuration(300);
                mScaleAnimator.start();
            }
        });
        Keyframe keyframe0 = Keyframe.ofFloat(0, 1);
        Keyframe keyframe1 = Keyframe.ofFloat(0.8f, 1);
        Keyframe keyframe2 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("alpha", keyframe0, keyframe1, keyframe2);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofPropertyValuesHolder(mIvGood, holder);
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.setInterpolator(new FastOutSlowInInterpolator());
        mAnimatorSet.playTogether(pathAnimator, alphaAnimator);
        mAnimatorSet.start();
    }

    private void reset() {
        if (x == 0) {
            return;
        }
        mIvGood.setVisibility(View.VISIBLE);
        mIvGood.setX(x);
        mIvGood.setY(y);
    }

    public class PointEvaluator implements TypeEvaluator<PointF> {

        private PointF mCtrlPoint;

        PointEvaluator(PointF ctrlPoint) {
            mCtrlPoint = ctrlPoint;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            int x = (int) ((1 - fraction) * (1 - fraction) * startValue.x + 2 * fraction * (1 - fraction) * mCtrlPoint.x + fraction * fraction * endValue.x);
            int y = (int) ((1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * mCtrlPoint.y + fraction * fraction * endValue.y);
            return new PointF(x, y);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAnimatorSet != null) {
            mAnimatorSet.end();
            mAnimatorSet = null;
        }
        if (mScaleAnimator != null) {
            mScaleAnimator.end();
            mScaleAnimator = null;
        }
    }
}
