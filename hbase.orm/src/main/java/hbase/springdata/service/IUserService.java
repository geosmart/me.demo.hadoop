package hbase.springdata.service;

import hbase.springdata.entity.User;

/**
 * UserService interface
 * 
 * @author geosmart
 */
public interface IUserService {

	User identify(String rowKey);

	void addUsers();

}
