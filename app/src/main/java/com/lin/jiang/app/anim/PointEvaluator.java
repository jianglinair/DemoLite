package com.lin.jiang.app.anim;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * Created by JiangLin.<br>
 * Date: 2019/08/20 16:38<br>
 * Description: PointEvaluator <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point start = (Point) startValue;
        Point end = (Point) endValue;
        float x = start.getX() + fraction * (end.getX() - start.getX());
        float y = start.getY() + fraction * (end.getY() - start.getY());
        return new Point(x, y);
    }
}
