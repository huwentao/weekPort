package com.example.mokey.weekport;

import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mokey.weekport.ui.HomeActivity;
import com.example.mokey.weekport.ui.core.AppHandler;
import com.example.mokey.weekport.ui.core.BaseActivity;

public class MainActivity extends BaseActivity {

    public final static int GOTO_NEXT = 1;

    private AppHandler.StaticHandler handler = new AppHandler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case GOTO_NEXT:
                    callMe(HomeActivity.class);
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
        handler.sendEmptyMessageDelayed(GOTO_NEXT, 1000);
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
