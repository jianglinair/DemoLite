package com.lin.jiang.app.helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;

import androidx.annotation.NonNull;

import static android.view.View.NO_ID;

/**
 * Created by JiangLin.<br>
 * Date: 2019/03/13 19:18<br>
 * Description: NavigationBarHelper <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class NavigationBarHelper {

    private static final String NAVIGATION = "navigationBarBackground";

    // 该方法需要在View完全被绘制出来之后调用，否则判断不了
    //在比如 onWindowFocusChanged（）方法中可以得到正确的结果
    public static boolean isNavigationBarExist(@NonNull Activity activity) {
        if (activity != null) {
            ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
            if (vp != null) {
                for (int i = 0; i < vp.getChildCount(); i++) {
                    vp.getChildAt(i).getContext().getPackageName();
                    if (vp.getChildAt(i).getId() != NO_ID && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }


    public static void isNavigationBarExist(Activity activity, final OnNavigationStateListener onNavigationStateListener) {
        if (activity == null) {
            return;
        }
        final int height = getNavigationHeight(activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            activity.getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets windowInsets) {
                    boolean isShowing = false;
                    int l = 0, t = 0, r = 0, b = 0;
                    if (windowInsets != null) {
                        l = windowInsets.getSystemWindowInsetLeft();
                        t = windowInsets.getSystemWindowInsetTop();
                        r = windowInsets.getSystemWindowInsetRight();
                        b = windowInsets.getSystemWindowInsetBottom();
                        isShowing = (l == height) || (t == height) || (r == height) || (b == height);
                    }
                    if (onNavigationStateListener != null/* && h <= height*/) {
                        onNavigationStateListener.onNavigationState(isShowing, l, t, r, b);
                    }
                    return windowInsets;
                }
            });
        }
    }

    public static void isNavigationBarExist(Context context, Window window, final OnNavigationStateListener onNavigationStateListener) {
        if (context == null || window == null) {
            return;
        }
        final int height = getNavigationHeight(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            window.getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets windowInsets) {
                    boolean isShowing = false;
                    int l = 0, t = 0, r = 0, b = 0;
                    if (windowInsets != null) {
                        l = windowInsets.getSystemWindowInsetLeft();
                        t = windowInsets.getSystemWindowInsetTop();
                        r = windowInsets.getSystemWindowInsetRight();
                        b = windowInsets.getSystemWindowInsetBottom();
                        isShowing = (l == height) || (t == height) || (r == height) || (b == height);
                    }
                    if (onNavigationStateListener != null/* && h <= height*/) {
                        onNavigationStateListener.onNavigationState(isShowing, l, t, r, b);
                    }
                    return windowInsets;
                }
            });
        }
    }

    public static int getNavigationHeight(Context context) {
        if (context == null) {
            return 0;
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        int height = 0;
        if (resourceId > 0) {
            //获取NavigationBar的高度
            height = resources.getDimensionPixelSize(resourceId);
        }
        return height;
    }

    public interface OnNavigationStateListener {
        /**
         * 虚拟导航栏相关信息，包含了竖屏、横屏等状态
         *
         * @param isShowing 是否显示
         * @param left      window到屏幕左边距离，left 大于0表示导航栏在左边
         * @param top       window到屏幕顶部距离，top 大于0表示导航栏在顶部
         * @param right     window到屏幕右边距离，right 大于0表示导航栏在右边
         * @param bottom    window到屏幕底部距离，bottom 大于0表示导航栏在底部
         */
        void onNavigationState(boolean isShowing, int left, int top, int right, int bottom);
    }

}
