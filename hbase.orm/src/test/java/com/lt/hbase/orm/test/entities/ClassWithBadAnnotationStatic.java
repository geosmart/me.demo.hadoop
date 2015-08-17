package com.lt.hbase.orm.test.entities;

import com.lt.hbase.orm.HBColumn;
import com.lt.hbase.orm.HBRecord;
import com.lt.hbase.orm.HBRowKey;

public class ClassWithBadAnnotationStatic implements HBRecord {
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

    @HBColumn(family = "a", column = "num_months")
    private static Integer NUM_MONTHS = 12; // not allowed
}
