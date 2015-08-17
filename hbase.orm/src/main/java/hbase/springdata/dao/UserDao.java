package hbase.springdata.dao;

import hbase.springdata.entity.User;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import com.lt.hbase.orm.BaseHBDAO;

/**
 * User-Dao
 * 
 * @author geosmart
 */
public class UserDao extends BaseHBDAO<User> {

	public UserDao(Configuration config) throws IOException {
		super(config);
	}
}
