package com.five9.service;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.five9.model.Conn;
import com.five9.model.DatabaseConnection;
/*
 * <p> connection with Microsoft SQL Server (logisense report)</p>
 * DataSource will be wired from xml configuration file. 
 * JdbcTemplate is used to do all the operations on database;
 * */
public class LsMSsqlConn extends DatabaseConnection implements Conn{
	protected DataSource mssqlPara;
	@Override
	public void moniter(){
		System.out.println();
		System.out.println("Echo from logisense mssql server");
//		if(deleteSwitch)
//			this.delete();
//		importDataToDB();
		if(this.querySwitch)
			query(this.querySql);
	}
	
	/*setters and getters*/
	
	public String getPath() {
		return path;
	}
	@Autowired
	public void setPath(String path) {
		this.path = path;
	}
	public String getBatchInsertionSql() {
		return batchInsertionSql;
	}
	@Autowired 
	public void setBatchInsertionSql(String batchInsertionSql) {
		this.batchInsertionSql = batchInsertionSql;
	}
	public String getQuerySql() {
		return querySql;
	}
	@Autowired
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public DataSource getMssqlPara() {
		return mssqlPara;
	}
	public String getUpdateSql() {
		return updateSql;
	}
	@Autowired
	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}
	public String getDeleteSql() {
		return deleteSql;
	}
	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}
	/* <p>DataSource injection function. </p>
	 * JdbcTemplate will receive Microsoft SQL Server data source here.
	 * 
	 */
	@Autowired//parameter name must meet with xml id name
	public void setMssqlPara(DataSource mssqlPara) {
		this.mssqlPara = mssqlPara;
		this.jdbctemplate= new JdbcTemplate(mssqlPara);
	}
	public boolean isDeleteSwitch() {
		return deleteSwitch;
	}
	public void setDeleteSwitch(boolean deleteSwitch) {
		this.deleteSwitch = deleteSwitch;
	}

	public boolean isQuerySwitch() {
		return querySwitch;
	}

	public void setQuerySwitch(boolean querySwitch) {
		this.querySwitch = querySwitch;
	}
	public boolean isDbInsertSwitch() {
		return dbInsertSwitch;
	}

	public void setDbInsertSwitch(boolean dbInsertSwitch) {
		this.dbInsertSwitch = dbInsertSwitch;
	}
	 
		
}
