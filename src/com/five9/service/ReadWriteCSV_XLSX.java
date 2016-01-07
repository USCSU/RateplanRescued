package com.five9.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadWriteCSV_XLSX {
	public static void appendToFile(String path, List<List<String>> data){
		 
		try{
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));
		HSSFSheet sheet = workbook.getSheetAt(0);
		int num = sheet.getLastRowNum()+2;
		Row title = sheet.createRow(++num);
		title.createCell(0).setCellValue("accounts");
		title.createCell(1).setCellValue("pbe");
		title.createCell(2).setCellValue("ls");
		
		for(int i =0;i<data.size();i++){
			 Row rowtemp = sheet.createRow(++num);
			 for(int j =0;j<data.get(i).size();j++){
				 rowtemp.createCell(j).setCellValue(data.get(i).get(j));
			 }
		}
		  
		FileOutputStream out = new FileOutputStream(path);
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	public static void writetoFile(String path, List<List<String>> data){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Rateplan Analysis");
		//set up title
		 Row row = sheet.createRow(0);
		 row.createCell(0).setCellValue("accounts");;
		 row.createCell(1).setCellValue("Vcc");;
		 row.createCell(2).setCellValue("Logisense");
		 
		 //set up content
		 for(int i =0;i<data.size();i++){
			 Row rowtemp = sheet.createRow(i+1);
			 for(int j =0;j<data.get(i).size();j++){
				 rowtemp.createCell(j).setCellValue(data.get(i).get(j));
			 }
		 }
		 
		 
		try {
		    FileOutputStream out = 
		            new FileOutputStream(new File(path));
		    workbook.write(out);
		    out.close();
		    System.out.println("Excel written successfully..");
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	public static Map<String,String> savaToMap(String path){
		if(path == null || path.isEmpty()) throw new IllegalArgumentException("Parameter is null or empty!");
		String[] resolver = path.split("\\.");
		String suffix = resolver[resolver.length-1];
		if(suffix.equals("csv"))
			return getResultCSV(path);
		else if(suffix.equals("xlsx"))
			return getResultXLSX(path);
		else throw new IllegalArgumentException("Parameter is not csv or xlsx");
	}
	private static Map<String,String> getResultCSV(String path){
		Map<String,String> ret = new HashMap<String,String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = "";
			boolean firstRow = true;
			while((line = br.readLine())!=null){
				if(firstRow){firstRow=!firstRow;continue;}
				//format strings 
				String[] cols = line.split(",");
				String tempCol = cols[0].replace("\"", "");
				String col1 = tempCol.contains("-")?tempCol.substring(tempCol.indexOf("-")+1):tempCol;
				String col2 = cols[1].replace("\"", "");
				
				ret.put(col1,col2);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		} 
		return ret;
	}
	private static Map<String,String> getResultXLSX(String path){
		//Blank workbook
		Map<String,String> ret = new HashMap<String,String>();
		try {
			XSSFWorkbook workbook;
			workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(path));
		
			XSSFSheet sheet = workbook.getSheetAt(0);
        
//			for(Row row:sheet){
			//starts from 2nd row since 1st row is title
			for(int i =1;i<sheet.getLastRowNum();i++){
				DataFormatter formatter = new DataFormatter();
				ret.put(formatter.formatCellValue(sheet.getRow(i).getCell(0)),formatter.formatCellValue(sheet.getRow(i).getCell(1)));
			}
        
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	 
}
