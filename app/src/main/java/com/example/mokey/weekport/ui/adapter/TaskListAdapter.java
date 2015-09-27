package com.example.mokey.weekport.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.weekly.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mokey on 15-9-27.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskItemView> {
    private List<Task> mTaskList = new ArrayList<>();

    public TaskListAdapter(List<Task> taskList) {
        mTaskList = taskList;
    }


    @Override
    public TaskItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_weekport_taskcontent, parent, false);
        return new TaskItemView(view);
    }

    @Override public void onBindViewHolder(TaskItemView holder, int position) {
        Task task = mTaskList.get(position);
        holder.taskNo.setText(task.getTaskSeq());
        holder.teskContent.setText(task.getTaskMemo());
    }

    @Override public int getItemCount() {
        return mTaskList.size();
    }

    class TaskItemView extends RecyclerView.ViewHolder {
        @Bind(R.id.taskNo) TextView taskNo;
        @Bind(R.id.taskContent) TextView teskContent;

        public TaskItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
