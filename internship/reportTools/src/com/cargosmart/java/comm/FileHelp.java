package com.cargosmart.java.comm;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class FileHelp {

//	public String GetObject(Object json){
//		Object object = json.getAt("result");
//		return object;
//	}
 
	
	public static String readBigFile(String path) throws IOException{

		FileInputStream is = new FileInputStream(path);       
		InputStreamReader isr = new InputStreamReader(is);    
		BufferedReader br = new BufferedReader(isr);      
		String buff = br.readLine();
		StringBuffer sb = new StringBuffer();
		while(buff!=null){
			sb.append(buff);
			buff = br.readLine();
		}
		String arr=sb.toString().trim().replaceAll("}}", "}},"); 
		String arr2 = "[" + arr + "]";
		String arr3 = arr2.replaceAll(",]", "]");
		System.out.println( arr3);
		System.out.println("Reading file is  completed.");
		return arr3;
	}
	
	
	public ArrayList<ArrayList> readBigFileCSV(String path) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		reader.readLine();
		String line = null;
		ArrayList<ArrayList> totalList=new ArrayList<ArrayList>();
		while((line=reader.readLine())!=null){
			String str=line;
            String r = null;
            String s="";
      
    		List<String> rs =new ArrayList<String>();
            if(str!=null){
            	r=str.replace(", ", "$");
            }
			if(r!=null){
				r=r.replace(".,","##");
			}
			String[] item;
            if(r!=null){
            	item=r.split(",");
					    
            	for(int i=0;i<item.length;i++){   			
        			if(item[i].startsWith("\"")&&!item[i].endsWith("\"")){
        				s=item[i]+","+item[i+1];
        				rs.add(s);
        			}
        			else if(!item[i].startsWith("\"")&&item[i].endsWith("\"")){
						     				
        			}
        			else{
        				s=item[i];
        				rs.add(s);
        			}			       			
        		}

            	for(int k=0;k<rs.size();k++){
            		if(rs.get(k).contains("$")){
            			rs.set(k,rs.get(k).replace("$", ", "));
            		}
					if(rs.get(k).contains("##")){
						rs.set(k,rs.get(k).replace("##", ".,"));
					}
					if(rs.get(k).contains("\"")){
						rs.set(k,rs.get(k).replace("\"", ""));
					}					
			     String pattern = "[0-9]+/[0-9]+(.[0-9])?/.*";
				 Pattern p = Pattern.compile(pattern);   
			     Matcher m = p.matcher(rs.get(k));
				 boolean b = m.matches();
				 if(b){
					   SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy KK:mm");
						Date date = null;
						try {
						 date = format.parse(rs.get(k));
						} catch (ParseException e) {
						 e.printStackTrace();
						}
						SimpleDateFormat f=new SimpleDateFormat("MMM dd, yyyy KK:mm:ss a");
						String dt=f.format(date);
						rs.set(k,dt);
					}							
            	}   				
				ArrayList<String> list=new ArrayList<String>();
				
				for(int i=0;i<rs.size();i++){
					list.add(i, rs.get(i));
				}
				totalList.add(list);
            }
		}		
		return totalList;
	}
	
	public static void renameFile(String path,String oldname,String newname){
		if(!oldname.equals(newname)){
			File oldfile=new File(path+"/"+oldname);
			File newfile=new File(path+"/"+newname);
			if(!oldfile.exists()){
				return;
			}
			if(newfile.exists())
				System.out.println(newname+" areadly exist");
			else{
				oldfile.renameTo(newfile);
			}
		}else{
			System.out.println("...");
		}
	}
	
	
	
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {		
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
		
			outBuff.flush();
		} finally {
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}
	
	
	public static void createFolder(String path){
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
	}

}
