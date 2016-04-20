package unitTest;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import b2b.robot.controller.VerificationController;

public class MccqTestMany {
	public static void main(String[] args) {
		String folderName = "MAEU";
		String pngPath = "C:\\Users\\LINI8\\Desktop\\" + folderName;
		
		Date dateBegin = new Date();
		File[] array = getFile(pngPath);
		
		VerificationController vc = new VerificationController();
		int countSuccess = 0;
		int countActual = 0;
		for (int j = 0; j < array.length; j++) {
			System.out.println(array[j].getPath());
			//get image chart 
			String key = array[j].getPath().substring(array[j].getPath().indexOf(folderName)+folderName.length()+1,array[j].getPath().length());
			key = key.substring(0,5);
			
			//get parser chat
			String charts = vc.getCharts(array[j].getPath());
			System.out.println(charts);
			System.out.println("progress bar：" + (j +1)  +"/" +array.length);
			
			//compare chats
			if(charts.length()==5){
				if(charts.trim().equals(key.trim())){
					System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
					countActual++;
				}else{
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
				countSuccess++;
			}
			System.out.println();
			System.out.println();
		}

		//print informations
		Date dateEnd = new Date();
		System.out.println(" countSuccess = "+ countSuccess);
		System.out.println(" countActual  = "+ countActual);
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
