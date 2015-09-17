package com.example.mokey.weekport.data.weekly;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.FieldType;
import com.example.mokey.weekport.data.XmlFieldName;
import com.example.mokey.weekport.data.XmlRootName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mokey on 15-9-6.
 */
@XmlRootName(rootName = "root")
public class WeeklyRoot implements Parcelable {
    @XmlFieldName(fieldName = "doc-type", value = "week-report") private String docType;
    @XmlFieldName(fieldName = "doc-version", value = "1.0") private String docVersion;
    @XmlFieldName(fieldName = "report-person") private String reportPerson;//名字
    @XmlFieldName(fieldName = "id-no") private String idNo;//身份证号码
    @XmlFieldName(fieldName = "report-year") private String reportYear;//当前年份
    @XmlFieldName(fieldName = "report-week") private String reportWeek;//本年第几周周数
    @XmlFieldName(fieldName = "task-list", type = FieldType.ListType) private List<Task> taskList;
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

    public WeekBrief getWeekBrief() {
        return weekBrief;
    }

    public void setWeekBrief(WeekBrief weekBrief) {
        this.weekBrief = weekBrief;
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.docType);
        dest.writeString(this.docVersion);
        dest.writeString(this.reportPerson);
        dest.writeString(this.idNo);
        dest.writeString(this.reportYear);
        dest.writeString(this.reportWeek);
        dest.writeList(this.taskList);
        dest.writeParcelable(this.weekBrief, flags);
    }

    public WeeklyRoot() {
    }

    protected WeeklyRoot(Parcel in) {
        this.docType = in.readString();
        this.docVersion = in.readString();
        this.reportPerson = in.readString();
        this.idNo = in.readString();
        this.reportYear = in.readString();
        this.reportWeek = in.readString();
        this.taskList = new ArrayList<Task>();
        in.readList(this.taskList, List.class.getClassLoader());
        this.weekBrief = in.readParcelable(WeekBrief.class.getClassLoader());
    }

    public static final Parcelable.Creator<WeeklyRoot> CREATOR = new Parcelable.Creator<WeeklyRoot>() {
        public WeeklyRoot createFromParcel(Parcel source) {
            return new WeeklyRoot(source);
        }

        public WeeklyRoot[] newArray(int size) {
            return new WeeklyRoot[size];
        }
    };
}
