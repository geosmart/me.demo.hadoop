package hbase.springdata.dao;

import hbase.springdata.dao.common.IBaseHbaseDao;
import hbase.springdata.entity.User;

import java.util.List;

/**
 * User-Dao
 * 
 * @author geosmart
 */
public interface IUserDao extends IBaseHbaseDao<User> {

	void initTable();

	/**
	 * query param
	 * 
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	List<User> find(String startRow, long pageSize);

	/**
	 * identify a row
	 * 
	 * @param rowKey
	 * @return
	 */
	User identify(String rowKey);

	/**
	 * insert
	 * 
	 * @param user
	 * @return
	 */
	User save(User user);

}
