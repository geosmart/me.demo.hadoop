package me.demo.hadoop.hbase.kundera.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User- Entity
 * 
 * @author geosmart
 */
@Entity
@Table(name = "User", schema = "HBaseNew@hbase-pu")
public class User {
	@Id
	@Column(name = "id")
	String id;

	@Column(name = "name")
	String name;
	@Column(name = "email")
	String email;
	@Column(name = "password")
	String password;

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

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + "]";
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

}
