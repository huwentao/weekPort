/*
 * Copyright (c) 2015. huwentao.
 */

package com.example.mokey.weekport.data.weekly;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.annotation.XmlFieldName;
import com.example.mokey.weekport.data.annotation.XmlRootName;
import com.example.mokey.weekport.db.annotation.Column;
import com.example.mokey.weekport.db.annotation.Id;
import com.example.mokey.weekport.db.annotation.Table;

/**
 * Created by mokey on 15-9-6.
 */
@Table(name = "tb_weekbrief")
@XmlRootName(rootName = "week-brief")
public class WeekBrief implements Parcelable {
    @Id(column = "_id")
    private Integer briefId;
    @Column(column = "weekSummary")
    @XmlFieldName(fieldName = "week-summary") private String weekSummary;//本周总结
    @Column(column = "nextPlan")
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

    public Integer getBriefId() {
        return briefId;
    }

    public void setBriefId(Integer briefId) {
        this.briefId = briefId;
    }

    public WeekBrief() {
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.briefId);
        dest.writeString(this.weekSummary);
        dest.writeString(this.nextPlan);
    }

    protected WeekBrief(Parcel in) {
        this.briefId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.weekSummary = in.readString();
        this.nextPlan = in.readString();
    }

    public static final Creator<WeekBrief> CREATOR = new Creator<WeekBrief>() {
        public WeekBrief createFromParcel(Parcel source) {
            return new WeekBrief(source);
        }

        public WeekBrief[] newArray(int size) {
            return new WeekBrief[size];
        }
    };
}
