package com.example.mokey.weekport.ui.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.project.Proj;
import com.example.mokey.weekport.db.DbUtils;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.db.sqlite.Selector;
import com.example.mokey.weekport.ui.adapter.ProjectListAdapter;
import com.example.mokey.weekport.ui.core.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProjectListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_USER = "arg_param_user";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Bundle mUserBundle;
    private String mParam2;
    private List<Proj> mProjList = new ArrayList<>();
    private ProjectListAdapter projectListAdapter = null;

    @Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.toolBar) Toolbar toolBar;

    private int currentNum = 0;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bundle Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectListFragment newInstance(Bundle bundle, String param2) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putBundle(ARG_PARAM_USER, bundle);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProjectListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserBundle = getArguments().getBundle(ARG_PARAM_USER);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        boolean isHaveThree = getResources().getBoolean(R.bool.isHaveThreePanel);
        if (!isHaveThree) {
            view = inflater.inflate(R.layout.fragment_project_list_other, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_project_list, container, false);
        }
        return view;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isHaveThree = getResources().getBoolean(R.bool.isHaveThreePanel);
        if (!isHaveThree) {
            getBaseActivity().setSupportActionBar(toolBar);
            getBaseActivity().initToolBar(getActivity().getTitle().toString());
        } else {
            toolBar.inflateMenu(R.menu.menu_projectlist);
            initSearchMenu(toolBar.getMenu());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        projectListAdapter = new ProjectListAdapter(mProjList, new ProjectListAdapter.OnItemClickListener() {
            @Override public void onItemClick(int position, Proj proj) {

            }
        }, new ProjectListAdapter.NodataListener() {
            @Override public void onItemClick() {

            }
        });
        projectListAdapter.setOnLoadMoreListener(new ProjectListAdapter.OnLoadMoreListener() {
            @Override public void loadMore() {
                if (loadProjectData.isCancelled()) {//还在加载中时不再多次加载
                    loadProjectData.execute();
                }
            }
        });
        recyclerView.setAdapter(projectListAdapter);
        loadProjectData.execute();
    }

    public void initSearchMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.requestFocus();
        searchView.setQueryHint("输入项目编号/项目名称搜索");
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(true);
    }

    private AsyncTask<Integer, Integer, List<Proj>> loadProjectData = new AsyncTask<Integer, Integer, List<Proj>>() {

        @Override protected List<Proj> doInBackground(Integer... params) {
            List<Proj> projList = null;
            try {
                DbUtils dbUtils = getBaseActivity().getDbUtils();
                projList = dbUtils.findAll(Selector.from(Proj.class)
                        .orderBy("projId")
                        .limit(30)
                        .offset(currentNum));
                currentNum += 30;
            } catch (DbException e) {
                e.printStackTrace();
            }
            return projList;
        }

        @Override protected void onPostExecute(List<Proj> projList) {
            if (projList != null) {//载入更多数据
                int startPos = mProjList.size();
                mProjList.addAll(projList);
                projectListAdapter.notifyItemRangeChanged(startPos, projList.size());
            } else {//没有更多数据
                projectListAdapter.setLoadAllData(true);
                projectListAdapter.notifyItemChanged(projectListAdapter.getItemCount() - 1);
            }
        }
    };

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (loadProjectData != null && !loadProjectData.isCancelled()) {
            loadProjectData.cancel(true);
        }
    }
}
