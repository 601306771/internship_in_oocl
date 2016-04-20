package lxy.ocrHandle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;





import com.google.common.io.Files;

public class OcrTest {
	public static void main(String[] args) {
		
//		String pngPath777 = "C:\\Users\\LINI8\\Desktop\\ORC\\777.png";
//		String pngPath888 = "C:\\Users\\LINI8\\Desktop\\ORC\\888.png";
		OcrController oc = new OcrController();
//		oc.getCharImgs(pngPath777);
//		oc.getCharImgs(pngPath888);
		
		
		
//		String pngPatha = "C:\\Users\\LINI8\\Desktop\\ddd\\08f65108-089c-41b9-96e4-e7c2b03f00d3.png";
//		
//		oc.getCharImgs(pngPatha);
		
		String pngPath = "C:\\Users\\LINI8\\Desktop\\Pics";
		File[] array = getFile(pngPath);
		for(int i=0 ; i<array.length ; i++){
			System.out.println("*****************" + array[i].getPath());
			oc.getCharImgs(array[i].getPath());
		}
		
	}
	
	public static  File[] getFile(String path){   
        // get file list where the path has   
        File file = new File(path);   
        // get the folder list   
        File[] array = file.listFiles();   
          
        return array;
    }   

}
