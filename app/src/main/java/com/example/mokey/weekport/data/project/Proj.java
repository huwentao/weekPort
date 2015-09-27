/*
 * Copyright (c) 2015. huwentao.
 */

package com.example.mokey.weekport.data.project;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.XmlFieldName;
import com.example.mokey.weekport.data.XmlRootName;
import com.example.mokey.weekport.db.annotation.Column;
import com.example.mokey.weekport.db.annotation.Foreign;
import com.example.mokey.weekport.db.annotation.Id;
import com.example.mokey.weekport.db.annotation.Table;

/**
 * Created by mokey on 15-9-6.
 */
@XmlRootName(rootName = "proj")
@Table(name = "tb_proj")
public class Proj implements Parcelable {
    @Id(column = "_id")
    private Integer pId;
    @Foreign(column = "projectId", foreign = "_id")
    private ProjectRoot projectRoot;
    @Column(column = "projId") @XmlFieldName(fieldName = "proj-id") private String projId;//
    @Column(column = "projName") @XmlFieldName(fieldName = "proj-name")
    private String projName;//中国银行卡部APPS开发中银开放平台
    @Column(column = "startDate") @XmlFieldName(fieldName = "start-date")
    private String startDate;//
    @Column(column = "startTime") @XmlFieldName(fieldName = "start-time")
    private String startTime;//
    @Column(column = "projType") @XmlFieldName(fieldName = "proj-type") private String projType;//
    @Column(column = "luodiFlag") @XmlFieldName(fieldName = "luodi-flag")
    private String luodiFlag;//
    @Column(column = "workType") @XmlFieldName(fieldName = "work-type") private String workType;//

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

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

    public ProjectRoot getProjectRoot() {
        return projectRoot;
    }

    public void setProjectRoot(ProjectRoot projectRoot) {
        this.projectRoot = projectRoot;
    }

    public Proj() {
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.pId);
        dest.writeParcelable(this.projectRoot, 0);
        dest.writeString(this.projId);
        dest.writeString(this.projName);
        dest.writeString(this.startDate);
        dest.writeString(this.startTime);
        dest.writeString(this.projType);
        dest.writeString(this.luodiFlag);
        dest.writeString(this.workType);
    }

    protected Proj(Parcel in) {
        this.pId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.projectRoot = in.readParcelable(ProjectRoot.class.getClassLoader());
        this.projId = in.readString();
        this.projName = in.readString();
        this.startDate = in.readString();
        this.startTime = in.readString();
        this.projType = in.readString();
        this.luodiFlag = in.readString();
        this.workType = in.readString();
    }

    public static final Creator<Proj> CREATOR = new Creator<Proj>() {
        public Proj createFromParcel(Parcel source) {
            return new Proj(source);
        }

        public Proj[] newArray(int size) {
            return new Proj[size];
        }
    };
}
