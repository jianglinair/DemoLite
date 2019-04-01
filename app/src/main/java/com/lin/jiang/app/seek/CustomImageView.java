package com.lin.jiang.app.seek;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：姜琳
 * 日期：2018/6/11 0011 17:37
 * 说明：CustomImageView
 */
public class CustomImageView extends View {
    private static final String TAG = "CustomImageView";
    Matrix mMatrix;
    /**
     * 原始 bitmap
     */
    private Bitmap mOriginalBitmap;
    /**
     * 适配小窗口的 bitmap
     */
    private Bitmap mAdaptedBitmap;
    private int mWindowWidth = 400;
    private int mWindowHeight = 225;

    public CustomImageView(Context context) {
        super(context);
        matrix();
    }

    private void matrix() {
        // 计算缩放比例
        float scaleWidth = ((float) mWindowWidth) / 200;
        float scaleHeight = ((float) mWindowHeight) / 112;
        // 取得想要缩放的 matrix 参数
        mMatrix = new Matrix();
        mMatrix.postScale(scaleWidth, scaleHeight);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        matrix();
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mAdaptedBitmap != null) {
            canvas.drawBitmap(mAdaptedBitmap, 0, 0, null);
        }
    }

    /**
     * 根据指定的偏移移动并绘制图片
     *
     * @param offX float|正值向右偏移
     * @param offY float|正值向下偏移
     */
    public void handleScroll(float offX, float offY) {
        if (getHeight() > 0 && getWidth() > 0 && mOriginalBitmap != null) {
            if (Math.abs(offY) <= mOriginalBitmap.getHeight() - getHeight()
                    && Math.abs(offX) <= mOriginalBitmap.getWidth() - getWidth()) {
                mAdaptedBitmap = Bitmap.createBitmap(mOriginalBitmap, (int) offX, (int) offY, mWindowWidth, mWindowHeight, mMatrix, true);
                invalidate();
            }
        }
    }

    /**
     * 更新图片
     *
     * @param bitmap Bitmap|需要滚蛋显示的 bitmap
     */
    public void setOriginalBitmap(Bitmap bitmap) {
        mOriginalBitmap = bitmap;
        mAdaptedBitmap = Bitmap.createBitmap(mOriginalBitmap);
        invalidate();
    }
}
