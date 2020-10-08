package com.openjad.orm.dialect.db;

import com.openjad.orm.constant.OrmLogCode;
import com.openjad.orm.exception.JadEntityParseException;

/**
 * Mysql方言的实现
 * 
 *  @author hechuan
 *
 */
public class MySQLDialect extends AbstractDialect {


    @Override
    public String getLimitString(String sql, long offset, int limit) {
        return getLimitString(sql, offset, Long.toString(offset),
                Integer.toString(limit));
    }

    public boolean supportsLimit() {
        return true;
    }

    /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符号(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     *
     * @param sql               实际SQL语句
     * @param offset            分页开始纪录条数
     * @param offsetPlaceholder 分页开始纪录条数－占位符号
     * @param limitPlaceholder  分页纪录条数占位符号
     * @return 包含占位符的分页sql
     */
    public String getLimitString(String sql, long offset, String offsetPlaceholder, String limitPlaceholder) {
        StringBuilder stringBuilder = new StringBuilder(sql);
        stringBuilder.append(" limit ");
        if (offset > 0) {
            stringBuilder.append(offsetPlaceholder).append(",").append(limitPlaceholder);
        } else {
            stringBuilder.append(limitPlaceholder);
        }
        return stringBuilder.toString();
    }
    
    /**
     * 字符串连接
     * @param strs strs
     * @return res
     */
    public String concat(String...strs) {
    	StringBuffer sb=new StringBuffer();
    	if(strs==null || strs.length<2){
    		throw new RuntimeException("sql 中 concat 函数的参数错误, strs "+(strs==null?" is null ":(":"+strs[0] )));
    	}
    	sb.append("CONCAT(");
    	for(int i=0;i<strs.length;i++){
    		if(i>0){
    			sb.append(",");
    		}
    		sb.append(strs[i]);
    	}
    	sb.append(")");
    	
    	return sb.toString();
    }
    
    

    /**
     * toDate
     *     %a 	缩写星期名
     *     %b 	缩写月名
     *     %c 	月，数值
     *     %D 	带有英文前缀的月中的天
     *     %d 	月的天，数值(00-31)
     *     %e 	月的天，数值(0-31)
     *     %f 	微秒
     *     %H 	小时 (00-23)
     *     %h 	小时 (01-12)
     *     %I 	小时 (01-12)
     *     %i 	分钟，数值(00-59)
     *     %j 	年的天 (001-366)
     *     %k 	小时 (0-23)
     *     %l 	小时 (1-12)
     *     %M 	月名
     *     %m 	月，数值(00-12)
     *     %p 	AM 或 PM
     *     %r 	时间，12-小时（hh:mm:ss AM 或 PM）
     *     %S 	秒(00-59)
     *     %s 	秒(00-59)
     *     %T 	时间, 24-小时 (hh:mm:ss)
     *     %U 	周 (00-53) 星期日是一周的第一天
     *     %u 	周 (00-53) 星期一是一周的第一天
     *     %V 	周 (01-53) 星期日是一周的第一天，与 %X 使用
     *     %v 	周 (01-53) 星期一是一周的第一天，与 %x 使用
     *     %W 	星期名
     *     %w 	周的天 （0=星期日, 6=星期六）
     *     %X 	年，其中的星期日是周的第一天，4 位，与 %V 使用
     *     %x 	年，其中的星期一是周的第一天，4 位，与 %v 使用
     *     %Y 	年，4 位
     *     %y 	年，2 位
     * @param dateStr str
     * @param pattern java date fromat
     * @return res
     */
    public String toDate(String dateStr,String pattern){
    	if(dateStr==null || pattern==null){
    		throw new RuntimeException("sql 中 to_data参数错误,dateStr:"+dateStr+",pattern:"+pattern);
    	}
    	StringBuffer sb=new StringBuffer();
    	sb.append("str_to_date(");
    	if(!"?".equals(dateStr.trim())){
    		sb.append("'");
    	}
    	sb.append(dateStr);
    	if(!"?".equals(dateStr.trim())){
    		sb.append("'");
    	}
    	sb.append(",").append(getSqlPattern(pattern)).append(")");
    	return sb.toString();
    }
    
    private String getSqlPattern(String pattern){
    	if("yyyyMMdd".equals(pattern)){
    		return "'%Y%m%d'";
    	}
    	if("yyyyMM".equals(pattern)){
    		return "'%Y%m'";
    	}
    	if("yyyy-MM-dd".equals(pattern)){
    		return "'%Y-%m-%d'";
    	}
    	if("yyyy-MM".equals(pattern)){
    		return "'%Y-%m'";
    	}
    	if("HH:mm:ss".equals(pattern)){
    		return "'%H:%i:%s'";
    	}
    	if("yyyy-MM-dd HH:mm:ss".equals(pattern)){
    		return "'%Y-%m-%d %H:%i:%s'";
    	}
    	if("yyyyMMddHHmmss".equals(pattern)){
    		return "'%Y%m%d%H%i%s'";
    	}
    	else{
//    		TODO 需要完善
    		throw new JadEntityParseException(OrmLogCode.CODE_00001,"暂不支持的时间格式:"+pattern+",请修改此处的代码，将之完善");
    		
    	}
    }
    
    public String sysdate(){
    	return "SYSDATE()";
    }
    

}
