package me.demo.hadoop.hbase.kundera.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import me.demo.hadoop.hbase.kundera.entity.User;

/**
 * User-Dao
 * 
 * @author geosmart
 */
public class UserDaoImpl implements IUserDao {
	@PersistenceContext(unitName = "hbase-pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Override
	public User identify(String id) {
		return em.find(User.class, id);
	}

	@Override
	public void save(User user) {
		em.persist(user);
		em.clear();
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public void closeEntityManager() {
		if (em != null) {
			em.close();
		}
	}

	@Override
	public void shutDown() {
		if (em != null) {
			em.close();
		}
	}

	@Override
	public void clearEntityManager() {
		if (em != null) {
			em.clear();
		}
	}
}
