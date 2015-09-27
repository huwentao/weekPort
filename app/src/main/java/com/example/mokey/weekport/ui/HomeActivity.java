package com.example.mokey.weekport.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mokey.weekport.MainActivity;
import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.ui.core.BaseActivity;
import com.example.mokey.weekport.ui.core.BaseFragment;
import com.example.mokey.weekport.ui.fragment.NavigationDrawerFragment;
import com.example.mokey.weekport.ui.fragment.PersonFragment;
import com.example.mokey.weekport.ui.fragment.ProjectFragment;
import com.example.mokey.weekport.ui.fragment.ProjectListFragment;
import com.example.mokey.weekport.ui.fragment.RequirementFragment;
import com.example.mokey.weekport.ui.fragment.WeekPortFragment;
import com.example.mokey.weekport.ui.fragment.WeekPortListFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import butterknife.Bind;
import hirondelle.date4j.DateTime;

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
    private ProjectListFragment mProjectListFragment;//
    private WeekPortListFragment mWeekPortListFragment;//
    private PersonFragment mPersonFragment;//
    private RequirementFragment mRequirementFragment;//
    private ProjectFragment mProjectFragment;//
    private NavigationDrawerFragment mNavigationDrawerFragment;//fragment 菜单

    private Map<String, BaseFragment> baseFragments = new HashMap<>();
    private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    private DateTime mChoiseTodayDate;
    private String mShowFragmentTag;
    private User mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolBar(toolbar);
        initToolBar();

        mCurrentUser = getIntent().getParcelableExtra(MainActivity.CURRENT_USER);

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

        mChoiseTodayDate = DateTime.now(TimeZone.getDefault());
        mWeekPortListFragment = getBaseFragment(WeekPortListFragment.class);
        showFragment(mWeekPortListFragment.getPrivateTag());

    }

    private void initToolBar(String title) {
        setTitle(title);
        toolbar.setLogo(null);
    }

    @Override
    public void onNavigationDrawerItemSelected(int itemId) {
        // update the main content by replacing fragments
        switch (itemId) {
            case R.id.myWeekPort:
                mWeekPortListFragment = getBaseFragment(WeekPortListFragment.class);
                showFragment(mWeekPortListFragment.getPrivateTag());
                initToolBar(mWeekPortListFragment.getTitle());
                break;
            case R.id.person:
                mPersonFragment = getBaseFragment(PersonFragment.class);
                showFragment(mPersonFragment.getPrivateTag());
                initToolBar(mPersonFragment.getTitle());
                break;
            case R.id.project:
                mProjectFragment = getBaseFragment(ProjectFragment.class);
                showFragment(mProjectFragment.getPrivateTag());
                initToolBar(mProjectFragment.getTitle());
                break;
            case R.id.requirement:
                mRequirementFragment = getBaseFragment(RequirementFragment.class);
                showFragment(mRequirementFragment.getPrivateTag());
                initToolBar(mRequirementFragment.getTitle());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            if (mWeekPortListFragment != null && mShowFragmentTag.equals(mWeekPortListFragment.getPrivateTag())) {
                getMenuInflater().inflate(R.menu.home_weekport, menu);
                initToolBar(mWeekPortListFragment.getTitle());
            } else if (mProjectFragment != null && mShowFragmentTag.equals(mProjectFragment.getPrivateTag())) {
                getMenuInflater().inflate(R.menu.home_project, menu);
                initToolBar(mProjectFragment.getTitle());
            } else if (mRequirementFragment != null && mShowFragmentTag.equals(mRequirementFragment.getPrivateTag())) {
                getMenuInflater().inflate(R.menu.home_requirement, menu);
                initToolBar(mRequirementFragment.getTitle());
            } else if (mPersonFragment != null && mShowFragmentTag.equals(mPersonFragment.getPrivateTag())) {
                getMenuInflater().inflate(R.menu.home_person, menu);
                initToolBar(mPersonFragment.getTitle());
            } else {
                initToolBar();
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int dayOfMonth) {
                                datePickerDialog.dismiss();
                                mChoiseTodayDate = new DateTime(year, month, dayOfMonth, 0, 0, 0, 0);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
                dpd.show(getFragmentManager(), "Datepickerdialog");
                mWeekPortListFragment.loadWeekProtByDate(mChoiseTodayDate);
                break;
            case R.id.importProjectFile:
                mProjectFragment.importProjectFile();
                break;
            case R.id.importRequirementFile:
                mRequirementFragment.importRequirementFile();
                break;
            case R.id.savePerson:
                mPersonFragment.savePerson();
                break;
            case R.id.saveWeekport:
                mWeekPortListFragment.saveWeekPort();
                break;
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
                mWeekPortListFragment = WeekPortListFragment.newInstance(getString(R.string.myWeekPort), mChoiseTodayDate);
                baseFragments.put(mWeekPortListFragment.getPrivateTag(), mWeekPortListFragment);
                transaction.add(R.id.contentContainer, mWeekPortListFragment, mWeekPortListFragment.getPrivateTag());
                transaction.commit();
            }
            return aClass.cast(mWeekPortListFragment);
        } else if (aClass.isAssignableFrom(PersonFragment.class)) {
            if (mPersonFragment == null) {
                mPersonFragment = PersonFragment.newInstance(getString(R.string.personInfo), mCurrentUser);
                baseFragments.put(mPersonFragment.getPrivateTag(), mPersonFragment);
                transaction.add(R.id.contentContainer, mPersonFragment, mPersonFragment.getPrivateTag());
                transaction.commit();
            }
            return aClass.cast(mPersonFragment);
        } else if (aClass.isAssignableFrom(ProjectFragment.class)) {
            if (mProjectFragment == null) {
                mProjectFragment = ProjectFragment.newInstance(getString(R.string.projectInfo), null);
                baseFragments.put(mProjectFragment.getPrivateTag(), mProjectFragment);
                transaction.add(R.id.contentContainer, mProjectFragment, mProjectFragment.getPrivateTag());
                transaction.commit();
            }
            return aClass.cast(mProjectFragment);
        } else if (aClass.isAssignableFrom(RequirementFragment.class)) {
            if (mRequirementFragment == null) {
                mRequirementFragment = RequirementFragment.newInstance(getString(R.string.requirement), null);
                baseFragments.put(mRequirementFragment.getPrivateTag(), mRequirementFragment);
                transaction.add(R.id.contentContainer, mRequirementFragment, mRequirementFragment.getPrivateTag());
                transaction.commit();
            }
            return aClass.cast(mRequirementFragment);
        }
        return null;
    }

    private <T> void getBaseFragment(Class<T> tClass, Bundle bundle) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (tClass.isAssignableFrom(ProjectListFragment.class)) {
            if (mProjectListFragment != null) {
                mProjectListFragment = ProjectListFragment.newInstance(bundle, null);
                baseFragments.put(mProjectListFragment.getPrivateTag(), mProjectListFragment);
                transaction.replace(R.id.contentDetailContainer, mProjectListFragment, mProjectListFragment.getPrivateTag());
                transaction.commit();
            }
        } else if (tClass.isAssignableFrom(WeekPortFragment.class)) {
            if (mWeekPortFragment != null) {
                mWeekPortFragment = WeekPortFragment.newInstance(bundle, mChoiseTodayDate);
                baseFragments.put(mWeekPortFragment.getPrivateTag(), mWeekPortFragment);
                transaction.replace(R.id.contentDetailContainer, mWeekPortFragment, mWeekPortFragment.getPrivateTag());
                transaction.commit();
            }
        }
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
                    mShowFragmentTag = privateTag;
                    transaction.show(baseFragment);
                } else {
                    transaction.hide(baseFragment);
                }
            }
        }
        transaction.commit();
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    /**
     * 判断要不要开启新activity
     *
     * @return
     */
    public boolean isNeedNewActivity() {
        return !isHaveThree;
    }


    public <T> void loadThreeFragment(Class<T> tClass, Bundle bundle) {
        getBaseFragment(tClass, bundle);
    }


}
