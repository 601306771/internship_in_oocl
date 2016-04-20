package lxy.ocrHandle;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public class EigenTest {
	public static void main(String[] args) {
		String pngPath = "C:\\Users\\LINI8\\Desktop\\repository3";
//		Map<Double,String> k_v = new TreeMap<Double,String>();
		File[] array = getFile(pngPath);
		CosineCompareTest ct = new CosineCompareTest();
		for(int i=0 ; i<array.length ; i++){
			for(int j=0 ; j<array.length ; j++){
//			String result = handle(array[i].getPath());
//			k_v.put(Double.parseDouble(result.split(",")[1]), result.split(",")[0]);
				ct.compare(array[i].getPath(),array[j].getPath());
			}
		}
		
		
//		for(Double key : k_v.keySet()){
//			System.out.println(key + " ---- " +k_v.get(key));
//		}
	}
	
	public static String handle(String imgFile){
		Entry en = new Entry();
		double value = en.getEigenValue(imgFile);
		String key = imgFile.substring(imgFile.indexOf("repository")+11,imgFile.length());
		
		String result = key + "," + value;
		return result;
//		System.out.println(key + " ==== " +value);
	}
	
	
	public static  File[] getFile(String path){   
        // get file list where the path has   
        File file = new File(path);   
        // get the folder list   
        File[] array = file.listFiles();   
          
        return array;
    }   
	                   
}
