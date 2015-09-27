package com.example.mokey.weekport.data.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.db.annotation.Column;
import com.example.mokey.weekport.db.annotation.Id;
import com.example.mokey.weekport.db.annotation.Table;
import com.example.mokey.weekport.db.annotation.Unique;

/**
 * Created by mokey on 15-9-17.
 */
@Table(name = "tb_user")
public class User implements Parcelable {
     @Id(column = "_id")
     private Integer userId;
     @Column(column = "reportPerson")
     private String reportPerson;//名字
     @Column(column = "idNo")
     @Unique
     private String idNo;//身份证号码

     public Integer getUserId() {
          return userId;
     }

     public void setUserId(Integer userId) {
          this.userId = userId;
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

     @Override public int describeContents() {
          return 0;
     }

     @Override public void writeToParcel(Parcel dest, int flags) {
          dest.writeValue(this.userId);
          dest.writeString(this.reportPerson);
          dest.writeString(this.idNo);
     }

     public User() {
     }

     public User(String reportPerson, String idNo) {
          this.reportPerson = reportPerson;
          this.idNo = idNo;
     }

     public User(Integer userId, String reportPerson, String idNo) {
          this.userId = userId;
          this.reportPerson = reportPerson;
          this.idNo = idNo;
     }

     protected User(Parcel in) {
          this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
          this.reportPerson = in.readString();
          this.idNo = in.readString();
     }

     public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
          public User createFromParcel(Parcel source) {
               return new User(source);
          }

          public User[] newArray(int size) {
               return new User[size];
          }
     };
}
