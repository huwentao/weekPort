package com.example.mokey.weekport.data.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.project.Proj;
import com.example.mokey.weekport.db.annotation.Column;
import com.example.mokey.weekport.db.annotation.Foreign;
import com.example.mokey.weekport.db.annotation.Id;
import com.example.mokey.weekport.db.annotation.Table;

/**
 * Created by mokey on 15-9-27.
 */
@Table(name = "tb_userProject")
public class UserProject implements Parcelable {
    @Id(column = "_id")
    private Integer userProjectId;
    @Foreign(column = "user_id", foreign = "_id")
    private User user;
    @Column(column = "pId") private Integer pId;//
    @Column(column = "projId") private String projId;//
    @Column(column = "projName") private String projName;//中国银行卡部APPS开发中银开放平台
    @Column(column = "startDate") private String startDate;//
    @Column(column = "startTime") private String startTime;//
    @Column(column = "projType") private String projType;//
    @Column(column = "luodiFlag") private String luodiFlag;//
    @Column(column = "workType") private String workType;//

    public UserProject(Integer userProjectId, User user, Proj proj) {
        this.user = user;
        this.userProjectId = userProjectId;
        this.pId = proj.getpId();
        this.projId = proj.getProjId();
        this.projName = proj.getProjName();
        this.startDate = proj.getStartDate();
        this.startTime = proj.getStartTime();
        this.projType = proj.getProjType();
        this.luodiFlag = proj.getLuodiFlag();
        this.workType = proj.getWorkType();
    }

    public Integer getUserProjectId() {
        return userProjectId;
    }

    public void setUserProjectId(Integer userProjectId) {
        this.userProjectId = userProjectId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public UserProject() {
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.userProjectId);
        dest.writeParcelable(this.user, 0);
        dest.writeValue(this.pId);
        dest.writeString(this.projId);
        dest.writeString(this.projName);
        dest.writeString(this.startDate);
        dest.writeString(this.startTime);
        dest.writeString(this.projType);
        dest.writeString(this.luodiFlag);
        dest.writeString(this.workType);
    }

    protected UserProject(Parcel in) {
        this.userProjectId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.pId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.projId = in.readString();
        this.projName = in.readString();
        this.startDate = in.readString();
        this.startTime = in.readString();
        this.projType = in.readString();
        this.luodiFlag = in.readString();
        this.workType = in.readString();
    }

    public static final Creator<UserProject> CREATOR = new Creator<UserProject>() {
        public UserProject createFromParcel(Parcel source) {
            return new UserProject(source);
        }

        public UserProject[] newArray(int size) {
            return new UserProject[size];
        }
    };
}
