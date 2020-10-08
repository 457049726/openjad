package com.openjad.orm.dialect.db;

import com.openjad.orm.constant.OrmLogCode;
import com.openjad.orm.exception.JadEntityParseException;


/**
 *  Oracle的方言实现
 * 
 *  @author hechuan
 *
 */
public class OracleDialect extends AbstractDialect {
    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String sql, long offset, int limit) {
        return getLimitString(sql, offset, Long.toString(offset), Integer.toString(limit));
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
        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);

        if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			pagingSelect.append(" ) row_ where rownum <= "+endString+") where rownum_ > ").append(offsetPlaceholder);
		} else {
			pagingSelect.append(" ) where rownum <= "+limitPlaceholder);
		}

        if (isForUpdate) {
            pagingSelect.append(" for update");
        }

        return pagingSelect.toString();
    }
    
    /**
     * 字符串连接
     * @param strs strs
     * @return res
     */
    public String concat(String...strs) {
    	StringBuffer sb=new StringBuffer();
    	if(strs==null || strs.length==0){
    		throw new RuntimeException("sql 中 字符串连接错误, strs is empty");
    	}
    	sb.append("(");
    	for(int i=0;i<strs.length;i++){
    		if(i>0){
    			sb.append(" || ");
    		}
    		sb.append(strs[i]);
    	}
    	sb.append(")");
    	return sb.toString();
    	
    }
    /**
     * toDate
     * @param dateStr str
     * @param pattern java date fromat
     * @return res
     */
    public String toDate(String dateStr,String pattern){
//   	 str_to_date(?,'%Y-%m-%d') 
   	if(dateStr==null || pattern==null){
   		throw new RuntimeException("sql 中 to_data参数错误,dateStr:"+dateStr+",pattern:"+pattern);
   	}
   	StringBuffer sb=new StringBuffer();
   	sb.append("to_date(");
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
   		return "'yyyymmdd'";
   	}
   	if("yyyyMM".equals(pattern)){
   		return "'yyyymm'";
   	}
   	if("yyyy-MM-dd".equals(pattern)){
   		return "'yyyy-mm-dd'";
   	}
   	if("yyyy-MM".equals(pattern)){
   		return "'yyyy-mm'";
   	}
   	if("HH:mm:ss".equals(pattern)){
   		return "'hh24:mi:ss'";
   	}
   	if("yyyy-MM-dd HH:mm:ss".equals(pattern)){
   		return "'yyyy-mm-dd hh24:mi:ss'";
   	}
   	if("yyyyMMddHHmmss".equals(pattern)){
   		return "'yyyymmddhh24miss'";
   	}
   	else{
//   		TODO 需要完善
   		throw new JadEntityParseException(OrmLogCode.CODE_00001,"暂不支持的时间格式:"+pattern+",请修改此处的代码，将之完善");
   		
   	}
   }
   
   
   public String sysdate(){
	   return "SYSDATE";
   }

}
