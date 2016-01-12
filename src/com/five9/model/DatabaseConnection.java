package com.five9.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class DatabaseConnection {
	
	protected JdbcTemplate jdbctemplate;
	protected String updateSql;
	protected String querySql;
	protected String batchInsertionSql;
	protected String path;
	protected String deleteSql;
	protected boolean deleteSwitch;
	protected boolean querySwitch;
	protected boolean dbInsertSwitch;
	protected Map<String,String> csv_xlsx;
	
	public void update(String updateSql){
		this.jdbctemplate.update(updateSql);
	}
	public void delete(String deleteSql){
		jdbctemplate.update(deleteSql);
	}
	public void query(String querySql){
		List<List<String>> data = this.jdbctemplate.query(querySql, new RowMapper<List<String>>(){
            public List<String> mapRow(ResultSet rs, int rowNum) 
                                         throws SQLException {
            		List<String> ret = new LinkedList<String>();
            		ret.add(rs.getString(1));
            		ret.add(rs.getString(2));
            		return ret;
            }
       });
		System.out.println(data);
	}
	
	protected void batchInsertion(List<List<String>> data, String batchInsertionSql){
		if(data == null || data.isEmpty()) throw new IllegalArgumentException("data is not valid when batchinsertion list");
		this.jdbctemplate.batchUpdate(batchInsertionSql, new BatchPreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException{
				List<String> record = data.get(i);
				ps.setString(1, record.get(0));
				ps.setString(2, record.get(1).isEmpty()?"NOT FOUND":record.get(1));
			}
			@Override
			public int getBatchSize(){
				return data.size();
			}
		});
	}
	protected void batchInsertion(Map<String,String> data,String batchInsertionSql){
		this.jdbctemplate.batchUpdate(batchInsertionSql, new BatchPreparedStatementSetter(){
			Object[] keys = data.keySet().toArray();
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException{
				String key = (String)keys[i];
				ps.setString(1, key);
				ps.setString(2, data.get(key));
			}
			@Override
			public int getBatchSize(){
				return data.size();
			}
		});
	}
	protected void importDataToDB(Map<String,String> datasource){
		if(datasource==null || datasource.isEmpty()) throw new IllegalArgumentException("No data from csv file!");
		 System.out.println("Saving data to database....");
		 batchInsertion(datasource, batchInsertionSql);
		 System.out.println("saving data done!");
	}
	protected void importDataToDB(List<List<String>> datasource){
		if(datasource==null || datasource.isEmpty()) throw new IllegalArgumentException("No data from csv file!");
		 System.out.println("Saving data to database....");
		 batchInsertion(datasource, batchInsertionSql);
		 System.out.println("saving data done!");
	}
	 protected void readCSV_XLSX(){
		 
	 }
	
}
