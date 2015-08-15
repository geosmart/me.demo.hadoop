package hbase.springdata.service.impl;

import hbase.springdata.dao.IUserDao;
import hbase.springdata.entity.User;
import hbase.springdata.entity.UserDetail;
import hbase.springdata.service.IUserService;

/**
 * UserServiceImpl
 * 
 * @author geosmart
 */
public class UserServiceImpl implements IUserService {
	IUserDao userDao;

	@Override
	public User identify(String rowKey) {
		return userDao.identify(rowKey);
	}

	@Override
	public void addUsers() {
		for (int i = 0; i < 10; i++) {
			User user = new User("user-" + i, "user-" + i + "@yahoo.com", "password-" + i);
			UserDetail detail = new UserDetail("address-" + i, "photo-" + i);
			user.setDetail(detail);
			userDao.save(user);
		}
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
