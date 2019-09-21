package com.openjad.test.db;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jad.logger.api.Logger;
import com.jad.logger.api.LoggerFactory;

import static com.openjad.common.constant.DefaultValueConstants.DEF_COLLECT_CAPACITY;

/**
 * 
 * 
 *  @author hechuan
 *
 */
public class DbTestUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(DbTestUtils.class);
	
	private DbConfig conf;
	
	private DbTestUtils(){
	}
	
	
	public static DbTestUtils newDbUtil(DbConfig conf)throws Exception{
		if(conf==null ){
			throw new Exception("无法实例化DbUtils,原因:末知的数据库配置");
		}
		String err=conf.validate();
		if(!isBlank(err)){
			throw new Exception("无法实例化DbUtils,原因:"+err);	
		}
		
		DbTestUtils util=new DbTestUtils();
		
		util.conf=conf;
		
		return util;
		
	}
	
	public static DbTestUtils newDbUtil(String driver,String username,String pwd,String url)throws Exception{
		if(isBlank(driver) || isBlank(username) 
				|| isBlank(pwd) || isBlank(url)  ){
			throw new Exception("无法实例化DbUtils,缺少必要信息");
		}
		DbTestUtils util=new DbTestUtils();
		DbConfig conf=new DbConfig();
		conf.setDriver(driver);
		conf.setUsername(username);
		conf.setPwd(pwd);
		conf.setUrl(url);
		util.conf=conf;

		return util;
	}
	
	private static boolean isBlank(String str){
		return str==null || "".equals(str.trim());
	}
	
	
	
	public Connection getConnections()throws Exception{
		Connection connection = null;
		Class.forName(conf.getDriver());
		connection= DriverManager.getConnection(conf.getUrl(),conf.getUsername(),conf.getPwd());
		return connection;
	}
	
	@SuppressWarnings("rawtypes")
	public int executeUpdate(String sql,List param)throws Exception {
		Connection conn=getConnections();
		PreparedStatement ps = conn.prepareStatement(sql);
		
		if(param!=null){
            for(int i=0;i<param.size();i++){
                Object obj = param.get(i);  
                this.setParameterValue(ps, i+1, obj);  
            }  
        }
		
		int count = ps.executeUpdate();
		close(ps);
		close(conn);
		return count;
	}
	
	@SuppressWarnings("rawtypes")
	public int executeUpdate(Connection conn,String sql,List param)throws Exception {
		
		PreparedStatement ps = conn.prepareStatement(sql);
		
		if(param!=null){  
            for(int i=0;i<param.size();i++){
                Object obj = param.get(i);  
                this.setParameterValue(ps, i+1, obj);  
            }  
        }
		
		int count = ps.executeUpdate();
		close(ps);
		
		return count;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String,Object>> executeQuery(String sql,List param)throws Exception {
		Connection conn=getConnections();
		PreparedStatement ps = conn.prepareStatement(sql);
		
        if(param!=null){  
            for(int i=0;i<param.size();i++){
                Object obj = param.get(i);  
                this.setParameterValue(ps, i+1, obj);  
            }  
        }
          
        ResultSet rs = ps.executeQuery();  
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();  
        Map<String,Integer> metaDataMap = null;  
        while(rs.next()){  
            if(rs.isFirst()){  
                metaDataMap = this.getMetaData(rs);  
            }  
            dataList.add(this.setData(rs,metaDataMap));  
        }  
        
        dataList=upperCaseKey(dataList);
        
        close(rs);
        close(ps);
        close(conn);
        
		return dataList;
	}
	
	private List<Map<String,Object>>upperCaseKey(List<Map<String,Object>>list){
		List<Map<String,Object>>newList=new ArrayList<Map<String,Object>>();
		if(list!=null && !list.isEmpty()){
			for(Map<String,Object>map:list){
				newList.add(upperCaseKey(map));
			}
		}
		
		return newList;
	}
	private Map<String,Object> upperCaseKey(Map<String,Object> map){
		Map<String,Object>newMap=new HashMap<String,Object>(DEF_COLLECT_CAPACITY);
		if(map!=null && !map.isEmpty()){
			for(Map.Entry<String, Object>ent:map.entrySet()){
				newMap.put(ent.getKey().toUpperCase(), ent.getValue());
			}
		}
		return newMap;
	}
	
	
	private void setParameterValue(PreparedStatement pStatement, int piIndex,Object pValueObject) throws Exception {  
        if (pValueObject instanceof String) {  
            pStatement.setString(piIndex, (String) pValueObject);  
        } else if (pValueObject instanceof Boolean) {  
            pStatement.setBoolean(piIndex, ((Boolean) pValueObject).booleanValue());  
        } else if (pValueObject instanceof Byte) {  
            pStatement.setByte(piIndex, ((Byte) pValueObject).byteValue());  
        } else if (pValueObject instanceof Short) {  
            pStatement.setShort(piIndex, ((Short) pValueObject).shortValue());  
        } else if (pValueObject instanceof Integer) {  
            pStatement.setInt(piIndex, ((Integer) pValueObject).intValue());  
        } else if (pValueObject instanceof Long) {  
            pStatement.setLong(piIndex, ((Long) pValueObject).longValue());  
        } else if (pValueObject instanceof Float) {  
            pStatement.setFloat(piIndex, ((Float) pValueObject).floatValue());  
        } else if (pValueObject instanceof Double) {  
            pStatement.setDouble(piIndex, ((Double) pValueObject).doubleValue());  
        } else if (pValueObject instanceof BigDecimal) {  
            pStatement.setBigDecimal(piIndex, (BigDecimal) pValueObject);  
        } else if (pValueObject instanceof java.sql.Date) {  
            pStatement.setDate(piIndex, (java.sql.Date) pValueObject);  
        } else if (pValueObject instanceof Time) {  
            pStatement.setTime(piIndex, (Time) pValueObject);  
        } else if (pValueObject instanceof Timestamp) {  
            pStatement.setTimestamp(piIndex, (Timestamp) pValueObject);  
        } else {  
            pStatement.setObject(piIndex, pValueObject);  
        }  
    }  
  
    /** 
     * 根据传入的结果集返回结果集的元数据，以列名为键以列类型为值的map对象 
     * @param rs  param
     * @return 结果 param
     * @throws SQLException 
     */  
    private Map<String,Integer> getMetaData(ResultSet rs) throws SQLException{  
        Map<String,Integer> map = new HashMap<String,Integer>(DEF_COLLECT_CAPACITY);  
        ResultSetMetaData metaData = rs.getMetaData();  
        int numberOfColumns =  metaData.getColumnCount();  
        for(int column = 0; column < numberOfColumns; column++) {  
            String columnName = metaData.getColumnLabel(column+1);  
            int colunmType = metaData.getColumnType(column+1);  
            columnName = columnName.toLowerCase();  
            map.put(columnName, colunmType);  
        }  
        return map;  
    }  
      
    /** 
     * 将结果集封装为以列名存储的map对象 
     * @param rs param
     * @param metaDataMap metaDataMap元数据集合  
     * @return  结果
     * @throws Exception 异常
     */  
    private Map<String,Object> setData(ResultSet rs,Map<String,Integer> metaDataMap) throws Exception {  
        Map<String,Object> map = Collections.emptyMap();  
        
        for (String columnName : metaDataMap.keySet()) {  
            int columnType = metaDataMap.get(columnName);  
            Object object = rs.getObject(columnName);  
            if(object==null){  
                map.put(columnName, null);  
                continue;  
            }  
            switch (columnType) {  
            case java.sql.Types.VARCHAR:  
                map.put(columnName, object);  
                break;  
            case java.sql.Types.DATE:  
                map.put(columnName, formatDateTime( (java.sql.Date) object));  
                break;  
            case java.sql.Types.TIMESTAMP:  
                map.put(columnName, formatDateTime( (java.sql.Timestamp) object));  
                break;  
            case java.sql.Types.TIME:  
                map.put(columnName, formatDateTime( (java.sql.Time) object));  
                break;  
            case java.sql.Types.CLOB:  
                try{  
                    if(object!=null){  
                        Clob clob = (Clob)object;  
                        long length = clob.length();  
                        map.put(columnName, clob.getSubString(1L, (int)length));  
                    }  
                }catch(Exception e){
                	logger.error(e.getMessage(),e);
                }
                break;  
            case java.sql.Types.BLOB:  
                map.put(columnName, "");  
                break;  
            default:  
                map.put(columnName, object);  
                break;  
            }  
        }  
        return map;  
    }  
	
    public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
    public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = new SimpleDateFormat(pattern[0].toString()).format(date);
		} else {
			formatDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		}
		return formatDate;
	}
    
	public void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {

			logger.error(e.getMessage(),e);
		}
	}
	
	public void close(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException e) {

			logger.error(e.getMessage(),e);
		}
	}
	
	public void close(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
		} catch (SQLException e) {

			logger.error(e.getMessage(),e);
		}
	}
	
	
	
	
	
}
