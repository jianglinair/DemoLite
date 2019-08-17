package com.lin.jiang.app.anim;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.lin.jiang.app.R;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        Button btnTranslate = findViewById(R.id.btn_translate);
        btnTranslate.setOnClickListener(v -> btnTranslate.startAnimation(animTranslate));

        Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        Button btnScale = findViewById(R.id.btn_scale);
        btnScale.setOnClickListener(v -> btnScale.startAnimation(animScale));
    }
}
