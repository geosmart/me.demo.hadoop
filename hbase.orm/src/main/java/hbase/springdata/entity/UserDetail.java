package hbase.springdata.entity;

import com.lt.hbase.orm.HBColumn;

/**
 * UserDetail Entity
 * 
 * @author geosmart
 */
public class UserDetail {

	@HBColumn(family = "detail", column = "address")
	private String address;
	@HBColumn(family = "detail", column = "photo")
	private String photo;

	public UserDetail() {
		super();
	}

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
