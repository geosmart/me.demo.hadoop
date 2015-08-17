package com.lt.hbase.orm.test.entities;

import com.lt.hbase.orm.HBColumn;
import com.lt.hbase.orm.HBRecord;
import com.lt.hbase.orm.HBRowKey;

public class ClassWithPrimitives implements HBRecord {
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
    private float f;

    public ClassWithPrimitives() {

    }

    public ClassWithPrimitives(float f) {
        this.f = f;
    }
}
