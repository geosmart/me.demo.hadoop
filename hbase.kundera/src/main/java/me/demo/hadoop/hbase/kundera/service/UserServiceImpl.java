package me.demo.hadoop.hbase.kundera.service;

import java.util.Random;

import me.demo.hadoop.hbase.kundera.dao.UserDaoImpl;
import me.demo.hadoop.hbase.kundera.entity.User;

/**
 * User-Service
 * 
 * @author geosmart
 */
public class UserServiceImpl implements IUserService {
	private UserDaoImpl userDao;

	@Override
	public void addUsers() {
		Random random = new Random(100);
		for (int i = random.nextInt(); i < 10; i++) {
			User user = new User("user" + i, "user" + i, "user" + i + "@gmail.com",
					String.valueOf(i));
			userDao.save(user);
		}
	}

	public UserDaoImpl getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

}
