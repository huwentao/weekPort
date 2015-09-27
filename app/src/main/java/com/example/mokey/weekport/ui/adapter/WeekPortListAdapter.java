package com.example.mokey.weekport.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.weekly.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mokey on 15-9-23.
 */
public class WeekPortListAdapter extends RecyclerView.Adapter<WeekPortListAdapter.WeekPortItem> {
    private Map<String, List<Task>> mDateTaskMap = new HashMap<>();
    private List<String> mTaskDates = new ArrayList<>();
    private Context mContext;

    public WeekPortListAdapter(List<Task> mDateTaskList) {
        if (mDateTaskList != null) {
            Collections.sort(mDateTaskList);
            for (int i = 0; i < mDateTaskList.size(); i++) {
                String dateStr = mDateTaskList.get(i).getTaskDate();
                List<Task> task = mDateTaskMap.get(dateStr);
                if (task == null) {
                    List<Task> taskList = new ArrayList<>();
                    taskList.add(mDateTaskList.get(i));
                    mDateTaskMap.put(dateStr, taskList);
                    mTaskDates.add(dateStr);
                } else {
                    task.add(mDateTaskList.get(i));
                }
            }
        }
    }

    @Override
    public WeekPortItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_weekport_list_item,
                parent,
                false
        );
        mContext = parent.getContext();
        return new WeekPortItem(view);
    }

    @Override public void onBindViewHolder(WeekPortItem holder, int position) {
        holder.toolbar.inflateMenu(R.menu.menu_toolbar_contentlist);
        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        List<Task> taskList = mDateTaskMap.get(mTaskDates.get(position));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(layoutManager);
        TaskListAdapter taskListAdapter = new TaskListAdapter(taskList);
        holder.recyclerView.setAdapter(taskListAdapter);
    }

    @Override public int getItemCount() {
        return mTaskDates.size();
    }

    /**
     * 内容
     */
    class WeekPortItem extends RecyclerView.ViewHolder {
        @Bind(R.id.toolBar) Toolbar toolbar;
        @Bind(R.id.recyclerView) RecyclerView recyclerView;

        public WeekPortItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
