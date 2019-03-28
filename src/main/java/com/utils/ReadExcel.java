package com.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel extends TestBase{
	
	XSSFSheet sheet;
	static XSSFSheet modelsheet;
	static XSSFRow row = null;
	static XSSFWorkbook workbook;
	static String excelpath = "C:\\Excelfiles\\DocPrime\\20190306_Visits_Path.xlsx";
	static int rowCount;
	static String baseurl = "https://docprime.com";
	
	public static void read(){
		
		try{
			FileInputStream fis = new FileInputStream(excelpath);
			workbook = new XSSFWorkbook(fis);
			modelsheet = workbook.getSheetAt(0);
			
			row = modelsheet.getRow(0);

			int colCount = row.getLastCellNum();
			System.out.println("Column Count :- " + colCount);

			rowCount = modelsheet.getLastRowNum() + 1;
			System.out.println("Row Count :- " + rowCount);
			 List<String> visitids = new ArrayList<String>();   
			
			for(int i =1;i<rowCount;i++){
				
				 XSSFRow row1 = modelsheet.getRow(i);
				 XSSFCell cell_11 = row1.getCell(0);
	             
	             String visitid = modelsheet.getRow(i).getCell(0).getRichStringCellValue().toString();
	             
	             Set<String> set = new HashSet<String>(visitids);
	             if (set.contains(visitid))
	             {
	               System.out.println("String found!");
	               break;
	             }
	             
	             visitids.add(cell_11.getRichStringCellValue().toString());  
	             List<String> urls = new ArrayList<String>();
	             for(int j = 1;j<rowCount;j++){
	            	 
	            	 XSSFRow row2 = modelsheet.getRow(j);
	            	 XSSFCell cell_1 = row2.getCell(0);
		             XSSFCell cell_2 = row2.getCell(2);
	            	 if(!(cell_1.getRichStringCellValue().toString().equalsIgnoreCase(visitid))){
		            	 
	 	            	continue;
	 	             }else{
	 	            	 urls.add(cell_2.getRichStringCellValue().toString());
	 	             } 
	             }
	                                   		
			
			System.out.println("Size of the visitids: "+visitids.size());
			System.out.println("Size of the urls: "+urls.size());
			
			String idvalue = visitids.get(i-1);
			System.out.println("the visit id is " + idvalue);
	        
	       Iterator<String> itr = urls.iterator();
	        while(itr.hasNext()){
	            System.out.println("url values: "+itr.next());
	            
	        }
	        	String url = "";
	        for (int k = 0; k<urls.size();k++){
	        	String newidvalue = "";
	        	url = urls.get(k);
	            
	          baseurl = baseurl +url;
	            
	         // System.out.println("the url for screenshot is " + baseurl);
	          driver.get(baseurl);     
	          newidvalue = idvalue +"_"+k;
	          //System.out.println("final idvalue " + newidvalue);
	          Screenshot.takescreenshot(newidvalue);	                    
	          
	            
	        }
	        
	        CombineMultipleImages.joinimages();
			
			}
			}catch(Exception e){
				
				e.printStackTrace();
			
		}
	}

}
