/*
 * Copyright (c) 2015 huwentao (vernon.huwt@gmail.com)
 */

package com.example.mokey.weekport.ui.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.db.DbUtils;
import com.example.mokey.weekport.util.DisplayUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    private GestureDetector gestureDetector = null;
    private boolean isOpenFlingClose = true; // activity右滑关闭功能开关
    private float flingWidthPX = 0f;
    private WeekPortApplication tutorApp;
    protected static Logger logger = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flingWidthPX = DisplayUtil.dip2px(this, 100);
        tutorApp = (WeekPortApplication) getApplication();
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        gestureDetector = new GestureDetector(this, onGestureListener);
    }

    public CharSequence getName() {
        return getTitle();
    }

    /**
     * 页面跳转
     *
     * @param aClass
     */
    public void callMe(Class<? extends BaseActivity> aClass) {
        callMe(aClass, null);
    }

    /**
     * 页面跳转
     *
     * @param aClass
     */
    public void callMe(Class<? extends BaseActivity> aClass, Bundle bundle) {
        Intent intent = new Intent(this, aClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 设置Toolbar
     *
     * @param toolBar
     */
    protected void setToolBar(Toolbar toolBar) {
        if (toolBar != null) {
            setSupportActionBar(toolBar);
        }
        initToolBar();
    }

    /**
     * 初始化Toolbar的初始配置
     */
    public void initToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getTitle());
            actionBar.setLogo(R.drawable.logo);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private GestureDetector.SimpleOnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        private float startFlingX = 0f;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            startFlingX = e1.getX();
            if (isOpenFlingClose && velocityX - startFlingX > flingWidthPX) {
                finish();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    /**
     * 是否打开右滑关闭功能
     *
     * @param isOpenFlingClose
     */
    public void setIsOpenFlingClose(boolean isOpenFlingClose) {
        this.isOpenFlingClose = isOpenFlingClose;
    }

    public WeekPortApplication getTutorApp() {
        return tutorApp = (WeekPortApplication) getApplication();
    }

    public DbUtils getDbUtils() {
        return getTutorApp().getDbUtils();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }


}
