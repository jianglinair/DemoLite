package com.lin.jiang.app.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by JiangLin.<br>
 * Date: 2019/08/15 09:57<br>
 * Description: ScrollerLayout <br>
 * <p>
 * Scroller的基本用法其实还是比较简单的，主要可以分为以下几个步骤：
 * <br>
 * 1. 创建Scroller的实例<br>
 * 2. 调用startScroll()方法来初始化滚动数据并刷新界面<br>
 * 3. 重写computeScroll()方法，并在其内部完成平滑滚动的逻辑<br>
 *
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class ScrollerLayout extends ViewGroup {

    private Scroller mScroller;
    private int mTouchSlop; // 最小滑动距离
    private float mXDown; // 手指按下时的坐标
    private float mXMove; // 手指滑动时的实时坐标
    private float mXLastMove; // 上次触发 ACTION_MOVE 时的坐标
    private int leftBorder; // 界面可滚动的左边界
    private int rightBorder; // 界面可滚动的右边界

    public ScrollerLayout(Context context) {
        super(context);
        init(context);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 第一步，创建Scroller实例
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop的值
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    /*=========================================================================
     * getMeasuredWidth() 和 getWidth() 的区别：
     * 1. getMeasuredWidth() 的值在 measure 过程之后就可以获得，getWidth() 的值需要在 layout 之后才可以获得；
     * 2. getMeasuredWidth() 的值是通过 setMeasuredDimension() 方法来设置的，而 getWidth() 的值是视图右坐标减去左坐标计算出来的，
     * 即 view.layout(int l, int t, int r, int b) 中的 r - l。
     *========================================================================*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        for (int i = 0; i < getChildCount(); i++) {
//            // 测量子控件
//            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
//        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(childCount - 1).getRight();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                // 手指滑动距离大于TouchSlop值时认为是滚动，此时拦截事件不分发给子控件，由自身的onTouchEvent()处理
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
            default:
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                float scrolled = mXLastMove - mXMove;
                if (getScrollX() + scrolled < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolled > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                }
                scrollBy((int) scrolled, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起时根据当前滚动值判断应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0 , dx, 0);
                invalidate();
                performClick();
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    //---------- 消除警告：overrides onTouchEvent but not performClick ----------//
    /*=========================================================================
     * 此警告实际上可以不用在意，因为一般不会在重写了 onTouchEvent() 之后还会给控件添加 onClick 事件。
     * 消除办法：
     * 1. 重写 performClick() 方法，直接 return super.performClick() 即可；
     * 2. 在 ACTION_UP 分支中添加 performClick()，由于不会给此控件添加点击事件，所以 performClick() 实际上不会做实际的事情。
     *========================================================================*/

    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    //---------- Scroller 代码模板 ----------//
    /*
    // 一、实例化Scroller对象，在自定义View中，mContext可以在自定义View的构造方法中获取
    Scroller mScroller = new Scroller(mContext);

    // 二、在一个自定义View中实现该方法，方法名可以自定义
    public void smoothScrollTo(int destX,int destY){
        int scrollX = getScrollX();
        int scrollY  = getScrollY();
        int dx = destX - scrollX;
        int dy = destY - scrollY;
        // 前两个参数表示起始位置，第三第四个参数表示位移量，最后一个参数表示时间
        mScroller.startScroll(scrollX,scrollY,dx,dy,1000);
        invalidate();
    }

    // 三、自定义View中重写该方法
    @Override
    public void computeScroll(){
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
    */
}
