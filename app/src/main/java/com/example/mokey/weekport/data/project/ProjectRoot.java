/*
 * Copyright (c) 2015. huwentao.
 */

package com.example.mokey.weekport.data.project;

import com.example.mokey.weekport.data.FieldType;
import com.example.mokey.weekport.data.XmlFieldName;
import com.example.mokey.weekport.data.XmlRootName;

import java.util.List;

/**
 * Created by mokey on 15-9-6.
 */
@XmlRootName(rootName = "root")
public class ProjectRoot {
    @XmlFieldName(fieldName = "doc-type", value = "proj-info")
    private String docType;
    @XmlFieldName(fieldName = "doc-type", value = "doc-version")
    private String docVersion;
    @XmlFieldName(fieldName = "doc-type", type = FieldType.ListType, value = "proj-list")
    private List<Proj> projList;
}
