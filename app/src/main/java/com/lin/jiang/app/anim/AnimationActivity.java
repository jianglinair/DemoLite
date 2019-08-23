package com.lin.jiang.app.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.lin.jiang.app.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AnimationActivity extends AppCompatActivity {
    /*=========================================================================
     * Variable used in lambda expression should be final or effectively final.
     *
     * 方法的局部变量存储在栈中，而栈是线程私有的，而 lambda 表达式也运行在一个独立的线程中，
     * 所以如果将 isStarted 变量写在 onCreate() 方法中、并且在 lambda 表达式内部需要修改
     * isStarted 变量的值就会出现同步问题。
     *========================================================================*/
    boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ConstraintLayout clContainer = findViewById(R.id.cl_container);

        Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        Button btnTranslate = findViewById(R.id.btn_translate);
        btnTranslate.setOnClickListener(v -> btnTranslate.startAnimation(animTranslate));

        Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        Button btnScale = findViewById(R.id.btn_scale);
        btnScale.setOnClickListener(v -> btnScale.startAnimation(animScale));

        Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        Button btnRotate = findViewById(R.id.btn_rotate);
        btnRotate.setOnClickListener(v -> btnRotate.startAnimation(animRotate));

        Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        Button btnAlpha = findViewById(R.id.btn_alpha);
        btnAlpha.setOnClickListener(v -> btnAlpha.startAnimation(animAlpha));

        Button btnFrame = findViewById(R.id.btn_frame);
        ImageView ivGif = findViewById(R.id.iv_gif);
        ivGif.setImageResource(R.drawable.anim_frame);

        btnFrame.setOnClickListener(v -> {
            AnimationDrawable drawable = (AnimationDrawable) ivGif.getDrawable();
            if (isStarted) {
                drawable.stop();
            } else {
                drawable.start();
            }
            isStarted = !isStarted;
        });

        /*=========================================================================
         * view.getTranslationX() = view.getX() - view.getLeft();
         * 在view的位置没有偏移时，view.getX() = view.getLeft()，偏移后 getX() 的值改变但是
         * getLeft() 的返回值是不变的。
         *========================================================================*/
        Button btnProperty = findViewById(R.id.btn_property);
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(ivGif, "translationX", 0, 300);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(ivGif, "rotation", 0, 360);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(ivGif, "alpha", 1, 0, 1);
        moveIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("[jianglin]", "AnimationActivity.onAnimationEnd: moveIn");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("[jianglin]", "AnimationActivity.onAnimationStart: moveIn");
            }
        });
        rotate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("[jianglin]", "AnimationActivity.onAnimationEnd: rotate");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("[jianglin]", "AnimationActivity.onAnimationStart: rotate");
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotate).with(fadeInOut).after(moveIn);
        animatorSet.setDuration(3000);
        btnProperty.setOnClickListener(v -> {
            Log.d("[jianglin]", "AnimationActivity.onCreate: " + ivGif.getX());
            Log.d("[jianglin]", "AnimationActivity.onCreate: " + ivGif.getLeft());
            Log.d("[jianglin]", "AnimationActivity.onCreate: " + ivGif.getTranslationX());
            animatorSet.start();
            ivGif.postDelayed(() -> {
                Log.d("[jianglin]", "AnimationActivity.onCreate: " + ivGif.getX());
                Log.d("[jianglin]", "AnimationActivity.onCreate: " + ivGif.getLeft());
                Log.d("[jianglin]", "AnimationActivity.onCreate: " + ivGif.getTranslationX());
            }, 7000);
        });


        Button btnProperty2 = findViewById(R.id.btn_property2);
        btnProperty2.setOnClickListener(v -> {
            Intent intent = new Intent(this, PropertyAnimation2Activity.class);
            startActivity(intent);
        });

        Button btnProperty3 = findViewById(R.id.btn_property3);
        btnProperty3.setOnClickListener(v -> {
            Intent intent = new Intent(this, PropertyAnimation3Activity.class);
            startActivity(intent);
        });

        Button btnProperty4 = findViewById(R.id.btn_property4);
        btnProperty4.setOnClickListener(v -> {
            Intent intent = new Intent(this, PropertyAnimation4Activity.class);
            startActivity(intent);
        });

        Button btnProperty5 = findViewById(R.id.btn_property5);
        btnProperty5.setOnClickListener(v -> {
            Intent intent = new Intent(this, PropertyAnimation5Activity.class);
            startActivity(intent);
        });
    }
}
