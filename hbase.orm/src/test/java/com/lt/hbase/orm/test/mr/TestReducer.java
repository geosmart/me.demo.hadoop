package com.lt.hbase.orm.test.mr;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import com.lt.hbase.orm.HBObjectMapper;
import com.lt.hbase.orm.Util;
import com.lt.hbase.orm.test.entities.CitizenSummary;
import com.lt.hbase.orm.test.mr.lib.TableReduceDriver;

public class TestReducer {

	HBObjectMapper hbObjectMapper = new HBObjectMapper();
	TableReduceDriver<ImmutableBytesWritable, IntWritable, ImmutableBytesWritable> reducerDriver;

	@Before
	public void setUp() throws Exception {
		// BUG
		// Reducer<ImmutableBytesWritable, IntWritable, ImmutableBytesWritable, Writable> reducer =
		// new CitizenReducer();
		// reducerDriver = TableReduceDriver.newTableReduceDriver(reducer);
		// reducerDriver = TableReduceDriver.newTableReduceDriver(new CitizenReducer());
	}

	@Test
	public void test() throws Exception {
		Pair<ImmutableBytesWritable, Writable> reducerResult = reducerDriver
				.withInput(Util.strToIbw("key"),
						Arrays.asList(new IntWritable(1), new IntWritable(5))).run().get(0);
		CitizenSummary citizenSummary = hbObjectMapper.readValue(reducerResult.getFirst(),
				(Put) reducerResult.getSecond(), CitizenSummary.class);
		assertEquals(citizenSummary.getAverageAge(), (Float) 3.0f);
	}
}
