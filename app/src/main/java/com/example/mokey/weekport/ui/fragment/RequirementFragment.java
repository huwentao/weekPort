package com.example.mokey.weekport.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.project.Proj;
import com.example.mokey.weekport.ui.adapter.ProjectAdapter;
import com.example.mokey.weekport.ui.core.BaseFragment;
import com.example.mokey.weekport.util.SnackBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequirementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequirementFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TITLE = "arg_param_title";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProjectAdapter mProjectAdapter;
    private List<Proj> mProjList = new ArrayList<>();

    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequirementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequirementFragment newInstance(String param1, String param2) {
        RequirementFragment fragment = new RequirementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TITLE, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RequirementFragment() {
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
        return inflater.inflate(R.layout.fragment_requirement, container, false);
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

                    }
                }, new ProjectAdapter.NodataListener() {
            @Override public void onItemClick() {
                SnackBarUtils.makeSnackBar(coordinatorLayout, "暂不提供选择需求文件").show();;
            }
        }));
    }

    public void importRequirementFile() {
        SnackBarUtils.makeSnackBar(coordinatorLayout, "暂不提供需求文件导入").show();;
    }

    @OnClick(R.id.floatActionButton)
    public void onClick() {
        SnackBarUtils.makeSnackBar(coordinatorLayout, "暂不提供选择需求文件").show();
    }
}
