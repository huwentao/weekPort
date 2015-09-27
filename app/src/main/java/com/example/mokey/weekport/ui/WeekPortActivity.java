package com.example.mokey.weekport.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.ui.core.BaseActivity;
import com.example.mokey.weekport.ui.fragment.WeekPortFragment;

public class WeekPortActivity extends BaseActivity {

    private FragmentManager mFragmentManger;
    private WeekPortFragment mWeekPortFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mFragmentManger = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManger.beginTransaction();
        mWeekPortFragment = WeekPortFragment.newInstance(null, null);
        transaction.add(R.id.contentContainer, mWeekPortFragment, mWeekPortFragment.getPrivateTag());
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
