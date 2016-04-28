package nigel.repository;

import java.io.File;


public class Handle {
	public static void main(String[] agrs){
		String pngPath = "C:\\Users\\LINI8\\Desktop\\repository";
		
		File[] array = getFile(pngPath);
		RemoveDuplicates rd = new RemoveDuplicates();
 		for(int j=0 ; j<array.length-1 ; j++){
 			for(int i=j+1; i<array.length ; i++){
 				
 				String key1 = array[j].getPath().substring(array[j].getPath().indexOf("repository")+11,array[j].getPath().length());
				key1 = key1.substring(0,1);
				String key2 = array[i].getPath().substring(array[i].getPath().indexOf("repository")+11,array[i].getPath().length());
				key2 = key2.substring(0,1);
				
				if(key1.equals(key2)){
					boolean flag = rd.isDuplicatesImg(array[i].getPath(), array[j].getPath());
	 				if(flag){
	 					System.out.println(array[j].getPath()+ " -and- " + array[i].getPath() + "  ---YSE");
	 					File delGrayfile = new File(array[j].getPath());
	 					if (delGrayfile.exists()) {
	 						delGrayfile.delete();
	 					}
	 					break;
	 				}else{
	 					System.out.println(array[j].getPath()+ " -and- " + array[i].getPath() + "  ---NO");
	 				}
				}
 			}
		}
	}
	
	/**
	 * read files from forder
	 * @param path
	 * @return
	 */
	public static  File[] getFile(String path){   
        // get file list where the path has   
        File file = new File(path);   
        // get the folder list   
        File[] array = file.listFiles();   
          
        return array;
    }
}
