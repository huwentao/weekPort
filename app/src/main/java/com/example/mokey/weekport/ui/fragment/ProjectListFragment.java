package com.example.mokey.weekport.ui.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokey.weekport.MainActivity;
import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.project.Proj;
import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.data.user.UserProject;
import com.example.mokey.weekport.db.DbUtils;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.db.sqlite.Selector;
import com.example.mokey.weekport.ui.adapter.HistorySuggestionsAdapter;
import com.example.mokey.weekport.ui.adapter.ProjectListAdapter;
import com.example.mokey.weekport.ui.core.BaseFragment;
import com.example.mokey.weekport.util.DialogUtil;
import com.example.mokey.weekport.util.SnackBarUtils;

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
    private List<Proj> mProjList = null;
    private ProjectListAdapter projectListAdapter = null;

    @Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.toolBar) Toolbar toolBar;

    private int currentNum = 0;//最后条目序号
    private AsyncTask loadProjectData;
    private Cursor mCursor;
    private HistorySuggestionsAdapter suggestionsAdapter;
    private String queryText = "";
    private SearchView searchView;

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
            @Override public void onItemClick(int position, final Proj proj) {
                DialogUtil.getAlertDialog(getActivity(), R.string.tip_title, "导入最新项目数据",
                        "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    User user = mUserBundle.getParcelable(MainActivity.CURRENT_USER);
                                    if (user != null && TextUtils.isEmpty(user.getReportPerson())) {
                                        SnackBarUtils.makeSnackBar(coordinatorLayout, "请先添加用户").show();
                                    } else {
                                        UserProject userProject = new UserProject(user, proj);
                                        getBaseActivity().getDbUtils().saveOrUpdate(userProject);
                                        dialog.dismiss();
                                        SnackBarUtils.makeSnackBar(coordinatorLayout, "添加成功").show();
                                    }
                                } catch (DbException e) {
                                    e.printStackTrace();
                                    SnackBarUtils.makeSnackBar(coordinatorLayout, "添加失败").show();
                                }
                            }
                        }, "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        }, new ProjectListAdapter.NodataListener() {
            @Override public void onItemClick() {
            }
        });
        projectListAdapter.setOnLoadMoreListener(new ProjectListAdapter.OnLoadMoreListener() {
            @Override public void loadMore() {
                if (loadProjectData.getStatus() == AsyncTask.Status.FINISHED) {//还在加载中时不再多次加载
                    loadProjectData = new LoadProjectData().execute(new Object[]{false, 30});
                }
            }
        });
        recyclerView.setAdapter(projectListAdapter);
        loadProjectData = new LoadProjectData().execute(new Object[]{false, 30});
    }

    public void initSearchMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("输入项目编号/项目名称搜索");
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                queryText = query;
                projectListAdapter.setLoadMoreData(true);
                loadProjectData = new LoadProjectData().execute(new Object[]{true, 30});
                return true;
            }

            @Override public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    queryText = newText;
                    projectListAdapter.setLoadMoreData(true);
                    loadProjectData = new LoadProjectData().execute(new Object[]{true, 30});
                }
                return true;
            }
        });
    }

    private class LoadProjectData extends AsyncTask<Object, Integer, List<Proj>> {

        @Override protected List<Proj> doInBackground(Object... params) {
            return filterProjList((Boolean) params[0], (Integer) params[1], queryText);
        }

        @Override protected void onPostExecute(List<Proj> projList) {
            if (mProjList == null) {//首次加载
                mProjList = new ArrayList<>();
                projectListAdapter.setProjList(mProjList);
                if (projList.size() == 0) {
                    projectListAdapter.setLoadMoreData(false);
                    projectListAdapter.notifyDataSetChanged();
                } else {
                    mProjList.addAll(projList);
                    projectListAdapter.setLoadMoreData(true);
                    projectListAdapter.notifyDataSetChanged();
                }
            } else {//载入更多数据
                if (projList.size() == 0) {
                    projectListAdapter.setLoadMoreData(false);
                    projectListAdapter.notifyItemChanged(projectListAdapter.getItemCount() - 1);
                } else {
                    mProjList.addAll(projList);
                    projectListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (loadProjectData != null && !loadProjectData.isCancelled()) {
            loadProjectData.cancel(true);
        }
    }

    /**
     * @param reLoad
     * @param pageCount  每页数目
     * @param filterText
     * @return
     */
    private List<Proj> filterProjList(boolean reLoad, int pageCount, String filterText) {
        List<Proj> projList = null;
        if (reLoad) {
            currentNum = 0;
            mProjList.clear();
        }
        try {
            DbUtils dbUtils = getBaseActivity().getDbUtils();
            Selector selector = Selector.from(Proj.class);
            if (!TextUtils.isEmpty(filterText)) {
                selector.where("projId", "like", "%" + filterText + "%")
                        .or("projName", "like", "%" + filterText + "%");
            }
            selector.orderBy("projId")
                    .limit(pageCount)
                    .offset(currentNum);
            projList = dbUtils.findAll(selector);
            currentNum += pageCount;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return projList;
    }
}
