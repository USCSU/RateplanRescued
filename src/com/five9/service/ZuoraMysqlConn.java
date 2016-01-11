package com.five9.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.five9.model.Conn;
import com.five9.model.DatabaseConnection;

public class ZuoraMysqlConn extends DatabaseConnection implements Conn{
	private DataSource mysqlPara; 
	private String inputpath;
	private String outputpath;
	
	/* <p>Function to moniter connection progress</p>
	 */
	@Override
	public void moniter(){
		System.out.println();
		System.out.println("Echo from zuora mysql server.......");
//		update();
		if(deleteSwitch)
			this.delete();
		
		readCSV_XLSX();
		importDataToDB(outputpath);
		if(this.querySwitch)
			query();
	}
	
	protected void readCSV_XLSX(){
		 System.out.println("Reading data from zuora csv/xlsx file....");
		 ReadWriteCSV_XLSX.rewriteZuora(inputpath,outputpath);
		 //static function here 
		 csv_xlsx =  ReadWriteCSV_XLSX.savaToMap(outputpath);
		 System.out.println("Done reading file.");
	 }
	
	
	
	
	/* setters and getters */
	
	public String getBatchInsertionSql() {
		return batchInsertionSql;
	}
	public String getInputpath() {
		return inputpath;
	}
	public void setInputpath(String inputpath) {
		this.inputpath = inputpath;
	}

	public String getOutputpath() {
		return outputpath;
	}
	public void setOutputpath(String outputpath) {
		this.outputpath = outputpath;
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
