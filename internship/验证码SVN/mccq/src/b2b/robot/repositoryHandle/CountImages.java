package b2b.robot.repositoryHandle;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;


public class CountImages {
	public static void main(String[] args) {
		String folderName = "MCCQrepository";
		String pngPath = "C:\\Users\\LINI8\\Desktop\\" + folderName;
		
		File[] array = getFile(pngPath);
		Map<String,Integer> chartNums = new TreeMap<String,Integer>();
		
		for (int j = 0; j < array.length; j++) {
			//get image chart 
			String key = array[j].getPath().substring(array[j].getPath().indexOf(folderName)+folderName.length()+1,array[j].getPath().length());
			key = key.substring(0,1);
			
			if(chartNums.get(key)!=null){
				int thisChartNum = chartNums.get(key);
				chartNums.put(key, thisChartNum+1);
			}else{
				chartNums.put(key, 1);
			}
		}

		for(String key : chartNums.keySet()){
			System.out.println(key + " : " + chartNums.get(key));
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
