package com.cargosmart.common.pdfVersion20160308;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * initialize config properties , if config not exist
 * @author LINI8
 *
 */
public class InitializeConfig {
	public void initializeConfig(){
		String filePath = (System.getProperty("user.dir") + "/").replace("\\", "/");  
		File file = new File(filePath,"PdfImgConfig.properties");
		if (!file.exists()) {
			try {
				System.out.println("config not exist , Initialize Config in : " + file.getPath());
				writeConfig();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writeConfig() throws Exception{
		//input stream
		InputStream configpath =  Object.class.getResourceAsStream("/com/cargosmart/common/pdfVersion20160308/PdfConfig.properties");
		InputStreamReader isr = new InputStreamReader(configpath);    
		BufferedReader br = new BufferedReader(isr); 
		
		//outpu Stream
		String filePath = (System.getProperty("user.dir") + "/").replace("\\", "/");  
		File file = new File(filePath,"PdfImgConfig.properties");
		file.createNewFile();
		OutputStream outconfigpath = new FileOutputStream(file);
		OutputStreamWriter write = new OutputStreamWriter(outconfigpath,"UTF-8");;
		BufferedWriter  bw = new BufferedWriter(write);;

		
		String buff;
		try {
			buff = br.readLine();
			StringBuffer sb = new StringBuffer();
			while(buff!=null){
				sb.append(buff+"\r\n");
				buff = br.readLine();
			}
			
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				configpath.close();
				isr.close();
				write.close();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
