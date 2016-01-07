package com.five9.consumer;

import org.springframework.beans.factory.annotation.Autowired;

import com.five9.model.Conn;

/*<p>Dao Impl class. which is used to do actual job after connection with database</p>
 * 
 */
public class DBConnection {
	private Conn connection;
	public void moniter(){
		connection.moniter();
	}
	public Conn getConnection() {
		return connection;
	}
	@Autowired
	public void setConnection(Conn connection) {
		this.connection = connection;
	}
}
