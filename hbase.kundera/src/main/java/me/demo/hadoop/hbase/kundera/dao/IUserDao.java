package me.demo.hadoop.hbase.kundera.dao;

import javax.persistence.EntityManager;

import me.demo.hadoop.hbase.kundera.entity.User;

/**
 * User-Dao
 * 
 * @author geosmart
 */
public interface IUserDao {

	User identify(String id);

	void save(User user);

	EntityManager getEntityManager();

	void closeEntityManager();

	void shutDown();

	void clearEntityManager();

}
