package com.openjad.orm.mybatis.utils;

import static com.openjad.common.constant.CharacterConstants.DOT_CHARACTER;
import static com.openjad.common.constant.CharacterConstants.DOT_REGEX;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.openjad.orm.mybatis.entity.EoFieldInfo;
import com.openjad.orm.mybatis.entity.EoMetaInfo;
import com.openjad.orm.mybatis.entity.WhereCondition;
import com.openjad.orm.mybatis.parse.impl.EoInfoAnnotationParser;
import com.openjad.orm.vo.BaseEO;
import com.openjad.common.exception.BizException;
import com.openjad.orm.constant.OrmLogCode;
import com.openjad.orm.enums.QueryOperateType;
import com.openjad.orm.exception.JadEntityParseException;

/**
 * 实体操作工具类
 * 
 * @author hechuan
 *
 */
public class EntityUtils {

	/**
	 * 最大关联列深度
	 */
	public static final int MAX_RELATE_DEPTH = 2;

	/**
	 * 默认表主键
	 */
	public static final String DEFAULT_ID_NAME = "id";

	/**
	 * 缓存实体信息 key:classname,val:EoMetaInfo
	 */
	private static final Map<String, EoMetaInfo> EO_INFO_CACHE = new ConcurrentHashMap<String, EoMetaInfo>();

	/**
	 * <p>
	 * 获取实体映射信息
	 * <p>
	 *
	 * @param clazz
	 *            反射实体类
	 * @return 实体元信息
	 */
	@SuppressWarnings("rawtypes")
	public static EoMetaInfo getEoInfo(Class clazz) {
		EoMetaInfo ei = EO_INFO_CACHE.get(clazz.getName());
		if (ei == null) {
			ei = initEoInfo(clazz);
		}
		return ei;
	}
	
	/**
	 * 生成一个eo实例
	 * @param <EO> eo
	 * @param clazz eo类型
	 * @return 返回 eo 实例
	 */
	public static <EO extends BaseEO>EO  newEoInstance(Class clazz){
		try {
			return (EO)clazz.newInstance();
		}catch (Exception e) {
			throw new BizException(OrmLogCode.CODE_00005,OrmLogCode.CODE_00005.getValue()+",class:"+clazz.getName());
		}
	}

	/**
	 * <p>
	 * 实体类反射获取表信息【初始化】
	 * <p>
	 *
	 * @param clazz
	 *            反射实体类
	 * @return 实体元信息
	 */
	@SuppressWarnings("rawtypes")
	public static EoMetaInfo initEoInfo(Class clazz) {
		EoMetaInfo entityInfo = null;
		synchronized (clazz) {
			EoMetaInfo ei = EO_INFO_CACHE.get(clazz.getName());
			if (ei != null) {
				return ei;
			}
			EoInfoAnnotationParser parser = new EoInfoAnnotationParser();
			entityInfo = parser.parseEo(clazz);
			EO_INFO_CACHE.put(clazz.getName(), entityInfo);
		}
		if (entityInfo != null) {
			validateRelateEntity(entityInfo);
		}
		return entityInfo;
	}

	/**
	 * 校验关联实体
	 * @param entityInfo 实体信息
	 */
	private static void validateRelateEntity(EoMetaInfo entityInfo) {

		for (EoFieldInfo fieldInfo : entityInfo.getFieldInfoMap().values()) {
			if (!fieldInfo.isRelateColumn()) {
				continue;
			}
			EoMetaInfo rEi = getEoInfo(fieldInfo.getFieldType());
			if (rEi == null || rEi.getKeyFieldInfo() == null) {
				throw new JadEntityParseException(String.format("实体%s关联的属性%s必须为实体，而且存在主键",
						entityInfo.getObjName(), fieldInfo.getFieldName()));
			}

		}

	}
	
	/**
	 * 查询where条件
	 * @param ei 实体信息
	 * @param key key
	 * @param specialType 查询类型
	 * @return where条件
	 */
	public static WhereCondition findWhereCondition(EoMetaInfo ei, String key, QueryOperateType specialType) {

		Collection<EoFieldInfo> fieldInfos = ei.getFieldInfoMap().values();

		boolean isRelateKey = isRelateProperty(key, MAX_RELATE_DEPTH);

		for (EoFieldInfo fi : fieldInfos) {
			if (!fi.isColumn()) {
				continue;
			}
			String property = fi.getFieldName();
			if (property.equals(key)) {
				WhereCondition wc = new WhereCondition(ei, fi);
				wc.setConditionKey(key);
				if (specialType != null) {
					wc.setOperateType(specialType);
				} else {
					wc.setOperateType(QueryOperateType.eq);
				}
				return wc;
			}

			if (isRelateKey && fi.isRelateColumn()) {
				String relateName = getRelateEntityName(key);
				if (property.equals(relateName)) {
					WhereCondition wc = new WhereCondition(ei, fi);
					wc.setConditionKey(key);
					if (specialType != null) {
						wc.setOperateType(specialType);
					} else {
						wc.setOperateType(QueryOperateType.eq);
					}
					return wc;
				}
			}

		}

		if (isRelateKey) {
			WhereCondition rwc = new WhereCondition(ei, null);
			rwc.setConditionKey(key);
			rwc.setValid(false);//无效
			return rwc;
		}

		WhereCondition wc = match(ei, fieldInfos, key, QueryOperateType.eq);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.gt);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.ge);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.lt);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.le);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.notLike);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.like);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.notLikeLeft);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.likeLeft);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.notLikeRight);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.likeRight);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.isNull);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.isNotNull);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.notIn);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.in);
		if (wc != null) {
			return wc;
		}

		wc = match(ei, fieldInfos, key, QueryOperateType.between);
		if (wc != null) {
			return wc;

		} else {
			wc = new WhereCondition(ei, null);
			wc.setConditionKey(key);
			wc.setValid(false);//无效
			//			logger.warn("在实体:"+ei.getObjName()+"中无法匹配到key为"+key+"的相应的条件");
			return wc;
		}

	}
	
	/**
	 * 条件匹配
	 * @param ei 实体信息
	 * @param fieldInfos 属性信息
	 * @param key key
	 * @param defType 操作类型
	 * @return where条件
	 */
	public static WhereCondition match(
			EoMetaInfo ei, Collection<EoFieldInfo> fieldInfos, String key, QueryOperateType defType) {

		if (key.endsWith(defType.getSuffix())) {
			String matchPro = key.substring(0, key.length() - defType.getSuffix().length());

			for (EoFieldInfo fi : fieldInfos) {
				if (!fi.isColumn()) {
					continue;
				}
				if (fi.getFieldName().equals(matchPro)) {
					WhereCondition wc = new WhereCondition(ei, fi);
					wc.setConditionKey(key);
					wc.setOperateType(defType);
					return wc;
				}

			}

		}
		return null;
	}
	
	/**
	 * 生成where条件
	 * @param ei 实体信息
	 * @param key key
	 * @param specialType 操作类型
	 * @return where条件
	 */
	public static WhereCondition getWhereCondition(EoMetaInfo ei, String key, QueryOperateType specialType) {

		String cacheKey = key;
		if (specialType != null) {
			cacheKey = cacheKey + "_" + specialType.getType();
		}

		WhereCondition wc = ei.getWhereConditionMap().get(cacheKey);

		if (wc != null) {

			return wc;

		} else {
			wc = findWhereCondition(ei, key, specialType);
			if (wc != null) {
				ei.getWhereConditionMap().put(cacheKey, wc);//缓存起来
			} else {
			}
			return wc;
		}

	}


	/**
	 * 跟据属性名称判断是否为关联属性
	 * 
	 * @param propertyName 属性名
	 * @param maxDepth maxDepth
	 * @return 是否为关联属性
	 */
	public static boolean isRelateProperty(String propertyName, int maxDepth) {
		//		TODO 待完善
		return propertyName.contains(DOT_CHARACTER) && propertyName.split(DOT_REGEX).length <= maxDepth;//目前只支持关联一级
	}

	public static boolean isRelateProperty(String propertyName) {
		//		TODO 待完善
		return propertyName.contains(DOT_CHARACTER);
	}

	/**
	 * 获得关联属性名
	 * 
	 * @param propertyName 属性名
	 * @return  关联属性名
	 */
	public static String getRelateEntityName(String propertyName) {
		//		TODO 待完善
		if (!isRelateProperty(propertyName)) {
			return propertyName;
		} else {
			return propertyName.split(DOT_REGEX)[0];
		}
	}

	/**
	 * 
	 * @param propertyName 属性名
	 * @return 关联属性名
	 */
	public static String getRelatePropertyName(String propertyName) {
		//		TODO 待完善
		if (!isRelateProperty(propertyName)) {
			return propertyName;
		} else {
			//			return propertyName.split(DOT_REGEX)[1];
			return propertyName.substring(propertyName.indexOf(DOT_CHARACTER) + 1);
		}
	}

	/**
	 * 生成一个符合规范的列名
	 * 
	 * @param name  列名
	 * @return 列名
	 */
	public static String convertColumnName(String name) {

		String connStr = "_";

		String nameStr = name.replaceAll("[^a-zA-Z0-9]", "");
		if ("".equals(nameStr)) {
			return "";
		}
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		sb.append(nameStr.charAt(0));

		for (int i = 1; i < nameStr.length(); i++) {
			String c = nameStr.charAt(i) + "";

			if (Pattern.matches("[A-Z]", c) && !sb.toString().endsWith(connStr)) {
				if (!flag) {
					sb.append(connStr);
				}
				flag = true;
			} else {
				flag = false;
			}
			sb.append(c);
		}

		return sb.toString().toLowerCase();
	}

}
