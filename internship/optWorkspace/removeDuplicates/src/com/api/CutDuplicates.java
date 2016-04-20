package com.api;

import java.io.File;
import java.io.IOException;

public class CutDuplicates {
	public void cut(String path,String type,int num){
//		String path  = "C:\\Users\\LINI8\\Desktop\\test"+"\\";
		Remove re = new Remove();
		File[] array = re.getFile(path);
		
		for(int i=0;i<array.length;i++){   
	         if(array[i].isFile()){   
	        	 String name = array[i].getName(); 
	        	 try {
					re.replace(path+name,type,num);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         } 
	     }
		System.out.println("All Finish");   
	}
	
}
