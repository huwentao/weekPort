/*
 * Copyright (c) 2015 huwentao (vernon.huwt@gmail.com)
 */

package com.example.mokey.weekport.util;

import android.widget.Filter;
import android.widget.Filterable;

import com.example.mokey.weekport.ui.core.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkey on 2015/8/17.
 * 在给定数据集中进行过滤
 */
public abstract class AutoCompleteFilterAdapter extends BaseAdapter<AutoCompleteFilterObject> implements Filterable {
    private ArrayFilter mFilter;
    private List<AutoCompleteFilterObject> mOriginalValues;

    /**
     * @param activity                  应用上下文
     * @param autoCompleteFilterObjects 列表参数集合
     * @param layoutResId               列表ITEM布局文件
     */
    public AutoCompleteFilterAdapter(BaseActivity activity, List<AutoCompleteFilterObject> autoCompleteFilterObjects, int layoutResId) {
        super(activity, autoCompleteFilterObjects, layoutResId);
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
                String prefixString = prefix.toString().toLowerCase();

                List<AutoCompleteFilterObject> values = new ArrayList<>(tList);

                final int count = values.size();
                final ArrayList<AutoCompleteFilterObject> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final AutoCompleteFilterObject value = values.get(i);
                    final String valueText = value.getSearchWord().toLowerCase();

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
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
