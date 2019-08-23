package com.lin.jiang.app.anim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.lin.jiang.app.R;

public class PropertyAnimation5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation5);

        ArcView arcView = findViewById(R.id.arcView);
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(v -> arcView.setCurStep(10000, 8765));
    }
}
