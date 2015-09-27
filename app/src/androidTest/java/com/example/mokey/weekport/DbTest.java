package com.example.mokey.weekport;

import android.test.ApplicationTestCase;

import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.data.weekly.WeeklyRoot;
import com.example.mokey.weekport.db.DbUtils;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.db.sqlite.Selector;
import com.example.mokey.weekport.ui.core.WeekPortApplication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mokey on 15-9-27.
 */
public class DbTest extends ApplicationTestCase<WeekPortApplication> {
    private String id = "430000198808169910";
    private DbUtils dbUtils;
    private User user;
    private Logger logger = LoggerFactory.getLogger(DbTest.class);

    public DbTest() {
        super(WeekPortApplication.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        WeekPortApplication application = getApplication();
        dbUtils = application.getDbUtils();
        user = dbUtils.findFirst(Selector.from(User.class).where("idNo", "=", id));
        if (user == null) {
            user = new User("测试用", id);
            dbUtils.saveOrUpdate(user);
        }
    }

    public void testForeign() throws DbException {
        List<WeeklyRoot> weeklyRoots = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            WeeklyRoot weeklyRoot = new WeeklyRoot();
            weeklyRoot.setReportPerson(user.getReportPerson());
            weeklyRoot.setUser(user);
            weeklyRoots.add(weeklyRoot);
        }
        dbUtils.saveOrUpdateAll(weeklyRoots);
    }

    public void testQueryForeign() throws DbException {
        List<WeeklyRoot> weeklyRoots = dbUtils.findAll(Selector.from(WeeklyRoot.class)
                .where("user_id", "=", user.getUserId()));
        for(WeeklyRoot weeklyRoot:weeklyRoots){
            logger.debug("getReportPerson={}",weeklyRoot.getReportPerson());
            System.out.println(weeklyRoot.getUser().getReportPerson());
        }
    }
}

