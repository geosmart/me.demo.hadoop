/*
 * Copyright 2011-2012 the original author or authors. Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package me.demo.hadoop.hbase.springdata.test;

import java.io.IOException;
import java.util.List;

import me.demo.hadoop.hbase.springdata.dao.UserDao;
import me.demo.hadoop.hbase.springdata.entity.User;
import me.demo.hadoop.hbase.springdata.service.UserService;

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
	UserService userService;
	@Autowired
	UserDao userDao;

	@Test
	public void hbaseTest() throws IOException {
		// userService.initialize();
		userService.addUsers();
		List<User> users = userDao.findAll();
		log.debug("Number of users ={}", users.size());
		System.out.println(users);
	}
}
