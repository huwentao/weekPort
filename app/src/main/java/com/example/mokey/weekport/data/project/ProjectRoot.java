/*
 * Copyright (c) 2015. huwentao.
 */

package com.example.mokey.weekport.data.project;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.annotation.FieldType;
import com.example.mokey.weekport.data.annotation.XmlFieldName;
import com.example.mokey.weekport.data.annotation.XmlRootName;
import com.example.mokey.weekport.db.annotation.Column;
import com.example.mokey.weekport.db.annotation.Foreign;
import com.example.mokey.weekport.db.annotation.Id;
import com.example.mokey.weekport.db.annotation.Table;

import java.util.List;

/**
 * Created by mokey on 15-9-6.
 */
@XmlRootName(rootName = "root")
@Table(name = "tb_project")
public class ProjectRoot implements Parcelable {
    @Id(column = "_id")
    private Integer projectId;
    @Column(column = "createTime")
    private String createTime;
    @XmlFieldName(fieldName = "doc-type", value = "proj-info")
    @Column(column = "docType")
    private String docType;
    @XmlFieldName(fieldName = "doc-version", value = "1.0")
    @Column(column = "docVersion")
    private String docVersion;
    @XmlFieldName(fieldName = "proj-list", type = FieldType.ListType)
    @Foreign(column = "projId", foreign = "_id")
    private List<Proj> projList;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

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

    public List<Proj> getProjList() {
        return projList;
    }

    public void setProjList(List<Proj> projList) {
        this.projList = projList;
    }


    public ProjectRoot() {
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.projectId);
        dest.writeString(this.createTime);
        dest.writeString(this.docType);
        dest.writeString(this.docVersion);
        dest.writeTypedList(projList);
    }

    protected ProjectRoot(Parcel in) {
        this.projectId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createTime = in.readString();
        this.docType = in.readString();
        this.docVersion = in.readString();
        this.projList = in.createTypedArrayList(Proj.CREATOR);
    }

    public static final Creator<ProjectRoot> CREATOR = new Creator<ProjectRoot>() {
        public ProjectRoot createFromParcel(Parcel source) {
            return new ProjectRoot(source);
        }

        public ProjectRoot[] newArray(int size) {
            return new ProjectRoot[size];
        }
    };
}
