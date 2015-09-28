package com.example.mokey.weekport.ui.fragment;


import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokey.weekport.MainActivity;
import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.project.ProjectRoot;
import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.data.user.UserProject;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.db.sqlite.Selector;
import com.example.mokey.weekport.task.ImportProjectFileTask;
import com.example.mokey.weekport.ui.HomeActivity;
import com.example.mokey.weekport.ui.ProjectListActivity;
import com.example.mokey.weekport.ui.adapter.ProjectAdapter;
import com.example.mokey.weekport.ui.core.BaseFragment;
import com.example.mokey.weekport.util.DialogUtil;
import com.example.mokey.weekport.util.SnackBarUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TITLE = "arg_param_title";
    private static final String ARG_PARAM_USER = "arg_param_user";
    private ImportProjectFileTask importProjectFileTask = null;

    // TODO: Rename and change types of parameters
    private String title;
    private User currentUser;
    private ProjectAdapter mProjectAdapter = null;

    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    private List<UserProject> mProjList = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(ProjectFragment.class);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title       Parameter 1.
     * @param currentUser Parameter 2.
     * @return A new instance of fragment ProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectFragment newInstance(String title, User currentUser) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TITLE, title);
        args.putParcelable(ARG_PARAM_USER, currentUser);
        fragment.setArguments(args);
        return fragment;
    }

    public ProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setTitle(getArguments().getString(ARG_PARAM_TITLE));
            currentUser = getArguments().getParcelable(ARG_PARAM_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProjectData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                initProjectData();
                mProjectAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mProjectAdapter = new ProjectAdapter(mProjList,
                new ProjectAdapter.OnItemClickListener() {
                    @Override public void onItemClick(final int position, final UserProject proj) {
                        DialogUtil.getAlertDialog(getActivity(), R.string.tip_title, "从我的项目中删除",
                                "确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        try {
                                            getBaseActivity().getDbUtils().delete(proj);
                                            initProjectData();
                                            mProjectAdapter.notifyItemChanged(position);
                                        } catch (DbException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, "取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                }, new ProjectAdapter.NodataListener() {
            @Override public void onItemClick() {
                gotoChoiceProject();
            }
        }));
    }

    private void initProjectData() {
        if (currentUser != null) {
            try {
                List<UserProject> projs = getBaseActivity().getDbUtils()
                        .findAll(Selector.from(UserProject.class)
                                .where("user_id", "=", currentUser.getUserId()));
                if (mProjList.size() > 0) mProjList.clear();
                if (projs != null) {
                    mProjList.addAll(projs);
                }
            } catch (DbException e) {
                logger.error(e.getMessage(), e);
            }
        }

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     *
     */
    public void importProjectFile() {
        importProjectFileTask = getImportProjectFileTask();
        importProjectFileTask.execute();
    }

    public void gotoChoiceProject() {
        HomeActivity homeActivity = ((HomeActivity) getActivity());
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.CURRENT_USER, currentUser);
        if (homeActivity.isNeedNewActivity()) {
            getBaseActivity().callMe(ProjectListActivity.class, bundle);
        } else {
            homeActivity.loadThreeFragment(ProjectListFragment.class, bundle);
        }
    }

    @OnClick(R.id.floatActionButton)
    public void onClick() {
        gotoChoiceProject();
    }

    /**
     * 创建导入项目数据任务
     *
     * @return
     */
    public ImportProjectFileTask getImportProjectFileTask() {
        return new ImportProjectFileTask(getBaseActivity().getDbUtils(), getActivity(),
                new ImportProjectFileTask.TaskCallback() {
                    @Override public void finish(ProjectRoot projectRoot) {
                        SnackBarUtils.makeSnackBar(coordinatorLayout, "导入成功").show();
                        gotoChoiceProject();
                    }

                    @Override public void failed(Exception e) {
                        SnackBarUtils.makeSnackBar(coordinatorLayout, "导入失败了").show();
                    }

                    @Override public void cancel(Exception e) {
                        SnackBarUtils.makeSnackBar(coordinatorLayout, "导入取消").show();
                    }
                });
    }
}
