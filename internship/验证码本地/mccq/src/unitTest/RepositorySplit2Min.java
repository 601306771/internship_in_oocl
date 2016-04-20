package unitTest;

import java.io.File;

import b2b.robot.image.split.ImageSplitService;
import b2b.robot.match.scan.ImageScanMatchService;

public class RepositorySplit2Min {
	public static void main(String[] args) {
		
		ImageSplitService is= new ImageSplitService();
		String pngPath = "C:\\Users\\LINI8\\Desktop\\MCCQrepositoryLarge";
		File[] array = getFile(pngPath);
		for (int j = 0; j < array.length; j++) {
			is.splitSingleChart2MinSize(array[j].getPath());
		}
	}
	
	
	/**
	 * get Files from folder
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
