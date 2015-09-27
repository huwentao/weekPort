package com.example.mokey.weekport;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mokey.weekport.data.XmlUtil;
import com.example.mokey.weekport.data.project.ProjectRoot;
import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.ui.HomeActivity;
import com.example.mokey.weekport.ui.core.AppHandler;
import com.example.mokey.weekport.ui.core.BaseActivity;
import com.example.mokey.weekport.util.BaseAdapter;
import com.example.mokey.weekport.util.DialogUtil;
import com.example.mokey.weekport.util.TextUtil;
import com.example.mokey.weekport.util.ViewHolder;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

public class MainActivity extends BaseActivity {

    public final static int GOTO_NEXT = 1;
    private List<User> mUserList;
    public static String CURRENT_USER = "current_user";

    private AppHandler.StaticHandler handler = new AppHandler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case GOTO_NEXT:
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
                                });
                    }
                    finish();
                    break;
            }
        }
    }.getHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override public void run() {
                XmlUtil xmlUtil = XmlUtil.getInstance();
                try {
                    ProjectRoot projectRoot = getDbUtils().findFirst(ProjectRoot.class);
                    if (projectRoot == null) {
                        DateTime dateTime = DateTime.now(TimeZone.getDefault());
                        xmlUtil.initXmlFile(MainActivity.this, getDbUtils(), dateTime.format("YYYYMMDDhhmmss") + ".xml");
                    }
                    mUserList = getDbUtils().findAll(User.class);
                } catch (DbException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendEmptyMessageDelayed(GOTO_NEXT, 1500);
                }
            }
        }).start();
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
