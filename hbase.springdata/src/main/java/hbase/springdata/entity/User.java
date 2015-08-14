package hbase.springdata.entity;

import java.util.UUID;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * User- Entity
 * 
 * @author geosmart
 */
public class User {
	public static final String TB_NAME = "User";
	public static byte[] TB_NAME_BYTES = Bytes.toBytes(TB_NAME);

	public static String CF_KEY = "user";
	public static byte[] CF_KEY_BYTES = Bytes.toBytes(CF_KEY);

	// cfkeys：without default cfkey
	public static String[] CF_KEYS = new String[] { CF_KEY, "detail" };

	private String id;
	private String name;
	private String email;
	private String password;
	private UserDetail detail;

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

	public User() {
		// TODO Auto-generated constructor stub
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

	public static String getCF_KEY() {
		return CF_KEY;
	}

	public static void setCF_KEY(String cF_KEY) {
		CF_KEY = cF_KEY;
	}

	public static byte[] getCF_KEY_BYTES() {
		return CF_KEY_BYTES;
	}

	public static void setCF_KEY_BYTES(byte[] cF_KEY_BYTES) {
		CF_KEY_BYTES = cF_KEY_BYTES;
	}

	public UserDetail getDetail() {
		return detail;
	}

	public void setDetail(UserDetail detail) {
		this.detail = detail;
	}

	public static String getTbName() {
		return TB_NAME;
	}

}
