package nigel;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lxy.verificationeParser.ParseVerificationeEntry;

public class test {
	public static void main(String[] args){
		Date dateBegin = new Date();
		
		
		
			String pngPath = "C:\\Users\\LINI8\\Desktop\\ddd\\0a02a281-2590-4706-be7b-b91e55b3629b.png";
			ParseVerificationeEntry en = new ParseVerificationeEntry();
			String charts = en.getCharts(pngPath);
			System.out.println(charts);
			
			Date dateEnd = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try{
				Date d2 = df.parse( df.format(dateBegin));
				Date d1 = df.parse( df.format(dateEnd));
				long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
				System.out.println(d1);
				System.out.println(d2);
				System.out.println(diff);
			}catch (Exception e){

			}
	}
}
