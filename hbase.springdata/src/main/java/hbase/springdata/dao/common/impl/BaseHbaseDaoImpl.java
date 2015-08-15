package hbase.springdata.dao.common.impl;

import hbase.springdata.dao.common.IBaseHbaseDao;
import hbase.springdata.util.JsonUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
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
	Class<T> clz;

	/**
	 * 通过一个Class泛型对象，获取泛型的Class
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getGenericClass() {
		try {
			if (clz == null) {

				Type mySuperclass = getClass().getGenericSuperclass();

				Type tType = ((java.lang.reflect.ParameterizedType) mySuperclass)
						.getActualTypeArguments()[0];
				String className = tType.toString().split(" ")[1];
				clz = (Class<T>) Class.forName(className);
			}
		} catch (Exception e) {
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
	public void initTable(String tbName, List<String> cfKeyList) {
		try {
			// clz.newInstance();

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

	@SuppressWarnings("unchecked")
	public List<Put> generatePuts(Object pojo, String rowKeyColumn, byte[] columFamilyBytes) {
		Map<String, Object> pojoMap = JsonUtil.convertEntityObj2Map(pojo, true);
		String rowKeyValue = pojoMap.get(rowKeyColumn).toString();
		List<Put> puts = new ArrayList<Put>();
		Put pDefault = new Put(Bytes.toBytes(rowKeyValue));
		Iterator<String> itUser = pojoMap.keySet().iterator();
		while (itUser.hasNext()) {
			String key = itUser.next();
			// 主键=rowKey避免重复写入，取出时将rowkey赋值给主键即可
			if (!rowKeyColumn.equals(key)) {
				Object value = pojoMap.get(key);
				if (!(value instanceof Map)) {
					// 获取Key类型，新建实例，获取属性
					pDefault.add(columFamilyBytes, Bytes.toBytes(key),
							Bytes.toBytes(value.toString()));
				} else {
					Put pEmber = new Put(Bytes.toBytes(rowKeyValue));
					Map<String, Object> emberdPojo = ((Map<String, Object>) value);
					Iterator<String> emberdCfKey = emberdPojo.keySet().iterator();

					while (emberdCfKey.hasNext()) {
						String emberKey = emberdCfKey.next();
						Object emberValue = emberdPojo.get(emberKey);

						pEmber.add(Bytes.toBytes(key), Bytes.toBytes(emberKey),
								Bytes.toBytes(emberValue.toString()));
					}
					puts.add(pEmber);
				}
			}
		}
		puts.add(pDefault);
		return puts;
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

						System.out.println(cfKey);
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
