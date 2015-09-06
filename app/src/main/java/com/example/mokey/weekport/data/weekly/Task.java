package com.example.mokey.weekport.data.weekly;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.FieldType;
import com.example.mokey.weekport.data.XmlFieldName;
import com.example.mokey.weekport.data.XmlRootName;

/**
 * Created by mokey on 15-9-6.
 */
@XmlRootName(rootName = "task")
public class Task implements Parcelable {
    @XmlFieldName(type = FieldType.StringType, fieldName = "task-date")
    private String taskDate;//日期
    @XmlFieldName(type = FieldType.StringType, fieldName = "task-seq")
    private String taskSeq;//>序号
    @XmlFieldName(type = FieldType.StringType, fieldName = "proj-id")
    private String projId;//项目ID
    @XmlFieldName(type = FieldType.StringType, fieldName = "szit-id")
    private String szitId;//需求ID
    @XmlFieldName(type = FieldType.StringType, fieldName = "task-memo")
    private String taskMemo;//任务内容
    @XmlFieldName(type = FieldType.StringType, fieldName = "normal-hour", value = "3")
    private String normalHour;//正常工时
    @XmlFieldName(type = FieldType.StringType, fieldName = "overtime-hour", value = "0")
    private String overtimeHour;//加班工时
    @XmlFieldName(type = FieldType.StringType, fieldName = "traffic-fee", value = "0.00")
    private String trafficFee;//交通费用
    @XmlFieldName(type = FieldType.StringType, fieldName = "meal-fee", value = "0.00")
    private String mealFee;//加班餐费
    @XmlFieldName(type = FieldType.StringType, fieldName = "other-fee", value = "0.00")
    private String otherFee;//其他
    @XmlFieldName(type = FieldType.StringType, fieldName = "out_flag", value = "0.00")
    private String outFlag;//
    @XmlFieldName(type = FieldType.StringType, fieldName = "task_type", value = "0")
    private String taskType;//任务类型>0</out_flag
    @XmlFieldName(type = FieldType.StringType, fieldName = "apply_no")
    private String applyNo;//
    @XmlFieldName(type = FieldType.StringType, fieldName = "memo1")
    private String memo1;//

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskSeq() {
        return taskSeq;
    }

    public void setTaskSeq(String taskSeq) {
        this.taskSeq = taskSeq;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getSzitId() {
        return szitId;
    }

    public void setSzitId(String szitId) {
        this.szitId = szitId;
    }

    public String getTaskMemo() {
        return taskMemo;
    }

    public void setTaskMemo(String taskMemo) {
        this.taskMemo = taskMemo;
    }

    public String getNormalHour() {
        return normalHour;
    }

    public void setNormalHour(String normalHour) {
        this.normalHour = normalHour;
    }

    public String getOvertimeHour() {
        return overtimeHour;
    }

    public void setOvertimeHour(String overtimeHour) {
        this.overtimeHour = overtimeHour;
    }

    public String getTrafficFee() {
        return trafficFee;
    }

    public void setTrafficFee(String trafficFee) {
        this.trafficFee = trafficFee;
    }

    public String getMealFee() {
        return mealFee;
    }

    public void setMealFee(String mealFee) {
        this.mealFee = mealFee;
    }

    public String getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(String otherFee) {
        this.otherFee = otherFee;
    }

    public String getOutFlag() {
        return outFlag;
    }

    public void setOutFlag(String outFlag) {
        this.outFlag = outFlag;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.taskDate);
        dest.writeString(this.taskSeq);
        dest.writeString(this.projId);
        dest.writeString(this.szitId);
        dest.writeString(this.taskMemo);
        dest.writeString(this.normalHour);
        dest.writeString(this.overtimeHour);
        dest.writeString(this.trafficFee);
        dest.writeString(this.mealFee);
        dest.writeString(this.otherFee);
        dest.writeString(this.outFlag);
        dest.writeString(this.taskType);
        dest.writeString(this.applyNo);
        dest.writeString(this.memo1);
    }

    public Task() {
    }

    protected Task(Parcel in) {
        this.taskDate = in.readString();
        this.taskSeq = in.readString();
        this.projId = in.readString();
        this.szitId = in.readString();
        this.taskMemo = in.readString();
        this.normalHour = in.readString();
        this.overtimeHour = in.readString();
        this.trafficFee = in.readString();
        this.mealFee = in.readString();
        this.otherFee = in.readString();
        this.outFlag = in.readString();
        this.taskType = in.readString();
        this.applyNo = in.readString();
        this.memo1 = in.readString();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
