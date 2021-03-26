package com.openjad.orm.constant;

import com.openjad.common.constant.BaseLogCode;
import com.openjad.common.constant.FrameworkCode;

/**
 * orm日志代码
 * 
 *  @author hechuan
 *
 */
public class OrmLogCode extends BaseLogCode {

	private static final String LOG_TYPE = "orm";
	
	public static final OrmLogCode CODE_00001 = new OrmLogCode("00001", "Dialect暂不支持的时间格式");
	public static final OrmLogCode CODE_00002 = new OrmLogCode("00002", "逻辑删除失败");
	
	public static final OrmLogCode CODE_00003 = new OrmLogCode("00003", "必须指定一个合法的最大深度");
	
	
	public static final OrmLogCode CODE_00004 = new OrmLogCode("00004", "超出最大深度限制");
	
	public static final OrmLogCode CODE_00005 = new OrmLogCode("00005", "实例化实体失败,请检查此实体是否包含可访问的无参构造函数");
	
	public OrmLogCode(String code, String value) {
		super(LOG_TYPE, code, value);
	}

}
