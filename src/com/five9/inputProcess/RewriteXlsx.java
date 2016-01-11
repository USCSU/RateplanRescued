package com.five9.inputProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

public class RewriteXlsx {
	@Value (value = "Input/zuora.xlsx")
	String inputpath;
	@Value(value = "Input/zuora.xlsx")
	String outputpath;
	public static void write(String path, List<List<String>> data){
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("sheet1");
			//set up title
			 Row row = sheet.createRow(0);
			 row.createCell(0).setCellValue("accounts");;
			 row.createCell(1).setCellValue("plan");;
			 
			 //set up content
			 for(int i =0;i<data.size();i++){
				 Row rowtemp = sheet.createRow(i+1);
				 for(int j =0;j<data.get(i).size();j++){
					 if(data.get(i).get(j)==null) data.get(i).set(j, "NOT FOUND");
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
	
	public static List<List<String>> read(String inputpath){
			List<List<String>> ret = new LinkedList<List<String>>();
			HashSet<String> checker = new HashSet<String>();
			try {
				XSSFWorkbook workbook;
				workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(inputpath));
			
				XSSFSheet sheet = workbook.getSheetAt(1);
	        
//				for(Row row:sheet){
				//starts from 2nd row since 1st row is title
				for(int i =1;i<sheet.getLastRowNum();i++){
					DataFormatter formatter = new DataFormatter();
					String key = formatter.formatCellValue(sheet.getRow(i).getCell(0));
					String value = formatter.formatCellValue(sheet.getRow(i).getCell(1));
					checker.add(key);
					List<String> list = new LinkedList<String>();
					list.add(key);
					list.add(value);
				}
				
				sheet = workbook.getSheetAt(2);
				for(int i =0;i<sheet.getLastRowNum();i++){
					DataFormatter formatter = new DataFormatter();
					String key = formatter.formatCellValue(sheet.getRow(i).getCell(0));
					String value = formatter.formatCellValue(sheet.getRow(i).getCell(1));
					if(checker.contains(key)) continue;
					List<String> list = new LinkedList<String>();
					list.add(key);
					list.add(value);
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
