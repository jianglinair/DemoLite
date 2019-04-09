package com.lin.jiang.app.helper;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;

/**
 * Created by JiangLin.<br>
 * Date: 2019/01/11 18:02<br>
 * Description: DeviceHelper <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class DeviceHelper {
    private static final String TAG = "DeviceHelper";
    private static final String PATTERN = "(\\d+\\.){2}\\d";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_FLYME_VERSION_NAME = "ro.build.display.id";
    private static final String FLYME = "flyme";
    private static final String ZTEC2016 = "zte c2016";
    private static final String ZUKZ1 = "zuk z1";
    private static final String ESSENTIAL = "essential";
    private static final String MEIZUBOARD[] = {"m9", "M9", "mx", "MX"};
    private static final String BRAND = Build.BRAND.toLowerCase();
    private static String sMiuiVersionName;
    private static String sFlymeVersionName;
    private static boolean sIsTabletChecked = false;
    private static boolean sIsTabletValue = false;

    static {
        Properties properties = new Properties();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // android 8.0，读取 /system/uild.prop 会报 permission denied
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                properties.load(fileInputStream);
            } catch (Exception e) {
                Log.d("[jianglin]", "DeviceHelper.static initializer: read file error");
            } finally {
                close(fileInputStream);
            }
        }

        Class<?> clzSystemProperties;
        try {
            clzSystemProperties = Class.forName("android.os.SystemProperties");
            Method getMethod = clzSystemProperties.getDeclaredMethod("get", String.class);
            // miui
            sMiuiVersionName = getLowerCaseName(properties, getMethod, KEY_MIUI_VERSION_NAME);
            //flyme
            sFlymeVersionName = getLowerCaseName(properties, getMethod, KEY_FLYME_VERSION_NAME);
        } catch (Exception e) {
            Log.d("[jianglin]", "DeviceHelper.static initializer: read SystemProperties error");
        }
    }

    private static boolean isTablet2(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断是否为平板设备
     */
    static boolean isTablet(Context context) {
        if (sIsTabletChecked) {
            return sIsTabletValue;
        }
        sIsTabletValue = isTablet2(context);
        sIsTabletChecked = true;
        return sIsTabletValue;
    }

    /**
     * 判断是否是flyme系统
     */
    private static boolean isFlyme() {
        return !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName.contains(FLYME);
    }

    /**
     * 判断是否是MIUI系统
     */
    static boolean isMIUI() {
        return !TextUtils.isEmpty(sMiuiVersionName);
    }

    static boolean isMIUIV5() {
        return "v5".equals(sMiuiVersionName);
    }

    static boolean isMIUIV6() {
        return "v6".equals(sMiuiVersionName);
    }

    static boolean isMIUIV7() {
        return "v7".equals(sMiuiVersionName);
    }

    static boolean isMIUIV8() {
        return "v8".equals(sMiuiVersionName);
    }

    static boolean isMIUIV9() {
        return "v9".equals(sMiuiVersionName);
    }

    static boolean isFlymeVersionHigher5_2_4() {
        //查不到默认高于5.2.4
        boolean isHigher = true;
        if (sFlymeVersionName != null && !sFlymeVersionName.equals("")) {
            Pattern pattern = Pattern.compile(PATTERN);
            Matcher matcher = pattern.matcher(sFlymeVersionName);
            if (matcher.find()) {
                String versionString = matcher.group();
                if (versionString != null && !versionString.equals("")) {
                    String[] version = versionString.split("\\.");
                    if (version.length == 3) {
                        if (Integer.valueOf(version[0]) < 5) {
                            isHigher = false;
                        } else if (Integer.valueOf(version[0]) > 5) {
                            isHigher = true;
                        } else {
                            if (Integer.valueOf(version[1]) < 2) {
                                isHigher = false;
                            } else if (Integer.valueOf(version[1]) > 2) {
                                isHigher = true;
                            } else {
                                if (Integer.valueOf(version[2]) < 4) {
                                    isHigher = false;
                                } else if (Integer.valueOf(version[2]) >= 5) {
                                    isHigher = true;
                                }
                            }
                        }
                    }

                }
            }
        }
        return isMeizu() && isHigher;
    }

    static boolean isMeizu() {
        return isPhone(MEIZUBOARD) || isFlyme();
    }

    /**
     * 判断是否为小米
     * https://dev.mi.com/doc/?p=254
     */
    static boolean isXiaomi() {
        return Build.MANUFACTURER.toLowerCase().equals("xiaomi");
    }

    static boolean isVivo() {
        return BRAND.contains("vivo") || BRAND.contains("bbk");
    }

    static boolean isOppo() {
        return BRAND.contains("oppo");
    }

    static boolean isHuawei() {
        return BRAND.contains("huawei") || BRAND.contains("honor");
    }

    static boolean isEssentialPhone() {
        return BRAND.contains("essential");
    }


    /**
     * 判断是否为 ZUK Z1 和 ZTK C2016。
     * 两台设备的系统虽然为 android 6.0，但不支持状态栏icon颜色改变，因此经常需要对它们进行额外判断。
     */
    static boolean isZUKZ1() {
        final String board = Build.MODEL;
        return board != null && board.toLowerCase().contains(ZUKZ1);
    }

    static boolean isZTKC2016() {
        final String board = Build.MODEL;
        return board != null && board.toLowerCase().contains(ZTEC2016);
    }

    private static boolean isPhone(String[] boards) {
        final String board = Build.BOARD;
        if (board == null) {
            return false;
        }
        for (String board1 : boards) {
            if (board.equals(board1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断悬浮窗权限（目前主要用户魅族与小米的检测）。
     */
    static boolean isFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24);  // 24 是AppOpsManager.OP_SYSTEM_ALERT_WINDOW 的值，该值无法直接访问
        } else {
            try {
                return (context.getApplicationInfo().flags & 1 << 27) == 1 << 27;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @TargetApi(19)
    private static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, op,
                        Binder.getCallingUid(), context.getPackageName());
                return AppOpsManager.MODE_ALLOWED == property;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Nullable
    private static String getLowerCaseName(Properties p, Method get, String key) {
        String name = p.getProperty(key);
        if (name == null) {
            try {
                name = (String) get.invoke(null, key);
            } catch (Exception ignored) {
            }
        }
        if (name != null) {
            name = name.toLowerCase();
        }
        return name;
    }

    private static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
