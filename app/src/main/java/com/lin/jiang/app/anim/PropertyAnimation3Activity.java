package com.lin.jiang.app.anim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.lin.jiang.app.R;

public class PropertyAnimation3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation3);

        SuccessView successView = findViewById(R.id.successView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> successView.startAnimation());

        FailView failView = findViewById(R.id.failView);
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> failView.startAnimation());
    }
}
