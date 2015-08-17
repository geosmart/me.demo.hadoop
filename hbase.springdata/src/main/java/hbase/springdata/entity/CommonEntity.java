package hbase.springdata.entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.util.Bytes;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Common Row Entity
 * 
 * @author geosmart
 */
public class CommonEntity<T> {
	private Class<T> pojo;

	public static String TB_NAME;

	public static byte[] TB_NAME_BYTES;

	public static String CF_KEY;

	public static byte[] CF_KEY_BYTES;

	public static List<String> CF_KEYS;

	/**
	 * constructor to load fields default value
	 */
	public CommonEntity() {
		super();
		getPojo();
		TB_NAME = pojo.getSimpleName();
		TB_NAME_BYTES = Bytes.toBytes(TB_NAME);

		CF_KEY = TB_NAME;
		CF_KEY_BYTES = Bytes.toBytes(CF_KEY);

		CF_KEYS = getEmberdCfKeys();
	}

	@JsonIgnore
	@SuppressWarnings("unchecked")
	public Class<T> getPojo() {
		if (pojo == null) {
			pojo = ((Class<T>) (((java.lang.reflect.ParameterizedType) (this.getClass()
					.getGenericSuperclass())).getActualTypeArguments()[0]));
		}

		return pojo;
	}

	/**
	 * get rowkey columnName(like primary key )
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getRowKeyColumnName() {
		String name = null;

		for (Field f : pojo.getDeclaredFields()) {

			javax.persistence.Id id = null;
			javax.persistence.Column column = null;

			Annotation[] as = f.getAnnotations();
			for (Annotation a : as) {
				if (a.annotationType() == javax.persistence.Id.class) {
					id = (javax.persistence.Id) a;
				} else if (a.annotationType() == javax.persistence.Column.class) {
					column = (javax.persistence.Column) a;
				}
			}
			if (id != null) {
				if (column != null) {
					name = column.name();
				} else {
					name = f.getName();
				}
				break;
			}
		}
		return name;
	}

	/**
	 * get embered ColumnFamilys
	 * 
	 * @return
	 */
	@JsonIgnore
	public List<String> getEmberdCfKeys() {
		List<String> cfKeys = new ArrayList<String>();
		cfKeys.add(CF_KEY);

		for (Field f : pojo.getDeclaredFields()) {

			javax.persistence.Embedded cfKey = null;
			javax.persistence.Column column = null;

			Annotation[] as = f.getAnnotations();
			for (Annotation a : as) {
				if (a.annotationType() == javax.persistence.Embedded.class) {
					cfKey = (javax.persistence.Embedded) a;
				} else if (a.annotationType() == javax.persistence.Column.class) {
					column = (javax.persistence.Column) a;
				}
			}
			if (cfKey != null) {
				if (column != null) {
					cfKeys.add(column.name());
				} else {
					cfKeys.add(f.getName());
				}
				break;
			}
		}
		return cfKeys;
	}

}
