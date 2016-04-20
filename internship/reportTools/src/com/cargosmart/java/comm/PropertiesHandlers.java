package com.cargosmart.java.comm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesHandlers {
	/** 
     * 根据KEY，读取文件对应的值 
     * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties 
     * @param key 键 
     * @return key对应的值 
     */  
    public static String readData(String filePath, String key) {  
        //获取绝对路径  
        filePath = PropertiesHandlers.class.getResource("/" + filePath).toString();  
        //截掉路径的”file:“前缀  
        filePath = filePath.substring(6);  
        Properties props = new Properties();  
        try {  
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));  
            props.load(in);  
            in.close();  
            String value = props.getProperty(key);  
            return value;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    /** 
     * 修改或添加键值对 如果key存在，修改, 反之，添加。 
     * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties 
     * @param key 键 
     * @param value 键对应的值 
     */  
    public static void writeData(String filePath, String key, String value) {  
        //获取绝对路径  
    	filePath = System.getProperty("user.dir") + "/" +filePath;
    	//filePath = System.getProperty("user.dir") + "/bin/config/" + filePath;
        //filePath = PropertiesHandlers.class.getResource("/" + filePath).toString();  
        //截掉路径的”file:/“前缀  
        //filePath = filePath.substring(6);  
        Properties prop = new Properties();  
        try {  
            File file = new File(filePath);  
            if (!file.exists())  
                file.createNewFile();  
            InputStream fis = new FileInputStream(file);  
            prop.load(fis);  
            //一定要在修改值之前关闭fis  
            fis.close();  
            OutputStream fos = new FileOutputStream(filePath);  
            prop.setProperty(key,value);  
            //保存，并加入注释  
            prop.store(fos, "Update '" + key + "' value");  
            fos.close();  
        } catch (IOException e) {  
            System.err.println("Visit " + filePath + " for updating " + value + " value error");  
        }  
    }
  	
  	 /**
     * get file name you except to run
     * @return
     */
  	public Map<Integer,String> getFileName(){
  		Map<Integer,String> cvsNames = new HashMap<Integer,String>();
  		Properties fileNames = new Properties(); 
  		
  		String proFilePath = System.getProperty("user.dir") + "/CvsNames.properties";
  		//String proFilePath = System.getProperty("user.dir") + "/bin/config/CvsNames.properties";
  		try {
  			InputStream fileNamesPath = new BufferedInputStream(new FileInputStream(proFilePath));
  			//InputStream fileNamesPath = PropertiesHandlers.class.getClassLoader().getResourceAsStream("CvsNames.properties");
  			fileNames.load(fileNamesPath); 
  			fileNamesPath.close();
  		} catch (IOException e) { 
  			e.printStackTrace(); 
  		}
  		//int i = 0;
  		StringBuffer temp = new StringBuffer(); 
  		fileNames.keySet();
  		for(Object key : fileNames.keySet()){
  			String realKey = String.valueOf(key);
  			if(realKey.indexOf("cvsName") >= 0){
  				temp.append(fileNames.get(key).toString()+",");
  				//cvsNames.put(i, fileNames.get(key).toString());
  				//i++;
  			}
  		}
  		String str = temp.toString();
  		String spStr[] = str.split(",");
  		Arrays.sort(spStr);
  		for(int k=0;k<spStr.length;k++){
  			cvsNames.put(k,spStr[k]);
  		}
  		return cvsNames;
  	}
  	
  	/**
  	 * get properties key and value
  	 * @return 
  	 */
  	public Map<String, String> getKey_Value(){
  		Map<String,String> cvsNames = new HashMap<String,String>();
  		Properties fileNames = new Properties(); 
  		
  		String proFilePath = System.getProperty("user.dir") + "/CvsNames.properties";
  		//String proFilePath = System.getProperty("user.dir") + "/bin/config/CvsNames.properties";
  		try {
  			InputStream fileNamesPath = new BufferedInputStream(new FileInputStream(proFilePath));
  			//InputStream fileNamesPath = PropertiesHandlers.class.getClassLoader().getResourceAsStream("CvsNames.properties");
  			fileNames.load(fileNamesPath); 
  			fileNamesPath.close();
  		} catch (IOException e) { 
  			e.printStackTrace(); 
  		}
  		//int i = 0;
  		StringBuffer temp = new StringBuffer(); 
  		fileNames.keySet();
  		for(Object key : fileNames.keySet()){
  			cvsNames.put((String) key, fileNames.get(key).toString());
  		}
  		return cvsNames;
  	}
  	
  	public  void  delete(String filePath,String delete_key) {
  		 //获取绝对路径  
        //filePath = PropertiesHandlers.class.getResource("/" + filePath).toString(); 
  		filePath = System.getProperty("user.dir") + "/" + filePath;
        //filePath = System.getProperty("user.dir") + "/bin/config/" + filePath;
        //截掉路径的”file:“前缀  
        //filePath = filePath.substring(6); 
        Map<String,String> toSaveMap = new HashMap<String,String>();
  		Properties fileNames = new Properties(); 
  		InputStream fileNamesPath;
		try {
			fileNamesPath = new BufferedInputStream(new FileInputStream(filePath));
  			fileNames.load(fileNamesPath); 
  			fileNamesPath.close();
	  		fileNames.keySet();
	  		for(Object key : fileNames.keySet()){
	  			toSaveMap.put(key.toString().trim(), fileNames.get(key).toString().trim());
	  		}
	        
	        /**
	         * 删除逻辑：找到对应的key，删除即可
	         */  
	  		fileNames.clear();
	        toSaveMap.remove(delete_key);
	        fileNames.putAll(toSaveMap);
	        OutputStream out = new FileOutputStream(filePath);
	        fileNames.store(out, "==== after remove ====");
	        out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
  	
  	
  	
}