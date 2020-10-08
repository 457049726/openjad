package com.openjad.orm.mybatis.utils;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.mybatis.entity.EoMetaInfo;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.enums.RelateLoadMethod;
import com.openjad.orm.exception.JadSqlException;

/**
 * sql帮助类
 * 
 * @author hechuan
 *
 */
public class SqlHelper {

	public static final Logger logger = LoggerFactory.getLogger(SqlHelper.class);

	/**
	 * 空字符
	 */
	public static final String EMPTY = "";

	/**
	 * 下划线字符
	 */
	public static final char UNDERLINE = '_';

	public static final String TOTAL_COUNT_COLUMN = "C";

	/**
	 * 生成 select 语句
	 * @param entityClass 实体类
	 * @return select 语句
	 */
	public static String getSelectSql(Class entityClass) {
		return getSelectSql(entityClass, null);
	}

	/**
	 * 生成 select 语句
	 * @param entityClass 实体类
	 * @param alias 别名
	 * @return select 语句
	 */
	public static String getSelectSql(Class entityClass, String alias) {
		return getSelectSql(entityClass, alias, new ArrayList<String>());
	}

	/**
	 * 生成 select 语句
	 * @param entityClass 实体类
	 * @param alias 别名
	 * @param rcSqlList 类联sql
	 * @return select 语句
	 */
	public static String getSelectSql(Class entityClass, String alias, List<String> rcSqlList) {

		EoMetaInfo ei = EntityUtils.getEoInfo(entityClass);

		if (alias == null || alias.length() == 0) {
			alias = getDefAlias(ei);
		}

		StringBuffer sb = new StringBuffer();
		sb.append("select ");

		sb.append(getSelectColumn(ei, null, alias, 0));

		sb.append(" from ");

		sb.append(getSelectFrom(ei, alias, rcSqlList));

		return sb.toString();
	}

	/**
	 * 生成 select语句列
	 * @param ei 实体类
	 * @param alias 别名
	 * @return select语句列
	 */
	public static String getSelectColumn(EoMetaInfo ei, String alias) {
		return getSelectColumn(ei, null, alias, 0);
	}
	
	/**
	 * 生成 select语句列
	 * @param ei 实体类
	 * @return select语句列
	 */
	public static String getSelectColumn(EoMetaInfo ei) {
		return getSelectColumn(ei, null, null, 0);
	}
	
	/**
	 * 生成 select语句列
	 * @param ei 实体类
	 * @param relateField 类联属性
	 * @param alias 别名
	 * @param relateDepth 类联深度
	 * @return select语句列
	 */
	public static String getSelectColumn(
			EoMetaInfo ei, String relateField, String alias, int relateDepth) {

		if (ei == null || ei.getFieldInfoMap() == null || ei.getFieldInfoMap().isEmpty()) {
			throw new JadSqlException("执行查询出错，参数错误");
		}

		if (isBlank(relateField) && relateDepth > 0) {
			throw new JadSqlException("relateField 不能为空");
		}

		StringBuffer sb = new StringBuffer();

		boolean isfirst = true;

		for (EoFieldInfo fi : ei.getFieldInfoMap().values()) {

			if (!fi.isColumn()) {
				continue;
			}

			if (!fi.isRelateColumn()) {//不是关联列

				if (isfirst) {
					isfirst = false;
				} else {
					sb.append(",");
				}

				if (isNotBlank(alias)) {
					sb.append(alias).append(".");
				}
				sb.append(fi.getColumn().trim());
				sb.append(" as ");
				if (relateDepth > 0) {
					if (isNotBlank(relateField)) {
						sb.append("\"").append(relateField).append(".");
					}

					sb.append(fi.getFieldName().trim()).append("\"");
				} else {
					sb.append("\"").append(fi.getFieldName().trim()).append("\"");
				}

			} else {

				if (fi.getRelateColumn() != null && fi.getRelateColumn().loadMethod() != null) {
					if (RelateLoadMethod.NOT_AUTO.getType().equals(fi.getRelateColumn().loadMethod().getType())
							|| relateDepth > 0) {

						if (isfirst) {
							isfirst = false;
						} else {
							sb.append(",");
						}
						if (isNotBlank(alias)) {
							sb.append(alias).append(".");
						}

						sb.append(fi.getColumn().trim());
						sb.append(" as ");
						sb.append("\"");

						if (relateDepth > 0 && isNotBlank(relateField)) {
							sb.append(relateField).append(".");
						}
						sb.append(fi.getFieldName().trim());
						String relateProperty = EntityUtils.getEoInfo(fi.getFieldType()).getKeyFieldInfo().getFieldName().trim();
						sb.append(".").append(relateProperty).append("\"");
					} else {
						//放到下一个循环中
					}
				}

			}

		}

		for (EoFieldInfo fi : ei.getFieldInfoMap().values()) {

			if (fi.getRelateColumn() != null && fi.getRelateColumn().loadMethod() != null) {
				if (fi.isRelateColumn() && relateDepth == 0
						&& !RelateLoadMethod.NOT_AUTO.getType().equals(fi.getRelateColumn().loadMethod().getType())) {

					if (sb.length() > 0) {
						sb.append(",");
					}

					EoMetaInfo rEi = EntityUtils.getEoInfo(fi.getFieldType());//关联信息

					String relateAlias = fi.getRelateAlias();
					if (isBlank(relateAlias)) {
						relateAlias = fi.getField().getName();
					}

					sb.append(getSelectColumn(rEi, fi.getFieldName(), relateAlias, relateDepth + 1));//递归把关联列都整出来
				}
			}

		}
		return sb.toString();
	}

	/**
	 * 生成 select from子句
	 * @param ei 类体类
	 * @param alias 别名
	 * @param rcSqlList 关联sql
	 * @return  select from子句
	 */
	public static String getSelectFrom(EoMetaInfo ei, String alias, List<String> rcSqlList) {

		StringBuffer sb = new StringBuffer();

		sb.append(ei.getTableName());
		if (isBlank(alias)) {
			alias = ei.getTableName();
		}

		if (!ei.getTableName().equalsIgnoreCase(alias)) {
			sb.append(" ").append(alias);
		}

		for (EoFieldInfo fi : ei.getFieldInfoMap().values()) {
			if (!fi.isColumn() || !fi.isRelateColumn()) {
				continue;
			}
			EoMetaInfo rEi = EntityUtils.getEoInfo(fi.getFieldType());//关联信息

			//左关联
			if (fi.getRelateColumn() != null && fi.getRelateColumn().loadMethod() != null) {

				if (RelateLoadMethod.LEFT_JOIN.getType().equals(fi.getRelateColumn().loadMethod().getType())) {

					String relateAlias = fi.getRelateAlias();
					if (isBlank(relateAlias)) {
						//					relateAlias=fi.getFieldName()+"_1";
						relateAlias = fi.getFieldName();
					}

					sb.append(" left join ").append(rEi.getTableName()).append(" ").append(relateAlias).append(" on ");
					sb.append(alias).append(".").append(fi.getColumn()).append("=");

					String relateColumn = fi.getRelateColumn().relateColumn();
					if (isBlank(relateColumn)) {
						relateColumn = EntityUtils.getEoInfo(fi.getFieldType()).getKeyFieldInfo().getColumn();
					}

					sb.append(relateAlias).append(".").append(relateColumn);

				} else if (RelateLoadMethod.DECLARE.getType().equals(fi.getRelateColumn().loadMethod().getType())) {

					String relateAlias = fi.getRelateAlias();
					if (isBlank(relateAlias)) {
						relateAlias = fi.getFieldName();
					}

					sb.append(",").append(rEi.getTableName());
					if (!rEi.getTableName().equalsIgnoreCase(alias)) {
						sb.append(" ").append(relateAlias);
					}

					String relateColumn = fi.getRelateColumn().relateColumn();
					if (isBlank(relateColumn)) {
						relateColumn = EntityUtils.getEoInfo(fi.getFieldType()).getKeyFieldInfo().getColumn();
					}

					StringBuffer rcSql = new StringBuffer();
					rcSql.append(alias).append(".").append(fi.getColumn()).append("=");
					rcSql.append(relateAlias).append(".").append(relateColumn);

					rcSqlList.add(rcSql.toString());
				}
			}

		}

		return sb.toString();

	}
 
	/**
	 * 生成默认别名
	 * @param ei 实体类
	 * @return 别名
	 */
	public static String getDefAlias(EoMetaInfo ei) {
//		return StringUtils.uncapitalize(ei.getMetaClass().getSimpleName());
		return "a";
	}

}
