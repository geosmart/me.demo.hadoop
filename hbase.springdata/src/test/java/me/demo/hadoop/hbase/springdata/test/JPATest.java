/*
 * Copyright 2011-2012 the original author or authors. Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package me.demo.hadoop.hbase.springdata.test;

import hbase.springdata.entity.User;
import hbase.springdata.util.JsonUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPATest {
	private static Logger log = LoggerFactory.getLogger(JPATest.class);

	@Test
	public void initializeTableTest() {
		new User();
		System.out.println(User.TB_NAME);

		System.out.println(JsonUtil.convertObject2String(new User(), true));
		System.out.println(JsonUtil.convertEntityObj2Map(new User("", "", "", ""), true));

		new User("", "", "", "");
		System.out.println(User.TB_NAME);
		System.out.println(User.TB_NAME_BYTES);
		System.out.println(User.CF_KEY);
		System.out.println(User.CF_KEY_BYTES);
		System.out.println(User.CF_KEYS);
	}
}
