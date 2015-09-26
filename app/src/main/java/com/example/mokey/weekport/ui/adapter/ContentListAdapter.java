package com.example.mokey.weekport.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.weekly.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mokey on 15-9-23.
 */
public class ContentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Task> mTaskList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_content_list_item,
                    parent,
                    false
            );
            return new ContentView(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_content_list_plus_item,
                    parent,
                    false
            );
            return new PlusView(view);
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE) {
            ContentView contentView = (ContentView) holder;
            contentView.toolbar.inflateMenu(R.menu.menu_toolbar_contentlist);
            contentView.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });
        }
    }

    @Override public int getItemCount() {
        return 0;
    }

    public final static int ITEM_TYPE = 1;
    public final static int PLUS_TYPE = 1;

    /**
     * @param position
     * @return
     */
    @Override public int getItemViewType(int position) {
        if (position == getItemCount()) {
            return PLUS_TYPE;
        } else {
            return ITEM_TYPE;
        }
    }

    /**
     * 内容
     */
    class ContentView extends RecyclerView.ViewHolder {
        @Bind(R.id.toolBar) Toolbar toolbar;

        public ContentView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 添加
     */
    class PlusView extends RecyclerView.ViewHolder {
        @Bind(R.id.plusButton) ImageButton plusButton;

        public PlusView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                }
            });
        }
    }
}
