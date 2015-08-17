package com.lt.hbase.orm.test.entities;


import com.lt.hbase.orm.HBColumn;
import com.lt.hbase.orm.HBRecord;
import com.lt.hbase.orm.HBRowKey;
import com.lt.hbase.orm.HBTable;

import lombok.ToString;

@ToString
@HBTable("citizen_summary")
public class CitizenSummary implements HBRecord {

    @HBRowKey
    private String key;

    @HBColumn(family = "a", column = "average_age")
    private Float averageAge;

    public CitizenSummary() {
        key = "summary";
    }

    @Override
    public String composeRowKey() {
        return key;
    }

    @Override
    public void parseRowKey(String rowKey) {
        key = rowKey;
    }

    public Float getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(float averageAge) {
        this.averageAge = averageAge;
    }
}
