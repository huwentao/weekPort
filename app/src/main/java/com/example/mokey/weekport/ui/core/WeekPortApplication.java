/*
 * Copyright (c) 2015 huwentao (vernon.huwt@gmail.com)
 */

package com.example.mokey.weekport.ui.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.db.DbUtils;
import com.example.mokey.weekport.db.exception.DbException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mokey on 2015/8/4.
 */
public class WeekPortApplication extends Application implements DbUtils.DbUpgradeListener {
    private DbUtils dbUtils;
    private User mUser;
    private String dbPath = "/weekport/db/";
    private String dbName = "weekport";
    private int dbVersion = 1;
    private Logger logger = LoggerFactory.getLogger(WeekPortApplication.class);

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());
        // 发送以前没发送的报告(可选)
//        crashHandler.sendPreviousReportsToServer();

        dbUtils = DbUtils.create(this, dbPath, dbName, dbVersion, this);
        registerActivityLifecycleCallbacks(new HTActivityLifecycleCallbacks());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public DbUtils getDbUtils() {
        return dbUtils;
    }


    /**
     * 取得当前登录用户
     *
     * @return
     */
    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    @Override public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                db.dropDb();
            } catch (DbException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 监听所有Acitivty的生命周期
     */
    public static class HTActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            System.out.println(activity.getTitle().toString() + "(" + ((BaseActivity) activity).getName() + ") onDestoryed");
        }
    }
}
