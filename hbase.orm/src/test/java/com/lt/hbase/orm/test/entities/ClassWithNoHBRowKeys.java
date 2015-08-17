package com.lt.hbase.orm.test.entities;

import com.lt.hbase.orm.HBColumn;
import com.lt.hbase.orm.HBRecord;

public class ClassWithNoHBRowKeys implements HBRecord {
    protected String key = "key";

    @Override
    public String composeRowKey() {
        return key;
    }

    @Override
    public void parseRowKey(String rowKey) {
        this.key = rowKey;
    }

    @HBColumn(family = "f", column = "c")
    private Float f;
}
