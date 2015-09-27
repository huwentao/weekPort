package com.example.mokey.weekport.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.ui.adapter.WeekPortListAdapter;
import com.example.mokey.weekport.ui.core.BaseFragment;

import butterknife.Bind;
import hirondelle.date4j.DateTime;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link WeekPortListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekPortListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TITLE = "arg_param_title";
    private static final String ARG_PARAM_CHOISEDATE = "arg_param_choisedate";

    // TODO: Rename and change types of parameters
    private String mChoiseDate;
    private WeekPortListAdapter mWeekPortListAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param choiseDate      Parameter 2.
     * @param title       Parameter 1.
     * @param choiseDate
     * @return A new instance of fragment WeekPortListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekPortListFragment newInstance(String title, DateTime choiseDate) {
        WeekPortListFragment fragment = new WeekPortListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TITLE, title);
        args.putString(ARG_PARAM_CHOISEDATE, choiseDate.format("YYYY-MM-DD hh:mm:ss"));
        fragment.setArguments(args);
        return fragment;
    }

    public WeekPortListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setTitle(getArguments().getString(ARG_PARAM_TITLE));
            mChoiseDate = getArguments().getString(ARG_PARAM_CHOISEDATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content_list, container, false);
    }

    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mWeekPortListAdapter = new WeekPortListAdapter(null);
        recyclerView.setAdapter(mWeekPortListAdapter);

    }

    /**
     *
     */
    public void saveWeekPort() {

    }

    public void loadWeekProtByDate(DateTime mChoiseTodayDate) {

    }
}
