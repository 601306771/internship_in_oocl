package lxy.ocrHandle;

import java.io.File;
import java.util.List;

import com.cargosmart.common.pdfVersion20160308.ImageUtil;

public class OcrController {
	public static boolean DEL_GRAY = true;
	public static boolean DEL_SPLIT = false;
	public static boolean SINGLE_CHARS = true;
	
	
	public void getCharImgs(String pngPath ) {
		OcrUtil img = new OcrUtil();

		String gray = img.img2Gray(pngPath);//装换成黑白
		
		String split = img.split(gray);//逻辑上分割
		
		List<String> singleChars  = img.split2Images(split);//物理上分割成单独字符的图片
		
		for(int i = 0; i<singleChars.size(); i++){//出去字符中冗余的部分
			img.split2MinSize(singleChars.get(i));
		}
	
		delImgs(gray,split);//删除临时文件
	}
	
	
	/**
	 * 删除临时文件
	 * @param gray
	 * @param split
	 */
	public void delImgs(String gray,String split){
		if(DEL_GRAY){//delete gray img
			File delGrayfile = new File(gray);
			if (delGrayfile.exists()) {
				delGrayfile.delete();
			}
		}
		
		if(DEL_SPLIT){//delete gray img
			File delSplitfile = new File(split);
			if (delSplitfile.exists()) {
				delSplitfile.delete();
			}
		}
	}
	
}
