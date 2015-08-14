package hbase.springdata.dao.common.impl;

import hbase.springdata.dao.common.IBaseHbaseDao;
import hbase.springdata.util.JsonUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * BaseHbaseDaoImpl
 * 
 * @author geosmart
 */
public class BaseHbaseDaoImpl<T> extends HbaseTemplate implements IBaseHbaseDao<T> {
	HBaseAdmin hBaseAdmin;

	/**
	 * 创建一个Class泛型对象
	 */
	private Class<T> clz;

	/**
	 * 通过一个Class泛型对象，获取泛型的Class
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getClz() {
		if (clz == null) {
			// 获取泛型的Class对象
			clz = ((Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass()))
					.getActualTypeArguments()[0]));
		}
		return clz;
	}

	/**
	 * TODO get tbName by reflection
	 * 
	 * @param tbName
	 * @param cfKey
	 */
	@Override
	public void initTable(String tbName, String[] cfKeyList) {
		try {
			TableName tableName = TableName.valueOf(tbName);
			HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
			for (String cfKey : cfKeyList) {
				HColumnDescriptor columnDescriptor = new HColumnDescriptor(cfKey);
				tableDescriptor.addFamily(columnDescriptor);
			}
			hBaseAdmin.createTable(tableDescriptor);
		} catch (IOException e) {
		}
	}

	@Override
	public void dropTable(String tbName) {
		try {
			byte[] tableNameBytes = Bytes.toBytes(tbName);
			if (hBaseAdmin.tableExists(tableNameBytes)) {
				if (!hBaseAdmin.isTableDisabled(tableNameBytes)) {
					hBaseAdmin.disableTable(tableNameBytes);
				}
				hBaseAdmin.deleteTable(tableNameBytes);
			}
		} catch (IOException e) {
		}
	}

	@Override
	public void deleteRow(String tbName, String rowKey) {
		try {
			HTable table = new HTable(getConfiguration(), tbName);
			Delete del = new Delete(Bytes.toBytes(rowKey));
			table.delete(del);
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ORM Map：Map Columns to POJO
	 * 
	 * @param cfKey default ColumnFamilyKey
	 * @param t pojo type
	 * @return RowMapper
	 */
	@Override
	public RowMapper<T> getRowMapper(final String cfKey, final TypeReference<T> t) {
		RowMapper<T> rowMapper = new RowMapper<T>() {
			@SuppressWarnings({ "unchecked" })
			public T mapRow(Result result, int rowNum) throws Exception {
				Map<String, Object> map = new HashMap<String, Object>();
				List<Cell> ceList = result.listCells();
				if (ceList != null && ceList.size() > 0) {
					for (Cell cell : ceList) {
						// String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(),
						// cell.getRowLength());
						String family = Bytes.toString(cell.getFamilyArray(),
								cell.getFamilyOffset(), cell.getFamilyLength());
						String quali = Bytes.toString(cell.getQualifierArray(),
								cell.getQualifierOffset(), cell.getQualifierLength());
						String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
								cell.getValueLength());

						if (family.equals(cfKey)) {
							// pojo prop
							map.put(quali, value);
						} else {
							// nested pojo prop
							Object pojoMapObj = map.get(family);
							HashMap<String, Object> pojoMap = new HashMap<String, Object>();
							if (pojoMapObj != null) {
								pojoMap = (HashMap<String, Object>) pojoMapObj;
								pojoMap.put(quali, value);
								map.put(family, pojoMap);
							} else {
								pojoMap.put(quali, value);
								map.put(family, pojoMap);
							}
						}
					}
				}
				T pojo = JsonUtil.ConvertMap2POJO(map, t);
				return pojo;
			}
		};
		return rowMapper;
	}

	public HBaseAdmin gethBaseAdmin() {
		return hBaseAdmin;
	}

	public void sethBaseAdmin(HBaseAdmin hBaseAdmin) {
		this.hBaseAdmin = hBaseAdmin;
	}

}
