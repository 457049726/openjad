package com.openjad.orm.mybatis.interceptor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.criterion.PagedCriteria;
import com.openjad.orm.dialect.Dialect;
import com.openjad.orm.dialect.db.MySQLDialect;
import com.openjad.orm.dialect.db.OracleDialect;
import com.openjad.orm.enums.DBType;

/**
 * 抽像拦截器
 * 
 * @author hechuan
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractInterceptor implements Interceptor, Serializable {

	private static final Logger logger = LoggerFactory.getLogger(AbstractInterceptor.class);

	/**
	 * 分页常量
	 */
	protected static final String PAGE = "paged_criteria";

	/**
	 * 数据库类型标识
	 */
	protected static final String DB_TYPE_KEY = "jdbc.type";

	/**
	 * 数据库方言
	 */
	private static Dialect dialect;

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 接口方法,不干活
	 */
	@Override
	public void setProperties(Properties properties) {
	}

	/**
	 * 复制BoundSql对象
	 * 
	 * @param ms
	 *                    mappedStatement
	 * @param oldBoundSql
	 *                    原bound
	 * @param newSql
	 *                    新sql
	 * @return 新bound
	 */
	protected BoundSql copyFromBoundSql(MappedStatement ms, BoundSql oldBoundSql, String newSql) {

		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), newSql,
				oldBoundSql.getParameterMappings(), oldBoundSql.getParameterObject());

		for (ParameterMapping mapping : oldBoundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (oldBoundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, oldBoundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	/**
	 * BoundSql包装类
	 * 
	 * @author hechuan
	 *
	 */
	protected static class BoundSqlSqlSource implements SqlSource {
		private BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	/**
	 * 获取数据方言
	 * 
	 * @return 方言
	 */
	protected Dialect getDialect() {
		if (dialect == null) {
			throw new RuntimeException("dialect 为空，请先初始化");
		}
		return dialect;
	}

	/**
	 * 对分页参数进行转换和检查
	 * 
	 * @param parameterObject 参数对象
	 * @return 分页对象
	 */
	@SuppressWarnings({ "rawtypes" })
	protected PagedCriteria convertPageParameter(Object parameterObject) {
		try {
			if (parameterObject instanceof PagedCriteria) {
				return (PagedCriteria) parameterObject;
			} else if (parameterObject instanceof MapperMethod.ParamMap) {
				MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameterObject;
				if (paramMap.containsKey(PAGE) && (paramMap.get(PAGE) instanceof PagedCriteria)) {
					return (PagedCriteria) paramMap.get(PAGE);
				}
				return null;
			} else {
				Field field = getAccessibleField(parameterObject, PAGE);
				if (field != null) {
					Object pageObject = field.get(parameterObject);
					if (pageObject instanceof PagedCriteria) {
						return (PagedCriteria) pageObject;
					}
				}
				return null;
			}
		} catch (Exception e) {
			logger.error(MybatisLogCode.CODE_00001, "从参数中提取分页信息出错，" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 复制新的 MappedStatement
	 * 
	 * @param ms           ms
	 * @param newSqlSource newSqlSource
	 * @return mappedStatement
	 */
	protected MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {

		MappedStatement.Builder builder = new MappedStatement.Builder(
				ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());

		if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
			StringBuilder keyProperties = new StringBuilder();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		return builder.build();
	}

	/**
	 * 获得mybatis原对象
	 * 
	 * @param proxyProject proxyProject
	 * @return 原对象
	 */
	protected Object getOriginalObject(Object proxyProject) {

		MetaObject metaStatementHandler = SystemMetaObject.forObject(proxyProject);

//      分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环 ,可以分离出最原始的的目标类)  
		while (metaStatementHandler.hasGetter("h")) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = SystemMetaObject.forObject(object);
		}
		// 分离最后一个代理对象的目标类  
		while (metaStatementHandler.hasGetter("target")) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = SystemMetaObject.forObject(object);
		}
		return metaStatementHandler.getOriginalObject();

	}

	/**
	 * 初始化方言
	 * 
	 * (这个方法待完善)
	 * 
	 */
	protected void initDialect() {
		if (dialect != null) {
			return;
		}

		String dbType = System.getProperty(DB_TYPE_KEY);

		if (DBType.MYSQL.equals(dbType)) {

			dialect = new MySQLDialect();

		} else if (DBType.ORACLE.equals(dbType)) {

			dialect = new OracleDialect();

		} else {
			//logger.warn("末知数据库类型:"+dbType+",使用默认的 mysql");
			dialect = new MySQLDialect();
			System.setProperty(DB_TYPE_KEY, DBType.MYSQL.getDb());
		}

	}

	/**
	 * 是否普通类型及包装类
	 * 
	 * @param parameterObjectClass parameterObjectClass
	 * @return 是否普通类型及包装类
	 */
	protected boolean isPrimitiveOrPrimitiveWrapper(Class<?> parameterObjectClass) {
		return parameterObjectClass.isPrimitive() ||
				(parameterObjectClass.isAssignableFrom(Byte.class) || parameterObjectClass.isAssignableFrom(Short.class) ||
						parameterObjectClass.isAssignableFrom(Integer.class) || parameterObjectClass.isAssignableFrom(Long.class) ||
						parameterObjectClass.isAssignableFrom(Double.class) || parameterObjectClass.isAssignableFrom(Float.class) ||
						parameterObjectClass.isAssignableFrom(Character.class) || parameterObjectClass.isAssignableFrom(Boolean.class));
	}

	/**
	 * 去掉空白字符
	 * 
	 * @param original original
	 * @return 去掉空白字符后的字符串
	 */
	protected String removeBreakingWhitespace(String original) {
		if (original == null || "".equals(original.trim())) {
			return "";
		}
		StringTokenizer whitespaceStripper = new StringTokenizer(original);
		StringBuilder builder = new StringBuilder();
		while (whitespaceStripper.hasMoreTokens()) {
			builder.append(whitespaceStripper.nextToken());
			builder.append(" ");
		}
		return builder.toString();
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 * 如果向上转型到Object仍无法找到, 返回null.
	 * 
	 * @param obj       原对象
	 * @param fieldName 属性名
	 * @return 属性
	 */
	protected static Field getAccessibleField(final Object obj, final String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
				continue;
			}
		}
		return null;
	}

	/**
	 * 改变private/protected的成员变量为public，
	 * 尽量不调用实际改动的语句，避免JDK的SecurityManager限制
	 * 
	 * @param field 成员属性
	 */
	private static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
						.isFinal(field.getModifiers()))
				&& !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

}
