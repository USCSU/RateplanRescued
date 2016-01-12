package com.five9.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.five9.model.Conn;
import com.five9.model.DatabaseConnection;


public class LsVccCmpConn extends DatabaseConnection implements Conn {
	@Autowired
	private DataSource mysqlPara;
//	@Autowired
//	@Qualifier(value = "lsmysqlmimic")
//	private LsMysqlMimicConn ls;
//	@Autowired
//	@Qualifier(value = "zuoramysql")
//	private ZuoraMysqlConn zuora;
//	@Autowired
//	@Qualifier(value = "vccmysql")
//	private VccMysqlConn vcc;
	
	
	private boolean exportSwitch;
	private boolean updateDBSwitch;
	private List<List<String>> data;
	@Override
	public void moniter() {
		// TODO Auto-generated method stub
		System.out.println("This is echo for ls vcc monitering process of comparison");
		if(this.deleteSwitch) {
			System.out.println("	deleting.....");
			this.delete(this.deleteSql);
			System.out.println("	Deletion done!");
		}
		if(this.dbInsertSwitch){
			System.out.println("	inserting.....");
//			ls.moniter();
//			vcc.moniter();
//			zuora.moniter();
			System.out.println("	Insertion done!");
			
		}
		if(this.querySwitch){
			System.out.println("	Querying data....");
			this.query(this.querySql);
			System.out.println("	Querying done!");
		}
		if(this.updateDBSwitch){
			System.out.println("	inserting data .....");
//			batchInsertion();
			update(updateSql);
			System.out.println("	Insertion done!.....");
		}
		if(this.exportSwitch){
			System.out.println("	Exporting data to file .....");
			exportToFile(path,data);	
			System.out.println("	Exporting done!.....");
		}
	}
	public void exportToFile(String path, List<List<String>> data){
		if(data==null || data.isEmpty()) throw new IllegalArgumentException("data not found or data is empty(Data comparison statge)....");
		ReadWriteCSV_XLSX.writetoFile(path, data);
	}
	@Override
	public void update(String updateSql){
		this.jdbctemplate.update(updateSql);
	}
	@Override
	public void batchInsertion(List<List<String>> data, String batchInsertionSql){
		this.jdbctemplate.batchUpdate(batchInsertionSql, new BatchPreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException{
				List<String> value = data.get(i);
				for(int index =0;index<value.size();index++)
					ps.setString(index+1, value.get(index));
			}
			@Override
			public int getBatchSize(){
				return data.size();
			}
		});
	}
	@Override
	public void query(String querySql){
		data = this.jdbctemplate.query(querySql, new RowMapper<List<String>>(){
            public List<String> mapRow(ResultSet rs, int rowNum) 
                                         throws SQLException {
            		List<String> ret = new LinkedList<String>();
//            		for(int i =1;i<=rs.getFetchSize();i++)
            			ret.add(rs.getString(1));
            			ret.add(rs.getString(2));
            			ret.add(rs.getString(3));
            			ret.add(rs.getString(4));
            		return ret;
            }
       });
	}
	
	
	//setters and getters
	public DataSource getMysqlPara() {
		return mysqlPara;
	}
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isDeleteSwitch() {
		return deleteSwitch;
	}
	public void setDeleteSwitch(boolean deleteSwitch) {
		this.deleteSwitch = deleteSwitch;
	}
	public boolean isExportSwitch() {
		return exportSwitch;
	}
	public void setExportSwitch(boolean exportSwitch) {
		this.exportSwitch = exportSwitch;
	}
	public boolean isUpdateDBSwitch() {
		return updateDBSwitch;
	}
	public void setUpdateDBSwitch(boolean updateDBSwitch) {
		this.updateDBSwitch = updateDBSwitch;
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
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public String getBatchInsertionSql() {
		return batchInsertionSql;
	}
	public void setBatchInsertionSql(String batchInsertionSql) {
		this.batchInsertionSql = batchInsertionSql;
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
