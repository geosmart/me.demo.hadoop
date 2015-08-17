package hbase.springdata.entity;

import java.util.UUID;

import com.lt.hbase.orm.HBColumn;
import com.lt.hbase.orm.HBRecord;
import com.lt.hbase.orm.HBRowKey;
import com.lt.hbase.orm.HBTable;

/**
 * User- Entity
 * 
 * @author geosmart
 */
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

	@Override
	public String composeRowKey() {
		return id;
	}

	@Override
	public void parseRowKey(String rowKey) {
		this.id = rowKey;
	}

	public User() {
		super();
	}

	public User(String id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(String name, String email, String password) {
		super();
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
