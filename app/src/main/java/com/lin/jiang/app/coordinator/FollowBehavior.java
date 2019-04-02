package com.lin.jiang.app.coordinator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * Created by JiangLin.<br>
 * Date: 2019/04/02 17:49<br>
 * Description: FollowBehavior <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class FollowBehavior extends CoordinatorLayout.Behavior {
    public FollowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        child.setX(dependency.getX());
        child.setY(dependency.getY() + dependency.getHeight());
        return true;
    }
}
