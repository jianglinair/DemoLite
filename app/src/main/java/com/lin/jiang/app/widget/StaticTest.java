package com.lin.jiang.app.widget;

import com.blankj.ALog;

/**
 * Created by JiangLin.<br>
 * Date: 2018/12/04 18:00<br>
 * Description: com.lin.jiang.app.widget.StaticTest <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class StaticTest {

    private static long a = System.currentTimeMillis();

    static {
        ALog.d("StaticTest static initializer: " + System.currentTimeMillis());
    }

    public static void test() {
        ALog.d("StaticTest test: c=" + System.currentTimeMillis());
        ALog.d("StaticTest test: a=" + a);
    }
}
