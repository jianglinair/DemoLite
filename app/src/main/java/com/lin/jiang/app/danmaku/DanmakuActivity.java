package com.lin.jiang.app.danmaku;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.ALog;
import com.lin.jiang.app.R;

import java.util.HashMap;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.ui.widget.DanmakuView;


public class DanmakuActivity extends AppCompatActivity {

    private DanmakuView mDanmakuView;
    private DanmakuContext mDanmakuContext;
    private AcFunDanmakuParser mDanmakuParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ALog.d("onCreate: IN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmaku);

        mDanmakuView = findViewById(R.id.dv_danmaku);
        Button show = findViewById(R.id.btn_show);
        Button hide = findViewById(R.id.btn_hide);
        Button post = findViewById(R.id.btn_post);
        Button pause = findViewById(R.id.btn_pause);
        Button resume = findViewById(R.id.btn_resume);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDanmakuView.show();
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDanmakuView.hide();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextMessage();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDanmakuView.pause();
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDanmakuView.resume();
            }
        });

        initDanmaku();
    }

    private void initDanmaku() {
        ALog.d("initDanmaku: IN");
        mDanmakuContext = DanmakuContext.create();
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 10)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
                // .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
                // .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);

        mDanmakuParser = new AcFunDanmakuParser();
        if (mDanmakuView == null) {
            ALog.d("initDanmaku: mDanmakuView is null, return.");
            return;
        }
        ALog.d("initDanmaku: DanmakuView.prepare(BaseDanmakuParser parser, DanmakuContext config)");
        mDanmakuView.prepare(mDanmakuParser, mDanmakuContext); // -> BaseDanmakuParser#getDanmakus() -> AcFunDanmakuParser#parse()
        mDanmakuView.showFPS(true);
        mDanmakuView.enableDanmakuDrawingCache(true);
        mDanmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                mDanmakuView.start();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {
//                ALog.d("danmakuShown: " + danmaku.text);
            }

            @Override
            public void drawingFinished() {
                mDanmakuView.seekTo(0L);
            }
        });
    }

    private void sendTextMessage() {
        addDanmaku(true);
    }

    private int mCount = 1;
    private void addDanmaku(boolean isLive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = "新增[" + mCount + "]条弹幕：" + System.currentTimeMillis();
        danmaku.padding = 5;
        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = isLive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200);
        danmaku.textSize = 20f * (mDanmakuParser.getDisplayer().getDensity() - 0.6f); //文本弹幕字体大小
        danmaku.textColor = getRandomColor(); //文本的颜色
        danmaku.textShadowColor = getRandomColor(); //文本弹幕描边的颜色
        danmaku.underlineColor = getRandomColor(); //文本弹幕下划线的颜色
        danmaku.borderColor = getRandomColor(); //边框的颜色
        mDanmakuView.addDanmaku(danmaku);
        mCount++;
    }

    /**
     * 从一系列颜色中随机选择一种颜色
     *
     * @return color
     */
    private int getRandomColor() {
        int[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.CYAN, Color.BLACK, Color.DKGRAY};
        int i = ((int) (Math.random() * 10)) % colors.length;
        return colors[i];
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }
}
