package hbase.springdata.entity;

import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Id;

/**
 * User- Entity
 * 
 * @author geosmart
 */
public class User extends CommonEntity<User> {
	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	@Embedded
	private UserDetail detail;

	public User() {
		super();
		detail = new UserDetail();
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

	public UserDetail getDetail() {
		return detail;
	}

	public void setDetail(UserDetail detail) {
		this.detail = detail;
	}

}
