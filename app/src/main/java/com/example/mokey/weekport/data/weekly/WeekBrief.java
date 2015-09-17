/*
 * Copyright (c) 2015. huwentao.
 */

package com.example.mokey.weekport.data.weekly;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.XmlFieldName;
import com.example.mokey.weekport.data.XmlRootName;

/**
 * Created by mokey on 15-9-6.
 */
@XmlRootName(rootName = "week-brief")
public class WeekBrief implements Parcelable {
    @XmlFieldName(fieldName = "week-summary") private String weekSummary;//本周总结
    @XmlFieldName(fieldName = "next-plan") private String nextPlan;//下周计划

    public String getWeekSummary() {
        return weekSummary;
    }

    public void setWeekSummary(String weekSummary) {
        this.weekSummary = weekSummary;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weekSummary);
        dest.writeString(this.nextPlan);
    }

    public WeekBrief() {
    }

    protected WeekBrief(Parcel in) {
        this.weekSummary = in.readString();
        this.nextPlan = in.readString();
    }

    public static final Parcelable.Creator<WeekBrief> CREATOR = new Parcelable.Creator<WeekBrief>() {
        public WeekBrief createFromParcel(Parcel source) {
            return new WeekBrief(source);
        }

        public WeekBrief[] newArray(int size) {
            return new WeekBrief[size];
        }
    };
}
