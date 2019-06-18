package com.simulation.dao.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.simulation.common.page.Pagination;


/**
 * Spring JDBC 基类
 */
public abstract class BaseJdbcDao 
{
	@Resource(name = "jdbcTemplate")  
	private JdbcTemplate jdbcTemplate; 
 
	@Resource(name = "dbType")
    private String dbType;
	
	

	/**
	 * 分页显示类
	 * @param pageNo 第几页
	 * @param pageSize 每页条数
	 * @param sql 
	 * @param paraMap 传入参数
	 * @return
	 * @throws Exception
	 */
	public Pagination getPage(int pageNo, int pageSize, String sql, Map paraMap)
			throws Exception {
		
		int offset = (pageNo - 1) * pageSize;
		String pageSql = getDialect(dbType).getLimitString(sql, offset, pageSize);
		String totalSql = getDialect(dbType).getCountSql(sql);
		List list = queryForList(pageSql, paraMap);
		int totalCount = queryForInt(totalSql, paraMap);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		p.setList(list);
		return p;
	}

	/**
	 * 根据sql语句获取列表数据
	 * @param sql
	 * @param parameter
	 * @param rowMap 查询结果定义
	 * @return
	 */
	public List queryForList(String sql, Map parameter, RowMapper rowMap) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				this.jdbcTemplate.getDataSource());
		return template.query(sql, parameter, rowMap);
	}

	/**
	 * 根据sql语句获取列表数据
	 * @param sql
	 * @param parameter 传入参数
	 * @return
	 */
	public List queryForList(String sql, Map parameter) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				this.jdbcTemplate.getDataSource());
		return template.queryForList(sql, parameter);
	}
	
	/**
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 */
	public int queryForInt(String sql, Map parameter) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				this.jdbcTemplate.getDataSource());
		return template.queryForInt(sql, parameter);
	}
    
	/**
     * 更新、删除等执行sql
     * @param sql
     * @return
     */
	public int update(String sql) {
		return this.jdbcTemplate.update(sql);
	}
	
	/**
	 * 根据数据库类型加载不同的dialect处理
	 * @param dbType
	 * @return
	 * @throws Exception
	 */
	private Dialect getDialect(String dbType) throws Exception {
		Dialect dialect=null;
		if (dbType.equals("oracle")) {
			dialect = new OracleDialect();
		}
//		if (dbType.equals("mssql")) {
//			dialect = new SQLServer2005Dialect();
//		} 
//		if (dbType.equals("db2")) {
//			dialect = new DB2Dialect();
//		}
//		if (dbType.equals("mysql")) {
//			dialect = new MySQLDialect();
//		} 
//		if (dbType.equals("h2")) {
//			dialect = new H2Dialect();
//		} 
		
		return dialect;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

}
