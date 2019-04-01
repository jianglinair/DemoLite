package com.lin.jiang.app.seek;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;

import com.lin.jiang.app.R;


/**
 * @author Administrator
 */
public class SeekImageActivity extends AppCompatActivity {
    private static final String TAG = "SeekImageActivity";

    private static final int WINDOW_WIDTH = 200;
    private static final int WINDOW_HEIGHT = 112;

    /**
     * 显示图片的窗口
     */
    private CustomImageView mCustomImageView;
    /**
     * 拖动条
     */
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_image);

        mCustomImageView = findViewById(R.id.civ_window);
        mSeekBar = findViewById(R.id.sb_controller);
        mSeekBar.setMax(52);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seek_test_3, opts);
        mCustomImageView.setOriginalBitmap(bitmap);

        setOnSeekBarChangeListener();
    }

    private void setOnSeekBarChangeListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int xCount, yCount;
                xCount = progress % 10;
                yCount = progress / 10;
                Log.d(TAG, "onProgressChanged: progress = " + progress + ", xCount = " + xCount + ", yCount = " + yCount);
                mCustomImageView.handleScroll(xCount * WINDOW_WIDTH, yCount * WINDOW_HEIGHT);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
