/*
 * Copyright (c) 2015. huwentao.
 */

package com.example.mokey.weekport.data.project;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.XmlFieldName;
import com.example.mokey.weekport.data.XmlRootName;

/**
 * Created by mokey on 15-9-6.
 */
@XmlRootName(rootName = "proj")
public class Proj implements Parcelable {
    @XmlFieldName(fieldName = "proj-id") private String projId;//
    @XmlFieldName(fieldName = "proj-name") private String projName;//中国银行卡部APPS开发中银开放平台
    @XmlFieldName(fieldName = "start-date") private String startDate;//
    @XmlFieldName(fieldName = "start-time") private String startTime;//
    @XmlFieldName(fieldName = "proj-type") private String projType;//
    @XmlFieldName(fieldName = "luodi-flag") private String luodiFlag;//
    @XmlFieldName(fieldName = "work-type") private String workType;//

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public String getLuodiFlag() {
        return luodiFlag;
    }

    public void setLuodiFlag(String luodiFlag) {
        this.luodiFlag = luodiFlag;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projId);
        dest.writeString(this.projName);
        dest.writeString(this.startDate);
        dest.writeString(this.startTime);
        dest.writeString(this.projType);
        dest.writeString(this.luodiFlag);
        dest.writeString(this.workType);
    }

    public Proj() {
    }

    protected Proj(Parcel in) {
        this.projId = in.readString();
        this.projName = in.readString();
        this.startDate = in.readString();
        this.startTime = in.readString();
        this.projType = in.readString();
        this.luodiFlag = in.readString();
        this.workType = in.readString();
    }

    public static final Parcelable.Creator<Proj> CREATOR = new Parcelable.Creator<Proj>() {
        public Proj createFromParcel(Parcel source) {
            return new Proj(source);
        }

        public Proj[] newArray(int size) {
            return new Proj[size];
        }
    };
}
