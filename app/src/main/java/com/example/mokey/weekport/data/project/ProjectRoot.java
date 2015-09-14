/*
 * Copyright (c) 2015. huwentao.
 */

package com.example.mokey.weekport.data.project;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mokey.weekport.data.FieldType;
import com.example.mokey.weekport.data.XmlFieldName;
import com.example.mokey.weekport.data.XmlRootName;

import java.util.List;

/**
 * Created by mokey on 15-9-6.
 */
@XmlRootName(rootName = "root")
public class ProjectRoot implements Parcelable {
    @XmlFieldName(fieldName = "doc-type", value = "proj-info")
    private String docType;
    @XmlFieldName(fieldName = "doc-version", value = "1.0")
    private String docVersion;
    @XmlFieldName(fieldName = "proj-list", type = FieldType.ListType)
    private List<Proj> projList;

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


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.docType);
        dest.writeString(this.docVersion);
        dest.writeTypedList(projList);
    }

    public ProjectRoot() {
    }

    protected ProjectRoot(Parcel in) {
        this.docType = in.readString();
        this.docVersion = in.readString();
        this.projList = in.createTypedArrayList(Proj.CREATOR);
    }

    public static final Parcelable.Creator<ProjectRoot> CREATOR = new Parcelable.Creator<ProjectRoot>() {
        public ProjectRoot createFromParcel(Parcel source) {
            return new ProjectRoot(source);
        }

        public ProjectRoot[] newArray(int size) {
            return new ProjectRoot[size];
        }
    };
}
