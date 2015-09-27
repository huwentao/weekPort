package com.example.mokey.weekport.data.weekly;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.FieldType;
import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.data.XmlFieldName;
import com.example.mokey.weekport.data.XmlRootName;
import com.example.mokey.weekport.db.annotation.Column;
import com.example.mokey.weekport.db.annotation.Foreign;
import com.example.mokey.weekport.db.annotation.Id;
import com.example.mokey.weekport.db.annotation.Table;

import java.util.List;

/**
 * Created by mokey on 15-9-6.
 */
@XmlRootName(rootName = "root")
@Table(name = "tb_weeklyroot")
public class WeeklyRoot implements Parcelable {
    @Id(column = "_id")
    private Integer weeklyId;
    @Foreign(column = "user_id", foreign = "_id")
    private User user;
    @Column(column = "docType")
    @XmlFieldName(fieldName = "doc-type", value = "week-report") private String docType;
    @Column(column = "docVersion")
    @XmlFieldName(fieldName = "doc-version", value = "1.0") private String docVersion;
    @Column(column = "reportPerson")
    @XmlFieldName(fieldName = "report-person") private String reportPerson;//名字
    @Column(column = "idNo")
    @XmlFieldName(fieldName = "id-no") private String idNo;//身份证号码
    @Column(column = "reportYear")
    @XmlFieldName(fieldName = "report-year") private String reportYear;//当前年份
    @Column(column = "reportWeek")
    @XmlFieldName(fieldName = "report-week") private String reportWeek;//本年第几周周数
    @Foreign(column = "taskList",foreign = "_id")
    @XmlFieldName(fieldName = "task-list", type = FieldType.ListType) private List<Task> taskList;
    @Foreign(column = "weekBrief", foreign = "_id")
    @XmlFieldName(fieldName = "week-brief", type = FieldType.WeekBriefType)
    private WeekBrief weekBrief;//周报总结

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(String docVersion) {
        this.docVersion = docVersion;
    }

    public String getReportPerson() {
        return reportPerson;
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getReportYear() {
        return reportYear;
    }

    public void setReportYear(String reportYear) {
        this.reportYear = reportYear;
    }

    public String getReportWeek() {
        return reportWeek;
    }

    public void setReportWeek(String reportWeek) {
        this.reportWeek = reportWeek;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public Integer getWeeklyId() {
        return weeklyId;
    }

    public void setWeeklyId(Integer weeklyId) {
        this.weeklyId = weeklyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WeekBrief getWeekBrief() {
        return weekBrief;
    }

    public void setWeekBrief(WeekBrief weekBrief) {
        this.weekBrief = weekBrief;
    }


    public WeeklyRoot() {
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.weeklyId);
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.docType);
        dest.writeString(this.docVersion);
        dest.writeString(this.reportPerson);
        dest.writeString(this.idNo);
        dest.writeString(this.reportYear);
        dest.writeString(this.reportWeek);
        dest.writeTypedList(taskList);
        dest.writeParcelable(this.weekBrief, 0);
    }

    protected WeeklyRoot(Parcel in) {
        this.weeklyId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.docType = in.readString();
        this.docVersion = in.readString();
        this.reportPerson = in.readString();
        this.idNo = in.readString();
        this.reportYear = in.readString();
        this.reportWeek = in.readString();
        this.taskList = in.createTypedArrayList(Task.CREATOR);
        this.weekBrief = in.readParcelable(WeekBrief.class.getClassLoader());
    }

    public static final Creator<WeeklyRoot> CREATOR = new Creator<WeeklyRoot>() {
        public WeeklyRoot createFromParcel(Parcel source) {
            return new WeeklyRoot(source);
        }

        public WeeklyRoot[] newArray(int size) {
            return new WeeklyRoot[size];
        }
    };
}
