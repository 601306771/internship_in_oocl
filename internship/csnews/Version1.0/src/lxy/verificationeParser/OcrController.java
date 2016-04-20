package lxy.verificationeParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class OcrController {
	public static boolean DEL_GRAY = true;
	public static boolean DEL_SPLIT = true;
	public static boolean SINGLE_CHARS = true;
	
	
	public List<String> getCharImgs(String pngPath ) {
		OcrService img = new OcrService();

		
		String gray;
		if("gray".equals(img.judgeImgType(pngPath))){
			gray = img.blackImg2Gray(pngPath);
		}else{
			gray = img.img2Gray(pngPath);
		}
		
		String split = img.split(gray);//閫昏緫涓婂垎鍓�
		
		List<String> singleChars  = img.split2Images(split);//鐗╃悊涓婂垎鍓叉垚鍗曠嫭瀛楃鐨勫浘鐗�
		
		List<String> charImgs = new ArrayList<String>();
		for(int i = 0; i<singleChars.size(); i++){//鍑哄幓瀛楃涓啑浣欑殑閮ㄥ垎
			String ifile = img.split2MinSize(singleChars.get(i));
			charImgs.add(ifile);
		}
	
		delImgs(gray,split);//鍒犻櫎涓存椂鏂囦欢
		return charImgs;
	}
	
	
	/**
	 * 鍒犻櫎涓存椂鏂囦欢
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
