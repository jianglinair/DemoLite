package com.lin.jiang.app.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by JiangLin.<br>
 * Date: 2019/04/03 14:53<br>
 * Description: CommonRecyclerHolder <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class CommonRecyclerHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private Context mContext;

    private CommonRecyclerHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        //指定一个初始为8
        mViews = new SparseArray<>(8);
    }

    /**
     * 取得一个RecyclerHolder对象
     *
     * @param context  上下文
     * @param itemView 子项
     *
     * @return 返回一个RecyclerHolder对象
     */
    public static CommonRecyclerHolder getRecyclerHolder(Context context, View itemView) {
        return new CommonRecyclerHolder(context, itemView);
    }

    public SparseArray<View> getViews() {
        return this.mViews;
    }

    /**
     * 通过view的id获取对应的控件，如果没有则加入views中
     *
     * @param viewId 控件的id
     *
     * @return 返回一个控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置字符串
     */
    public CommonRecyclerHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonRecyclerHolder setTextColor(int viewId, int color) {
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonRecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonRecyclerHolder setImageByUrl(int viewId, String url) {
        throw new RuntimeException("Method \"setImageByUrl()\" is not implemented now.");
    }


    public CommonRecyclerHolder setOnRecyclerItemClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
