/*
 * Copyright (c) 2015 huwentao (vernon.huwt@gmail.com)
 */

package com.example.mokey.weekport.util;

import android.database.Cursor;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.mokey.weekport.db.DbUtils;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.db.table.TableUtils;
import com.example.mokey.weekport.ui.core.BaseActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by monkey on 2015/8/17.
 * 在给定数据集中进行过滤
 */
public abstract class AutoCompleteFilterFormDbAdapter extends BaseAdapter<AutoCompleteFilterObject> implements Filterable {
    private final Class<?> mTableClass;
    private final String mFindCloumnName;
    private final String mOrderBycloumnName;
    private ArrayFilter mFilter;
    private DbUtils dbUtils;
    private List<AutoCompleteFilterObject> filterObjectList = new ArrayList<>();
    private AutoCompleteFilterObject mFilterObject;
    private static final Logger logger = LoggerFactory.getLogger(AutoCompleteFilterFormDbAdapter.class);

    /**
     * @param activity    应用上下文
     * @param layoutResId 列表ITEM布局文件
     */
    public AutoCompleteFilterFormDbAdapter(BaseActivity activity,
                                           AutoCompleteFilterObject filterObject,
                                           Class<?> tableClass,
                                           String findCloumnName,
                                           String orderByCloumnName,
                                           int layoutResId) {
        super(activity, new ArrayList<AutoCompleteFilterObject>(), layoutResId);
        dbUtils = activity.getDbUtils();
        mFilterObject = filterObject;
        mTableClass = tableClass;
        mFindCloumnName = findCloumnName;
        mOrderBycloumnName = orderByCloumnName;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || prefix.length() == 0) {
                results.values = new ArrayList<AutoCompleteFilterObject>();
                results.count = 0;
            } else {
                filterObjectList.clear();
                String prefixString = prefix.toString().toLowerCase();
                String tableName = TableUtils.getTableName(mTableClass);
                String querySql = new StringBuffer().append("select * from ")
                        .append(tableName)
                        .append(" where ")
                        .append(mFindCloumnName)
                        .append(" like '%")
                        .append(prefixString)
                        .append("%' order by ")
                        .append(mOrderBycloumnName).toString();
                logger.debug("autoComplete querysql={}", querySql);
                Cursor cursor = null;
                try {
                    cursor = dbUtils.execQuery(querySql);
                    while (cursor.moveToNext()) {
                        filterObjectList.add(mFilterObject.createObject(cursor));
                    }
                    results.values = filterObjectList;
                    results.count = filterObjectList.size();
                } catch (DbException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            tList = (List<AutoCompleteFilterObject>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            AutoCompleteFilterObject completeFilterObject = (AutoCompleteFilterObject) resultValue;
            return super.convertResultToString(completeFilterObject.getSearchWord());
        }
    }
}
