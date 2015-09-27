package com.example.mokey.weekport.task;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mokey.weekport.data.XmlUtil;
import com.example.mokey.weekport.data.project.ProjectRoot;
import com.example.mokey.weekport.db.DbUtils;
import com.example.mokey.weekport.db.exception.DbException;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * Created by mokey on 15-9-27.
 */
public class InitProjectFileTask extends AsyncTask<Integer, Integer, ProjectRoot> {
    private DbUtils mDbUtils;
    private Context mContext;
    private TaskCallback mTaskCallback;

    public InitProjectFileTask(DbUtils mDbUtils, Context mContext, TaskCallback taskCallback) {
        this.mDbUtils = mDbUtils;
        this.mContext = mContext;
        this.mTaskCallback = taskCallback;
    }

    @Override protected void onPostExecute(ProjectRoot projectRoot) {
        super.onPostExecute(projectRoot);
        if (mTaskCallback != null) {
            mTaskCallback.callBack(projectRoot);
        }
    }

    @Override protected ProjectRoot doInBackground(Integer... params) {
        try {
            XmlUtil xmlUtil = XmlUtil.getInstance();
            ProjectRoot projectRoot = mDbUtils.findFirst(ProjectRoot.class);
            if (projectRoot == null) {
                DateTime dateTime = DateTime.now(TimeZone.getDefault());
                xmlUtil.initXmlFile(mContext, mDbUtils, dateTime.format("YYYYMMDDhhmmss") + ".xml");
            }
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
        }
        return null;
    }

    public interface TaskCallback {
        public void callBack(ProjectRoot projectRoot);
    }
}
