package com.lin.jiang.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jiang.lin.demo.fragment.Fragment1;
import com.lin.jiang.app.danmaku.DanmakuActivity;

/**
 * @author Jiang Lin
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn_main);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route();
            }
        });
    }

    private void route() {
        Intent intent = new Intent(this, DanmakuActivity.class);
        startActivity(intent);
    }

    private void print() {
        System.out.println(getCacheDir());
        System.out.println(getFilesDir());
        System.out.println(getExternalCacheDir());
        System.out.println(getExternalFilesDir(null));
        System.out.println(Environment.getExternalStorageDirectory());
        System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    private void addFragment() {
        Fragment1 fragment1 = new Fragment1();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_container, fragment1);
        transaction.addToBackStack("");
        transaction.commitAllowingStateLoss();
    }
}
