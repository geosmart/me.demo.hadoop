/*
 * Copyright 2011-2012 the original author or authors. Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package me.demo.hadoop.hbase.springdata.test;

import hbase.springdata.dao.IUserDao;
import hbase.springdata.entity.User;
import hbase.springdata.service.IUserService;
import hbase.springdata.util.JsonUtil;

import java.io.IOException;
import java.util.List;

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
	@Autowired
	IUserService userService;
	@Autowired
	IUserDao userDao;

	@Test
	public void initializeTableTest() {
		// userService.initialize();
	}

	@Test
	public void saveTest() {
		// userService.addUsers();
	}

	@Test
	public void deleteRowTest() {
		userDao.deleteRow(User.TB_NAME, "0caf7a87-8208-4809-a402-664af8653921");
	}

	@Test
	public void identifyTest() {
		User user = userService.identify("0b395e30-45f7-4252-9945-7094a485beb9");
		System.err.println(JsonUtil.convertObject2String(user));
	}

	@Test
	public void queryTest() throws IOException {
		List<User> userList = userDao.find("0b395e30-45f7-4252-9945-7094a485beb9", 10);
		System.err.println(JsonUtil.convertObject2String(userList));
	}
}
