package com.lin.jiang.app.helper;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by JiangLin.<br>
 * Date: 2019/01/11 18:42<br>
 * Description: OpenNotchHelper 刘海屏适配帮助类。<br/>
 * 使用方法：<br/>
 * 预先调用 {@link OpenNotchHelper#checkNotch(View)} 方法进行刘海屏检测，然后使用 {@link OpenNotchHelper.Notch} 其中的相关信息。
 * 如果 {@link OpenNotchHelper#checkNotch(View)} 方法不能满足需求，使用者可以自行使用 {@link OpenNotchHelper#hasNotch(View)} 和 {@link OpenNotchHelper#hasNotch(Activity)}
 * 判断设备是否为刘海屏；可以使用 {@link OpenNotchHelper#getSafeInsetTop(View)} 和 {@link OpenNotchHelper#getSafeInsetTop(Activity)} 等 getSafeInsetXxx() 系列方法获取安全区域与屏幕边缘的距离。
 * <ul>
 * 注意事项：
 * <li>1.通过 {@link OpenNotchHelper#hasNotch(View)} 获取刘海屏信息并传参为 View 时，View 必须是已经 attach 到 window 上了的</li>
 * <li>2.刘海屏适配并不能兼容到 Vivo、Oppo 等手机提供的所有设置项，更不能兼容到某些厂商白名单带来的不同效果</li>
 * <li>3.小米8在横屏状态下 WindowInsets 的左右值会出错，导致 fitSystemWindows 失效。此外，旋转屏幕，小米也不会重下发 windowInsets</li>
 * </ul>
 *
 * @author JiangLin
 */
public class OpenNotchHelper {
    /**
     * 刘海检测，建议在 MainActivity#onCreate() 中调用，后续使用时可以直接使用 {@link OpenNotchHelper.Notch} 中的相关信息。
     * <p>
     * 如果此封装方法不能满足需求，使用者可以自行使用 {@link OpenNotchHelper#hasNotch(View)} 和 {@link OpenNotchHelper#hasNotch(Activity)} 判断设备是否为刘海屏；
     * 可以使用 {@link OpenNotchHelper#getSafeInsetTop(View)} 和 {@link OpenNotchHelper#getSafeInsetTop(Activity)} 等 getSafeInsetXxx() 系列方法获取安全区域与屏幕边缘的距离。
     *
     * @param decorView 这里并不要求是decorView，只是在MainActivity#onCreate()中使用会比较方便。比如MgtvNotchHelper.checkNotch(getWindow().getDecorView());
     */
    public static void checkNotch(final View decorView) {
        Log.d("[jianglin]", "OpenNotchHelper.checkNotch: {API=" + Build.VERSION.SDK_INT + ", MANUFACTURER=" + Build.MANUFACTURER
                + ", BRAND=" + Build.BRAND + ", MODEL=" + Build.MODEL + ", AndroidOS=" + Build.VERSION.RELEASE + "}");
        Notch.sChecked = false;
        decorView.post(new Runnable() {
            @Override
            public void run() {
                Notch.sChecked = true;
                Notch.sHasNotch = hasNotch(decorView);
                Notch.sLeft = getSafeInsetLeft(decorView);
                Notch.sTop = getSafeInsetTop(decorView);
                Notch.sRight = getSafeInsetRight(decorView);
                Notch.sBottom = getSafeInsetBottom(decorView);
                Log.d("[jianglin]", "OpenNotchHelper.checkNotch: notch[" + Notch.sHasNotch + "{" + Notch.sLeft + ", " + Notch.sTop + ", "
                        + Notch.sRight + ", " + Notch.sBottom + "}]");
            }
        });
    }

    /**
     * 设备刘海屏信息，注意本类中的 left, top 指的是竖屏状态下，安全区域与屏幕边缘左端、顶部的距离，单位px，所以全屏时左边距应该设置成这里的 {@link Notch#sTop}，
     * 除非在全屏变换后再次调用 {@link OpenNotchHelper#checkNotch(View)}。
     * <p>
     * 本类必须在 {@link OpenNotchHelper#checkNotch(View)} 方法调用成功之后才能使用，静态变量 {@link Notch#sChecked} 表示 {@link OpenNotchHelper#checkNotch(View)} 方法是否有调用成功。
     */
    public static class Notch {
        /** 是否完成刘海屏检测 */
        public static boolean sChecked;
        /** 是否为刘海屏 */
        public static boolean sHasNotch;
        /** 竖屏下的安全区域与左侧的间隔 */
        public static int sLeft;
        /** 竖屏下的安全区域与顶部的间隔 */
        public static int sTop;
        /** 竖屏下的安全区域与右侧的间隔 */
        public static int sRight;
        /** 竖屏下的安全区域与底部的间隔 */
        public static int sBottom;
    }

    //---------- hasNotch ----------//

    /**
     * 检测设备是否为刘海屏
     *
     * @param activity activity instance
     *
     * @return true|刘海屏, false|非刘海屏
     */
    public static boolean hasNotch(Activity activity) {
        return NotchHelper.hasNotch(activity);
    }

    /**
     * 检测设备是否为刘海屏
     *
     * @param view 已经attach到window的 view
     *
     * @return true|刘海屏, false|非刘海屏
     */
    public static boolean hasNotch(View view) {
        return NotchHelper.hasNotch(view);
    }

    //---------- getSafeInsetTop ----------//

    /**
     * 安全区域到屏幕顶部的距离
     *
     * @param activity activity instance
     *
     * @return 距离，单位px
     */
    public static int getSafeInsetTop(Activity activity) {
        return NotchHelper.getSafeInsetTop(activity);
    }

    /**
     * 安全区域到屏幕顶部的距离
     *
     * @param view 已经attach到window的 view
     *
     * @return 距离，单位px
     */
    public static int getSafeInsetTop(View view) {
        return NotchHelper.getSafeInsetTop(view);
    }

    //---------- getSafeInsetBottom ----------//

    /**
     * 安全区域到屏幕底部的距离
     *
     * @param activity activity instance
     *
     * @return 距离，单位px
     */
    public static int getSafeInsetBottom(Activity activity) {
        return NotchHelper.getSafeInsetBottom(activity);
    }

    /**
     * 安全区域到屏幕底部的距离
     *
     * @param view 已经attach到window的 view
     *
     * @return 距离，单位px
     */
    public static int getSafeInsetBottom(View view) {
        return NotchHelper.getSafeInsetBottom(view);
    }

    //---------- getSafeInsetLeft ----------//

    /**
     * 安全区域到屏幕左边缘的距离
     *
     * @param activity activity instance
     *
     * @return 距离，单位px
     */
    public static int getSafeInsetLeft(Activity activity) {
        return NotchHelper.getSafeInsetLeft(activity);
    }

    /**
     * 安全区域到屏幕左边缘的距离
     *
     * @param view 已经attach到window的 view
     *
     * @return 距离，单位px
     */
    public static int getSafeInsetLeft(View view) {
        return NotchHelper.getSafeInsetLeft(view);
    }

    //---------- getSafeInsetRight ----------//

    /**
     * 安全区域到屏幕右边缘的距离
     *
     * @param activity activity instance
     *
     * @return 距离，单位px
     */
    public static int getSafeInsetRight(Activity activity) {
        return NotchHelper.getSafeInsetRight(activity);
    }

    /**
     * 安全区域到屏幕右边缘的距离
     *
     * @param view 已经attach到window的 view
     *
     * @return 距离，单位px
     */
    public static int getSafeInsetRight(View view) {
        return NotchHelper.getSafeInsetRight(view);
    }

    /**
     * 查询已经完成刘海屏手动适配的厂商
     *
     * @return 厂商列表
     */
    public static List<String> getSupportManufacturers() {
        return NotchHelper.getSupportManufacturers();
    }

}
