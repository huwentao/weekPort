package com.example.mokey.weekport.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.ui.core.BaseActivity;
import com.example.mokey.weekport.ui.fragment.ProjectListFragment;
import com.example.mokey.weekport.ui.fragment.WeekPortFragment;

public class ProjectListActivity extends BaseActivity {

    private FragmentManager mFragmentManger;
    private ProjectListFragment mProjectListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        mFragmentManger = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManger.beginTransaction();
        mProjectListFragment = ProjectListFragment.newInstance(null, null);
        transaction.add(R.id.contentContainer, mProjectListFragment, mProjectListFragment.getPrivateTag());
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
