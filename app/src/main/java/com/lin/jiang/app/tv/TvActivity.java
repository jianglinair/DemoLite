package com.lin.jiang.app.tv;

import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.lin.jiang.app.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TvActivity extends AppCompatActivity {

    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        ButterKnife.bind(this);

        test();
    }

    private void test() {
        String text = "根据公司经营发展规划";
        mTvTest.setText(text);
        mTvTest.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int lineCount = mTvTest.getLineCount();
                Log.d("[jianglin]", "TvActivity.onPreDraw: count=" + lineCount);
                if (lineCount > 3) {
                    mTvTest.setMaxLines(3);
                }
                mTvTest.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    private int getLineCount(String text, TextView tv) {
        StaticLayout staticLayout = new StaticLayout(text, tv.getPaint(), tv.getMeasuredWidth() - tv.getPaddingLeft() - tv.getPaddingRight(),
                Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        return staticLayout.getLineCount();
    }
}
