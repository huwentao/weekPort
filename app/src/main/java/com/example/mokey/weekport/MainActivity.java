package com.example.mokey.weekport;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mokey.weekport.data.project.ProjectRoot;
import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.task.InitProjectFileTask;
import com.example.mokey.weekport.ui.HomeActivity;
import com.example.mokey.weekport.ui.core.BaseActivity;
import com.example.mokey.weekport.util.BaseAdapter;
import com.example.mokey.weekport.util.DialogUtil;
import com.example.mokey.weekport.util.TextUtil;
import com.example.mokey.weekport.util.ViewHolder;

import java.util.List;

public class MainActivity extends BaseActivity {

    public final static int GOTO_NEXT = 1;
    private List<User> mUserList = null;
    public static String CURRENT_USER = "current_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override protected void onResume() {
        super.onResume();
        new InitProjectFileTask(getDbUtils(), this, new InitProjectFileTask.TaskCallback() {
            @Override public void callBack(ProjectRoot projectRoot) {
                try {
                    mUserList = getDbUtils().findAll(User.class);
                } catch (DbException e) {
                }
                if (mUserList != null) {
                    DialogUtil.getListAlertDialog(MainActivity.this, "选择使用人",
                            new BaseAdapter<User>(MainActivity.this, mUserList, R.layout.layout_userlist_item) {
                                @Override
                                public void viewHandler(int position, User user, View convertView) {
                                    TextView textView = ViewHolder.get(convertView, R.id.userName);
                                    TextUtil.setText(textView, user.getReportPerson());
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable(CURRENT_USER, mUserList.get(which));
                                    callMe(HomeActivity.class, bundle);
                                    finish();
                                }
                            }, new DialogInterface.OnCancelListener() {
                                @Override public void onCancel(DialogInterface dialog) {
                                    callMe(HomeActivity.class);
                                    finish();
                                }
                            }).show();
                } else {
                    callMe(HomeActivity.class);
                    finish();
                }
            }
        }).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
