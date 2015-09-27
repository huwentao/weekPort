package com.example.mokey.weekport.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.ui.core.BaseFragment;

import hirondelle.date4j.DateTime;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link WeekPortFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekPortFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_BUNDLE = "arg_param_bundle";
    private static final String ARG_PARAM_CHOISEDATE = "arg_param_choisedate";

    // TODO: Rename and change types of parameters
    private Bundle bundle;
    private String mChoiseDate;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bundle Parameter 2.
     * @param dateTime Parameter 1.
     * @param dateTime
     * @return A new instance of fragment WeekPortFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekPortFragment newInstance(Bundle bundle, DateTime dateTime) {
        WeekPortFragment fragment = new WeekPortFragment();
        Bundle args = new Bundle();
        args.putBundle(ARG_PARAM_BUNDLE, bundle);
        args.putString(ARG_PARAM_CHOISEDATE, dateTime.format("YYYY-MM-DD hh:mm:ss"));
        fragment.setArguments(args);
        return fragment;
    }

    public WeekPortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundle = getArguments().getBundle(ARG_PARAM_CHOISEDATE);
            mChoiseDate = getArguments().getString(ARG_PARAM_CHOISEDATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
