package com.api;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class Remove {
	
	
	public  void replace(String path,String type,int num) throws IOException{


		BufferedReader countbr = new BufferedReader(new FileReader(path));
		String  temp = null;
		int count = 0;
		while((temp=countbr.readLine())!=null){
			if(temp.trim()!=null&&!temp.equals("")){
				count++;
			}
			
		}
		countbr.close();
		
		InputStreamReader read = new InputStreamReader(new FileInputStream(path),"GB2312");//使用io字节流，指定encoding
		BufferedReader br = new BufferedReader(read);
		StringBuffer sb=new StringBuffer();
		int start = 0;
		if("begin".equals(type)){
			start = num;//去掉开头行数
		}else if("removeCopy".endsWith(type)){
			int times = num;//复制次数
			start = (count/times)/(times-1);//复制次数
		}
		
	
		int line = 1;
		while((temp=br.readLine())!=null){
			if(line > start){
		        sb.append(temp).append( "\n");
			}
			line++;
		}
		br.close();
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(path),"GB2312");
		BufferedWriter  bw=new   BufferedWriter(write);
		bw.write(sb.toString());
		bw.close();
		
	}
	
	
	public  File[] getFile(String path){   
        // get file list where the path has   
        File file = new File(path);   
        // get the folder list   
        File[] array = file.listFiles();   
          
        return array;
    }   
}
