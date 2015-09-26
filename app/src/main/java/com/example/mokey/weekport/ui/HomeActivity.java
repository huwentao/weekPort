package com.example.mokey.weekport.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.ui.core.BaseActivity;
import com.example.mokey.weekport.ui.core.BaseFragment;
import com.example.mokey.weekport.ui.fragment.NavigationDrawerFragment;
import com.example.mokey.weekport.ui.fragment.PersonFragment;
import com.example.mokey.weekport.ui.fragment.ProjectFragment;
import com.example.mokey.weekport.ui.fragment.RequirementFragment;
import com.example.mokey.weekport.ui.fragment.WeekPortFragment;
import com.example.mokey.weekport.ui.fragment.WeekPortListFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    @Bind(R.id.toolBar) Toolbar toolbar;

    private boolean isHaveTwo = false;
    private boolean isHaveThree = false;
    private boolean isHaveOne = false;

    private WeekPortFragment mWeekPortFragment;//
    private WeekPortListFragment mWeekPortListFragment;//
    private PersonFragment mPersonFragment;//
    private RequirementFragment mRequirementFragment;//
    private ProjectFragment mProjectFragment;//
    private NavigationDrawerFragment mNavigationDrawerFragment;//fragment 菜单

    private Map<String, BaseFragment> baseFragments = new HashMap<>();
    private CharSequence mTitle;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolBar(toolbar);

        mFragmentManager = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationDrawerFragment) mFragmentManager
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                toolbar,
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mNavigationDrawerFragment.setCallbacks(this);

        isHaveOne = getResources().getBoolean(R.bool.isHaveOnePanel);
        isHaveTwo = getResources().getBoolean(R.bool.isHaveTwoPanel);
        isHaveThree = getResources().getBoolean(R.bool.isHaveThreePanel);

        mWeekPortListFragment = getBaseFragment(WeekPortListFragment.class);
        showFragment(mWeekPortListFragment.getPrivateTag());
    }

    @Override public void initToolBar() {
        super.initToolBar();
        setTitle("");
    }

    @Override
    public void onNavigationDrawerItemSelected(int itemId) {
        // update the main content by replacing fragments
        switch (itemId) {
            case R.id.myWeekPort:
                mWeekPortListFragment = getBaseFragment(WeekPortListFragment.class);
                showFragment(mWeekPortListFragment.getPrivateTag());
                break;
            case R.id.person:
                mPersonFragment = getBaseFragment(PersonFragment.class);
                showFragment(mPersonFragment.getPrivateTag());
                break;
            case R.id.project:
                mProjectFragment = getBaseFragment(ProjectFragment.class);
                showFragment(mProjectFragment.getPrivateTag());
                break;
            case R.id.requirement:
                mRequirementFragment = getBaseFragment(RequirementFragment.class);
                showFragment(mRequirementFragment.getPrivateTag());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.home, menu);
            initToolBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param aClass
     * @param <T>
     * @return
     */
    public <T> T getBaseFragment(Class<T> aClass) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (aClass.isAssignableFrom(WeekPortListFragment.class)) {
            if (mWeekPortListFragment == null) {
                mWeekPortListFragment = WeekPortListFragment.newInstance(null, null);
                baseFragments.put(mWeekPortListFragment.getPrivateTag(), mWeekPortListFragment);
            }
            transaction.add(R.id.contentContainer, mWeekPortListFragment, mWeekPortListFragment.getPrivateTag());
            transaction.commit();
            return aClass.cast(mWeekPortListFragment);
        } else if (aClass.isAssignableFrom(PersonFragment.class)) {
            if (mPersonFragment == null) {
                mPersonFragment = PersonFragment.newInstance(null, null);
                baseFragments.put(mPersonFragment.getPrivateTag(), mPersonFragment);
                transaction.add(R.id.contentContainer, mPersonFragment, mPersonFragment.getPrivateTag());
            }
            transaction.commit();
            return aClass.cast(mPersonFragment);
        } else if (aClass.isAssignableFrom(ProjectFragment.class)) {
            if (mProjectFragment == null) {
                mProjectFragment = ProjectFragment.newInstance(null, null);
                baseFragments.put(mProjectFragment.getPrivateTag(), mProjectFragment);
                transaction.add(R.id.contentContainer, mProjectFragment, mProjectFragment.getPrivateTag());
            }
            transaction.commit();
            return aClass.cast(mProjectFragment);
        } else if (aClass.isAssignableFrom(RequirementFragment.class)) {
            if (mRequirementFragment == null) {
                mRequirementFragment = RequirementFragment.newInstance(null, null);
                baseFragments.put(mRequirementFragment.getPrivateTag(), mRequirementFragment);
                transaction.add(R.id.contentContainer, mRequirementFragment, mRequirementFragment.getPrivateTag());
            }
            transaction.commit();
            return aClass.cast(mRequirementFragment);
        }
        return null;
    }

    /**
     * @param privateTag
     */
    public void showFragment(String privateTag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (Map.Entry<String, BaseFragment> fragmentEntry : baseFragments.entrySet()) {
            BaseFragment baseFragment = fragmentEntry.getValue();
            if (baseFragment != null) {
                if (fragmentEntry.getKey().equals(privateTag)) {
                    transaction.show(baseFragment);
                } else {
                    transaction.hide(baseFragment);
                }
            }
        }
        transaction.commit();
    }
}
