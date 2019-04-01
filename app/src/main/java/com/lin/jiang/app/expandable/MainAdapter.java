package com.lin.jiang.app.expandable;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.jiang.app.R;

import java.util.ArrayList;

/**
 * Created by Clevo on 2016/9/13.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    Context context;
    ArrayList<DataBean> models;

    public MainAdapter(Context context, ArrayList<DataBean> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_expandable_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, final int position) {
        holder.expandable_text.setText(models.get(position).getText(), models.get(position).isCollapsed());
        holder.expandable_text.setListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(boolean isExpanded) {
                models.get(position).setCollapsed(isExpanded);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        ExpandableTextView expandable_text;

        public MainViewHolder(View itemView) {
            super(itemView);

            expandable_text = itemView.findViewById(R.id.expandable_text);
        }
    }
}
