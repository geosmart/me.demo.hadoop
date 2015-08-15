package hbase.springdata.dao.impl;

import hbase.springdata.dao.IUserDao;
import hbase.springdata.dao.common.impl.BaseHbaseDaoImpl;
import hbase.springdata.entity.User;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.TableCallback;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * User-Dao
 * 
 * @author geosmart
 */
public class UserDaoImpl extends BaseHbaseDaoImpl<User> implements IUserDao {
	// TODO 初始化Dao时，默认加载User属性
	User u = new User();
	// TODO　need to extract to BaseHbaseDao,how?
	TypeReference<User> type = new TypeReference<User>() {
	};

	@Override
	public void initTable() {
		initTable(User.TB_NAME, u.getEmberdCfKeys());
	}

	@Override
	public User identify(String rowKey) {
		Scan scan = new Scan();
		Filter f = new RowFilter(CompareOp.EQUAL, new SubstringComparator(rowKey));
		scan.setFilter(f);
		List<User> userList = find(User.TB_NAME, scan, getRowMapper(User.CF_KEY, type));
		return userList.get(0);
	}

	@Override
	public void delete(String rowName, String familyName, String qualifier) {
		if (StringUtils.isNotEmpty(qualifier)) {
			delete(User.TB_NAME, rowName, familyName, qualifier);
		} else {
			delete(User.TB_NAME, rowName, familyName);
		}
	}

	@Override
	public List<User> find(String startRow, long pageSize) {
		Scan scan = new Scan();
		startRow = startRow == null ? "" : startRow;
		scan.setStartRow(Bytes.toBytes(startRow));
		PageFilter filter = new PageFilter(pageSize);
		// TODO order and sort
		// scan.setStartRow(startRow).setMaxResultSize(pageSize);
		scan.setFilter(filter);
		List<User> userList = find(User.TB_NAME, scan, getRowMapper(User.CF_KEY, type));
		return userList;
	}

	@Override
	public User save(final User pojo) {
		User result = execute(User.TB_NAME, new TableCallback<User>() {
			@Override
			public User doInTable(HTableInterface table) throws Throwable {
				String rowKeyColumn = u.getRowKeyColumnName();

				List<Put> puts = generatePuts(pojo, rowKeyColumn, User.CF_KEY_BYTES);

				table.put(puts);
				return pojo;
			}
		});
		return result;
	}

	public TypeReference<User> getType() {
		return type;
	}

	public void setType(TypeReference<User> type) {
		this.type = type;
	}
}
