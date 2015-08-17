package com.lt.hbase.orm.test.mr;

import com.lt.hbase.orm.HBObjectMapper;
import com.lt.hbase.orm.Util;
import com.lt.hbase.orm.test.TestObjects;
import com.lt.hbase.orm.test.TestUtil;
import com.lt.hbase.orm.test.entities.Citizen;
import com.lt.hbase.orm.test.mr.lib.TableMapDriver;
import com.lt.hbase.orm.test.mr.samples.CitizenMapper;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMapper {

    HBObjectMapper hbObjectMapper = new HBObjectMapper();
    TableMapDriver<ImmutableBytesWritable, IntWritable> mapDriver;

    @Before
    public void setUp() throws Exception {
        mapDriver = TableMapDriver.newTableMapDriver(new CitizenMapper());
    }

    @Test
    public void testSingle() throws Exception {
        Citizen citizen = TestObjects.validObjs.get(0);
        mapDriver
                .withInput(
                        hbObjectMapper.getRowKey(citizen),
                        hbObjectMapper.writeValueAsResult(citizen)
                )
                .withOutput(Util.strToIbw("key"), new IntWritable(citizen.getAge()))
                .runTest();
    }


    @Test
    public void testMultiple() throws Exception {
        List<Pair<ImmutableBytesWritable, Result>> citizens = TestUtil.writeValueAsRowKeyResultPair(TestObjects.validObjs);
        List<Pair<ImmutableBytesWritable, IntWritable>> mapResults = mapDriver.withAll(citizens).run();
        for (Pair<ImmutableBytesWritable, IntWritable> mapResult : mapResults) {
            assertEquals(Util.ibwToStr(mapResult.getFirst()), "key");
        }
    }
}
