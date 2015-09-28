package com.example.mokey.weekport.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.example.mokey.weekport.data.annotation.XmlUtil;
import com.example.mokey.weekport.data.project.Proj;
import com.example.mokey.weekport.data.project.ProjectRoot;
import com.example.mokey.weekport.db.DbUtils;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.db.sqlite.Selector;
import com.example.mokey.weekport.util.DialogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mokey on 15-9-27.
 */
public class ImportProjectFileTask extends AsyncTask<Integer, Integer, ProjectRoot> {
    private DbUtils mDbUtils;
    private TaskCallback mTaskCallback;
    private ProgressDialog progressDialog;
    private ProjectRoot projectRoot;
    private Logger logger = LoggerFactory.getLogger(ImportProjectFileTask.class);

    public ImportProjectFileTask(DbUtils mDbUtils, Context context, TaskCallback mTaskCallback) {
        this.mDbUtils = mDbUtils;
        this.mTaskCallback = mTaskCallback;
        this.progressDialog = DialogUtil.getProgressDialog(context, "正在导入项目文件，请稍后",
                new DialogInterface.OnCancelListener() {
                    @Override public void onCancel(DialogInterface dialog) {
                        cancel(true);
                        dialog.dismiss();
                    }
                });
    }

    @Override protected void onPreExecute() {
        super.onPreExecute();
        if (progressDialog != null)
            progressDialog.show();
    }

    @Override protected void onPostExecute(ProjectRoot projectRoot) {
        super.onPostExecute(projectRoot);
        if (progressDialog != null)
            progressDialog.dismiss();
        if (mTaskCallback != null) {
            mTaskCallback.finish(projectRoot);
        }
    }

    @Override protected void onCancelled() {
        super.onCancelled();
        restoreProjectData();
        if (mTaskCallback != null) {
            mTaskCallback.cancel(null);
        }
    }

    @Override protected ProjectRoot doInBackground(Integer... params) {
        ProjectRoot newProjectRoot = null;
        try {
            //备份原数据
            projectRoot = mDbUtils.findFirst(ProjectRoot.class);
            if (projectRoot != null) {
                List<Proj> projList = mDbUtils.findAll(Selector.from(Proj.class));
                projectRoot.setProjList(projList);
            } else {
                mDbUtils.deleteAll(Proj.class);
            }
            //载入新项目配置数据
            XmlUtil xmlUtil = XmlUtil.getInstance();
            File directory = XmlUtil.getXmlProjectDirectory();
            List<File> fileList = Arrays.asList(directory.listFiles());
            Collections.sort(fileList, new Comparator<File>() {
                @Override public int compare(File lhs, File rhs) {
                    long lhstime = lhs.lastModified();
                    long rhstime = rhs.lastModified();
                    if (lhstime - rhstime == 0) return 0;
                    return lhstime - rhstime > 0 ? 1 : -1;
                }
            });
            File file = fileList.get(fileList.size() - 1);
            newProjectRoot = xmlUtil.parseXMLToObject(ProjectRoot.class, file);
            mDbUtils.deleteAll(ProjectRoot.class);
            mDbUtils.deleteAll(Proj.class);
            for (Proj proj : newProjectRoot.getProjList()) {
                proj.setProjectId(newProjectRoot.getProjectId());
            }
            mDbUtils.saveOrUpdate(newProjectRoot);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            restoreProjectData();
            if (mTaskCallback != null) {
                mTaskCallback.failed(e);
            }
        }
        return newProjectRoot;
    }

    public void restoreProjectData() {
        try {
            if (projectRoot != null) {
                mDbUtils.deleteAll(ProjectRoot.class);
                mDbUtils.deleteAll(Proj.class);
                mDbUtils.saveOrUpdate(projectRoot);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public interface TaskCallback {
        public void finish(ProjectRoot projectRoot);

        public void failed(Exception e);

        public void cancel(Exception e);

    }
}
