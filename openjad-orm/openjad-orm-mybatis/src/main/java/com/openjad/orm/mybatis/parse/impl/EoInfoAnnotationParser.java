package com.openjad.orm.mybatis.parse.impl;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.openjad.common.util.reflection.ReflectUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.annotation.DelStrategy;
import com.openjad.orm.annotation.KeyGenStrategy;
import com.openjad.orm.annotation.OrganizeStrategy;
import com.openjad.orm.dialect.db.AbstractDialect;
import com.openjad.orm.exception.JadEntityParseException;
import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.mybatis.entity.EoMetaInfo;
import com.openjad.orm.mybatis.entity.OrganizeStrategyInfo;
import com.openjad.orm.mybatis.parse.EoInfoParser;
import com.openjad.orm.mybatis.utils.EntityUtils;

/**
 * 通过注解解析实体信息
 * 
 * @author hechuan
 *
 */
public class EoInfoAnnotationParser implements EoInfoParser {

	private static final Logger logger = LoggerFactory.getLogger(EoInfoAnnotationParser.class);

	/*
	 * 默认表主键
	 */
	private static final String DEFAULT_ID_NAME = "id";
	
	/**
	 * Column注解解析器
	 */
	private ColumnParser columnParser = new ColumnParser();

	/**
	 * NotColumn注解解析器
	 */
	private NotColumnParser notColumnParser = new NotColumnParser();
	
	/**
	 * InsertConf注解解析器
	 */
	private InsertConfParser insertConfParser = new InsertConfParser();
	
	/**
	 * UpdateConf注解解析器
	 */
	private UpdateConfParser updateConfParser = new UpdateConfParser();

	/**
	 * Id注解解析器
	 */
	private IdParser idParser = new IdParser();

	/**
	 * OrderBy注解解析器
	 */
	private OrderByParser orderByParser = new OrderByParser();

	/**
	 * Temporal注解解析器
	 */
	private TemporalParser temporalParser = new TemporalParser();

	/**
	 * RelateColumn注解解析器
	 */
	private RelateColumnParser relateColumnParser = new RelateColumnParser();

	/**
	 * ManyToOne注解解析器
	 */
	private ManyToOneParser manyToOneParser = new ManyToOneParser();

	@SuppressWarnings("rawtypes")
	@Override
	public  EoMetaInfo parseEo(Class clazz) {
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new JadEntityParseException(MybatisLogCode.CODE_00006,
					String.format("实体类:%s没有被@Entity标注", clazz.getName()));
		}
		Table table = (Table) clazz.getAnnotation(Table.class);
		String tableName = null;
		if (table != null && (table.name() != null && table.name().length() > 0)) {
			tableName = table.name();
		}
		if (tableName == null) {
			tableName = clazz.getSimpleName();
			logger.warn(MybatisLogCode.CODE_00007,
					String.format("实体类:%s没有通过@Table注解指定表名,使用类名%s作为表名", clazz.getName(), tableName));
		}
		
		DelStrategy delStrategy = (DelStrategy) clazz.getAnnotation(DelStrategy.class);
		EoMetaInfo eoInfo = new EoMetaInfo(clazz);
		eoInfo.setTableName(tableName);
		
		OrganizeStrategy organizeStrategy = (OrganizeStrategy)clazz.getAnnotation(OrganizeStrategy.class);
		if(organizeStrategy!=null) {
			try {
				eoInfo.setOrganizeStrategyInfo(OrganizeStrategyInfo.valueFormOrganizeStrategy(organizeStrategy));
			} catch (Exception e) {
				String error="无法识别类["+clazz.getName()+"]的组织策略，因为["+e.getMessage()+"]";
				throw new RuntimeException(error,e);
			}
		}
		
		if(delStrategy!=null) {
			eoInfo.setDelStrategy(delStrategy.value());
		}

		KeyGenStrategy keyGenStrategy = (KeyGenStrategy) clazz.getAnnotation(KeyGenStrategy.class);
		if (keyGenStrategy != null) {
			eoInfo.setIdType(keyGenStrategy.idType());
			eoInfo.setSequenceName(keyGenStrategy.sequenceName());
		}

		//实体字段
		Map<String, EoFieldInfo> fieldInfoMap = getEoFieldInfos(clazz, eoInfo);
		eoInfo.setFieldInfoMap(fieldInfoMap);

		Iterator<EoFieldInfo> iterator = fieldInfoMap.values().iterator();
		boolean isFirst = true;
		StringBuffer sb = new StringBuffer();
		while (iterator.hasNext()) {
			EoFieldInfo fieldInfo = iterator.next();
			String orderBy = fieldInfo.getOrderBy();
			if (orderBy != null && orderBy.length() > 0) {
				if (isFirst) {
					isFirst = false;
				} else {
					sb.append(",");
				}
				sb.append(orderBy);
			}
		}
		if (sb.length() > 0) {
			eoInfo.setOrderBy(sb.toString());
		}

		// 设置默认主键
		if (eoInfo.getKeyFieldInfo() == null) {
			iterator = fieldInfoMap.values().iterator();
			while (iterator.hasNext()) {
				EoFieldInfo fieldInfo = iterator.next();
				String column = fieldInfo.getColumn();
				// 如果没有主键,数据库字段为ID则设置为默认主键
				if (DEFAULT_ID_NAME.equals(column)) {
					eoInfo.setKeyFieldInfo(fieldInfo);
					break;
				}
			}
			if (eoInfo.getKeyFieldInfo() == null) {
				logger.warn(MybatisLogCode.CODE_00008,
						String.format("实体类:%s没有通过@Id注解指定主键", clazz.getSimpleName()));
			}
		}

		return eoInfo;

	}

	/**
	 * 获取实体类所有字段
	 * @param clazz 实体类
	 * @param eoInfo 实体元信息
	 * @return 实体所有字段信息
	 */
	private  Map<String, EoFieldInfo> getEoFieldInfos(Class clazz, EoMetaInfo eoInfo) {

		Map<String, EoFieldInfo> fieldInfoMap = new TreeMap<String, EoFieldInfo>();

		Set<Field> fields = ReflectUtils.getClassFields(clazz);//获得所有属性
		for (Field field : fields) {

			EoFieldInfo eoFieldInfo = new EoFieldInfo(eoInfo, field);

			boolean parseColumnRes = columnParser.parseEoField(eoFieldInfo, field);
			boolean parseIdRes = idParser.parseEoField(eoFieldInfo, field);
			
			insertConfParser.parseEoField(eoFieldInfo, field);
			updateConfParser.parseEoField(eoFieldInfo, field);
			
//			TODO	数据库类型
			eoFieldInfo.setJdbcType(AbstractDialect.getJdbcType(eoFieldInfo.getFieldType().getName()));

			if (!parseColumnRes && !parseIdRes
					&& field.getType().getAnnotation(Entity.class) != null
					&& manyToOneParser.parseEoField(eoFieldInfo, field)) {

				relateColumnParser.parseEoField(eoFieldInfo, field);

			}

			temporalParser.parseEoField(eoFieldInfo, field);
			orderByParser.parseEoField(eoFieldInfo, field);
			
			notColumnParser.parseEoField(eoFieldInfo, field);

			//主键
			if (eoFieldInfo.isId()) {

				if (!parseColumnRes) {
					eoFieldInfo.setFieldName(field.getName());
				}
				eoInfo.setKeyFieldInfo(eoFieldInfo);
			}
			
			if(eoFieldInfo.getColumn()==null || eoFieldInfo.getColumn().length()==0){
				eoFieldInfo.setColumn(EntityUtils.convertColumnName(field.getName()));
			}
			
			fieldInfoMap.put(field.getName(), eoFieldInfo);
		}

		return fieldInfoMap;
	}

}
