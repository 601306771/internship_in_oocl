package com.cargosmart.java.api;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

import com.cargosmart.java.comm.PropertiesHandlers;

public class Options {
	
	public void changeFileName(StringBuffer newFileNames){
		String str = newFileNames.toString();
  		String newFileNamesStr[] = str.split(",");
  		PropertiesHandlers config = new PropertiesHandlers();
    	Map<String,String> oldFileNames = config.getKey_Value();
    	
    	//修改或添加键值对 如果key存在，修改, 反之，添加。
  		for(int i=0;i<newFileNamesStr.length;i++){
  			if(newFileNamesStr[i].trim().equals("")){
  				
  			}else{
  				String configKey = "cvsName"  +  newFileNamesStr[i].trim();
  				config.writeData("/CvsNames.properties", configKey, newFileNamesStr[i].trim());
  			}
  		}
    	//删除无效的老键值对
    	for(String key : oldFileNames.keySet()){
    		String old = oldFileNames.get(key);
    		boolean delete = true; 
    		for(int i=0;i<newFileNamesStr.length;i++){
      			if(old.trim().equals(newFileNamesStr[i].trim())){//if exist 
      				delete = false;
      			}
      		}
    		//delete change name
    		if(delete){
    			config.delete("CvsNames.properties",key.trim());
    		}
    	}
    	
    	
	}
	
}
