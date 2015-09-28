package com.example.mokey.weekport.data.annotation;

import com.example.mokey.weekport.data.project.Proj;
import com.example.mokey.weekport.data.project.ProjectRoot;
import com.example.mokey.weekport.data.weekly.Task;
import com.example.mokey.weekport.data.weekly.WeekBrief;
import com.example.mokey.weekport.data.weekly.WeeklyRoot;

import java.util.List;

/**
 * Created by mokey on 15-9-6.
 */
public enum FieldType {
    StringType(String.class),
    ListType(List.class),
    WeeklyRootType(WeeklyRoot.class),
    WeekBriefType(WeekBrief.class),
    ProjType(Proj.class),
    ProjectRootType(ProjectRoot.class),
    TaskType(Task.class);

    Class<?> mTypeClass;

    FieldType(Class<?> stringClass) {
        mTypeClass = stringClass;
    }

    public Class getTypeClass() {
        return mTypeClass;
    }
}
