package lxy.test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import lxy.verificationeParser.ParseVerificationeEntry;

public class Test {
	public static void main(String[] args) {
		
		String pngPath = "C:\\Users\\LINI8\\Desktop\\khb";
		Date dateBegin = new Date();
		File[] array = getFile(pngPath);
		ParseVerificationeEntry en = new ParseVerificationeEntry();
		int countSuccess = 0;
		int countActual = 0;
		for (int j = 0; j < array.length; j++) {
			String key = array[j].getPath().substring(array[j].getPath().indexOf("archive")+8,array[j].getPath().length());
			key = key.substring(0,5);
			String charts = en.getCharts(array[j].getPath());
			System.out.println(charts);
			System.out.println("progress bar：" + (j +1)  +"/" +array.length);
			if(charts.length()==5){
				if(charts.equals(key)){
					System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
					countActual++;
				}else{
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
				countSuccess++;
			}
			
		}

		System.out.println(" countSuccess = "+ countSuccess);
		System.out.println(" countActual  = "+ countActual);
		Date dateEnd = new Date();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try{
			Date d2 = df.parse( df.format(dateBegin));
			Date d1 = df.parse( df.format(dateEnd));

			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
		
			System.out.println("end time" + d1);
			System.out.println("start time" + d2);
			System.out.println(diff/1000 + "s");
		}catch (Exception e){

		}

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
