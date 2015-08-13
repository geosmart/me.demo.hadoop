package me.demo.hadoop.hbase.springdata.service;

import java.io.IOException;

import javax.annotation.Resource;

import me.demo.hadoop.hbase.springdata.dao.UserDao;
import me.demo.hadoop.hbase.springdata.entity.User;
import me.demo.hadoop.hbase.springdata.entity.UserDetail;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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

	private HBaseAdmin admin;

	@Autowired
	private UserDao userDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		admin = new HBaseAdmin(hbaseConfiguration);
	}

	public void initialize() throws IOException {
		TableName tableName = TableName.valueOf(User.TB_NAME);
		byte[] tableNameAsBytes = Bytes.toBytes(User.TB_NAME);
		if (admin.tableExists(tableNameAsBytes)) {
			if (!admin.isTableDisabled(tableNameAsBytes)) {
				admin.disableTable(tableNameAsBytes);
			}
			admin.deleteTable(tableNameAsBytes);
		}
		HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
		HColumnDescriptor columnDescriptor = new HColumnDescriptor(User.CF_KEY);
		tableDescriptor.addFamily(columnDescriptor);

		HColumnDescriptor columnDescriptor2 = new HColumnDescriptor(UserDetail.CF_KEY);
		tableDescriptor.addFamily(columnDescriptor2);

		admin.createTable(tableDescriptor);
	}

	public void addUsers() {
		for (int i = 0; i < 100; i++) {
			userDao.save("user-" + i, "user-" + i + "@yahoo.com", "password-" + i);
		}
	}

}
