package me.demo.hadoop.hbase.springdata.entity;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * UserDetail Entity
 * 
 * @author geosmart
 */
public class UserDetail {
	public static String CF_KEY = "detail";
	public static byte[] CF_KEY_BYTES = Bytes.toBytes(CF_KEY);

	private String address;
	private String photo;

	public UserDetail(String address, String photo) {
		super();
		this.address = address;
		this.photo = photo;
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
