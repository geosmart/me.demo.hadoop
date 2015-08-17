
结合spring data for hadoop和jackson objectMapper实现的Hbase ORM
---

# SpringData for  Hbase Config
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:hdp="http://www.springframework.org/schema/hadoop" xmlns:p="http://www.springframework.org/schema/p"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
	http://www.springframework.org/schema/hadoop http://www.springframework.org/schema/hadoop/spring-hadoop.xsd">
	<description>hbase config</description>
 
	<!-- hBaseAdmin config -->
	<bean id="hBaseAdmin" class="org.apache.hadoop.hbase.client.HBaseAdmin">
		<constructor-arg>
			<ref bean="hbaseConfig" />
		</constructor-arg>
	</bean>
	
	<!-- hbase config -->
	<hdp:hbase-configuration id="hbaseConfig"
		configuration-ref="hadoopConfig" zk-quorum="192.168.1.100" zk-port="2181">
	</hdp:hbase-configuration>

	<!--hadoop: hdfs NameNode config -->
	<hdp:configuration id="hadoopConfig">
		fs.defaultFS=hdfs://192.168.1.100:8020
	</hdp:configuration> 
	 
	<bean id="userDao" class="hbase.springdata.dao.UserDao">
		<constructor-arg ref="hbaseConfig"></constructor-arg>
	</bean>
</beans>

```
 
# ORM映射

``` java
@HBTable("User")
public class User implements HBRecord {
	@HBRowKey
	private String id;
	@HBColumn(family = "user", column = "name")
	private String name;
	@HBColumn(family = "user", column = "email")
	private String email;
	@HBColumn(family = "user", column = "password")
	private String password;

	@HBColumn(family = "detail", column = "address")
	private String address;
	@HBColumn(family = "detail", column = "photo")
	private String photo;
}
```



# BaseDAO 注入HbaseConfiguration
 
``` java 
public class UserDao extends BaseHBDAO<User> {

	public UserDao(Configuration config) throws IOException {
		super(config);
	}
} 
```
# Hbase CRUD测试用例 
``` java 
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
```