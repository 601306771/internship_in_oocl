package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;









import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import com.api.Remove;

public class copyNametest {
	private static SXSSFWorkbook wb = new SXSSFWorkbook(100); 
	private static Sheet sh ;
	private static Remove re = new Remove();
	
	public static void main(String[] agrs){
		String file1 = "IESFile";
		String file2 = "OB2BFile";
		String path  = "C:/SVN/Regression/ParaRun/SYDPORT/OUT/IFCSUM"+"/";
		
//		copyFileNameSize(path,file1);
//		copyFileNameSize(path,file2);
		
		
		copyFileNameSort(path,file1);
		copyFileNameSort(path,file2);
		
		
		outPutPath(path,"copyNames");
	   
		
		
		
		 
	    
	}
	
	public static long compareMap(Map map,long size) {
		if(map.containsKey(size)){
			size = size + 1;
			return compareMap(map,size);
		}
		return size;
	}
	
	public static void copyFileNameSize(String path,String file){
		File[] array = re.getFile(path+file);
		sh = wb.createSheet(file);
		Map<Long,String> map = new TreeMap<Long,String>();
	    int countRow = 0;
	    long temp = 0;
		for(int i=0;i<array.length;i++){   
	         if(array[i].isFile()){   
	        	 String name = array[i].getName(); 
	        	 long size = fileSize(path+file+"/",name);
	        	 System.out.println(size);
//	        	 size = compareMap(map,size);
	        	 map.put(size, name);
	         }else{
	        	 System.out.println("Missing:--"+array[i].getName());
			 }
	         
	     }
		System.out.println("array.length--"+array.length);
		System.out.println(" len ="+map.size());
		for(Long key : map.keySet()){
			String name = map.get(key);
			Row row = sh.createRow(countRow);
   	     	row.createCell(0).setCellValue(name);
   	     	countRow++;
		}
		
		
		System.out.println("----------one handle finish-------------");
	}
	
	public static void outPutPath(String path,String file){
		 FileOutputStream os;
		 try {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			 String filePath = path+file+sdf.format(new java.util.Date())+".xlsx";
			 os = new FileOutputStream(filePath);
			 wb.write(os);
			 os.close();
		}catch (FileNotFoundException e) {
		     e.printStackTrace();
		} catch (IOException e) {
			 e.printStackTrace();
		}  
	}
	
	public static void copyFileNameSort(String path,String file){
		File[] array = re.getFile(path+file);
		sh = wb.createSheet(file);
	    int countRow = 0;
		for(int i=0;i<array.length;i++){   
	         if(array[i].isFile()){   
	        	 String name = array[i].getName(); 
	        	 Row row = sh.createRow(countRow);
	    	     row.createCell(0).setCellValue(name);
	    	     countRow++;
	         }else{
	        	 System.out.println("Missing:--"+array[i].getName());
			 }
	         
	     }
		System.out.println("----------one handle finish-------------");
	}
	
	public static long fileSize(String path,String name) {  
	    File f= new File(path+name);  
	    if (f.exists() && f.isFile()){  
	        return(f.length());  
	    }else{  
	        System.out.println("fail");
	    }  
	    return 0;
	} 
	
	
	
}
