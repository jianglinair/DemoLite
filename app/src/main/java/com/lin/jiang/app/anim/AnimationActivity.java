package com.lin.jiang.app.anim;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    }
}
