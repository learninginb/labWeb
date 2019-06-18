package com.simulation.core.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simulation.common.page.Pagination;
import com.simulation.core.util.AppUtil;
import com.simulation.dao.base.Dialect;
import com.simulation.dao.base.MySQLDialect;
import com.simulation.dao.base.OracleDialect;

/**
 * 分页拦截处理类
 */
@Intercepts({@Signature(type =StatementHandler.class, method = "prepare", args ={Connection.class})})  
public class OffsetLimitInterceptor implements Interceptor {
	private static final Logger log = LoggerFactory.getLogger(OffsetLimitInterceptor.class);
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;
	Dialect dialect;
	String dbType = "oracle";
	private Properties properties;
	private String defaultPageSqlId=".*Page$";

	public OffsetLimitInterceptor() {
		this.properties = null;
	}

	private void getDialect() throws Exception {
		
//		if (this.dialect != null)
//			return;
//		Properties prop = (Properties) AppUtil.getBean("configproperties");
//		String dbType = prop.getProperty("jdbc.dbType");
//		String dialectClass = this.properties.getProperty(dbType);
//		try {
//			this.dialect = ((Dialect) Class.forName(dialectClass).newInstance());
//		} catch (Exception e) {
//			throw new RuntimeException("cannot create dialect instance by dialectClass:"+ dialectClass, e);
//		}
		dbType = (String)AppUtil.getBean("dbType");
		if(dbType.equals("oracle")){
			this.dialect= new OracleDialect();
		}else if(dbType.equals("mysql")){
			this.dialect= new MySQLDialect();
		}
		
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Object intercept(Invocation invocation) throws Throwable {
		
		getDialect();
		
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,
        		MetaObject.DEFAULT_OBJECT_FACTORY, MetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
        // 可以分离出最原始的的目标类)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, MetaObject.DEFAULT_OBJECT_FACTORY, 
            		MetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, MetaObject.DEFAULT_OBJECT_FACTORY, 
            		MetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
       
        String pageSqlId = this.properties.getProperty("pageSqlId");
        if (null == pageSqlId || "".equals(pageSqlId)) {
            log.warn("Property pageSqlId is not setted,use default '.*Page$' ");
            pageSqlId = defaultPageSqlId;
        }
        MappedStatement mappedStatement = (MappedStatement) 
        metaStatementHandler.getValue("delegate.mappedStatement");
        // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的
        //  MappedStatement的sql
        if (mappedStatement.getId().matches(pageSqlId)) {
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                throw new NullPointerException("parameterObject is null!");
            } else {
                // 分页参数作为参数对象parameterObject的一个属性
            	Pagination page = (Pagination) metaStatementHandler.getValue("delegate.boundSql.parameterObject.page");
                String sql = boundSql.getSql();
                // 重写sql
                String pageSql = buildPageSql(sql, page);
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                metaStatementHandler.setValue("delegate.rowBounds.offset", 
                RowBounds.NO_ROW_OFFSET);
                metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                Connection connection = (Connection) invocation.getArgs()[0];
                // 重设分页参数里的总页数等
                setPageParameter(sql, connection, mappedStatement, boundSql, page);
            }
        }
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }



private String buildPageSql(String sql, Pagination page) {
    if (page != null) {
        StringBuilder pageSql = new StringBuilder();
        //pageSql = buildPageSqlForOracle(sql, page);
        if ("mysql".equals(dbType)) {
            pageSql = buildPageSqlForMysql(sql, page);
        } else if ("oracle".equals(dbType)) {
            pageSql = buildPageSqlForOracle(sql, page);
        } else {
            return sql;
        }
        return pageSql.toString();
    } else {
        return sql;
    }
}

public StringBuilder buildPageSqlForMysql(String sql, Pagination page) {  
    StringBuilder pageSql = new StringBuilder(100);  
    String beginrow = String.valueOf((page.getPageNo() - 1) * page.getPageSize());  
    pageSql.append(sql);  
    pageSql.append(" limit " + beginrow + "," + page.getPageSize());  
    return pageSql;  
}  

public StringBuilder buildPageSqlForOracle(String sql, Pagination page) {
    StringBuilder pageSql = new StringBuilder(100);
    String beginrow = page.getFirstResult()+"";   
    String endrow = String.valueOf(page.getPageNo() * page.getPageSize());
    pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
    pageSql.append(sql);
    pageSql.append(" ) temp where rownum <= ").append(endrow);
    pageSql.append(") where row_id > ").append(beginrow);
    return pageSql;
}





void processIntercept(Object[] queryArgs) throws Exception {
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		BoundSql boundSql = ms.getBoundSql(parameter);
		if ((this.dialect.supportsLimit()) && ((offset != 0) || (limit != 2147483647))) {
			String sql = boundSql.getSql().trim();
			if (this.dialect.supportsLimitOffset()) {
				sql = this.dialect.getLimitString(sql, offset, limit);
				offset = 0;
			} else {
				sql = this.dialect.getLimitString(sql, 0, limit);
			}
			limit = 2147483647;
			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql,	boundSql.getParameterMappings(),boundSql.getParameterObject());
			MappedStatement newMs = copyFromMappedStatement(ms,	new BoundSqlSqlSource(newBoundSql));
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
		}
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms,
			SqlSource newSqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(
				ms.getConfiguration(), ms.getId(), newSqlSource,
				ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		/* 下面这句ms.getKeyProperty()报错，原因是新包修改了MappedStatement类中keyProperty的数据类型， 
        `* 将String改为String[]，相应的get方法由getKeyProperty()改为getKeyProperties() 
         * 因此，这里我是如下修改的，原因是在分页查询时，我跟踪此处的keyProperties为null，而其他查询并未使用此处 
         * */ 
		String[] s = ms.getKeyProperties(); 
        if(s == null){ 
        	builder.keyProperty(null); 
        }else{ 
        	builder.keyProperty(s[0]); 
        } 
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		return builder.build();
	}

	public Object plugin(Object target) {
		
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的  
	    // 次数  
	    if (target instanceof StatementHandler) {  
	        return Plugin.wrap(target, this);  
	    } else {  
	        return target;  
	    }  
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return this.boundSql;
		}
	}
	
    /**
     * 从数据库里查询总的记录数并计算总页数，回写进分页参数<code>PageParameter</code>,这样调用 
     * 者就可用通过 分页参数<code>PageParameter</code>获得相关信息。
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
            BoundSql boundSql, Pagination page) {
        // 记录总记录数
        String countSql = "select count(0) from (" + sql + ") total";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalCount(totalCount);           
        } catch (SQLException e) {
            log.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                log.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 对SQL参数(?)设值
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

	
	
}
