package com.five9.service;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.five9.model.Conn;
import com.five9.model.DatabaseConnection;
/* <p>Provide connection to mysql database and all the needed operations</p>
 * DataSource will be wired from xml configuration file. 
 * JdbcTemplate is used to do all the operations on database;
 * 
 */
public class VccMysqlConn extends DatabaseConnection implements Conn{
	 
	private DataSource mysqlPara; 
	/* <p>Function to moniter connection progress</p>
	 */
	@Override
	public void moniter(){
		System.out.println();
		System.out.println("Echo from vcc mysql server.......");
//		update();
		if(deleteSwitch)
			this.delete();
		readCSV_XLSX();
		importDataToDB(csv_xlsx);
		if(this.querySwitch)
			query();
	}
	
	protected void readCSV_XLSX(){
		 System.out.println("Reading data from vcc and pbe csv/xlsx file....");
		 //static function here 
		 csv_xlsx =  ReadWriteCSV_XLSX.savaToMap(path);
		 System.out.println("Done reading file.");
	 }
	
	
	
	
	/* setters and getters */
	public String getBatchInsertionSql() {
		return batchInsertionSql;
	}
//	@Autowired
	public void setBatchInsertionSql(String batchInsertionSql) {
		this.batchInsertionSql = batchInsertionSql;
	}
	public String getUpdateSql() {
		return updateSql;
	}
	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}
	public String getQuerySql() {
		return querySql;
	}
//	@Value(value = "select * from student")
//	@Autowired
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public DataSource getMysqlPara() {
		return mysqlPara;
	}
	/* <p>DataSource injection function.</p> 
	 * JdbcTemplate will receive mysql data source here.
	 * 
	 */
	@Autowired(required = false)//parameter name must meet with xml id name
	public void setMysqlPara(DataSource mysqlPara) {
		this.mysqlPara = mysqlPara;
		this.jdbctemplate = new JdbcTemplate(mysqlPara);
	}
	public String getPath() {
		return path;
	}
	@Autowired(required = false)
	public void setPath(String path) {
		this.path = path;
	}
	public String getDeleteSql() {
		return deleteSql;
	}
	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
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

	 

 
}
