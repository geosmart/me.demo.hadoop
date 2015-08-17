package com.lt.hbase.orm.test.daos;


import com.lt.hbase.orm.BaseHBDAO;
import com.lt.hbase.orm.test.entities.CitizenSummary;

import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

public class CitizenSummaryDAO extends BaseHBDAO<CitizenSummary> {
    
    public CitizenSummaryDAO(Configuration conf) throws IOException {
        super(conf);
    }
}
