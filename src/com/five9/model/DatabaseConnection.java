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
	protected Map<String,String> csv_xlsx;
	
	public void update(){
		this.jdbctemplate.update(updateSql);
	}
	public void delete(){
		jdbctemplate.update(deleteSql);
	}
	public void query(){
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
	private void batchInsertion(List<List<String>> data){
		if(data == null || data.isEmpty()) throw new IllegalArgumentException("data is not valid when batchinsertion list");
		this.jdbctemplate.batchUpdate(batchInsertionSql, new BatchPreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException{
				List<String> record = data.get(i);
				ps.setString(1, record.get(0));
				ps.setString(2, record.get(1));
			}
			@Override
			public int getBatchSize(){
				return data.size();
			}
		});
	}
	private void batchInsertion(Map<String,String> data){
		this.jdbctemplate.batchUpdate(batchInsertionSql, new BatchPreparedStatementSetter(){
			Object[] keys = csv_xlsx.keySet().toArray();
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException{
				String key = (String)keys[i];
				ps.setString(1, key);
				ps.setString(2, csv_xlsx.get(key));
			}
			@Override
			public int getBatchSize(){
				return csv_xlsx.size();
			}
		});
	}
	protected void importDataToDB(String path,Map<String,String> datasource){
		if(path == null || path.length() ==0) throw new IllegalArgumentException("Wrong format of path :" + path);
		if(datasource==null || datasource.isEmpty()) throw new IllegalArgumentException("No data from csv file!");
		 System.out.println("Saving data to database....");
		 batchInsertion(datasource);
		 System.out.println("saving data done!");
	}
	protected void importDataToDB(String path,List<List<String>> datasource){
		if(path == null || path.length() ==0) throw new IllegalArgumentException("Wrong format of path :" + path);
		if(datasource==null || datasource.isEmpty()) throw new IllegalArgumentException("No data from csv file!");
		 System.out.println("Saving data to database....");
		 batchInsertion(datasource);
		 System.out.println("saving data done!");
	}
	 protected void readCSV_XLSX(){
		 
	 }
	
}
