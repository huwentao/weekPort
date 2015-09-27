package com.example.mokey.weekport.ui.fragment;


import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokey.weekport.MainActivity;
import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.project.Proj;
import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.data.user.UserProject;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.db.sqlite.Selector;
import com.example.mokey.weekport.ui.HomeActivity;
import com.example.mokey.weekport.ui.ProjectListActivity;
import com.example.mokey.weekport.ui.adapter.ProjectAdapter;
import com.example.mokey.weekport.ui.core.BaseFragment;
import com.example.mokey.weekport.util.DialogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TITLE = "arg_param_title";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProjectAdapter mProjectAdapter = null;

    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    private List<Proj> mProjList = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(ProjectFragment.class);
    private User user;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectFragment newInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TITLE, param1);
        args.putString(ARG_PARAM2, param2);
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
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mProjectAdapter = new ProjectAdapter(mProjList,
                new ProjectAdapter.OnItemClickListener() {
                    @Override public void onItemClick(int position, Proj proj) {
                        DialogUtil.getAlertDialog(getActivity(), R.string.tip_title, "从我的项目中删除",
                                "确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        HomeActivity homeActivity = ((HomeActivity) getActivity());
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable(MainActivity.CURRENT_USER, user);
                                        if (homeActivity.isNeedNewActivity()) {
                                            getBaseActivity().callMe(ProjectListActivity.class, bundle);
                                        } else {
                                            homeActivity.loadThreeFragment(ProjectListFragment.class, bundle);
                                        }
                                    }
                                }, "取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HomeActivity homeActivity = ((HomeActivity) getActivity());
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable(MainActivity.CURRENT_USER, user);
                                        if (homeActivity.isNeedNewActivity()) {
                                            getBaseActivity().callMe(ProjectListActivity.class, bundle);
                                        } else {
                                            homeActivity.loadThreeFragment(ProjectListFragment.class, bundle);
                                        }
                                    }
                                }).show();
                    }
                }, new ProjectAdapter.NodataListener() {
            @Override public void onItemClick() {

            }
        }));
        initProjectData();
    }

    private void initProjectData() {
        user = ((HomeActivity) getActivity()).getCurrentUser();
        if (user != null) {
            try {
                getBaseActivity().getDbUtils()
                        .findAll(Selector.from(UserProject.class)
                                .where("user_id", "=", user.getUserId()));
            } catch (DbException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     *
     */
    public void importProjectFile() {

    }
}
