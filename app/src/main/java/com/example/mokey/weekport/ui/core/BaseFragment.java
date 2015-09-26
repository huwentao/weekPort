/*
 * Copyright (c) 2015 huwentao (vernon.huwt@gmail.com)
 */

package com.example.mokey.weekport.ui.core;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.UUID;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    private String mName = UUID.randomUUID().toString();
    private String mTitle = "";
    private BaseActivity baseActivity;
    private String privateTag = UUID.randomUUID().toString();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        baseActivity = (BaseActivity) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 取到Fragment Name
     *
     * @return
     */
    public String getName() {
        return mName;
    }

    /**
     * 设置Fragment标题
     *
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * 取到Fragment标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public String getPrivateTag() {
        return privateTag;
    }

}
