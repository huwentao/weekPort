package com.example.mokey.weekport.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.weekly.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mokey on 15-9-23.
 */
public class WeekPortListAdapter extends RecyclerView.Adapter<WeekPortListAdapter.WeekPortItem> {
    private Context mContext;
    private List<Task> mTaskList = new ArrayList<>();

    @Override
    public WeekPortItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_weekport_list_item,
                parent,
                false
        );
        return new WeekPortItem(view);
    }

    @Override public void onBindViewHolder(WeekPortItem holder, int position) {
        WeekPortItem weekPortItem = (WeekPortItem) holder;
        weekPortItem.toolbar.inflateMenu(R.menu.menu_toolbar_contentlist);
        weekPortItem.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override public int getItemCount() {
        return mTaskList.size() + 10;
    }

    /**
     * 内容
     */
    class WeekPortItem extends RecyclerView.ViewHolder {
        @Bind(R.id.toolBar) Toolbar toolbar;

        public WeekPortItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
