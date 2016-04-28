package b2b.robot.repositoryHandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;




public class PasteRepositoryConfigToProperties {
	public static String IMG_PATH = "C:/Users/LINI8/Desktop/MCCQrepository";
	public static String PROPERTIES_PATH  = (System.getProperty("user.dir") + "/").replace("\\", "/") + "/MCCQrepositoryConfig.properties";
	
	public static void main(String[] args) throws IOException{
		//
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(PROPERTIES_PATH),"GB2312");
		BufferedWriter  bw = new   BufferedWriter(write);
		
		GetRepositoryConfigFromImg getConfig = new GetRepositoryConfigFromImg();
		StringBuffer result = new StringBuffer();
		
		File[] array = getFile(IMG_PATH);
		for (int j = 0; j < array.length; j++) {
			String recode =   getConfig.getRepositoryConfig(array[j].getPath());
			result.append(recode + "\r\n");
		}
		
		
		bw.write(result.toString());
		bw.close();
		
	}
	
	/**
	 * read files from forder
	 * 
	 * @param path
	 * @return
	 */
	public static File[] getFile(String path) {
		// get file list where the path has
		File file = new File(path);
		// get the folder list
		File[] array = file.listFiles();
		return array;
	}
}
