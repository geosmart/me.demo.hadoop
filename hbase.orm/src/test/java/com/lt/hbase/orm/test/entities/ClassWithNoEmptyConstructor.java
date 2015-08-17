package com.lt.hbase.orm.test.entities;

import com.lt.hbase.orm.HBColumn;
import com.lt.hbase.orm.HBRecord;
import com.lt.hbase.orm.HBRowKey;

public class ClassWithNoEmptyConstructor implements HBRecord {
    @HBRowKey
    protected String key = "key";

    @Override
    public String composeRowKey() {
        return key;
    }

    @Override
    public void parseRowKey(String rowKey) {
        this.key = rowKey;
    }

    @HBColumn(family = "a", column = "b")
    private Integer i;

    public ClassWithNoEmptyConstructor(int i) {
        this.i = i;
    }
}
