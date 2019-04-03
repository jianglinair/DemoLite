package com.lin.jiang.app.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by JiangLin.<br>
 * Date: 2019/04/03 14:58<br>
 * Description: BaseRecyclerAdapter <br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerHolder> {
    private Context mContext; //上下文
    private List<T> mList; //数据源
    private LayoutInflater mLayoutInflater; //布局器
    private int mItemLayoutId; //布局id
    private boolean isScrolling; //是否在滚动
    private OnItemClickListener mOnItemClickListener; //点击事件监听器
    private OnItemLongClickListener mOnItemLongClickListener; //长按监听器
    private RecyclerView mRecyclerView;

    public BaseRecyclerAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mContext = context;
        this.mList = list;
        this.mItemLayoutId = itemLayoutId;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 在RecyclerView提供数据的时候调用
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = newState != RecyclerView.SCROLL_STATE_IDLE;
                if (!isScrolling) {
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mRecyclerView = null;
    }

    /**
     * 插入一项
     */
    public void insert(T item, int position) {
        mList.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 删除一项
     *
     * @param position 删除位置
     */
    public void delete(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    @NonNull
    public CommonRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(mItemLayoutId, parent, false);
        return CommonRecyclerHolder.getRecyclerHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommonRecyclerHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {
            if (mOnItemClickListener != null && view != null && mRecyclerView != null) {
                int position1 = mRecyclerView.getChildAdapterPosition(view);
                mOnItemClickListener.onItemClick(mRecyclerView, view, position1);
            }
        });
        holder.itemView.setOnLongClickListener(view -> {
            if (mOnItemLongClickListener != null && view != null && mRecyclerView != null) {
                int position12 = mRecyclerView.getChildAdapterPosition(view);
                mOnItemLongClickListener.onItemLongClick(mRecyclerView, view, position12);
                return true;
            }
            return false;
        });
        convert(holder, mList.get(position), position, isScrolling);

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 填充RecyclerView适配器的方法，子类需要重写
     *
     * @param holder      ViewHolder
     * @param item        子项
     * @param position    位置
     * @param isScrolling 是否在滑动
     */
    public abstract void convert(CommonRecyclerHolder holder, T item, int position, boolean isScrolling);

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.mOnItemLongClickListener = longClickListener;
    }

    public interface OnItemClickListener {
        /**
         * 点击事件
         *
         * @param parent   RecyclerView
         * @param view     item
         * @param position position
         */
        void onItemClick(RecyclerView parent, View view, int position);

    }

    public interface OnItemLongClickListener {
        /**
         * 长按事件
         *
         * @param parent   RecyclerView
         * @param view     item
         * @param position position
         *
         * @return 是否消费
         */
        boolean onItemLongClick(RecyclerView parent, View view, int position);
    }
}
