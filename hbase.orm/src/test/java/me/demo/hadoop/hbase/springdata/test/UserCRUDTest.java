package me.demo.hadoop.hbase.springdata.test;

import hbase.springdata.dao.UserDao;
import hbase.springdata.entity.User;
import hbase.springdata.util.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserCRUDTest {
	private static Logger log = LoggerFactory.getLogger(UserCRUDTest.class);

	static List<User> TestUsers;

	@Autowired
	UserDao userDao;

	@Before
	public void setup() throws Exception {
		// create table
		userDao.createTable();
		initUsers();
	}

	public void initUsers() {
		TestUsers = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			User user = new User("user-" + i, "user-" + i + "@yahoo.com", "password-" + i);
			user.setAddress("address-" + i);
			user.setPhoto("photo-" + i);
			TestUsers.add(user);
		}
	}

	@Test
	public void UserDaoTest() throws IOException, NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		for (User pojo : TestUsers) {
			// save row
			userDao.persist(pojo);
		}
		String testRowKey = TestUsers.get(0).getId();
		// get row
		User user = userDao.get(testRowKey);

		List<User> userList = userDao.get(null, null);
		for (User pojo : userList) {
			// delete row
			userDao.delete(pojo.getId());
		}
	}

	public void ConsoleObject(Object object) {
		System.err.println(JsonUtil.convertObject2String(object));
	}

}
