package com.lt.hbase.orm.test.daos;


import com.lt.hbase.orm.BaseHBDAO;
import com.lt.hbase.orm.test.entities.Citizen;

import org.apache.hadoop.conf.Configuration;

import java.io.IOException;

public class CitizenDAO extends BaseHBDAO<Citizen> {
    
    public CitizenDAO(Configuration conf) throws IOException {
        super(conf);
    }
}
