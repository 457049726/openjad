package com.openjad.orm.mybatis.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.openjad.common.util.StringUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.dialect.Dialect;

/**
 * 拦截 select 语句，处理分页
 * 
 */
@SuppressWarnings("serial")
@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PageInterceptor extends AbstractInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

	private static final String TOTAL_COUNT_COLUMN = "C";
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];

		initDialect();// 初始化方言

		MappedStatement newStat = parsePageParams(mappedStatement, parameter);// 处理分页参数
		if (newStat != null) {
			invocation.getArgs()[0] = newStat;
		}

		return invocation.proceed();// 执行调用链方法

	}

	/**
	 * 处理分页
	 * 
	 * @param mappedStatement 原 mappedStatement
	 * @param parameter sql参数
	 * @return 新的 mappedStatement
	 * @throws Throwable 末知异常
	 */
	protected MappedStatement parsePageParams(MappedStatement mappedStatement, Object parameter) throws Throwable {

		if (parameter == null) {
			return null;
		}

		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Object parameterObject = boundSql.getParameterObject();

		PagedCriteria page = null;

		if (parameterObject != null) {
			page = convertPageParameter(parameterObject);
		}

		if (page == null || page.isNotCount()) {
			return null;
		}
		String originalSql = boundSql.getSql();
		originalSql = removeBreakingWhitespace(originalSql);

		if (originalSql == null || "".equals(originalSql.trim())) {
			return null;
		}

		// 得到总记录数
		if (!page.isNotCount()) {
			page.setCount(getCount(originalSql, null, mappedStatement, parameterObject, boundSql, page.getCountSqlFragment()));
		}
		// 分页查询 本地化对象 修改数据库注意修改实现
		String pageSql = generatePageSql(originalSql, page, getDialect());

		BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, pageSql);

		MappedStatement newstat = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

		return newstat;

	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 * 
	 * @param sql
	 *            Mapper中的Sql语句
	 * @param page
	 *            分页对象
	 * @param dialect
	 *            方言类型
	 * @return 分页SQL
	 */
	private String generatePageSql(String sql, PagedCriteria page, Dialect dialect) {
		if (dialect.supportsLimit()) {
			return dialect.getLimitString(sql, page.getFirstResult(), page.getMaxResults());
		} else {
			return sql;
		}
	}

	/**
	 * 查询总纪录数
	 * 
	 * @param sql
	 *            SQL语句
	 * @param connection
	 *            数据库连接
	 * @param mappedStatement
	 *            mapped
	 * @param parameterObject
	 *            参数
	 * @param boundSql
	 *            boundSql
	 * @return 总记录数
	 * @throws SQLException
	 *             sql查询错误
	 */
	private int getCount(final String sql, final Connection connection,
			final MappedStatement mappedStatement, final Object parameterObject,
			final BoundSql boundSql, String countSqlFragment) throws SQLException {

		String countSql = convertToCountSql(sql, countSqlFragment);

		Connection conn = connection;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			logger.debug("带总记录数的sql: " + countSql);
			if (conn == null) {
				conn = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
			}

			ps = conn.prepareStatement(countSql);

			BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
			DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
			parameterHandler.setParameters(ps);

			rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public static String convertToCountSql(String sql, String countSqlFragment) {
		if (StringUtils.isBlank(countSqlFragment)) {
			countSqlFragment = " count(*) ";
		}
		return "select " + countSqlFragment + " " + TOTAL_COUNT_COLUMN + " " + sql.substring(sql.toUpperCase().indexOf("FROM"));
	}


}
