package com.example.mokey.weekport.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.weekly.Task;
import com.example.mokey.weekport.data.weekly.WeekBrief;
import com.example.mokey.weekport.data.weekly.WeeklyRoot;
import com.example.mokey.weekport.util.TextUtil;

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
public class WeekPortListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int WEEKSUMMARY_TYPE = 1;
    private static final int NEXTPLAN_TYPE = 2;
    private static final int WEEKPORT_TYPE = 0;
    private final WeeklyRoot mWeeklyRoot;
    private Map<String, List<Task>> mDateTaskMap = new HashMap<>();
    private Context mContext;
    private List<String> mDateList = new ArrayList<>();
    private WeekBrief mWeekBrief;

    public WeekPortListAdapter(Context context, WeeklyRoot weeklyRoot) {
        mContext = context;
        mWeeklyRoot = weeklyRoot;
        if (mWeeklyRoot != null) {
            mWeekBrief = mWeeklyRoot.getWeekBrief();
            List<Task> taskList = mWeeklyRoot.getTaskList();
            for (Task task : taskList) {
                List<Task> tempTaskList = mDateTaskMap.get(task.getTaskDate());
                if (tempTaskList == null) {
                    mDateList.add(task.getTaskDate());
                    tempTaskList = new ArrayList<>();
                    tempTaskList.add(task);
                    mDateTaskMap.put(task.getTaskDate(), tempTaskList);
                } else {
                    tempTaskList.add(task);
                }
            }
            Collections.sort(mDateList);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == WEEKSUMMARY_TYPE || viewType == NEXTPLAN_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_weekport_week_summaryandnext,
                    parent,
                    false
            );
            return new SummaryAndNext(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_weekport_list_item,
                    parent,
                    false
            );
            return new WeekPortItem(view);
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == WEEKPORT_TYPE) {
            WeekPortItem weekPortItem = (WeekPortItem) holder;
            weekPortItem.toolbar.inflateMenu(R.menu.menu_toolbar_contentlist);
            weekPortItem.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });
            List<Task> taskList = mDateTaskMap.get(mDateList.get(position));
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            weekPortItem.recyclerView.setHasFixedSize(true);
            weekPortItem.recyclerView.setLayoutManager(layoutManager);
            TaskListAdapter taskListAdapter = new TaskListAdapter(taskList);
            weekPortItem.recyclerView.setAdapter(taskListAdapter);
        } else if (getItemViewType(position) == WEEKSUMMARY_TYPE) {
            SummaryAndNext summaryAndNext = (SummaryAndNext) holder;
            summaryAndNext.toolbar.setTitle("本周总结");
            String weekSummary = null;
            if (mWeekBrief != null) {
                weekSummary = mWeekBrief.getWeekSummary();
            }
            TextUtil.setText(summaryAndNext.contentWord, weekSummary, "暂无本周总结");
        } else if (getItemViewType(position) == NEXTPLAN_TYPE) {
            SummaryAndNext summaryAndNext = (SummaryAndNext) holder;
            summaryAndNext.toolbar.setTitle("下周计划");
            String nextPlan = null;
            if (mWeekBrief != null) {
                nextPlan = mWeekBrief.getNextPlan();
            }
            TextUtil.setText(summaryAndNext.contentWord, nextPlan, "暂无下周计划");
        }

    }

    @Override public int getItemCount() {
        return mDateList.size() + 2;
    }

    @Override public int getItemViewType(int position) {
        if (position == mDateList.size()) {
            return WEEKSUMMARY_TYPE;
        } else if (position == mDateList.size() + 1) {
            return NEXTPLAN_TYPE;
        } else {
            return WEEKPORT_TYPE;
        }
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

    /**
     * 内容
     */
    class SummaryAndNext extends RecyclerView.ViewHolder {
        @Bind(R.id.toolBar) Toolbar toolbar;
        @Bind(R.id.contentWord) TextView contentWord;

        public SummaryAndNext(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
