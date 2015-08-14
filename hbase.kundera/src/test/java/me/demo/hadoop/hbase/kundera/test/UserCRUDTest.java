package me.demo.hadoop.hbase.kundera.test;

import java.io.IOException;

import me.demo.hadoop.hbase.kundera.service.IUserService;

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

	/*
	 * XXX throw error org.springframework.beans.factory.BeanCreationException: Error creating bean
	 * with name 'emf-p' defined in class path resource [applicationContext.xml]: Invocation of init
	 * method failed; nested exception is java.lang.IllegalArgumentException: Cannot find class
	 * [com.impetus.kundera.KunderaPersistence]
	 */
	@Test
	public void hbaseTest() throws IOException {
		// userService.initialize();
		userService.addUsers();
	}
}
