package me.demo.hadoop.hbase.springdata.dao;

import java.util.List;

import me.demo.hadoop.hbase.springdata.entity.User;
import me.demo.hadoop.hbase.springdata.entity.UserDetail;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

/**
 * User-Dao
 * 
 * @author geosmart
 */
@Repository
public class UserDao {
	@Autowired
	private HbaseTemplate hbaseTemplate;

	private byte[] qId = Bytes.toBytes("id");
	private byte[] qName = Bytes.toBytes("name");
	private byte[] qEmail = Bytes.toBytes("email");
	private byte[] qPassword = Bytes.toBytes("password");

	private byte[] qPhoto = Bytes.toBytes("photo");
	private byte[] qAddress = Bytes.toBytes("address");

	public List<User> list() {
		List<User> userList = hbaseTemplate.find(User.TB_NAME, User.CF_KEY, new RowMapper<User>() {
			@Override
			public User mapRow(Result result, int rowNum) throws Exception {
				String id = Bytes.toString(result.getValue(User.CF_KEY_BYTES, qId));
				String name = Bytes.toString(result.getValue(User.CF_KEY_BYTES, qName));
				String email = Bytes.toString(result.getValue(User.CF_KEY_BYTES, qEmail));
				String psd = Bytes.toString(result.getValue(User.CF_KEY_BYTES, qPassword));

				String photo = Bytes.toString(result.getValue(UserDetail.CF_KEY_BYTES, qPhoto));
				String address = Bytes.toString(result.getValue(UserDetail.CF_KEY_BYTES, qAddress));

				User user = new User(id, name, email, psd);
				UserDetail detail = new UserDetail(address, photo);
				user.setDetail(detail);
				return user;
			}
		});
		return userList;
	}

	public User save(final String userName, final String email, final String password) {
		return hbaseTemplate.execute(User.TB_NAME, new TableCallback<User>() {
			@Override
			public User doInTable(HTableInterface table) throws Throwable {
				User user = new User(userName, email, password);

				Put pUser = new Put(Bytes.toBytes(user.getId()));
				pUser.add(User.CF_KEY_BYTES, qId, Bytes.toBytes(user.getId()));
				pUser.add(User.CF_KEY_BYTES, qName, Bytes.toBytes(user.getName()));
				pUser.add(User.CF_KEY_BYTES, qEmail, Bytes.toBytes(user.getEmail()));
				pUser.add(User.CF_KEY_BYTES, qPassword, Bytes.toBytes(user.getPassword()));

				// nested row
				Put pDetail = new Put(Bytes.toBytes(user.getId()));
				pDetail.add(UserDetail.CF_KEY_BYTES, qPhoto, Bytes.toBytes("test-photo"));
				pDetail.add(UserDetail.CF_KEY_BYTES, qAddress, Bytes.toBytes("test-address"));

				table.put(pUser);
				table.put(pDetail);
				return user;
			}
		});
	}
}
