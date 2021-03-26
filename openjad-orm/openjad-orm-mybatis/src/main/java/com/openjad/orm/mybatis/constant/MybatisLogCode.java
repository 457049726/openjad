package com.openjad.orm.mybatis.constant;

import com.openjad.common.constant.BaseLogCode;

/**
 * mybatis日志代码
 * 
 *  @author hechuan
 *
 */
public class MybatisLogCode extends BaseLogCode {

	private static final String LOG_TYPE = "mybatis";
	
	public static final MybatisLogCode CODE_00001 = new MybatisLogCode("00001", "从参数中提取分页信息出错");
	
	public static final MybatisLogCode CODE_00002 = new MybatisLogCode("00002", "自动重新加载出错,无法识别的类,可能是某个mapper文件中的namespace有误");
	
	public static final MybatisLogCode CODE_00003 = new MybatisLogCode("00003", "自定义dao扫描失败，自动切换为mybatis默认的扫描器");
	public static final MybatisLogCode CODE_00004 = new MybatisLogCode("00004", "自动加载dao/mapper信息失败");
	public static final MybatisLogCode CODE_00005 = new MybatisLogCode("00005", "实体类的属性上不能使用Temporal注解");
	
	public static final MybatisLogCode CODE_00006 = new MybatisLogCode("00006", "实体类没有被@Entity标注");
	public static final MybatisLogCode CODE_00007 = new MybatisLogCode("00007", "实体类没有通过@Table注解指定表名");
	public static final MybatisLogCode CODE_00008 = new MybatisLogCode("00008", "实体类没有通过@Id注解指定主键");
	public static final MybatisLogCode CODE_00009 = new MybatisLogCode("00009", "获取bean对应的接口类失败,忽略");
	public static final MybatisLogCode CODE_00010 = new MybatisLogCode("00010", "没有加载资源,自动加载",false);

	public static final MybatisLogCode CODE_00011 = new MybatisLogCode("00011", "无法通过dao类解析出对应的实体类型");
	public static final MybatisLogCode CODE_00012 = new MybatisLogCode("00012", "无法通过namespace加载dao类");
	public static final MybatisLogCode CODE_00013 = new MybatisLogCode("00013", "无法分析实体类的元属性");
	public static final MybatisLogCode CODE_00014 = new MybatisLogCode("00014", "获取 mapperLocations配置失败");
	public static final MybatisLogCode CODE_00015 = new MybatisLogCode("00015", "跟据resource解析配置失败");
	
	public static final MybatisLogCode CODE_00016 = new MybatisLogCode("00016", "注册jad-dao失败，程序异常退出");
	public static final MybatisLogCode CODE_00017 = new MybatisLogCode("00017", "无法识别主键类型");
//	public static final MybatisLogCode CODE_00018 = new MybatisLogCode("00018", "");
//	public static final MybatisLogCode CODE_00019 = new MybatisLogCode("00019", "");
	
	
	public static final MybatisLogCode CODE_00030 = new MybatisLogCode("00030", "因为存在下级节点而无法删除");
	
	public MybatisLogCode(String code, String value) {
		super(LOG_TYPE, code, value);
	}
	
	protected MybatisLogCode(String code, String value, boolean urlForMore) {
		super(LOG_TYPE, code, value,urlForMore);
	}

}
