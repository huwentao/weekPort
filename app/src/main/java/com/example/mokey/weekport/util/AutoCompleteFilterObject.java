package com.example.mokey.weekport.util;

import android.database.Cursor;

/**
 * Created by monkey on 2015/8/17.
 */
public interface AutoCompleteFilterObject {

    public String getSearchWord();

    public AutoCompleteFilterObject createObject(Cursor cursor);
}
