package com.example.mokey.weekport.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.util.TextUtil;

/**
 * Created by mokey on 15-9-28.
 */
public class HistorySuggestionsAdapter extends CursorAdapter {

    public HistorySuggestionsAdapter(Context context, Cursor c) {
        super(context, c, true);
    }


    @Override public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_searchhistory_item, parent, false);
    }

    @Override public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.historyText);
        String historyText = cursor.getString(cursor.getColumnIndex("historyText"));
        TextUtil.setText(textView, historyText);
    }


}
