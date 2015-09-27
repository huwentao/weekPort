package com.example.mokey.weekport.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.project.Proj;
import com.example.mokey.weekport.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mokey on 15-9-27.
 */
public class ProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NODATA_TYPE = 0;
    private static final int PROJLIST_TYPE = 1;
    private List<Proj> projList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private NodataListener nodataListener;

    public ProjectAdapter(List<Proj> projList, OnItemClickListener onItemClickListener, NodataListener nodataListener) {
        this.projList = projList;
        this.onItemClickListener = onItemClickListener;
        this.nodataListener = nodataListener;
    }

    public ProjectAdapter(List<Proj> projList) {
        this.projList = projList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PROJLIST_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_projectlist_item, parent, false);
            return new ProjectItemView(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listnodata_item, parent, false);
            return new ProjectItemView(view);
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == PROJLIST_TYPE) {
            ProjectItemView projectItemView = (ProjectItemView) holder;
            Proj proj = projList.get(position);
            TextUtil.setText(projectItemView.projectCode, String.valueOf(position));
            TextUtil.setText(projectItemView.projectCode, proj.getProjId());
            TextUtil.setText(projectItemView.projectCode, proj.getProjName());
            projectItemView.setOnItemClickListener(position, proj);
        } else {
            NoDataView noDataView = (NoDataView) holder;
            TextUtil.setText(noDataView.noDataText, "空，前往添加项目");
        }
    }

    @Override public int getItemCount() {
        return projList.size() == 0 ? 1 : projList.size();
    }

    @Override public int getItemViewType(int position) {
        if (projList.size() == 0) {
            return PROJLIST_TYPE;
        } else {
            return NODATA_TYPE;
        }
    }

    public class ProjectItemView extends RecyclerView.ViewHolder {
        @Bind(R.id.seqText) TextView seqText;
        @Bind(R.id.projectCode) TextView projectCode;
        @Bind(R.id.projectName) TextView projectName;

        public ProjectItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final int position, final Proj proj) {
            if (onItemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        onItemClickListener.onItemClick(position, proj);
                    }
                });
            }
        }
    }

    public class NoDataView extends RecyclerView.ViewHolder {
        @Bind(R.id.noDataText) TextView noDataText;

        public NoDataView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (nodataListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        nodataListener.onItemClick();
                    }
                });
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int position, Proj proj);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface NodataListener {
        public void onItemClick();
    }

    public void setNodataListener(NodataListener nodataListener) {
        this.nodataListener = nodataListener;
    }
}
