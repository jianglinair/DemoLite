package com.lin.jiang.app.other;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lin.jiang.app.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenOrientationActivity extends AppCompatActivity {

    @BindView(R.id.btn_landscape)
    Button mBtnLandscape;
    @BindView(R.id.btn_portrait)
    Button mBtnPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_orientation);
        ButterKnife.bind(this);
        Log.d("[jianglin]", "ScreenOrientationActivity.onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("[jianglin]", "ScreenOrientationActivity.onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("[jianglin]", "ScreenOrientationActivity.onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("[jianglin]", "ScreenOrientationActivity.onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("[jianglin]", "ScreenOrientationActivity.onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("[jianglin]", "ScreenOrientationActivity.onDestroy: ");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("[jianglin]", "ScreenOrientationActivity.onConfigurationChanged: " + newConfig.orientation);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("[jianglin]", "ScreenOrientationActivity.onAttachedToWindow: ");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("[jianglin]", "ScreenOrientationActivity.onDetachedFromWindow: ");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("[jianglin]", "ScreenOrientationActivity.onWindowFocusChanged: hasFocus=" + hasFocus);
    }

    @OnClick({R.id.btn_landscape, R.id.btn_portrait})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_landscape:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                break;
            case R.id.btn_portrait:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                break;
            default:
        }
    }
}
