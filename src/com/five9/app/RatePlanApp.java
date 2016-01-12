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
		DBConnection pbe  = (DBConnection) ctx.getBean("pbe");
		pbe.moniter();
		DBConnection vcc  = (DBConnection) ctx.getBean("vcc");
		vcc.moniter();
		DBConnection ls  = (DBConnection) ctx.getBean("ls");
		ls.moniter();
		DBConnection zuora  = (DBConnection) ctx.getBean("zuora");
		zuora.moniter();
		
		
		DBConnection lsvcc  = (DBConnection) ctx.getBean("lsvcccmp");
		lsvcc.moniter();
		DBConnection lspbe  = (DBConnection) ctx.getBean("lspbecmp");
		lspbe.moniter();
		}
	}
}
