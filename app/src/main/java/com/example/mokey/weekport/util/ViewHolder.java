package com.example.mokey.weekport.util;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * <p>配合ListView控件使用，省去在创建listAdapter时getView()时重复编写ViewHolder类。</p>
 * <p>可与BaseAdapter配合使用。</p>
 *
 */
@SuppressWarnings("unchecked")
public class ViewHolder {
    private static SparseArray<View> viewHolder;
    /**
     * @param view 当前listView itemview的视图
     * @param id   视图中控件ID
     * @param <T>  返回根据提供的控件ID从itemview中找到的控件视图
     * @return ID指向的View
     */
    public static <T extends View> T get(View view, int id) {
        viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public static void clearViewHolder() {
        for (int i = 0; i < viewHolder.size(); i++) {
            View view = viewHolder.get(viewHolder.keyAt(i));
            if (view instanceof TextView) {
                ((TextView) view).setText("");
            }
        }
    }
}
