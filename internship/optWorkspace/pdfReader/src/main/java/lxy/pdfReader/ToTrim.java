package lxy.pdfReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToTrim {
	public static void main(String[] args){
		String path1 = "C:\\Users\\LINI8\\Desktop\\pdfRead\\"+"1.txt";
		String path2 = "C:\\Users\\LINI8\\Desktop\\pdfRead\\"+"2.txt";
		String str1 = null;
		String str2 = null;
		try {
			str1 = readTxt(path1).toString().replaceAll("#", "");
			str2 = readTxt(path2).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    str1 = replaceBlank(str1);
		System.out.println(str1);
		
		str2 = replaceBlank(str2);
		System.out.println(str2);
		
		if(str1.equals(str2)){
			System.out.println("the same!");
		}else{
			System.out.println("not the same!");
		}
	}
	
	public static StringBuffer readTxt(String path) throws IOException {
		InputStreamReader read = new InputStreamReader(new FileInputStream(path), "GB2312");// 使用io字节�?，指定encoding
		BufferedReader br = new BufferedReader(read);
		StringBuffer sb = new StringBuffer();
		String temp = null;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		return sb;
	}
	
	public static String replaceBlank(String str) {
		  String dest = "";
		  if (str!=null) {
		   Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		   Matcher m = p.matcher(str);
		   dest = m.replaceAll("");
		  }
		  return dest;
		 }
}
