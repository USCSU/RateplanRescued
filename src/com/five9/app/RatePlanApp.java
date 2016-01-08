package com.five9.app;

/*
 *  <p>Main function;</p> 
 *  Entrance to whole app
 */
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.five9.consumer.DBConnection;;
public class RatePlanApp {
	public static void main(String[] args){
		try(ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml")){
//		DBConnection mysqlConn  = (DBConnection) ctx.getBean("mysql");
//		mysqlConn.moniter();
//		DBConnection mssalMimic  = (DBConnection) ctx.getBean("mssqlmimic");
//		mssalMimic.moniter();
//		DBConnection pbe  = (DBConnection) ctx.getBean("pbemimic");
//		pbe.moniter();
//		DBConnection zuora  = (DBConnection) ctx.getBean("zuora");
//		zuora.moniter();
		
		DBConnection lsvcccmp  = (DBConnection) ctx.getBean("lsvcccmp");
		lsvcccmp.moniter();
		
		DBConnection lspbecmp  = (DBConnection) ctx.getBean("lspbecmp");
		lspbecmp.moniter();
//		
		}
	}
}
