package com.openjad.test.db;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title DbConfig
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
public class DbConfig {
	
	private String driver ;
	private String url ;
	private String username ;
	private String pwd ;
	
	
	public String validate(){
		String err=null;
		if(isBlank(driver)){
			err="缺失数据库驱动";
		}
		else if(isBlank(url)){
			err="缺失数据库url";
		}
		else if(isBlank(username)){
			err="缺失数据库用户名";
		}
		else if(isBlank(pwd)){
			err="缺失数据库密码";
		}		
		return err;
	}
	
	private boolean isBlank(String str){
		return str==null || "".equals(str.trim());
	}

	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
