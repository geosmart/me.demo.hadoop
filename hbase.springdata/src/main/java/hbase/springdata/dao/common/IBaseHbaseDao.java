package hbase.springdata.dao.common;

import org.springframework.data.hadoop.hbase.RowMapper;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * a common dao for hbase operation
 * 
 * @author geosmart
 */
public interface IBaseHbaseDao<T> {

	RowMapper<T> getRowMapper(String cfKey, TypeReference<T> t);

	void initTable(String tbName, String[] cfKeyList);

	void dropTable(String tbName);

	void deleteRow(String tbName, String rowKey);
}
