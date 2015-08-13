package me.demo.hadoop.hbase.springdata.service;

import java.io.IOException;
import java.util.Random;

import javax.annotation.Resource;

import me.demo.hadoop.hbase.springdata.dao.UserDao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

/**
 * User-Service
 * 
 * @author geosmart
 */
@Service
public class UserService implements InitializingBean {

	@Resource(name = "hbaseConfiguration")
	private Configuration hbaseConfiguration;

	@Autowired
	private HbaseTemplate hbaseTemplate;

	private HBaseAdmin admin;

	@Autowired
	private UserDao userDao;

	private final TableName tableName = TableName.valueOf("users");
	private final byte[] tableNameAsBytes = Bytes.toBytes("users");

	@Override
	public void afterPropertiesSet() throws Exception {
		admin = new HBaseAdmin(hbaseConfiguration);
	}

	public void initialize() throws IOException {
		if (admin.tableExists(tableNameAsBytes)) {
			if (!admin.isTableDisabled(tableNameAsBytes)) {
				System.out.printf("Disabling %s\n", tableName);
				admin.disableTable(tableNameAsBytes);
			}
			System.out.printf("Deleting %s\n", tableName);
			admin.deleteTable(tableNameAsBytes);
		}
		HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
		HColumnDescriptor columnDescriptor = new HColumnDescriptor(UserDao.CF_INFO);
		tableDescriptor.addFamily(columnDescriptor);

		admin.createTable(tableDescriptor);

	}

	public void addUsers() {
		Random random = new Random(1);
		for (double i = random.nextDouble(); i < 20; i++) {
			userDao.save("user" + i, "user" + i + "@yahoo.com", "password" + i);
		}
	}

}
