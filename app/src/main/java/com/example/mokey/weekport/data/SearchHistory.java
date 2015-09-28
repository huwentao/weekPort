package com.example.mokey.weekport.data;

import com.example.mokey.weekport.db.annotation.Column;
import com.example.mokey.weekport.db.annotation.Id;
import com.example.mokey.weekport.db.annotation.Unique;

/**
 * Created by mokey on 15-9-28.
 */
public class SearchHistory {
    @Id(column = "_id")private Long _id;
    @Unique
    @Column(column = "historyText") private String historyText;
    @Column(column = "createTime") private String createTime;

}
