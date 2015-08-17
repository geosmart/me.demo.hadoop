package com.lt.hbase.orm.test.entities;

import com.lt.hbase.orm.HBRecord;
import com.lt.hbase.orm.HBRowKey;

public class ClassWithNoHBColumns implements HBRecord {
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

    private Float f; //not adding @HBColumn here!
}
