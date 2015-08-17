package com.lt.hbase.orm.test.entities;

import com.lt.hbase.orm.HBColumn;
import com.lt.hbase.orm.HBRecord;
import com.lt.hbase.orm.HBRowKey;

public class UninstantiatableClass implements HBRecord {
    @HBRowKey
    private Integer uid;
    @HBColumn(family = "main", column = "name")
    private String name;

    public UninstantiatableClass() {
        throw new RuntimeException("I'm a bad constructor");
    }

    @Override
    public String composeRowKey() {
        return uid.toString();
    }

    @Override
    public void parseRowKey(String rowKey) {
        this.uid = Integer.valueOf(rowKey);
    }
}
