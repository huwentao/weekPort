package com.example.mokey.weekport.data;

import com.example.mokey.weekport.data.weekly.WeeklyRoot;

import java.util.List;

/**
 * Created by mokey on 15-9-6.
 */
public enum FieldType {
    StringType(String.class), ListType(List.class),WeeklyRootType(WeeklyRoot.class);

    Class<?> mTypeClass;

    FieldType(Class<?> stringClass) {
        mTypeClass = stringClass;
    }

    public Class getTypeClass() {
        return mTypeClass;
    }
}
